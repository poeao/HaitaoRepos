package com.vip.delivery.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;


/**
 * 快递员服务接口类  
 * @author Haitao Song
 *
 */
@WebService
public interface DeliveryService {

	/**
	 * 根据承运商ID和快递员ID查询快递员的汇总信息
	 * @param cid   承运商Id
	 * @param did  快递员Id
	 * @return   
	 */
	@WebMethod
	public @WebResult(name="deliveryMan") String getDeliveryManSumInfo
				(@WebParam(name="carrierCode") String carrierCode, @WebParam(name="did") Integer did);
	
	/**
	 * 根据承运商ID和快递员ID, 用户ID， 查询快递员与本用户相关的汇总信息
	 * @param cid   承运商Id
	 * @param did  快递员Id
	 * @param userId  用户ID
	 * @return
	 */
	@WebMethod
	public @WebResult(name="deliveryMan") String getUserDelivery
			(@WebParam(name = "carrierCode") String carrierCode, @WebParam(name="did") Integer did, @WebParam(name="userId") Integer userId);
	
}
