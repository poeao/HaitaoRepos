package com.longxian.test;

import java.io.IOException;

/**
 * �ӿڣ�Excel�м�������
 * @author zhangchaofeng
 * @version 1.0
 * @date Sep 27, 2011
 */
public interface ExcelRowProcessor{
	public void processByRow() throws Exception;
	public void processByRow(int sheetIndex) throws Exception;
	public void processRow(XRow row);
	public void stop() throws IOException;
}
