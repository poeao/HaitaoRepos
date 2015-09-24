<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<html>
<head>
<title>上传页面</title>
<script type="text/javascript" src="js/env.js"></script>
<style type="text/css">
body,tr,td { font-size: 12px;}
</style>
</head>

<body leftmargin="0" topmargin="0">
<p>上传进度显示测试</p>
<script type="text/javascript">
      Env.require("application/uploadProgress.js");
      //detailUrl="progressDetail1.jsp";
  </script>
		<form action="receive.jsp" method="POST" enctype="multipart/form-data" onsubmit="showProgress()">
			<input type="file" name="file1" maxlength="100">
			<input type="file" name="file2" maxlength="100">
			<input type="submit" value="开始上传">
		</form>
</body>
</html>
