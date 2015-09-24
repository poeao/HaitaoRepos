package com.upload;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProgressUtils extends HttpServlet{
	//½ø¶ÈÌõ
	public void service(ServletRequest request, ServletResponse response)
			throws ServletException, IOException {
		FileUtils fileUtils = new FileUtils();
		String detail = fileUtils.getProgressDetail((HttpServletRequest)request);
		System.out.println("detail value is " + detail);
		try {
			response.getWriter().print(detail);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
