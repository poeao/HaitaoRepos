package com.longxian.test.excel;

import java.io.File;   
import java.io.FileInputStream;   
import java.sql.Connection;   
import java.sql.DriverManager;   
import java.sql.PreparedStatement;   
import java.sql.SQLException;   
  
import org.apache.poi.hssf.usermodel.HSSFCell;   
import org.apache.poi.hssf.usermodel.HSSFRow;   
import org.apache.poi.hssf.usermodel.HSSFSheet;   
import org.apache.poi.hssf.usermodel.HSSFWorkbook;   
  
public class ExcelRead {   
  
    /**  
     * @author ROC  武鹏  wupeng1003@gmail.com  
     * @since 2011年6月19日9:53:30  
     */  
  
    public static void main(String[] args) {   
  
        PreparedStatement ps = null;//是PreparedStatement，不是Statement   
        Connection con = null;   
        try {   
            Class.forName("com.mysql.jdbc.Driver");   
            String url = "jdbc:mysql://localhost:3306/xiaoyou?useUnicode=true&characterEncoding=UTF8";   
            String username = "root";   
            String password = "root";   
            con = DriverManager.getConnection(url, username, password);   
            con.setAutoCommit(false);   
            StringBuffer sql = new StringBuffer();   
            sql.append("INSERT INTO `info`");   
            sql.append("(`xueyuan`,`xingming`,`xingbie`,`nianji`,`zhuanye`,`suozaichengshi`,`gongzuodanwei`,`zhiwuzhicheng`,`youbian`,`gudingdianhua`,`yidongdianhua`,`dianziyouxiang`)");   
            sql.append("VALUES(?,?,?,?,?,?,?,?,?,?,?,?)");   
            ps = con.prepareStatement(sql.toString());   
        } catch (Exception e) {   
            e.printStackTrace();   
        }   
  
        long lll = 1;   
        for (int fileCount = 1; fileCount <= 19; fileCount++) {//循环读取19个excel文件   
            String fileName = "H:/xyl/" + fileCount + "/" + fileCount + ".xls";   
            try {   
                FileInputStream is = new FileInputStream(new File(fileName));   
                HSSFWorkbook wb = new HSSFWorkbook(is);   
                int sheetNum = wb.getNumberOfSheets();   
                for (int i = 0; i < sheetNum; i++) {//循环标签表格   
                   HSSFSheet childSheet = wb.getSheetAt(i);   
                    int rowNum = childSheet.getLastRowNum();   
                    for (int j = 2; j < rowNum; j++) {//循环行   
                        HSSFRow row = childSheet.getRow(j);   
                        int cellNum = row.getLastCellNum();   
                        for (int k = 0; k < 12; k++) {//循环列   
                            String s=new String();   
                            try {   
                                row.getCell(k).setCellType(HSSFCell.CELL_TYPE_STRING);   
                                s=row.getCell(k).toString();   
                            } catch (NullPointerException e) {   
                                s=" ";//一定要捕捉这个异常，不然会丢失数据，个别情况表格空值的情况下，不会反悔" " ，而是抛出异常   
                                //e.printStackTrace();   
                            }   
                            ps.setString(k+1, s);   
                        }   
                        ps.addBatch();//添加批处理   
                        lll++;   
                        if (lll % 5000 == 0) {   
                            System.out.println(lll/5000+"    个5000条开始插入");   
                            ps.executeBatch();   
                            con.commit();//执行批处理，并且提交   
                            ps.clearBatch();//  清楚批处理缓存，一定要有这个步骤   
                            System.out.println(lll/5000+"    个5000条已经插入");   
                        }   
                    }   
                }   
            } catch (Exception e) {   
                e.printStackTrace();   
            }   
            System.out.println(fileName + "结束");   
        }   
        try {   
            ps.close();   
            con.close();   
            System.out.println("链接关闭");   
        } catch (SQLException e) {   
            e.printStackTrace();   
        }   
    }   
}  
