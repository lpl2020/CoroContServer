package com.coroapi.model;

import java.sql.Date;
import java.util.List;
import javax.xml.crypto.Data;

public class SchoolPara {
	
	/**
	 * 主键
	 */
	private Integer id;
	/**
	 * 学校
	 */
	private String school;
	
	/**
	 * 省份
	 */
	private String province;
	/**
	 * 城市
	 */
	private String city;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
}
