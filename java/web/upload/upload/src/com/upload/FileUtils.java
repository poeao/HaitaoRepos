package com.upload;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CZipInputStream;
import java.util.zip.ZipEntry;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.cnblogs.zxub.upload.DefaultReportItemManage;
import com.cnblogs.zxub.upload.HttpFileUpload;
import com.cnblogs.zxub.upload.ReportItemImpl;


public class FileUtils {
	//�ϴ��ļ�
	public File saveFileUpload(HttpServletRequest request,
			String filePath) throws Exception {
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(4096);
		File tempFile = new File("");
		factory.setRepository(tempFile);
		HttpFileUpload fu = new HttpFileUpload(factory);
		fu.addAllowFileTypes("application/msword,text/plain,application/x-zip-compressed,application/vnd.ms-excel");
		fu.setSizeMax(500 * 1024 * 1024);
		fu.setHeaderEncoding("utf-8");
		File resFile = new File(filePath);
		try {

			List fileItemList = fu.parseRequest(request);
			Iterator fileItemListIte = fileItemList.iterator();
			while (fileItemListIte.hasNext()) {
				FileItem file = (FileItem) fileItemListIte.next();
				String fileName = file.getName();
				if (null != fileName && !"".equals(fileName)) {
					int b = fileName.lastIndexOf("\\");
					int e = fileName.length();
					if (b != -1) {
						fileName = fileName.substring(b, e);
					}
					if (!resFile.exists()) {
						resFile.mkdirs();
					}
					SimpleDateFormat format=new SimpleDateFormat("yyyyMMddHHmmss");
					String fileType=fileName.substring(fileName.lastIndexOf("."));;
					fileName=format.format(new Date())+fileType;
					resFile = new File(filePath + fileName);
					file.write(resFile);
				} else {

				}
			}
		} catch (Exception e) {

			throw e;
		} finally {
			fu.dispose();
		}
		return resFile;
	}
	
	//�����ļ�
	public void copyFile(java.io.File from, java.io.File to) throws FileNotFoundException, IOException {
		
		FileInputStream fis = null;
		FileOutputStream fos = null;
		try {
			fis = new FileInputStream(from);
			fos = new FileOutputStream(to);
			int tmpByte = fis.read();
			while(tmpByte != -1) {
				fos.write(tmpByte);
				tmpByte = fis.read();
			}
			fos.flush();
		}
		finally {
			if (fis != null)
				fis.close();
			if (fos != null)
				fos.close();
		}
	}
	
	//��������Ϣ
	public  String getProgressDetail(HttpServletRequest request){
		HttpSession ses = request.getSession(false);
		ReportItemImpl reportItem = DefaultReportItemManage.getItem(ses);
		if (reportItem != null) {
			return reportItem.getCompletePercent() + "||"
			+ reportItem.getFileName() + "||"//�ļ��ϴ�����
			+ reportItem.getUploadSpeedKB() + "||"//�ϴ��ٶ�
			+ reportItem.getUploadSizeMKB() + "||"//���ϴ�
			+ reportItem.getTotalSizeMKB() + "||"//�ϴ��ļ���С
			+ reportItem.getRemainTimeHMS() + "||"//ʣ��ʱ��
			+ reportItem.getTotalTimeHMS();//������ʱ��
		}else{
			return null;
		}
	}
}

