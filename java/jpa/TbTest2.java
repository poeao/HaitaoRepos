package com.anhry.app.ansafety.manage.system.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.anhry.app.ansafety.bean.BaseEntity;
import com.anhry.app.util.bean.CustomDateSerializer;

/**
 * 系统用户 
 * @author Haitao Song
 *
 */
@Entity
@Table(name = "tb_test2")
@DynamicInsert(true)
@DynamicUpdate(true)
@TableGenerator(name = "testId2", table = "PK_TABLE", 
                pkColumnName = "KEYID", valueColumnName = "KEYVALUE",allocationSize=1, pkColumnValue = "TestUSER_ID")
public class TbTest2   implements java.io.Serializable {

	private static final long serialVersionUID = 550413338040421794L;
	public static final String USER_LOCK = "1";

	private Integer id;

	private String realname;//用户真实姓名
	

	@Id
	@Column(name = "ID", precision = 11, scale = 0)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "testId2")
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	

	@Column(name = "user_real_name", length = 100)
	public String getRealname() {
		return this.realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	
	
	

}