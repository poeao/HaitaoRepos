<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%><%@ 
	page contentType="text/html;charset=UTF-8"%><%@ 
	page import="Xproer.*" %><%@ 
	page import="org.apache.commons.lang.*" %><%@ 
	page import="java.net.URLDecoder" %><%@ 
	page import="java.net.URLEncoder" %><%@ 
	page import="java.io.*" %><%
/*
	下载文件数据
	更新记录：
		2015-05-11 创建
*/

String fid 	= request.getParameter("fid");
if (StringUtils.isBlank(fid))
{
	return;
}

DnFile db = new DnFile();
DnFileInf inf = db.Find( Integer.parseInt(fid) );//从数据库获取文件路径
//文件不存在
if(null == inf)
{
	return;
}
File f = new File(inf.m_pathLoc);
long fileLen = f.length();
RandomAccessFile raf = new RandomAccessFile(inf.m_pathSvr,"r");
FileInputStream in = new FileInputStream(inf.m_lengthSvr);

String fileName = f.getName();//QQ.exe
fileName = URLEncoder.encode(fileName,"UTF-8");
response.setContentType("application/x-download");
response.addHeader("Content-Disposition","attachment;filename=" + fileName);
OutputStream outp = response.getOutputStream();
String range = request.getHeader("Range");
long rangePos = 0;
if(range != null)
{
	//客户端提交的字段：0-100
	String[] rs = range.split("-");
	rangePos = Long.parseLong(rs[0]);//起始位置
}
response.setHeader("Content-Range",new StringBuffer()
	.append("bytes ")
	.append(rangePos)//起始位置
	.append("-")
	.append(Long.toString(fileLen-1)).toString()//结束位置
	);
byte[] b = new byte[1024];
int i = 0;
in.skip(rangePos);//定位索引

while((i = in.read(b)) > 0)
{
    outp.write(b, 0, i);
}
outp.flush();

in.close();
outp.close();
in 	 = null;
outp = null;
%>