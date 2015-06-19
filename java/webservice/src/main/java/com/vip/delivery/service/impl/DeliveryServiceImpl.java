package com.vip.delivery.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.JAXBException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.vip.delivery.bean.DeliveryMan;
import com.vip.delivery.bean.UserDelivery;
import com.vip.delivery.service.DeliveryService;
import com.vip.delivery.util.XmlHelper;

@Service("deliveryServiceImpl")
@WebService(endpointInterface="com.vip.delivery.service.DeliveryService")
public class DeliveryServiceImpl implements DeliveryService {
	public static Logger logger = LoggerFactory.getLogger(DeliveryServiceImpl.class);
	@Resource
	private JdbcTemplate jdbcTemplate;

	@Override
	@WebMethod
	public @WebResult(name = "deliveryMan")
	String getDeliveryManSumInfo(@WebParam(name = "carrierCode") String carrierCode,
			@WebParam(name = "did") Integer did) {
		if(StringUtils.isBlank(carrierCode) || did == null) {
			return "";
		}
		String sql = "select * from dm_app_delivery_sum where carrier_code = ? and did = ?";
		DeliveryMan deliveryMan = null;
		try {
			deliveryMan = jdbcTemplate.queryForObject(sql, new Object[]{carrierCode, did }, new RowMapper<DeliveryMan>(){
				@Override
				public DeliveryMan mapRow(ResultSet rs, int index)
						throws SQLException {
					DeliveryMan delivery = new DeliveryMan();
					delivery.setCid(rs.getInt("cid"));
					delivery.setCarrier_code(rs.getString("carrier_code"));
					delivery.setCarrier_name(rs.getString("carrier_name"));
					delivery.setDid(rs.getInt("did"));
					delivery.setDelivery_name(rs.getString("delivery_name"));
					delivery.setMobile(rs.getString("mobile"));
					delivery.setAll_duration_days(rs.getInt("all_duration_days"));
					delivery.setPosted_order_cnt(rs.getInt("posted_order_cnt"));
					delivery.setAll_order_cnt(rs.getInt("all_order_cnt"));
					delivery.setSatisfaction_order_cnt(rs.getInt("satisfaction_order_cnt"));
					delivery.setDistance(rs.getInt("distance"));
					delivery.setEvaluate_cnt(rs.getInt("evaluate_cnt"));
					
					return delivery;
				}
				
				
			});
		} catch (Exception e1) {
			logger.error(e1.getMessage());
		}
		
		try {
			if(deliveryMan != null) {
				return XmlHelper.objectToXML(DeliveryMan.class, deliveryMan);
			}
		} catch (JAXBException e) {
			logger.error(e.getMessage());
		}
		
		return "";
	}

	@Override
	@WebMethod
	public @WebResult(name = "deliveryMan")
	String getUserDelivery(@WebParam(name = "carrierCode") String carrierCode,
			@WebParam(name = "did") Integer did,
			@WebParam(name = "userId") Integer userId) {
		if(StringUtils.isBlank(carrierCode) || did == null || userId == null) {
			return "";
		}
		String sql = "select * from dm_app_delivery_user_sum where carrier_code = ? and did = ? and user_id = ?";
		UserDelivery userDelivery = null;
		try {
			userDelivery = jdbcTemplate.queryForObject(sql, new Object[]{carrierCode, did, userId }, new RowMapper<UserDelivery>(){
				@Override
				public UserDelivery mapRow(ResultSet rs, int index)
						throws SQLException {
					UserDelivery userDelivery = new UserDelivery();
					userDelivery.setUser_id(rs.getInt("user_id"));
					userDelivery.setOrder_cnt(rs.getInt("order_cnt"));
					userDelivery.setCid(rs.getInt("cid"));
					userDelivery.setCarrier_code(rs.getString("carrier_code"));
					userDelivery.setCarrier_name(rs.getString("carrier_name"));
					userDelivery.setDid(rs.getInt("did"));
					userDelivery.setDelivery_name(rs.getString("delivery_name"));
					userDelivery.setMobile(rs.getString("mobile"));
					
					return userDelivery;
				}
				
				
			});
		} catch (Exception e1) {
			logger.error(e1.getMessage());
		}
		
		
		try {
			if(userDelivery != null) {
				return XmlHelper.objectToXML(UserDelivery.class, userDelivery);
			}
		} catch (JAXBException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		
		return "";
	}
	
	

}

