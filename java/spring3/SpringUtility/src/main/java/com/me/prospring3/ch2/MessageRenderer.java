package com.me.prospring3.ch2;

/**
 * 
 * @author Leo
 *
 */
public interface MessageRenderer {

	public void render();
	
	public void setMessageProvider(MessageProvider provider);
	
	public MessageProvider getMessageProvider();
	
}
