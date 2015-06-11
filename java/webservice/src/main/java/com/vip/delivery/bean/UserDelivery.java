package com.vip.delivery.bean;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 用户相关快递员信息
 * 
 * @author leo.song
 * 
 */
@XmlRootElement(name="delivery")
public class UserDelivery implements Serializable {

	private static final long serialVersionUID = 2745851867990144983L;
	private Integer user_id; // 用户ID
	private Integer order_cnt; // 订单数量
	private Integer did;   //快递员Id
	private Integer cid;   //承运商Id 
	private String carrier_code;    //承运商代码
	private String carrier_name;   //承运商名称
	private String mobile;            //手机号
	private String delivery_name;  // 快递员姓名
	 

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public Integer getOrder_cnt() {
		return order_cnt;
	}

	public void setOrder_cnt(Integer order_cnt) {
		this.order_cnt = order_cnt;
	}

	public Integer getDid() {
		return did;
	}

	public void setDid(Integer did) {
		this.did = did;
	}

	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public String getCarrier_code() {
		return carrier_code;
	}

	public void setCarrier_code(String carrier_code) {
		this.carrier_code = carrier_code;
	}

	public String getCarrier_name() {
		return carrier_name;
	}

	public void setCarrier_name(String carrier_name) {
		this.carrier_name = carrier_name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getDelivery_name() {
		return delivery_name;
	}

	public void setDelivery_name(String delivery_name) {
		this.delivery_name = delivery_name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((user_id == null) ? 0 : user_id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserDelivery other = (UserDelivery) obj;
		if (user_id == null) {
			if (other.user_id != null)
				return false;
		} else if (!user_id.equals(other.user_id))
			return false;
		return true;
	}
}
