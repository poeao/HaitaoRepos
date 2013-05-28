<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
 <head>
  <%@ include file="/common/taglib.jsp"%>
  <title>Welcome to OA</title>
  <link href="${ctx}/css/login.css" rel="stylesheet">
 </head>

 <body>
  <div id="container">
	<div class="center login-header"><h2>欢迎使用本办公系统</h2></div>
  </div>
  <div style="width:100%;">
	<div class="well center login-box">
		<div class="alert alert-info">
			请使用您的用户名和密码，进行登录。
		</div>

		<form class="form-horizontal" action="" method="post">
						<fieldset>
							<div class="input-prepend" title="用户名" data-rel="tooltip">
								<span class="add-on"><i class="icon-user"></i></span><input autofocus class="input-large span10" name="username" id="username" type="text"/>
							</div>
							<div class="clearfix"></div>

							<div class="input-prepend" title="密码" data-rel="tooltip">
								<span class="add-on"><i class="icon-lock"></i></span><input class="input-large span10" name="password" id="password" type="password"/>
							</div>
							<div class="clearfix"></div>

							<div class="input-prepend">
							<label class="remember" for="remember"><input type="checkbox" id="remember" />记住我</label>
							</div>
							<div class="clearfix"></div>

							<p class="center span5">
							<button type="submit" class="btn">登录</button>
							</p>
						</fieldset>
					</form>
	</div>
  </div>
 </body>
</html>
