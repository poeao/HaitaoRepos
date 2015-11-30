package com.struts.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class FileUtil{
	private static final int BUFFER_SIZE = 16 * 1024;

	public static void uploadFile(File src, File dst) throws IOException {
		InputStream in = null;
		OutputStream out = null;
		try {
			in = new BufferedInputStream(new FileInputStream(src), BUFFER_SIZE);
			out = new BufferedOutputStream(new FileOutputStream(dst), BUFFER_SIZE);
			byte[] buffer = new byte[BUFFER_SIZE];
			while (in.read(buffer) > 0) {
				out.write(buffer);
			}
		} finally {
			if (null != in) {
				in.close();
			}
			if (null != out) {
				out.close();
			}
		}
	}

	public static String getExtention(String fileName) {
		int pos = fileName.lastIndexOf(".");
		return fileName.substring(pos);
	}

	public static void makeDir(String directory) {
		File dir = new File(directory);
		if (!dir.isDirectory()) {
			dir.mkdirs();
		}
	}

	public static String generateFileName(String fileName) throws UnsupportedEncodingException {
		DateFormat format = new SimpleDateFormat("yyMMddHHmmss");
		String formatDate = format.format(new Date());
		String extension = fileName.substring(fileName.lastIndexOf("."));
		fileName = new String(fileName.getBytes("ISO8859-1"), "UTF-8");
		return fileName + "_" + formatDate + new Random().nextInt(10000) + extension;
	}
}
