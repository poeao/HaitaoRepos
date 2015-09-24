package com.cnblogs.zxub.upload;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.MultipartStream;
import org.apache.commons.fileupload.servlet.ServletRequestContext;

/**
 * @author zxub 2006-7-14 下午05:25:10 处理输入流，并提供相应信息
 */
public class HttpFileUpload extends FileUpload
{
    // 有默认参数文件
    public static ResourceBundle config = ResourceBundle.getBundle("config");

    private static final long K = 1024;
    private static final long M = 1024 * 1024;

    // 默认的单个文件大小限制，0为无限制，配置文件中存放的为String类型
    private long fileLimitSize = getByteSize(config.getString("fileLimitSize"));
    // 默认的文件上传类型限制
    private String allowFileTypes = config.getString("allowFileTypes");

    // private String allowFiles = null; // 允许上传的文件类型，null表示无限制
    private boolean allowField = false; // 是否允许处理非文件域，默认为否
    private int reportLimitSize = 10240; // 最小报告长度，默认为10K
    private StreamReport streamReport = null; // 上传进度处理对象    

    public HttpFileUpload()
    {
        super();
        setSizeMax(getByteSize(config.getString("onceMaxSize")));        
    }

    public HttpFileUpload(FileItemFactory fileItemFactory)
    {
        super(fileItemFactory);
        setSizeMax(getByteSize(config.getString("onceMaxSize")));
    }

    public long getFileLimitSize()
    {
        return fileLimitSize;
    }

    public void setFileLimitSize(long fileLimitSize)
    {
        this.fileLimitSize = fileLimitSize;
    }

    public static long getByteSize(String size)
    {
        String unit = size.substring(size.length() - 1).toUpperCase();
        String num;
        if (unit.equals("K"))
        {
            num = size.substring(0, size.length() - 1);
            return Long.parseLong(num) * HttpFileUpload.K;
        }
        else if (unit.equals("M"))
        {
            num = size.substring(0, size.length() - 1);
            return Long.parseLong(num) * HttpFileUpload.M;
        }
        else
        {
            return Long.parseLong(size);
        }
    }

    /**
     * @return [String] 允许上传的文件类型
     */
    public String getAllowFileTypes()
    {
        return allowFileTypes;
    }

    /**
     * 设置允许上传的文件类型，默认是""，无限制
     * 
     * @param allowFileTypes
     */
    public void setAllowFileTypes(String allowFileTypes)
    {
        this.allowFileTypes = allowFileTypes;
    }

    public void addAllowFileTypes(String allowFileTypes)
    {
        this.allowFileTypes += ("," + allowFileTypes);
    }

    /**
     * 设置是否允许上载除file以外的field的内容
     * 
     * @param allow
     */
    public void setAllowField(boolean allow)
    {
        allowField = allow;
    }

    /**
     * @return [boolean] 是否允许上载除file以外的field
     */
    public boolean isAllowField()
    {
        return allowField;
    }

    /**
     * 设置最小报告长度，这个参数影响报告频度，默认是10K
     * 
     * @param reportLimitSize
     */
    public void setReportLimitSize(int reportLimitSize)
    {
        this.reportLimitSize = reportLimitSize;
    }

    public int getReportLimitSize()
    {
        return reportLimitSize;
    }

    /**
     * 上传后清理工作
     */
    public void dispose()
    {
        if (streamReport != null) streamReport.dispose();
    }

    /**
     * 完成上传操作 根据allowField值决定是否接收除file以外的其它field，并检查上传的文件是否为允许的文件
     * 
     * @param request
     * @return
     * @throws FileUploadException
     * @throws IOException
     */
    public List parseRequest(HttpServletRequest request)
            throws FileUploadException
    {
        ServletRequestContext ctx = new ServletRequestContext(request);
        if (ctx == null)
        {
            throw new NullPointerException(
                "HttpFileUpload(parseRequestEx): ctx parameter");
        }
        ArrayList items = new ArrayList();
        String contentType = ctx.getContentType();
        if ((null == contentType)
                || (!contentType.toLowerCase().startsWith(MULTIPART)))
        {
            throw new InvalidContentTypeException(
                "HttpFileUpload(parseRequestEx): the request doesn't contain a "
                        + MULTIPART_FORM_DATA + " or " + MULTIPART_MIXED
                        + " stream, content type header is " + contentType);
        }
        int requestSize = ctx.getContentLength();
        if (requestSize == -1)
        {
            throw new UnknownSizeException("由于数据大小未知，上传被终止！");
        }
        long sizeMax = getSizeMax();
        if (sizeMax >= 0 && requestSize > sizeMax)
        {
            throw new SizeLimitExceededException("由于文件总大小 [ " + requestSize
                    + " ] 超出限定 [ " + sizeMax + " ]，上传被终止！", requestSize,
                sizeMax);
        }

        String charEncoding = getHeaderEncoding();
        if (charEncoding == null)
        {
            charEncoding = ctx.getCharacterEncoding();
        }

        try
        {
            byte[] boundary = getBoundary(contentType);
            if (boundary == null)
            {
                throw new FileUploadException(
                    "the request was rejected because "
                            + "no multipart boundary was found");
            }

            streamReport = new StreamReport(request);
            streamReport.init();
            streamReport.setReportLimitSize(reportLimitSize);

            InputStream input = ctx.getInputStream();
            InputSteamEx inputEx = new InputSteamEx(input, streamReport);
            MultipartStream multi = new MultipartStream(inputEx, boundary);
            multi.setHeaderEncoding(charEncoding);

            boolean nextPart = multi.skipPreamble();
            List invalidFiles = new ArrayList();
            while (nextPart)
            {
                Map headers = parseHeaders(multi.readHeaders());
                String fieldName = getFieldName(headers);
                if (fieldName != null)
                {
                    String subContentType = getHeader(headers, CONTENT_TYPE);
                    if (subContentType != null
                            && subContentType.toLowerCase().startsWith(
                                MULTIPART_MIXED))
                    {
                        // Multiple files.
                        // 由于一般传送文件时，设置的是multipart/form-data，所以这里不会进入
                        byte[] subBoundary = getBoundary(subContentType);
                        multi.setBoundary(subBoundary);
                        boolean nextSubPart = multi.skipPreamble();
                        while (nextSubPart)
                        {
                            headers = parseHeaders(multi.readHeaders());
                            if (getFileName(headers) != null)
                            {
                                FileItem item = createItem(headers, false);
                                OutputStream os = item.getOutputStream();
                                try
                                {
                                    multi.readBodyData(os);
                                }
                                finally
                                {
                                    os.close();
                                }
                                items.add(item);
                            }
                            else
                            {
                                // Ignore anything but files inside
                                // multipart/mixed.
                                multi.discardBodyData();
                            }
                            nextSubPart = multi.readBoundary();
                        }
                        multi.setBoundary(boundary);
                    }
                    else
                    {
                        // 传送文件时，设置的是multipart/form-data，所以主要部分在这里
                        String fileName = getFileName(headers);
                        FileItem item = createItem(headers, fileName == null);
                        if (item.isFormField()) // 非文件域
                        {
                            OutputStream os = item.getOutputStream();
                            try
                            {
                                multi.readBodyData(os);
                            }
                            finally
                            {
                                os.close();
                            }
                            if (this.allowField)
                            {
                                items.add(item);
                            }
                        }
                        else
                        // 文件域
                        {
                            String fileContentType = item.getContentType();
                            if (!isAllowFileType(fileContentType)) // 不属于允许上传的文件类型
                            {
                                invalidFiles.add(fileName);
                                throw new InvalidFileUploadException(
                                    "非法的文件上传类型 [ " + fileContentType + " ]",
                                    invalidFiles);
                            }
                            OutputStream os = item.getOutputStream();
                            try
                            {
                                streamReport.setCurrentUploadFileName(fileName);
                                multi.readBodyData(os);
                            }
                            finally
                            {
                                os.close();
                            }
                            long fileSize = item.getSize(); // getSize不能在前面进行
                            if (fileSize > this.fileLimitSize)
                            {
                                invalidFiles.add(getFileName(headers));
                                throw new InvalidFileUploadException("[ "
                                        + fileName + " ]超过单个文件大小限制，文件大小[ "
                                        + fileSize + " ]，限制为[ "
                                        + this.fileLimitSize + " ] ",
                                    invalidFiles);
                            }
                            items.add(item);
                        }
                    }
                }
                else
                {
                    // Skip this part.
                    multi.discardBodyData();
                }
                nextPart = multi.readBoundary();
            }
        }
        catch (IOException e)
        {
            throw new FileUploadException("Processing of "
                    + MULTIPART_FORM_DATA + " request failed. "
                    + e.getMessage());
        }
        return items;
    }

    private boolean isAllowFileType(String fileType)
    {
        if (allowFileTypes.length() > 0 && fileType.trim().length() > 0)
            return allowFileTypes.indexOf(fileType.toLowerCase()) != -1;
        else
            return true;
    }

    public class InvalidFileUploadException extends FileUploadException
    {
        private static final long serialVersionUID = 5458085280561303071L;
        private List invalidFileList;

        public List getInvalidFileList()
        {
            return invalidFileList;
        }

        public InvalidFileUploadException(List list)
        {
            invalidFileList = list;
        }

        public InvalidFileUploadException(String message, List list)
        {
            super(message);
            invalidFileList = list;
        }
    }
}
