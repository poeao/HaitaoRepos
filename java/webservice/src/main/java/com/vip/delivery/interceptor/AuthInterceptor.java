package com.vip.delivery.interceptor;

import java.lang.reflect.Method;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.headers.Header;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.MessageContentsList;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.service.Service;
import org.apache.cxf.service.invoker.MethodDispatcher;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.vip.delivery.bean.AppUser;
import com.vip.delivery.bean.ServiceCallLog;

/**
 * 
 * 名称 : AuthInterceptor
 * 描述 : 用户权限验证拦截器  
 * 创建人 :  Song Haitao
 * 创建时间: 2014-9-28 下午03:06:19
 * 
 */
public class AuthInterceptor extends AbstractPhaseInterceptor<SoapMessage> {

	public static Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);
	@Resource
	private JdbcTemplate jdbcTemplate;
	
	//private String defaultUser = "admin";
	//private String defaultPassword = "321_admin";
	
	//在调用之前拦截  
    public AuthInterceptor() {  
        super(Phase.PRE_INVOKE);  
    }  
  
    /** 
     * 自定义拦截器需要实现handleMessage方法，该方法抛出Fault异常，可以自定义异常集成自Fault， 
     * 也可以new Fault(new Throwable()) 
     */  
    public void handleMessage(SoapMessage soap) throws Fault {  
    	logger.debug("开始验证用户信息 ......");  
        List<Header> headers = soap.getHeaders();  
        ServiceCallLog scl = new ServiceCallLog();
        scl.setIsSuccess("0");
        //获取请求参数 
        List<Object> result = MessageContentsList.getContentsList(soap);
        StringBuilder params = new StringBuilder();
        for(Object mes : result) {
        	if(params.length() > 0) {
        		params.append(", ");
        	}
        	params.append(mes);
        }
 
        scl.setParams(params.toString());
        //请求方法
        Exchange exchange = soap.getExchange();  
        BindingOperationInfo bop = exchange.get(BindingOperationInfo.class);  
        MethodDispatcher md = (MethodDispatcher) exchange.get(Service.class)  
                .get(MethodDispatcher.class.getName());  
        Method method = md.getMethod(bop);  
        scl.setInterfaceName(method.getName());
        
        //检查headers是否存在  
        if(headers == null | headers.size()<1){  
        	scl.setRemark("找不到Header，无法验证用户信息");
        	saveCallLog(scl);
            throw new Fault(new IllegalArgumentException("找不到Header，无法验证用户信息"));  
        }  
          
        Header header = headers.get(0);  
          
        Element el = (Element)header.getObject();  
        NodeList users = el.getElementsByTagName("username");  
        NodeList passwords = el.getElementsByTagName("password");  
          
        //检查是否有用户名和密码元素  
        if(users.getLength()<1){  
        	scl.setRemark("找不到用户信息");
        	saveCallLog(scl);
            throw new Fault(new IllegalArgumentException("找不到用户信息"));  
        }  
        String username = users.item(0).getTextContent().trim();  
          
        if(passwords.getLength()<1){  
        	scl.setRemark("找不到密码信息");
        	saveCallLog(scl);
            throw new Fault(new IllegalArgumentException("找不到密码信息"));  
        }  
        String password = passwords.item(0).getTextContent();  
          
        //检查用户名和密码是否正确  
      /*  if(!defaultUser.equals(username) || !defaultPassword.equals(password)){  
            throw new Fault(new IllegalArgumentException("未授权的访问！"));  
        } */
        
        if(StringUtils.isNotBlank(username)) {
        	scl.setUsername(username);
        	logger.info("username : " + username +  ", password : " + password);
        	AppUser user = getAppUser(username);
        	if(user != null) {
        		if(!password.equals(user.getPassword())) {
        			scl.setRemark("未授权的访问");
        			saveCallLog(scl);
        			throw new Fault(new IllegalArgumentException("未授权的访问！"));  
        		} else {
        			logger.info("用户信息校验通过！");
        			scl.setRemark("身份校验通过");
        			scl.setIsSuccess("1");
        			saveCallLog(scl);
        		}
        	} else {
        		scl.setRemark("用户信息未找到!");
        		 saveCallLog(scl);
        		 throw new Fault(new IllegalArgumentException("未授权的访问！"));  
        	}
        } else {
        	scl.setRemark("用户名为空！");
        	saveCallLog(scl);
        	throw new Fault(new IllegalArgumentException("未授权的访问！"));  
        }
    }  
    
    /**
     * 根据用户名获取用户信息
     * @param username
     * @return
     */
    public AppUser getAppUser(String username) {
    	if(StringUtils.isBlank(username)) {
    		return null;
    	}
    	String sql = "select * from  app_user where username = ?";
    	AppUser user = null;
		try {
			user = jdbcTemplate.queryForObject(sql, new Object[]{username}, new RowMapper<AppUser>(){

				@Override
				public AppUser mapRow(ResultSet rs, int rowNum) throws SQLException {
					AppUser user = new AppUser();
					user.setUsername(rs.getString("username"));
					user.setPassword(rs.getString("username"));
					
					return user;
				}
				
			});
		} catch (DataAccessException e) {
			logger.error(e.getMessage());
		}
    	return user;
    }

    /**
     * 记录调用记录信息 
     * @param callLog
     */
    public void saveCallLog(ServiceCallLog callLog) {
    	if(callLog != null) {
    		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    		String sql = "insert into service_call_log(username, interface_name, params, call_time, is_success,remark)  " +
    				"values (? ,? , ?, ?, ?, ?)";
    		jdbcTemplate.update(sql,  StringUtils.defaultString(callLog.getUsername()), StringUtils.defaultString(callLog.getInterfaceName()),
    				StringUtils.defaultString(callLog.getParams()),
    				sdf.format(new Date()), StringUtils.defaultString(callLog.getIsSuccess()), 
    				StringUtils.defaultString(callLog.getRemark()));
    		
    	}
    }
    
    
}
