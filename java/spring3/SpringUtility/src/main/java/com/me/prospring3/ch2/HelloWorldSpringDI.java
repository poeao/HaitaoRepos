/**
 * 
 */
package com.me.prospring3.ch2;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 
 * @author Leo
 *
 */
public class HelloWorldSpringDI {

	public static void main(String[] args) {

		// Initialize Spring application context
		ApplicationContext ctx = new ClassPathXmlApplicationContext("META-INF/spring/app-context.xml");
		
		MessageRenderer mr = ctx.getBean("renderer", MessageRenderer.class);
		mr.render();
		
	}

}
