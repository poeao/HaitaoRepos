<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>My JSP 'index.jsp' starting page</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
		<script type="text/javascript">
	
	function test(){
	alert("dddddddddddd");
var arr=document.getElementsByName("a1[]");
  var len=arr.length;
  var love=new Array();
  var count=0;
  for(var i=0;i<len;i++){
   if(arr[i].checked){
    love[count]=arr[i].value;
    count++;
   }
  }
  if(love==""){
   a[n++]="* 请选择你的爱好！！"
  }
}

function  checkSelect(love){
	var cbxs = document.getElementsByName(love);
	var s=0;
	for(var i=0;i<cbxs.length;i++){
		if(cbxs[i].checked){
			s+=1;
			alert(cbxs[i].value);
			
		}
	}
	if(!cbxs[0].checked&&cbxs[0].value==""){
		alert('第一项，你现在才选了'+cbxs[0].value+'项')
	}
	//alert(cbxs[0].checked);
	//if(!cbxs[0].checked&&cbxs[0].value==""){
		//alert('第一项，你现在才选了'+cbxs[0].value+'项')
	//}
	//if (s<3){alert('请到少选择三项，你现在才选了'+s+'项');}
	//else{alert('OK');}
}

		
	</script>
	</head>

	<body>
		<form id="form1" name="form1" method="post" action="">
			<input type="checkbox" name="love" value="">
			<input type="checkbox" name="love" value="CR_Rivewer">
			<input type="checkbox" name="love" value="Manager">
			<input type="checkbox" name="love" value="dirwer">
			<input type="checkbox" name="love" value="SSR_Dirwer">
			<input type="checkbox" name="love" value="CFO">
			<input type="button" value="test" onclick="checkSelect('love');">
		</form>
		<!-- 
		1、onmouseenter：当鼠标进入选区执行代码
		2、onmouseleave：当鼠标离开选区执行代码
		3、onmousewheel：当鼠标在选区滚轮时执行代码
		32、ondeactivate：当标签内值改变时执行代码
		31、onbeforedeactivate：当标签内值改变时执行代码
		<div style="background-color: red" 
		ondeactivate="checkSelect('love2');">
		 -->
		<div style="background-color: red" 
		ondeactivate="checkSelect('love2');">
			<table>
				<tr>
					<td>篮球：<input type="checkbox" name="love2" value="篮球"></td>
					<td>足球：<input type="checkbox" name="love2" value="足球"></td>
					<td>游泳：<input type="checkbox" name="love2" value="游泳"></td>
				</tr>
			</table>
		</div>


	</body>
</html>
