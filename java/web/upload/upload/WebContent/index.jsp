<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.upload.*"%>

<html>
<script type="text/javascript" src="scripts/env.js"></script>

<body>
	<%
	String flag=request.getParameter("flag");
	if(flag!=null){
		FileUtils file=new FileUtils();
		file.saveFileUpload(request,"d://upload//");
	}
	%>
	
  	<script type="text/javascript">
  			Env.require("application/uploadProgress.js"); 
  	</script> 																						<!-- 参数解释：显示进度条的方法路径,可上传文件的类型(如果可上传多个类型，已|分割),上传文件情况 -->
    <form action="index.jsp?flag=upload" enctype="multipart/form-data" method="post" onsubmit="return showProgress('showProgressDetail','.zip|.rar|.jif',document.getElementById('myfile').value)">
    	<input type="file" name="myfile" id="myfile"/><br/>
    	<input type="submit" value="上传"/>
    </form>
</body>
</html>
