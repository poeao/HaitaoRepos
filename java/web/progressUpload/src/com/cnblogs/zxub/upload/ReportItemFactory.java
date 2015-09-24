package com.cnblogs.zxub.upload;

/**
 * @author zxub 2006-7-14 下午05:19:21 用于产生及重用信息反馈对象
 */
public class ReportItemFactory
{
    public static ReportItemImpl Create(String fileName, long beginTime,
            long totalSize, long uploadSize, long uploadTime,
            ReportItemImpl reportItem)
    {
        if (reportItem == null)
        {
            return new DefaultReportItem(fileName, beginTime, totalSize,
                uploadSize, uploadTime);
        }
        else
        {
            DefaultReportItem defautItem = (DefaultReportItem) reportItem;
            defautItem.reload();
            defautItem.setFileName(fileName);
            defautItem.setBeginTime(beginTime);
            defautItem.setTotalSize(totalSize);
            defautItem.setUploadSize(uploadSize);
            defautItem.setUploadTime(uploadTime);
            return defautItem;
        }
    }
}
