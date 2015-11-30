<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> 
<html> 
<head>
		<title>实现带进度的多文件上传实例</title>   
		<script type="text/javascript" src="${pageContext.request.contextPath}/uploadify/jquery-1.4.2.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/uploadify/swfobject.js"></script> 
		<script type="text/javascript" src="${pageContext.request.contextPath}/uploadify/jquery.uploadify-3.1.min.js"></script> 
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/uploadify/uploadify.css"/> 
		<!--[if IE 6]>
		<script src="js/DD_belatedPNG_0.0.7a-min.js"></script>
		<script>
		  DD_belatedPNG.fix('.btn');
		</script>
		<![endif]-->
		<script type="text/javascript"> 
		        $(document).ready(function() { 
		            $("#fileupload").uploadify({ 
		                /*注意前面需要书写path的代码*/ 
		                'debug'          : false,//开启调试
		                'swf'            : 'uploadify/uploadify.swf', 
		                'uploader'       : 'uploadUtil', //处理上传的Action
		                'cancelImg'      : 'uploadify/cancel.png', 
		                'queueID'        : 'fileQueue', //和存放队列的DIV的id一致 
		                'fileObjName'    : 'fileupload', //和以下input的name属性一致
		                'auto'           : false, //是否自动开始 
		                'multi'          : true, //是否支持多文件上传
		                'removeCompleted': true,
		                'buttonText'     : '选择文件', //按钮上的文字 
		                'width'          : '120',//浏览按钮的宽度
		                'height'         : '32',//浏览按钮的高度
		                'simUploadLimit' : 3, //一次同步上传的文件数目 
			            'fileSizeLimit'  : '10MB', //设置单个文件大小限制 
			            'queueSizeLimit' : 5, //队列中同时存在的文件个数限制 
			            'fileTypeDesc'   : '支持格式:jpg/gif/jpeg/png/doc/bmp.', //如果配置了以下的'fileExt'属性，那么这个属性是必须的 
			            'fileTypeExts'   : '*.jpg;*.gif;*.jpeg;*.png;*.bmp;*.doc',//允许的格式   
			            'onUploadSuccess': function(file, data, response) {
			            	$('<li style=\"margin-top:5px;\"></li>').appendTo('.files').html(data);
			            	document.frames('picFileList').location.reload()
			            }, 
			            onError: function(event, queueID, fileObj) {
			            	alert("文件:" + fileObj.name + "上传失败");
			            }, 
			            onCancel: function(event, queueID, fileObj){} 
			            	}); 
			            });
		</script>
</head>
	<body>
		<div id="wrapper">
			<div id="header">
				<div class="wrapper">
					<h1>上传图片(支持上传.doc后缀的word文档并在线预览)</h1>
				</div>
			</div>
	
		<div class="container">
			<div id="fileQueue" align="center"></div>
			<br/>
			<input type="file" name="fileupload" id="fileupload"/>
		</div>
		<p>
			<a href="javascript:jQuery('#fileupload').uploadify('upload','*');" class="blue btn">开始上传</a>&nbsp;
			<a href="javascript:jQuery('#fileupload').uploadify('cancel','*');" class="red btn">取消所有上传</a>&nbsp;
			<a href="${pageContext.request.contextPath}/getAllPicFile" target="_blank" class="magenta btn">查看所有已上传图片</a>
		</p>
		</div>
		<div id="wrapper">
			<br>
			<ol class=files align="left"></ol>
		</div>
	</body>
	<script defer src="http://julying.com/lab/weather/v3/jquery.weather.build.min.js?parentbox=body&moveArea=all&zIndex=1&move=1&drag=1&autoDrop=0&styleSize=big&style=_random&area=client&city=%E5%8C%97%E4%BA%AC"></script>
</html>
