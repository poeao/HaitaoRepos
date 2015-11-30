<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>所有已上传的文件</title>
    <link type="text/css" href="${pageContext.request.contextPath}/uploadify/uploadify.css" rel="stylesheet"  />
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jQuery.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.clouds.albums.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/highslide/highslide.js"></script>
	<style type="text/css">
	 .contents{ float:left; width:720px;}
	 .contents h1{ width:720px; height:83px; font-size:24px; line-height:80px; background:url(${pageContext.request.contextPath}/images/portfolio.png) no-repeat; padding:0 0 0 30px; }
	 .content{ width:712px; margin:0 auto;}
	 .listImg{ background:#eeeaeb;}
	 .listImg li{ float:left;border:#000 solid 1px; background:#CCC; overflow:hidden;}
	 .listImg h2{ margin:0 auto; height:60px; position:relative; background-color:#eeeaeb;}
	 .listImg span{ display:block; width:720px; padding:5px 0 0 50px;  height:55px; position:absolute; top:0; left:-30px; font-size:16px; color:#FFF;}
	 .listSmall{ padding:30px 0 ;}
	 .listSmall ul{ width:800px; margin:0 auto;}
	 .listSmall li{ width:100px; height:100px; }
	 .listSmall h2 span{ background:url(${pageContext.request.contextPath}/images/listSingleTitle.png);}
     .moveShow{position: relative; top:0; left:0;overflow: hidden;}
	 .moveShowBox { position: absolute; top:0; left:0; }
	</style>
	<!--[if IE 6]>
		<script src="${pageContext.request.contextPath}/js/DD_belatedPNG_0.0.7a-min.js"></script>
		<script>
		  DD_belatedPNG.fix('.btn');
		</script>
	<![endif]-->
	<script type="text/javascript">
		hs.graphicsDir = 'js/highslide/graphics/';
		hs.wrapperClassName = 'wide-border';
		hs.fadeInOut = true;
		$(function(){
		     $('#listSmall li').synchroMove({complete:completeDo}) ;
		});
		function completeDo(){
		     $('#listSmall a').click(function(){
		        hs.expand(this);
		        return false;
		     });
		};
	</script>
  </head>
  
  <body>
  <div id="wrapper">
  <div class="contents">
  <h1 class="png"><span>所有已上传图片列表</span></h1>
  <div class="content">
  <div id="listSmall" class="listImg listSmall">
    <h2><span class="png">所有已上传图片</span></h2>
	<ul>
	    <c:forEach var="picFile" items="${picFileList}">
		 	 <li>
			 	 <a href="${pageContext.request.contextPath}/upload/${picFile }">
			 	 	<img src="${pageContext.request.contextPath}/upload/${picFile }" width="420" height="414">
			 	 </a>
		 	 </li>
	    </c:forEach>
	    <div class="clear"></div>
    </ul>
   </div>
   </div>
  </body>
  <script defer src="http://julying.com/lab/weather/v3/jquery.weather.build.min.js?parentbox=body&moveArea=all&zIndex=1&move=1&drag=1&autoDrop=0&styleSize=big&style=_random&area=client&city=%E5%8C%97%E4%BA%AC"></script>
</html>
