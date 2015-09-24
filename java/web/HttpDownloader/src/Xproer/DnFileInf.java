package Xproer;
import com.google.gson.annotations.SerializedName;

public class DnFileInf 
{
    public DnFileInf()
    { }

    @SerializedName("idSvr")
    public int m_fid;

    @SerializedName("uid")
    public int m_uid;

    @SerializedName("mac")
    public String m_mac;

    @SerializedName("pathLoc")
    public String m_pathLoc;

    @SerializedName("pathSvr")
    public String m_pathSvr;

    @SerializedName("lengthLoc")
    public String m_lengthLoc;

    @SerializedName("lengthSvr")
    public String m_lengthSvr;
    
    @SerializedName("percent")
    public String m_percent;
}