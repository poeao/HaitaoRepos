package com.longxian.test;

/**
 * 解析Excel测试类
 * 
 * @author zhangchaofeng
 * @version 1.0
 * @date Oct 18, 2011
 */
public class TestReadExcel extends ExcelProcessor {

	public TestReadExcel(String fileName) throws Exception {
		super(fileName);
	}

	@Override
	public void processRow(XRow row) {
		for (int i = 0; i < row.getCellsSize(); i++) {
			System.out.print("[" + row.getRowIndex() + ","
					+ (char) row.getCell(i).getColumnIndex() + ","
					+ row.getCell(i).getValue() + "]");
		}
		System.out.println();
	}
	
	public static void main(String[] args) throws Exception {
		TestReadExcel reader=new TestReadExcel("D:/IOT导入模板.xls");
		//reader.processByRow();//处理所有的sheet
		//reader.stop();//运行一半需要停止调用此方法，释放文件流，正常运行完毕会自动释放
		reader.processByRow(1);//处理第一个sheet，sheet索引从1开始
	}
}
