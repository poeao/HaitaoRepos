package com.garavo.jacob;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

/**
 * Word应用程序类
 * 说明：
 * 作者：xudd
 * 创建时间：2014-7-3 上午午05:16:47
 * 修改时间：2014-7-3 上午午05:16:47
 */
public class Application{
	// Word应用程序本身
	private ActiveXComponent wordApp;
	
	// Word的文档集合对象
	private Documents documents;
	
	/**
	 * 构造函数
	 */
	public Application() throws Exception{
		initialize();
	}
	
	/**
	 * 应用程序初始化
	 * 
	 * 说明：
	 * 创建时间：2014-7-3 上午午05:16:47
	 */
	public void initialize() throws Exception{
		// 初始化com的线程，使用结束后要调用realease方法，见quit函数
		ComThread.InitSTA();
		
		wordApp=new ActiveXComponent("Word.Application");
		wordApp.setProperty("Visible", new Variant(false));
		
		Dispatch d=wordApp.getProperty("Documents").toDispatch();
		documents=new Documents(d);
	}
	
	/**
	 * 应用程序退出
	 * 
	 * 说明：
	 * 创建时间：2014-7-3 上午午05:16:47
	 */
	public void quit() throws Exception{
		wordApp.invoke("Quit", new Variant[]{});
		ComThread.Release();
	}
	
	/**
	 * 新建文档,并返回新建文档的句柄
	 * 
	 * 说明：
	 * @return
	 * @throws Exception
	 * 创建时间：2014-7-3 上午午05:16:47
	 */
	public Document addNewDocument() throws Exception{
		Dispatch d=Dispatch.call(documents.getInstance(),"Add").toDispatch();
		Document doc=new Document(d);
		return doc;
	}
	
	/**
	 * 得到当前选择的文字
	 * 
	 * 说明：
	 * @return
	 * @throws Exception
	 * 创建时间：2014-7-3 上午午05:16:47
	 */
	public Selection getSelection() throws Exception{
		Dispatch d=Dispatch.call(wordApp,"Selection").toDispatch();
		Selection selection=new Selection(d,wordApp);
		return selection;
	}
	
	/**
	 * 打开一个已存在的文档
	 * 
	 * 说明：
	 * @param filePathName
	 * @return
	 * @throws Exception
	 * 创建时间：2014-7-3 上午午05:16:47
	 */
	public Document openExistDocument(String filePathName) throws Exception{
		Dispatch d = Dispatch.call(documents.getInstance(), "Open", filePathName).toDispatch();
		Document doc=new Document(d);
		return doc;
	}
}