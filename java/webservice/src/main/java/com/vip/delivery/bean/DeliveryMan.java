package com.vip.delivery.bean;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 快递员信息汇总
 * @author leo.song
 *
 */
@XmlRootElement(name="delivery")
public class DeliveryMan implements Serializable{

	private static final long serialVersionUID = 8466937427515465394L;
	private Integer did;   //快递员Id
	private Integer cid;   //承运商Id 
	private String carrier_code;    //承运商代码
	private String carrier_name;   //承运商名称
	private String mobile;            //手机号
	private String delivery_name;  // 快递员姓名
	private Integer all_duration_days; //   总配送时长(天)
	private Integer all_order_cnt;       // 总订单数量
	private Integer posted_order_cnt;  // 妥投数量
	private Integer satisfaction_order_cnt; //   满意数量
	private Integer evaluate_cnt; // 已评价的数据
	private Integer distance; // 配送距离 
	
	
	public DeliveryMan() {
		super();
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
	public Integer getAll_duration_days() {
		return all_duration_days;
	}
	public void setAll_duration_days(Integer all_duration_days) {
		this.all_duration_days = all_duration_days;
	}
	public Integer getAll_order_cnt() {
		return all_order_cnt;
	}
	public void setAll_order_cnt(Integer all_order_cnt) {
		this.all_order_cnt = all_order_cnt;
	}
	public Integer getPosted_order_cnt() {
		return posted_order_cnt;
	}
	public void setPosted_order_cnt(Integer posted_order_cnt) {
		this.posted_order_cnt = posted_order_cnt;
	}
	public Integer getSatisfaction_order_cnt() {
		return satisfaction_order_cnt;
	}
	public void setSatisfaction_order_cnt(Integer satisfaction_order_cnt) {
		this.satisfaction_order_cnt = satisfaction_order_cnt;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cid == null) ? 0 : cid.hashCode());
		result = prime * result + ((did == null) ? 0 : did.hashCode());
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
		DeliveryMan other = (DeliveryMan) obj;
		if (cid == null) {
			if (other.cid != null)
				return false;
		} else if (!cid.equals(other.cid))
			return false;
		if (did == null) {
			if (other.did != null)
				return false;
		} else if (!did.equals(other.did))
			return false;
		return true;
	}

	public Integer getEvaluate_cnt() {
		return evaluate_cnt;
	}

	public void setEvaluate_cnt(Integer evaluate_cnt) {
		this.evaluate_cnt = evaluate_cnt;
	}

	public Integer getDistance() {
		return distance;
	}

	public void setDistance(Integer distance) {
		this.distance = distance;
	}
	 	
}
