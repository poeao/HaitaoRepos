package com.garavo.jacob;

import java.util.Random;

 

public class WordUtil {

	
	/*@param docPath word文档路径
	 *@param imgPath 水印图片路径
	 * 
	 */
	// 设置页眉，图片水印以及设置保护
	public static void setImageWaterMarkAndProtect(String docPath,String imgPath) throws Exception{
		Application app=new Application();
		
		Document doc=app.openExistDocument(docPath);
		Selection sel=app.getSelection();
		 doc.setHeaderText("NIM.AC.CN", sel.getInstance());//设置页眉
		doc.setImageWaterMark(imgPath, sel.getInstance(),160,714,78,78);
		int password =nextInt();
		doc.setProtected(""+password);
		doc.save();
		
		doc.close();
		app.quit();
	}
	
	/*
	 * 为word文档
	 * 产生随机密码
	 * 
	 */
 	public static int nextInt(){
		Random randm = new Random();
		
		return Math.abs(randm.nextInt());
	} 
	
	
	
	
	
	
}
