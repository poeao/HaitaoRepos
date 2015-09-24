<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" language="java"%>
<%@ page import="com.cnblogs.zxub.upload.*"%>
<%@ page import="org.apache.commons.fileupload.*"%>
<%@ page import="org.apache.commons.fileupload.disk.*"%>
<%@ page import="java.io.*"%>
<%@ page import="java.util.*"%>
<%
   DiskFileItemFactory factory = new DiskFileItemFactory();

   factory.setSizeThreshold(4096);
   factory.setRepository(new File("e:\\temp"));

   HttpFileUpload fu = new HttpFileUpload(factory);

   fu.addAllowFileTypes("text/plain,application/x-zip-compressed");

   //fu.setFileLimitSize(1024);
   //fu.setAllowField(true);

   fu.setSizeMax(150 * 1024 * 1024);

   try
   {
       List fileItemList = fu.parseRequest(request);
       out.println(fileItemList.size());
       Iterator fileItemListIte = fileItemList.iterator();
       while (fileItemListIte.hasNext())
       {
           FileItem file = (FileItem) fileItemListIte.next();
           out.println(file.getName() + "<br>" + file.getSize());
       }
       out.println("上传成功！");
   }
   catch (Exception e)
   {
       out.println("上传失败<br>");
       out.println(e.getMessage());
       if (e instanceof HttpFileUpload.InvalidFileUploadException)
       {
           out.println("<p>以下文件不被允许</p>");
           Iterator unAllowFileS = ((HttpFileUpload.InvalidFileUploadException) e)
               .getInvalidFileList().iterator();
           while (unAllowFileS.hasNext())
           {
               out.println((String) unAllowFileS.next() + "<br>");
           }           
       }

   }
   finally
   {
       Thread.sleep(1000);
       fu.dispose();
   }
%>