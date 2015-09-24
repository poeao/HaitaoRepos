package com.longxian.test;

import java.util.ArrayList;
import java.util.List;
/**
 * Excel行对象封装
 * @author zhangchaofeng
 * @version 1.0
 * @date Sep 27, 2011
 */
public class XRow {
	private int rowIndex;
	private List<XCell> cells=new ArrayList<XCell>();
	
	public int getRowIndex() {
		return rowIndex;
	}
	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}
	public int getCellsSize() {
		return cells.size();
	}
	public XRow addCell(XCell cell){
		this.cells.add(cell);
		return this;
	}
	public XCell getCell(int cellIndex){
		return cells.get(cellIndex);
	}
}
