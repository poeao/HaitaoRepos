package com.longxian.test;
/**
 * Excel单元格对象封装
 * @author zhangchaofeng
 * @version 1.0
 * @date Sep 27, 2011
 */
public class XCell {
	private int rowIndex;
	private int columnIndex;
	private String value;
	
	public int getRowIndex() {
		return rowIndex;
	}
	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}
	public int getColumnIndex() {
		return columnIndex;
	}
	public void setColumnIndex(int columnIndex) {
		this.columnIndex = columnIndex;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
