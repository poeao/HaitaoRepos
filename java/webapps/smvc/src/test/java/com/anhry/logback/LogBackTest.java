package com.anhry.logback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogBackTest {

	static final Logger LOG = LoggerFactory.getLogger(LogBackTest.class);
	
	public static void main(String[] args) {
		LOG.trace("Hello World!");
		LOG.debug("How are you today?");
		LOG.info("I am fine.");
		LOG.warn("I love programming.");
		LOG.error("I am programming.");
		
		System.out.print("fuck=======================");
	}
}
