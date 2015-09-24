package com.cnblogs.zxub.upload;

/**
 * @author zxub 2006-7-14 下午05:20:09
 * 
 * 默认的信息反馈对象，实现接口中定义的必要方法
 */
public class DefaultReportItem implements ReportItemImpl
{

    private String fileName = null;
    private long beginTime = 0L;
    private long totalSize = 0L;
    private long uploadTime = 0L;
    private long uploadSize = 0L;

    public DefaultReportItem(String fileName, long beginTime, long totalSize,
            long uploadSize, long uploadTime)
    {
        this.fileName = fileName;
        this.beginTime = beginTime;
        this.totalSize = totalSize;
        this.uploadSize = uploadSize;
        this.uploadTime = uploadTime;
    }

    public void reload()
    {
        fileName = null;
        beginTime = 0L;
        totalSize = 0L;
        uploadSize = 0L;
        uploadTime = 0L;
    }

    public long getBeginTime()
    {
        return beginTime;
    }

    public void setBeginTime(long beginTime)
    {
        this.beginTime = beginTime;
    }

    public String getFileName()
    {
        return fileName.replaceAll("[^\\\\+\\\\+]+\\\\", "");
    }

    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }

    public long getTotalSize()
    {
        return totalSize;
    }

    public void setTotalSize(long totalSize)
    {
        this.totalSize = totalSize;
    }

    public long getUploadSize()
    {
        return uploadSize;
    }

    public void setUploadSize(long uploadSize)
    {
        this.uploadSize = uploadSize;
    }

    public long getUploadTime()
    {
        return uploadTime;
    }

    public void setUploadTime(long uploadTime)
    {
        this.uploadTime = uploadTime;
    }

    public int getCompletePercent()
    {
        int completePercent = (int) (((float) uploadSize / (float) totalSize) * 100F);
        return completePercent;
    }

    public float getRemainTime()
    {
        float remainTime = (float) (totalSize - uploadSize)
                / (getUploadSpeedKB() * 1000F);
        return (float) Math.round(remainTime * 100F) / 100F;
    }

    public String getRemainTimeHMS()
    {
        int remainS = (int) getRemainTime();
        int remainM = remainS / 60;
        int remainH = remainM / 60;
        remainM -= remainH * 60;
        remainS = remainS - remainH * 3600 - remainM * 60;
        return remainH + ":" + remainH + ":" + remainS;
    }

    public float getTotalSizeMKB()
    {
        float kbTotalSize = (float) totalSize / 1000000F;
        return (float) Math.round(kbTotalSize * 100F) / 100F;
    }

    public float getTotalTime()
    {
        float totalTimeS = (float) totalSize / (getUploadSpeedKB() * 1000F);
        return (float) Math.round(totalTimeS * 100F) / 100F;
    }

    public String getTotalTimeHMS()
    {
        int totalS = (int) getTotalTime();
        int totalM = totalS / 60;
        int totalH = totalM / 60;
        totalM -= totalH * 60;
        totalS = totalS - totalH * 3600 - totalM * 60;
        return totalH + ":" + totalM + ":" + totalS;
    }

    public float getUploadSizeMKB()
    {
        float kbByteTotalSize = (float) uploadSize / 1000000F;
        return (float) Math.round(kbByteTotalSize * 100F) / 100F;
    }

    public float getUploadSpeedKB()
    {
        float kbPerS = (float) uploadSize / (float) (uploadTime - beginTime);
        return (float) Math.round(kbPerS * 100F) / 100F;
    }

    public String toString(ReportItemImpl preItem)
    {
        return "file:" + getFileName() + "|completePercent:"
                + getCompletePercent() + "%|uploadSize:" + getUploadSizeMKB()
                + " kb|totalSize:" + getTotalSizeMKB() + "kb|uploadSpeed:"
                + getUploadSpeedKB() + "kb/s|remainTime:" + getRemainTime()
                + "s|totalTime:" + getTotalTime() + "s";
    }
}
