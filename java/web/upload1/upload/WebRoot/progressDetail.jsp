<%@ page  contentType="text/html; charset=utf-8" language="java"%>
<%@ page import="com.cnblogs.zxub.upload.*"%>
<%
	HttpSession ses=request.getSession(false);
	ReportItemImpl reportItem=DefaultReportItemManage.getItem(ses);
	if (reportItem!=null){
		out.println(reportItem.getCompletePercent()+"||"
					+reportItem.getFileName()+"||"
					+reportItem.getUploadSpeedKB()+"||"
					+reportItem.getUploadSizeMKB()+"||"
					+reportItem.getTotalSizeMKB()+"||"
					+reportItem.getRemainTimeHMS()+"||"
					+reportItem.getTotalTimeHMS()); 		
	}
	else
	{
		out.println("测试");
	}
%>
