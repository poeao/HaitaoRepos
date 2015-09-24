package Xproer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.File;

/**
 * 
 */

/**
 * 配置文件，主要设置文件上传路径。
 *
 */
public class UploaderCfg {

	public String m_uploadPath="";		//上传路径。d:\\www\\upload\\201204\\09\\
	public String m_uploadFolder="";	//上传文件夹。d:\\www\\upload\\
	public String m_relatPath="";		//相对路径。/upload/201204/09/
	public String m_domain="";			//网站域名。
	public String m_curFile="";		//
	public String m_curFolder="";		//当前路径 D:\\Tomcat 6.0\\webapps\\HttpUploader3\\	
		
	/*
	 * 构造函数
	 * 在JSP页面中传入 this.getServletContext()
	*/
	public UploaderCfg()
	{
		this.m_curFolder = this.ProjectPath();
		//F:\\www\\upload\\
		this.m_uploadFolder = this.m_curFolder + ("upload/");
	}
	
	/**
	 * @return 返回 WEB-INF 文件夹路径。示例：D:/ProjectName/WEB-INF/
	 */
	public String WebInfPath()
	{
		String path = new String("");
		//前面会多返回一个"/", 例如  /D:/test/WEB-INF/, 奇怪, 所以用 substring()
		path = this.getClass().getResource("/").getPath().substring(1).replaceAll("//", "/");
		if (path != null && !path.endsWith("/"))
		{
			path.concat("/");    //避免 WebLogic 和 WebSphere 不一致
		}
		path = path.replace("classes/", "");
		return (path);
	}
	
	/**
	 * @return	返回当前工程目录路径。示例：D:/HttpUploader6/
	 */
	public String ProjectPath()
	{
		String path = this.WebInfPath();
		path = path.replace("WEB-INF/","");
		return path;
	}

	// 创建上传路径
	// d:\\www\\upload\\2012\\04\\10	
	public void CreateUploadPath()
	{
		SimpleDateFormat fmtDD = new SimpleDateFormat("dd");
		SimpleDateFormat fmtMM = new SimpleDateFormat("MM");
		SimpleDateFormat fmtYY = new SimpleDateFormat("yyyy");
		
		Date date = new Date();
		String strDD = fmtDD.format(date);
		String strMM = fmtMM.format(date);
		String strYY = fmtYY.format(date);

		//DateTime timeCur = DateTime.Now;
		String timeStr = strYY+"/"+strMM+"/"+strDD;
		this.m_relatPath = strYY + "/" + strMM + "/" + strDD + "/";
		this.m_uploadPath = this.m_uploadFolder + timeStr + "/";
		
		File folder = new File(this.m_uploadPath);
		if(!folder.exists()) folder.mkdirs();
	}

	/**
	 * 获取上传路径
	 * @return d:\\www\\upload\\201204\\10\\
	 */
	public String GetUploadPath()
	{
		if(this.m_uploadPath.isEmpty())
		{
			this.CreateUploadPath();
		}
		return this.m_uploadPath;
	}

	/**
	 * 获取相对路径
	 * @return /upload/201204/10/
	 */
	public String GetRelatPath()
	{
		return this.m_relatPath;
	}
}
