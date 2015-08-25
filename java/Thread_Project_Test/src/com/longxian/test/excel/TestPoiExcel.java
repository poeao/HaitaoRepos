package com.longxian.test.excel;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class TestPoiExcel {
	public static String fileToBeRead = "1253587336281.xls";

	@SuppressWarnings("unused")
	public static void main(String argv[]) {
		Connection conn = getConnection();
		PreparedStatement ps = null;
		// 创建对Excel工作簿文件的引用;
		HSSFWorkbook workbook;
		try {
			workbook = new HSSFWorkbook(new FileInputStream(fileToBeRead));
			// 创建对工作表的引用。
			// 本例是按名引用（让我们假定那张表有着缺省名"Sheet1"）
			// 也可用getSheetAt(int index)按索引引用，
			// 在Excel文档中，第一张工作表的缺省索引是0，
			// 其语句为：HSSFSheet sheet = workbook.getSheetAt(0);
			HSSFSheet sheet = workbook.getSheet("StudentCource");
			int rows = sheet.getPhysicalNumberOfRows();
			for (int r = 1; r < rows; r++) {
				// 读取左上端单元
				HSSFRow row = sheet.getRow(r);
				if (row != null) {
					// 下面写的有点死，欢迎大家提出宝贵意见
					int cells = row.getPhysicalNumberOfCells();
					HSSFCell idcell = row.getCell((short) 0);
					int id = (int) idcell.getNumericCellValue();
					HSSFCell namecell = row.getCell((short) 1);
					String name = namecell.getStringCellValue();
					HSSFCell pwdcell = row.getCell((short) 2);
					String pwd = pwdcell.getStringCellValue();
					System.out.println("ID:" + id + "/t姓名:" + name + "/t密码:"
							+ pwd);
					conn.setAutoCommit(false);
					ps = conn
							.prepareStatement("insert into user values(?,?,?)");
					ps.setInt(1, id);
					ps.setString(2, name);
					ps.setString(3, pwd);
					ps.addBatch();// 加入批处理
					// ps.executeUpdate();不能与addBatch（）同时用 }
					ps.executeBatch();
					conn.commit();
					// 下面可以将查找到的行内容用SQL语句INSERT到oracle
				}
			}
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}

	public static Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/java", "root", "123456");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
}
