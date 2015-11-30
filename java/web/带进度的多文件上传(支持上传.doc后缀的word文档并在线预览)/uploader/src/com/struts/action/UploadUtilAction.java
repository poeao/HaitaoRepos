package com.struts.action;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.struts.util.DeleteFileUtil;
import com.struts.util.FileViewer;
import com.struts.util.WordToHtml;

@SuppressWarnings("serial")
public class UploadUtilAction extends ActionSupport{
	private File fileupload; // 和JSP中input标记name同名

	// Struts2拦截器获得的文件名,命名规则，File的名字+FileName
	// 如此处为 'fileupload' + 'FileName' = 'fileuploadFileName'
	private String fileuploadFileName; // 上传来的文件的名字

	private List<String> picFileList;//所有图片文件
	
	public File getFileupload() {
		return fileupload;
	}

	public void setFileupload(File fileupload) {
		this.fileupload = fileupload;
	}

	public String getFileuploadFileName() {
		return fileuploadFileName;
	}

	public void setFileuploadFileName(String fileuploadFileName) {
		this.fileuploadFileName = fileuploadFileName;
	}
	public List<String> getPicFileList() {
    	return picFileList;
    }
	public void setPicFileList(List<String> picFileList) {
    	this.picFileList = picFileList;
    }

	public String uploadFile() throws Exception {

		String extName = ""; // 保存文件拓展名
		String newFileName = ""; // 保存新的文件名
		String nowTimeStr = ""; // 保存当前时间
		SimpleDateFormat sDateFormat;
		Random r = new Random();

		String savePath = ServletActionContext.getServletContext().getRealPath(""); // 获取项目根路径
		savePath = savePath + "/upload/"; // 拼串组成要上传保存文件的路径
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();
		response.setCharacterEncoding("utf-8"); // 务必，防止返回文件名是乱码

		// 生成随机文件名：当前年月日时分秒+五位随机数（为了在实际项目中防止文件同名而进行的处理）
		int rannum = (int) (r.nextDouble() * (99999 - 10000 + 1)) + 10000; // 获取随机数
		sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss"); // 时间格式化的格式
		nowTimeStr = sDateFormat.format(new Date()); // 当前时间

		// 获取拓展名
		if (fileuploadFileName.lastIndexOf(".") >= 0) {
			extName = fileuploadFileName.substring(fileuploadFileName.lastIndexOf("."));
		}

		newFileName = nowTimeStr + rannum + extName; // 文件重命名后的名字
		fileupload.renameTo(new File(savePath + newFileName)); // 保存文件

		String wordFileName = null;
		try {
			if (extName != null && ".doc".equalsIgnoreCase(extName)) {
				wordFileName = WordToHtml.WordConverterHtml(savePath, newFileName);
				// 向页面端返回结果信息
				response.getWriter().print(this.fileuploadFileName + " 上传成功！<a href=\"http://users.zeroteam.net" + request.getContextPath() + wordFileName + "\" target=\"_blank\" class=\"orange btn\">预览文档</a>");
		        DeleteFileUtil.deleteFile(savePath + newFileName);
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		if ((extName != null) && (!".doc".equalsIgnoreCase(extName))) {
			// 向页面端返回结果信息
		      response.getWriter().print(this.fileuploadFileName + " 上传成功！<a href=\"http://users.zeroteam.net" + request.getContextPath()+ "/upload/" + newFileName + "\" target=\"_blank\" class=\"green btn\">预览图片</a>");
		    }
		return null; // 这里不需要页面转向，所以返回空就可以了
	}
	
	public String getAllPicFile() throws Exception {
		String savePath = ServletActionContext.getServletContext().getRealPath(""); // 获取项目根路径
		savePath = savePath + "/upload"; // 拼串组成要上传保存文件的路径
		picFileList = FileViewer.getListFiles(savePath.replace("\\", "/"), "", false);
		return SUCCESS;
	}
}
