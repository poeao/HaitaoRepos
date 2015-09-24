package com.cnblogs.zxub.upload;

/**
 * @author zxub 2006-7-14 下午05:09:37
 * 
 * 上传进度处理类
 */

import javax.servlet.http.HttpServletRequest;

public class StreamReport implements StreamReportImpl
{

    private long beginTime = 0L;
    private int reportLimitSize = 10240;
    private String currentUploadFileName = null;
    private long uploadSize = 0L;
    private long totalSize = 0L;
    private long reportTimes = 0L;
    private ReportItemManageImpl reportItemManage = null;

    public StreamReport(HttpServletRequest req)
    {
        totalSize = req.getContentLength();
        reportItemManage = new DefaultReportItemManage(req.getSession());
        reportItemManage.init();
    }

    public void init()
    {
        beginTime = System.currentTimeMillis();
    }

    public void dispose()
    {
        if (reportItemManage != null)
        {
            reportItemManage.dispose();
            reportItemManage = null;
        }
    }

    public void report(long size)
    {
        if (size != -1L)
        {
            uploadSize += size;
            long tempReportTimes = (int) (uploadSize / (long) reportLimitSize);
            if (tempReportTimes >= reportTimes + 1L)
            {
                reportTimes = tempReportTimes;
                ReportItemImpl reportItem = reportItemManage.getItem();
                reportItemManage.save(ReportItemFactory.Create(
                    currentUploadFileName, beginTime, totalSize, uploadSize,
                    System.currentTimeMillis(), reportItem));
            }
        }
    }

    public int getReportLimitSize()
    {
        return reportLimitSize;
    }

    public void setReportLimitSize(int reportLimitSize)
    {
        if (reportLimitSize < 10240)
            this.reportLimitSize = 10240;
        else
            this.reportLimitSize = reportLimitSize;
    }

    public void setCurrentUploadFileName(String currentUploadFileName)
    {
        this.currentUploadFileName = currentUploadFileName;
    }

}
