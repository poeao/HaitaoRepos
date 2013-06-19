package com.me.smvc.bean;

import java.util.List;

public class PageHelper<T> {
	
	private List<T> list; //结果集
	private long record; //总记录
	private long count; //总页数
	private int page;  //当前页
	private int size; //每页记录

	public PageHelper(List<T> list, long record, int page, int size) {
		this.list = list;
		this.page = page;
		this.size = size;
		this.record = record;
		this.count = (record+size-1)/size;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public long getRecord() {
		return record;
	}

	public void setRecord(long record) {
		this.record = record;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
}
