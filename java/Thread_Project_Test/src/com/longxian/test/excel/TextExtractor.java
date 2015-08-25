package com.longxian.test.excel;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TextExtractor {
	
	public static void main(String[] args) {
		TextExtractor tt = new TextExtractor();
		StringBuffer sb = new StringBuffer();
		String filePath = "d:/grid-demo.sql";
		sb = tt.extract(filePath);
		System.out.println(sb.toString());
	}
	
	public StringBuffer extract(String filepath) {
		try {
			InputStream is = new FileInputStream(filepath);
			return extract(is);			
		} catch (Exception e) {

		}
		return new StringBuffer();
	}
	
	public StringBuffer extract(InputStream is) {
		StringBuffer sb = new StringBuffer();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			
			is.close();
		} catch (Exception e) {
			//e.printStackTrace();
		}
		finally
		{
			if (is != null)
			{
				try{is.close();}catch(Exception ex){}
			}
		}
		return sb;
	}
}
