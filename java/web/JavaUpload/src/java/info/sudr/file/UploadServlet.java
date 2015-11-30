package info.sudr.file;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;
import javax.imageio.ImageIO;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.imgscalr.Scalr;
import org.json.JSONArray;
import org.json.JSONObject;

public class UploadServlet extends HttpServlet {

    private File fileUploadPath;

    @Override
    public void init(ServletConfig config) throws ServletException {
        fileUploadPath = new File(config.getInitParameter("upload_path"));
        super.init(config);
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     *
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("get");
        String sc = this.getServletContext().getRealPath("");
        if (request.getParameter("getfile") != null && !request.getParameter("getfile").isEmpty()) {
            File file = new File(sc+fileUploadPath,request.getParameter("getfile"));
            if (file.exists()) {
                int bytes = 0;
                ServletOutputStream op = response.getOutputStream();

                response.setContentType(getMimeType(file));
                response.setContentLength((int) file.length());
                response.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");

                byte[] bbuf = new byte[1024];
                DataInputStream in = new DataInputStream(new FileInputStream(file));

                while ((in != null) && ((bytes = in.read(bbuf)) != -1)) {
                    op.write(bbuf, 0, bytes);
                }
                in.close();
                op.flush();
                op.close();
            }
        } else if (request.getParameter("delfile") != null && !request.getParameter("delfile").isEmpty()) {
            File file = new File(sc+fileUploadPath, request.getParameter("delfile"));
            if (file.exists()) {
                file.delete(); // TODO:check and report success
            }
        } else if (request.getParameter("getthumb") != null && !request.getParameter("getthumb").isEmpty()) {
            File file = new File(sc+fileUploadPath, request.getParameter("getthumb"));
            if (file.exists()) {
                String mimetype = getMimeType(file);
                if (mimetype.endsWith("png") || mimetype.endsWith("jpeg") || mimetype.endsWith("gif")) {
                    BufferedImage im = ImageIO.read(file);
                    if (im != null) {
                        BufferedImage thumb = Scalr.resize(im, 75);
                        ByteArrayOutputStream os = new ByteArrayOutputStream();
                        if (mimetype.endsWith("png")) {
                            ImageIO.write(thumb, "PNG", os);
                            response.setContentType("image/png");
                        } else if (mimetype.endsWith("jpeg")) {
                            ImageIO.write(thumb, "jpg", os);
                            response.setContentType("image/jpeg");
                        } else {
                            ImageIO.write(thumb, "GIF", os);
                            response.setContentType("image/gif");
                        }
                        ServletOutputStream srvos = response.getOutputStream();
                        response.setContentLength(os.size());
                        response.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");
                        os.writeTo(srvos);
                        srvos.flush();
                        srvos.close();
                    }
                }
            } // TODO: check and report success
        } else {
            PrintWriter writer = response.getWriter();
            writer.write("call POST with multipart form data");
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     *
     */
    @SuppressWarnings("unchecked")
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("post------------");
        String sc = this.getServletContext().getRealPath("");
        File filePath = new File(sc + fileUploadPath);//上传文件的目标文件夹

        //确保这个请求的确是一个正确的上传文件
        if (!ServletFileUpload.isMultipartContent(request)) {
            throw new IllegalArgumentException("Request is not multipart, please 'multipart/form-data' enctype for your form.");
        }
        
        // 创建处理工具
        ServletFileUpload uploadHandler = new ServletFileUpload(new DiskFileItemFactory());
        PrintWriter writer = response.getWriter();
        response.setContentType("application/json");
        JSONArray json = new JSONArray();
        try {
            // 解析
            List<FileItem> items = uploadHandler.parseRequest(request);
            for (FileItem item : items) {
                if (!item.isFormField()) {
                    File file = new File(filePath, item.getName());
                    item.write(file);
                    JSONObject jsono = new JSONObject();
                    jsono.put("name", item.getName());
                    jsono.put("size", item.getSize());
                    jsono.put("url", "upload?getfile=" + item.getName());
                    jsono.put("thumbnail_url", "upload?getthumb=" + item.getName());
                    jsono.put("delete_url", "upload?delfile=" + item.getName());
                    jsono.put("delete_type", "GET");
                    json.put(jsono);
                }
            }
        } catch (FileUploadException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            writer.write(json.toString());
            writer.close();
        }

    }

    private String getMimeType(File file) {
        String mimetype = "";
        if (file.exists()) {
            if (getSuffix(file.getName()).equalsIgnoreCase("png")) {
                mimetype = "image/png";
            } else {
                javax.activation.MimetypesFileTypeMap mtMap = new javax.activation.MimetypesFileTypeMap();
                mimetype = mtMap.getContentType(file);
            }
        }
        System.out.println("mimetype: " + mimetype);
        return mimetype;
    }

    private String getSuffix(String filename) {
        String suffix = "";
        int pos = filename.lastIndexOf('.');
        if (pos > 0 && pos < filename.length() - 1) {
            suffix = filename.substring(pos + 1);
        }
        System.out.println("suffix: " + suffix);
        return suffix;
    }
}