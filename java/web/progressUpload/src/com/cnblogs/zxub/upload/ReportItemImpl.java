package com.cnblogs.zxub.upload;

/**
 * @author zxub 2006-7-14 ����05:12:43
 * 
 * ������Ϣ����������Ҫʵ�ֵķ���
 */
public interface ReportItemImpl
{
    public abstract float getTotalSizeMKB();

    public abstract float getUploadSizeMKB();

    public abstract String getFileName();

    public abstract int getCompletePercent();

    public abstract String getTotalTimeHMS();

    public abstract String getRemainTimeHMS();

    public abstract float getUploadSpeedKB();

    public abstract void reload();
}
