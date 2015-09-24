package com.longxian.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;
import com.longxian.test.StringUtil;
/**
 * Excel-2007行级处理器，ExcelRowProcessor的实现类
 * @author zhangchaofeng
 * @version 1.0
 * @date Sep 28, 2011
 */
public abstract class Excel2007RowProcessor implements ExcelRowProcessor {
	private MyHander hander;
	private OPCPackage pkg;
	private int sheetIndex = -1;
	private List<String> rowlist = new ArrayList<String>();
	private int curRow = 0;
	private int curCol = 0;
	private String fileName;
	
	/**
	 * 构造Excel-2007行级解析器
	 * @param filename
	 * @throws IOException
	 */
	public Excel2007RowProcessor(String filename) throws Exception{
		if(filename.endsWith(".xls")){
			throw new Exception("Excel板式与解析器不匹配，解析器仅支持Excel-2007及以上版本。");
		}
		this.fileName=filename;
		this.hander=new MyHander();
	}

	/**
	 * 辅助实现方法，xml解析
	 * @param sst
	 * @return
	 * @throws SAXException
	 */
	private XMLReader fetchSheetParser(SharedStringsTable sst)
			throws SAXException {
		XMLReader parser = XMLReaderFactory
				.createXMLReader("org.apache.xerces.parsers.SAXParser");
		hander.setSst(sst);
		parser.setContentHandler(hander);
		return parser;
	}

	/**
	 * 处理所有sheet
	 */
	public void processByRow() throws Exception {
		curRow = 0;
		pkg = OPCPackage.open(fileName);
		XSSFReader r = new XSSFReader(pkg);
		SharedStringsTable sst = r.getSharedStringsTable();
		XMLReader parser = fetchSheetParser(sst);
		Iterator<InputStream> sheets = r.getSheetsData();
		while (sheets.hasNext()) {
			curRow = 0;
			sheetIndex++;
			InputStream sheet = sheets.next();
			InputSource sheetSource = new InputSource(sheet);
			parser.parse(sheetSource);
			sheet.close();
		}
		
	}

	/**
	 * 处理指定索引的sheet
	 */
	public void processByRow(int optSheetIndex) throws Exception {
		curRow = 0;
		pkg = OPCPackage.open(fileName);
		XSSFReader r = new XSSFReader(pkg);
		SharedStringsTable sst = r.getSharedStringsTable();

		XMLReader parser = fetchSheetParser(sst);

		// rId2 found by processing the Workbook
		// 根据 rId# 或 rSheet# 查找sheet
		InputStream sheet = r.getSheet("rId" + optSheetIndex);
		sheetIndex++;
		InputSource sheetSource = new InputSource(sheet);
		parser.parse(sheetSource);
		sheet.close();
	}
	
	
	public void stop() throws IOException {
		if(pkg!=null){
			pkg.close();
		}
	}

	/**
	 * 处理行数据的策略
	 */
	public abstract void processRow(XRow row);

	/**
	 * 辅助实现类，解析excel元素的句柄
	 * @author zhangchaofeng
	 * @version 1.0
	 * @date Sep 28, 2011
	 */
	private class MyHander extends DefaultHandler{
		private SharedStringsTable sst;
		private String lastContents;
		private boolean nextIsString;
		private boolean closeV=false;;

		public void setSst(SharedStringsTable sst) {
			this.sst = sst;
		}

		@Override
		public void characters(char[] ch, int start, int length)
				throws SAXException {
			// 得到单元格内容的值
			lastContents += new String(ch, start, length);
		}

		@Override
		public void endElement(String uri, String localName, String name)
				throws SAXException {
			// 根据SST的索引值的到单元格的真正要存储的字符串
			// 这时characters()方法可能会被调用多次
			if (nextIsString) {
				try {
					int idx = Integer.parseInt(lastContents);
					lastContents = new XSSFRichTextString(sst.getEntryAt(idx)).toString();
				} catch (Exception e) {
					//e.printStackTrace();
				}
			}
			// v => 单元格的值，如果单元格是字符串则v标签的值为该字符串在SST中的索引
			// 将单元格内容加入rowlist中，在这之前先去掉字符串前后的空白符
			if (name.equals("v")) {
				String value = lastContents.trim();
				value = value.equals("") ? " " : value;
				rowlist.add(curCol, value);
				curCol++;
				closeV=true;
			} else {
				if(name.equals("c")){
					if(!closeV){
						rowlist.add(curCol, "");
						curCol++;
					}
				}
				// 如果标签名称为 row ，这说明已到行尾，调用 optRows() 方法
				if (name.equals("row")) {
					//System.out.println(rowlist.size());
					XRow row=new XRow();
					for(int i=0;i<rowlist.size();i++){
						XCell cell=new XCell();
						cell.setColumnIndex(i+'A');
						cell.setRowIndex(curRow+1);
						cell.setValue((String)rowlist.get(i));
						row.setRowIndex(curRow+1);
						row.addCell(cell);
					}
					//optRows(sheetIndex, curRow, rowlist);
					if(!isBlankRow(row)){
						processRow(row);
					}
					rowlist.clear();
					curRow++;
					curCol = 0;
				}
			}
		}
		
		private boolean isBlankRow(XRow row){
			boolean b=true;
			for(int i=0;i<row.getCellsSize();i++){
				XCell cell=row.getCell(i);
				if(StringUtil.hasValue(cell.getValue())){
					b=false;
				}
			}
			return b;
		}

		@Override
		public void startElement(String uri, String localName, String name,
				Attributes attributes) throws SAXException {
			// c => 单元格
			if (name.equals("c")) {
				// 如果下一个元素是 SST 的索引，则将nextIsString标记为true
				String cellType = attributes.getValue("t");
				if (cellType != null && cellType.equals("s")) {
					nextIsString = true;
				} else {
					nextIsString = false;
				}
				closeV=false;
			}
			// 置空
			lastContents = "";
		}
		
	}
}
