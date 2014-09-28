package com.garavo.jacob;

import com.jacob.com.Dispatch;



public abstract class BaseWord{
	protected Dispatch instance;

	public BaseWord(Dispatch instance){
		this.instance=instance;
	}
	
	public Dispatch getInstance() {
		return instance;
	}

	public void setInstance(Dispatch instance) {
		this.instance = instance;
	}
}