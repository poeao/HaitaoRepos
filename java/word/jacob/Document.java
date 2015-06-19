package com.garavo.jacob;

import com.jacob.com.Dispatch;
import com.jacob.com.Variant;


/**
 * 文档对象
 * 说明：
 * 作者：xudd
 * 创建时间：2014-7-3 上午09:41:47
 * 修改时间：2014-7-3 上午09:41:47
 */
public class Document extends BaseWord{
	
	public Document(Dispatch instance) {
		super(instance);
	}

	/**
	 * 文档另存为
	 * 
	 * 说明：
	 * @param filePathName
	 * @throws Exception
	 * 创建时间：2014-7-3 上午09:41:47
	 */
	public void saveAs(String filePathName) throws Exception{
		Dispatch.call(instance, "SaveAs", filePathName);
	}
	
	public void save() throws Exception{
		Dispatch.call(instance, "Save");
	}
	
	/**
	 * 关闭文档
	 * 
	 * 说明：
	 * @throws Exception
	 * 创建时间：2014-7-3 上午09:41:47
	 */
	public void close() throws Exception{
		Dispatch.call(instance, "Close", new Variant(true));
	}
	
	/**
	 * 设置页眉的文字
	 * 
	 * 说明：
	 * @param headerText
	 * @throws Exception
	 * 创建时间：2014-7-3 上午09:41:47
	 */
	public void setHeaderText(String headerText,Dispatch selection) throws Exception{
		Dispatch activeWindow = Dispatch.get(instance, "ActiveWindow").toDispatch();
		Dispatch view = Dispatch.get(activeWindow, "View").toDispatch();
		Dispatch.put(view, "SeekView", new Variant(9)); //wdSeekCurrentPageHeader-9

		Dispatch headerFooter = Dispatch.get(selection, "HeaderFooter").toDispatch();
		Dispatch range = Dispatch.get(headerFooter, "Range").toDispatch();
		Dispatch.put(range, "Text", new Variant(headerText));
		Dispatch font = Dispatch.get(range, "Font").toDispatch();

		Dispatch.put(font, "Name", new Variant("楷体_GB2312"));
		Dispatch.put(font, "Bold", new Variant(true));
		Dispatch.put(font, "Size", 9);

		Dispatch.put(view, "SeekView", new Variant(0)); //wdSeekMainDocument-0恢复视图;
	}
	
	/**
	 * 设置图片水印
	 * 
	 * 说明：
	 * @param imagePath
	 * @param selection
	 * @throws Exception
	 * 创建时间：2014-7-3 上午09:41:47
	 */
	public void setImageWaterMark(String imagePath,Dispatch selection) throws Exception{
		Dispatch activeWindow = Dispatch.get(instance, "ActiveWindow").toDispatch();
		Dispatch view = Dispatch.get(activeWindow, "View").toDispatch();
		Dispatch.put(view, "SeekView", new Variant(9)); //wdSeekCurrentPageHeader-9

		Dispatch headerFooter = Dispatch.get(selection, "HeaderFooter").toDispatch();
		
		// 获取水印图形对象
		Dispatch shapes=Dispatch.get(headerFooter, "Shapes").toDispatch();
		
		Dispatch picture=Dispatch.call(shapes, "AddPicture",imagePath).toDispatch();
		
		Dispatch.call(picture, "Select");
		Dispatch.put(picture,"Left",new Variant(10));
		Dispatch.put(picture,"Top",new Variant(10));
		Dispatch.put(picture,"Width",new Variant(190));
		Dispatch.put(picture,"Height",new Variant(190));
		
		Dispatch.put(view, "SeekView", new Variant(0)); //wdSeekMainDocument-0恢复视图;
	}
	
	/**
	 * 设置图片水印
	 * 
	 * 说明：
	 * @param imagePath
	 * @param selection
	 * @param left
	 * @param top
	 * @param width
	 * @param height
	 * @throws Exception
	 * 创建时间：2014-7-3 上午09:41:47
	 */
	public void setImageWaterMark(String imagePath,Dispatch selection,int left,int top,int width,int height) throws Exception{
		Dispatch activeWindow = Dispatch.get(instance, "ActiveWindow").toDispatch();
		Dispatch view = Dispatch.get(activeWindow, "View").toDispatch();
		Dispatch.put(view, "SeekView", new Variant(9)); //wdSeekCurrentPageHeader-9

		Dispatch headerFooter = Dispatch.get(selection, "HeaderFooter").toDispatch();
		
		// 获取水印图形对象
		Dispatch shapes=Dispatch.get(headerFooter, "Shapes").toDispatch();
		
		Dispatch picture=Dispatch.call(shapes, "AddPicture",imagePath).toDispatch();
		
		Dispatch.call(picture, "Select");
		Dispatch.put(picture,"Left",new Variant(left));
		Dispatch.put(picture,"Top",new Variant(top));
		Dispatch.put(picture,"Width",new Variant(width));
		Dispatch.put(picture,"Height",new Variant(height));
		
		Dispatch.put(view, "SeekView", new Variant(0)); //wdSeekMainDocument-0恢复视图;
	}
	
	/**
	 * 给文档加上保护
	 * 
	 * 说明：
	 * @param pswd
	 * @throws Exception
	 * 创建时间：2014-7-3 上午09:41:47
	 */
	public void setProtected(String pswd) throws Exception{
		String protectionType = Dispatch.get(instance, "ProtectionType").toString();
		if(protectionType.equals("-1")){
			Dispatch.call(instance, "Protect", new Variant(3), new Variant(true), pswd);
		} 
	}
	
	/**
	 * 给文档解除保护
	 * 
	 * 说明：
	 * @param pswd
	 * @throws Exception
	 * 创建时间：2014-7-3 上午09:41:47
	 */
	public void releaseProtect(String pswd) throws Exception{
		String protectionType = Dispatch.get(instance, "ProtectionType").toString();
		if(protectionType.equals("3")){
			Dispatch.call(instance, "Unprotect", pswd);
		}
	}
}