package com.longxian.test;

/**
 * ����Excel������
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
		TestReadExcel reader=new TestReadExcel("D:/IOT����ģ��.xls");
		//reader.processByRow();//�������е�sheet
		//reader.stop();//����һ����Ҫֹͣ���ô˷������ͷ��ļ���������������ϻ��Զ��ͷ�
		reader.processByRow(1);//�����һ��sheet��sheet������1��ʼ
	}
}
