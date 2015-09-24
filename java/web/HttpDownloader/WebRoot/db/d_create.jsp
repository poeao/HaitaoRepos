<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%><%@ 
	page contentType="text/html;charset=UTF-8"%><%@ 
	page import="Xproer.*" %><%@ 
	page import="java.net.URLDecoder" %><%@ 
	page import="java.net.URLEncoder" %><%@ 
	page import="org.apache.commons.lang.*" %><%@ 
	page import="com.google.gson.FieldNamingPolicy" %><%@ 
	page import="com.google.gson.Gson" %><%@ 
	page import="com.google.gson.GsonBuilder" %><%@ 
	page import="com.google.gson.annotations.SerializedName" %><%@ 
	page import="java.io.*" %><%
/*
	此页面主要用来向数据库添加一条记录。
	一般在 HttpUploader.js HttpUploader_MD5_Complete(obj) 中调用
	更新记录：
		2012-05-24 完善
		2012-06-29 增加创建文件逻辑，
*/

String uid 		 = request.getParameter("uid");
String mac		 = request.getParameter("mac");
String pathLoc	 = request.getParameter("pathLoc");
String pathSvr	 = request.getParameter("pathSvr");
String lengthLoc = request.getParameter("lengthLoc");
String lengthSvr = request.getParameter("lengthSvr"); 
String callback  = request.getParameter("callback");//jsonp
pathLoc 		 = pathLoc.replaceAll("\\+","%20");
pathSvr			 = pathSvr.replaceAll("\\+","%20");
pathLoc			 = URLDecoder.decode(pathLoc,"UTF-8");//utf-8解码
pathSvr			 = URLDecoder.decode(pathSvr,"UTF-8");//utf-8解码

if (StringUtils.isBlank(uid)
	||StringUtils.isBlank(mac)
	||StringUtils.isBlank(pathLoc)
	||StringUtils.isBlank(pathSvr)
	||StringUtils.isBlank(lengthLoc)
	||StringUtils.isBlank(lengthSvr))
{
	out.write("参数为空 ");
	return;
}

DnFileInf	inf = new DnFileInf();
inf.m_uid = Integer.parseInt(uid);
inf.m_mac = mac;
inf.m_pathLoc = pathLoc;
inf.m_pathSvr = pathSvr;
inf.m_lengthLoc = lengthLoc;
inf.m_lengthSvr = lengthSvr;
DnFile db = new DnFile();
inf.m_fid =  db.Add(inf);

Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
String json = gson.toJson(inf);
json = callback + "(" + json + ")";//返回jsonp格式数据。
out.write(json);
%>