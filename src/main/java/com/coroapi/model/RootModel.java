package com.coroapi.model;

import java.sql.Date;
import java.util.List;
import javax.xml.crypto.Data;

public class RootModel {
	/**
	 * 主键Id
	 */
	private Integer id;
	
	/**
	 * 学校
	 */
	private String school;
	
	/**
	 * 用户
	 */
	private String user;
	/**
	 * 密码
	 */
	private String bigscreenUrl;
	
	/**
	 * 密码
	 */
	private String wxUrl;
	
	/**
	 * 密码
	 */
	private String password;
	
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
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getBigscreenUrl() {
		return bigscreenUrl;
	}
	public void setBigscreenUrl(String bigscreenUrl) {
		this.bigscreenUrl = bigscreenUrl;
	}
	public String getWxUrl() {
		return wxUrl;
	}
	public void setWxUrl(String wxUrl) {
		this.wxUrl = wxUrl;
	}
}
