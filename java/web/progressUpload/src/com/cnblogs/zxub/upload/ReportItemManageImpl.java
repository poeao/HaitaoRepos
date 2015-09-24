package com.cnblogs.zxub.upload;

/**
 * @author zxub 2006-7-14 обнГ05:11:50
 */
public interface ReportItemManageImpl
{
    public abstract void init();

    public abstract ReportItemImpl getItem();

    public abstract void save(ReportItemImpl reportitem);

    public abstract void dispose();
}
