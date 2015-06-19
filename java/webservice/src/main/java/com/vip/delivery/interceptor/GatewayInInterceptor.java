package com.vip.delivery.interceptor;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


/**
 * 对客户端上送的数据进行处理，可以完成以下功能
 * （1）、对加密的字段统一加密；
 * （2）、对特殊的字段进行特殊处理
 * 
 * @author  XX
 * @version  [版本号, 2012-11-07 下午02:34:07 ]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Service("gatewayInInterceptor")
public class GatewayInInterceptor extends AbstractPhaseInterceptor<Message> {
	private Logger logger = LoggerFactory.getLogger(GatewayInInterceptor.class);
	public GatewayInInterceptor(String phase) {
		super(phase);
	}
	
	public GatewayInInterceptor() {
		super(Phase.RECEIVE);
	}

	/** <功能详细描述>
	 * 创 建 人:  XX
	 * 创建时间:  2012-11-07 下午02:34:07  
	 * @param arg0
	 * @throws Fault
	 * @see [类、类#方法、类#成员]
	 */
	@SuppressWarnings("static-access")
	public void handleMessage(Message message) throws Fault {

		/* Iterator<Entry<String, Object>> it = message.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, Object> e = it.next();
			System.out.println(e.getKey() + "," + e.getValue());
		}*/
		String reqParams=null;
		 if(message.get(message.HTTP_REQUEST_METHOD).equals("GET")){//采用GET方式请求
			 reqParams=(String) message.get(message.QUERY_STRING);
			 message.remove(message.QUERY_STRING);
			 reqParams=this.getParams(this.getParamsMap(reqParams));
			 message.put(message.QUERY_STRING,reqParams);
			 
		 }else if(message.get(message.HTTP_REQUEST_METHOD).equals("POST")){//采用POST方式请求
			 try {
				 InputStream is = message.getContent(InputStream.class);
				 reqParams=this.getParams(this.getParamsMap(is.toString()));
					if (is != null)
						message.setContent(InputStream.class, new ByteArrayInputStream(reqParams.getBytes()));
				} catch (Exception e) {
					logger.error("GatewayInInterceptor异常",e);
				}
		 }
		 logger.info("请求的参数："+reqParams);
	}
	
	private Map<String,String> getParamsMap(String strParams){
		if(strParams==null||strParams.trim().length()<=0){
			return null;
		}
		Map<String,String> map =new HashMap<String,String>();
		String[] params=strParams.split("&");
		for(int i=0;i<params.length;i++){
			String[] arr=params[i].split("=");
			map.put(arr[0], arr[1]);
			  System.out.println(arr[0] +"---->" + arr[1]);
		}
		return map;
	}
	
	private String getParams(Map<String,String> map){
		if(map==null||map.size()==0){
			return null;
		}
		StringBuffer sb=new StringBuffer();
		Iterator<String> it =map.keySet().iterator();
		while(it.hasNext()){
			String key=it.next();
			String value =map.get(key);
			/*这里可以对客户端上送过来的输入参数进行特殊处理。如密文解密；对数据进行验证等等。。。
			if(key.equals("content")){
				value.replace("%3D", "=");
				value = DesEncrypt.convertPwd(value, "DES");
			}*/
			if(sb.length()<=0){
				sb.append(key+"="+value);
			}else{
				sb.append("&"+key+"="+value);
			}
		}
		return sb.toString();
	}

}