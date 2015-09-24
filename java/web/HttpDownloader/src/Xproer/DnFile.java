package Xproer;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.google.gson.Gson;

public class DnFile 
{
	public DnFile()
	{
	}
	
	/**
	 * 获取文件信息
	 * @param fid
	 * @return
	 */
	public DnFileInf Find(int fid)
	{		
		String sql = "select * from down_files where fid=?";
		DbHelper db = new DbHelper();
		PreparedStatement cmd = db.GetCommand(sql);
		try
		{
			cmd.setInt(1, fid);
			ResultSet r = db.ExecuteDataSet(cmd);
			
			DnFileInf inf 	= new DnFileInf();
			inf.m_fid 		= fid;
			inf.m_lengthLoc = r.getString(6);
			inf.m_lengthSvr = r.getString(7);
			inf.m_mac 		= r.getString(3);
			inf.m_pathLoc 	= r.getString(4);
			inf.m_pathSvr 	= r.getString(5);
			
			cmd.close();
			cmd.getConnection().close();
			
			return inf;
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return null;
	}

    public int Add(DnFileInf inf)
    {
    	int idGen = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("insert into down_files(");
        sb.append(" f_uid");
        sb.append(",f_mac");
        sb.append(",f_pathLoc");
        sb.append(",f_pathSvr");
        sb.append(",f_lengthLoc");
        sb.append(",f_lengthSvr");
        sb.append(") values(");
        sb.append(" ?");
        sb.append(",?");
        sb.append(",?");
        sb.append(",?");
        sb.append(",?");
        sb.append(",?");
        sb.append(")");
		
		DbHelper db = new DbHelper();
		PreparedStatement cmd = db.GetCommand(sb.toString());

		try
		{
			cmd.setInt(1,inf.m_uid);
			cmd.setString(2,inf.m_mac);
			cmd.setString(3,inf.m_pathLoc);
			cmd.setString(4,inf.m_pathSvr);
			cmd.setString(5,inf.m_lengthLoc);
			cmd.setString(6,inf.m_lengthSvr);
			db.ExecuteNonQuery(cmd);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		idGen = db.ExecuteScalar("select max(f_id) from down_files");
		//XDebug.Output("id:",idGen);
		return idGen;

    }

    /**
     * 将文件设为已完成
     * @param fid
     */
    public void Complete(int fid)
    {
		DbHelper db = new DbHelper();
		PreparedStatement cmd = db.GetCommand("update down_files set f_complete=1 where f_id=?;");
		try
		{
			cmd.setInt(1,fid);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		db.ExecuteNonQuery(cmd);
    }

    /// <summary>
    /// 删除文件
    /// </summary>
    /// <param name="fid"></param>
    public void Delete(int fid,int uid,String mac)
    {
        String sql = "delete from down_files where f_id=? and f_uid=? and f_mac=?";
        DbHelper db = new DbHelper();
		PreparedStatement cmd = db.GetCommand(sql);

		try
		{
			cmd.setInt(1,fid);
			cmd.setInt(2,uid);
			cmd.setString(3,mac);
			db.ExecuteNonQuery(cmd);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
    }
    
    public static void Clear()
    {
        String sql = "delete from down_files ";
        DbHelper db = new DbHelper();
		PreparedStatement cmd = db.GetCommand(sql);
		db.ExecuteNonQuery(cmd);    	
    }

    /**
     * 更新文件进度信息
     * @param fid
     * @param uid
     * @param mac
     * @param lenLoc
     */
    public void UpdateLengthLoc(int fid,int uid,String mac,String lenLoc,String percent)
    {
        String sql = "update down_files set f_lengthLoc=?,f_percent=? where f_id=? and f_uid=? and f_mac=?";
        DbHelper db = new DbHelper();
		PreparedStatement cmd = db.GetCommand(sql);

		try
		{
			cmd.setString(1,lenLoc);
			cmd.setString(2,percent);
			cmd.setInt(3,fid);
			cmd.setInt(4,uid);
			cmd.setString(5,mac);
			
			db.ExecuteNonQuery(cmd);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
    }

    /// <summary>
    /// 获取所有未完成的文件列表
    /// </summary>
    /// <returns></returns>
    public String GetAll(int uid,String mac)
    {
        String sql = "select f_id,f_mac,f_pathLoc,f_pathSvr,f_lengthLoc,f_lengthSvr,f_complete,f_percent from down_files where f_uid=? and f_mac=? and f_complete=0";

        ArrayList<DnFileInf> files = new ArrayList<DnFileInf>();
		DbHelper db = new DbHelper();
		PreparedStatement cmd = db.GetCommand(sql);
		try
		{
			cmd.setInt(1,uid);
			cmd.setString(2,mac);
			ResultSet r = db.ExecuteDataSet(cmd);
			while (r.next())
			{
				DnFileInf f		= new DnFileInf();
				f.m_fid			= r.getInt(1);
				f.m_mac		    = r.getString(2);
			    f.m_pathLoc		= r.getString(3);
				f.m_pathSvr		= r.getString(4);
			    f.m_lengthLoc	= r.getString(5);
			    f.m_percent		= r.getString(8);
				files.add(f);
			}
			r.close();
			cmd.close();
			cmd.getConnection().close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

        Gson g = new Gson();
	    return g.toJson( files );
	}
}