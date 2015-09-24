package com.cnblogs.zxub.upload;

/**
 * @author zxub 2006-7-14 下午05:10:22
 * 
 * 进度处理类需要实现的接口
 */
public interface StreamReportImpl
{
    public abstract void report(long size);
}
