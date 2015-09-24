package com.longxian.test;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.poi.hssf.eventusermodel.FormatTrackingHSSFListener;
import org.apache.poi.hssf.eventusermodel.HSSFEventFactory;
import org.apache.poi.hssf.eventusermodel.HSSFListener;
import org.apache.poi.hssf.eventusermodel.HSSFRequest;
import org.apache.poi.hssf.eventusermodel.MissingRecordAwareHSSFListener;
import org.apache.poi.hssf.eventusermodel.EventWorkbookBuilder.SheetRecordCollectingListener;
import org.apache.poi.hssf.eventusermodel.dummyrecord.LastCellOfRowDummyRecord;
import org.apache.poi.hssf.eventusermodel.dummyrecord.MissingCellDummyRecord;
import org.apache.poi.hssf.model.HSSFFormulaParser;
import org.apache.poi.hssf.record.BOFRecord;
import org.apache.poi.hssf.record.BlankRecord;
import org.apache.poi.hssf.record.BoolErrRecord;
import org.apache.poi.hssf.record.BoundSheetRecord;
import org.apache.poi.hssf.record.FormulaRecord;
import org.apache.poi.hssf.record.LabelRecord;
import org.apache.poi.hssf.record.LabelSSTRecord;
import org.apache.poi.hssf.record.NoteRecord;
import org.apache.poi.hssf.record.NumberRecord;
import org.apache.poi.hssf.record.RKRecord;
import org.apache.poi.hssf.record.Record;
import org.apache.poi.hssf.record.SSTRecord;
import org.apache.poi.hssf.record.StringRecord;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

/**
 * Excel-2003行级处理器，ExcelRowProcessor的实现类
 * @author zhangchaofeng
 * @version 1.0
 * @date Sep 28, 2011
 */
public abstract class Excel2003RowProcessor implements ExcelRowProcessor{
	private MyHSSFListener hssfListener;
	private String fileName;
	private InputStream is;
	private POIFSFileSystem fs;

	private int lastRowNumber;
	private int lastColumnNumber;

	/** Should we output the formula, or the value it has? */
	private boolean outputFormulaValues = true;

	/** For parsing Formulas */
	private SheetRecordCollectingListener workbookBuildingListener;
	private HSSFWorkbook stubWorkbook;

	// Records we pick up as we process
	private SSTRecord sstRecord;
	private FormatTrackingHSSFListener formatListener;
	
	/** So we known which sheet we're on */
	private int sheetIndex = -1;
	private int optSheetIndex=-1;
	private BoundSheetRecord[] orderedBSRs;
	private ArrayList<BoundSheetRecord> boundSheetRecords = new ArrayList<BoundSheetRecord>();

	// For handling formulas with string results
	private int nextRow;
	private int nextColumn;
	private boolean outputNextStringRecord;
	private XRow row=new XRow();

	/**
	 * 构造Excel-2003行级解析器
	 * @param filename excel完整文件名
	 * @throws IOException
	 */
	public Excel2003RowProcessor(String filename) throws Exception{
		if(filename.endsWith(".xlsx")){
			throw new Exception("Excel板式与解析器不匹配，解析器仅支持Excel-2003及以下版本。");
		}
		this.fileName=filename;
		this.hssfListener=new MyHSSFListener();
	}

	/**
	 * 处理指定索引的sheet，-1则表示处理所有sheet
	 * @param optSheetIndex
	 * @throws IOException
	 */
	private void process(int optSheetIndex) throws IOException {
		this.optSheetIndex=optSheetIndex;
		MissingRecordAwareHSSFListener listener = new MissingRecordAwareHSSFListener(hssfListener);
		formatListener = new FormatTrackingHSSFListener(listener);

		HSSFEventFactory factory = new HSSFEventFactory();
		HSSFRequest request = new HSSFRequest();

		if(outputFormulaValues) {
			request.addListenerForAllRecords(formatListener);
		} else {
			workbookBuildingListener = new SheetRecordCollectingListener(formatListener);
			request.addListenerForAllRecords(workbookBuildingListener);
		}

		factory.processWorkbookEvents(request, fs);
	}

	/**
	 * 处理所有sheet
	 */
	public void processByRow() throws IOException{
		this.is=new FileInputStream(fileName);
		this.fs=new POIFSFileSystem(is);
		sheetIndex = -1;
		this.process(-1);
	}

	/**
	 * 处理指定索引的sheet
	 */
	public void processByRow(int optSheetIndex) throws Exception {
		this.is=new FileInputStream(fileName);
		this.fs=new POIFSFileSystem(is);
		sheetIndex = -1;
		if(optSheetIndex<1){
			throw new Exception("目标sheet索引至少要从1开始。");
		}
		this.process(optSheetIndex);
	}

	public void stop() throws IOException {
		if(is!=null){
			is.close();
		}
	}

	/**
	 * 处理行数据的策略
	 */
	public abstract void processRow(XRow row);
	
	/**
	 * 辅助实现类，HSSF监听器
	 * @author zhangchaofeng
	 * @version 1.0
	 * @date Sep 28, 2011
	 */
	private class MyHSSFListener implements HSSFListener{
		public void processRecord(Record record) {
			int thisRow = -1;
			int thisColumn = -1;
			String thisStr = null;
			
			switch (record.getSid()){
			case BoundSheetRecord.sid:
				boundSheetRecords.add((BoundSheetRecord)record);
				break;
			case BOFRecord.sid:
				BOFRecord br = (BOFRecord)record;
				if(br.getType() == BOFRecord.TYPE_WORKSHEET) {
					// Create sub workbook if required
					if(workbookBuildingListener != null && stubWorkbook == null) {
						stubWorkbook = workbookBuildingListener.getStubHSSFWorkbook();
					}
					
					// Output the worksheet name
					// Works by ordering the BSRs by the location of
					//  their BOFRecords, and then knowing that we
					//  process BOFRecords in byte offset order
					sheetIndex++;
					//System.out.println("锁定一个sheet："+sheetIndex+",要操作的sheet:"+optSheetIndex);
					/*if(sheetIndex!=optSheetIndex){
						System.out.println("不是要操作的sheet。");
						return;
					}*/
					if(orderedBSRs == null) {
						orderedBSRs = BoundSheetRecord.orderByBofPosition(boundSheetRecords);
					}
					
				}
				break;

			case SSTRecord.sid:
				sstRecord = (SSTRecord) record;
				break;

			case BlankRecord.sid:
				BlankRecord brec = (BlankRecord) record;

				thisRow = brec.getRow();
				thisColumn = brec.getColumn();
				thisStr = "";
				break;
			case BoolErrRecord.sid:
				BoolErrRecord berec = (BoolErrRecord) record;

				thisRow = berec.getRow();
				thisColumn = berec.getColumn();
				thisStr = "";
				break;

			case FormulaRecord.sid:
				FormulaRecord frec = (FormulaRecord) record;

				thisRow = frec.getRow();
				thisColumn = frec.getColumn();

				if(outputFormulaValues) {
					if(Double.isNaN( frec.getValue() )) {
						// Formula result is a string
						// This is stored in the next record
						outputNextStringRecord = true;
						nextRow = frec.getRow();
						nextColumn = frec.getColumn();
					} else {
						thisStr = formatListener.formatNumberDateCell(frec);
					}
				} else {
					thisStr = 
						HSSFFormulaParser.toFormulaString(stubWorkbook, frec.getParsedExpression()) ;
				}
				break;
			case StringRecord.sid:
				if(outputNextStringRecord) {
					// String for formula
					StringRecord srec = (StringRecord)record;
					thisStr = srec.getString();
					thisRow = nextRow;
					thisColumn = nextColumn;
					outputNextStringRecord = false;
				}
				break;

			case LabelRecord.sid:
				LabelRecord lrec = (LabelRecord) record;

				thisRow = lrec.getRow();
				thisColumn = lrec.getColumn();
				thisStr =   lrec.getValue() ;
				break;
			case LabelSSTRecord.sid:
				LabelSSTRecord lsrec = (LabelSSTRecord) record;

				thisRow = lsrec.getRow();
				thisColumn = lsrec.getColumn();
				if(sstRecord == null) {
					thisStr = '"' + "(No SST Record, can't identify string)" + '"';
				} else {
					thisStr = sstRecord.getString(lsrec.getSSTIndex()).toString();
				}
				break;
			case NoteRecord.sid:
				NoteRecord nrec = (NoteRecord) record;

				thisRow = nrec.getRow();
				thisColumn = nrec.getColumn();
				thisStr = '"' + "(TODO)" + '"';
				break;
			case NumberRecord.sid:
				NumberRecord numrec = (NumberRecord) record;

				thisRow = numrec.getRow();
				thisColumn = numrec.getColumn();

				// Format
				thisStr = formatListener.formatNumberDateCell(numrec);
				break;
			case RKRecord.sid:
				RKRecord rkrec = (RKRecord) record;

				thisRow = rkrec.getRow();
				thisColumn = rkrec.getColumn();
				thisStr = '"' + "(TODO)" + '"';
				break;
			default:
				break;
			}
			
			
			//如果不是要操作的sheet，跳过
			if((sheetIndex+1)!=optSheetIndex&&optSheetIndex!=-1){
				//System.out.println("不是要操作的sheet。");
				return;
			}

			// Handle new row
			if(thisRow != -1 && thisRow != lastRowNumber) {
				lastColumnNumber = -1;
			}

			// Handle missing column
			if(record instanceof MissingCellDummyRecord) {
				MissingCellDummyRecord mc = (MissingCellDummyRecord)record;
				thisRow = mc.getRow();
				thisColumn = mc.getColumn();
				thisStr = "";
			}
			
			// If we got something to print out, do so
			if(thisStr != null) {
				if(thisColumn > 0) {
					//output.print(',');
				}
				row.setRowIndex(thisRow+1);
				XCell cell=new XCell();
				cell.setRowIndex(thisRow+1);
				cell.setColumnIndex(thisColumn+'A');
				cell.setValue(thisStr);
				row.addCell(cell);
				//output.print(thisStr);
			}

			// Update column and row count
			if(thisRow > -1)
				lastRowNumber = thisRow;
			if(thisColumn > -1)
				lastColumnNumber = thisColumn;

			// Handle end of row
			if(record instanceof LastCellOfRowDummyRecord) {
				// Print out any missing commas if needed
				if(lastColumnNumber == -1) { lastColumnNumber = 0; }

				// We're onto a new row
				lastColumnNumber = -1;

				// End the row
				if(!isBlankRow(row)){
					processRow(row);
				}
				row=new XRow();
				//output.println();
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
	}
}
