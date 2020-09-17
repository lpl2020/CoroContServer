package com.coroapi.model;

import java.sql.Date;
import java.util.List;
import javax.xml.crypto.Data;

public class WxApiModel {
	
	/**
	 * 主键
	 */
	private Integer id;
	/**
	 * 学校
	 */
	
	private String isQuarantine;
	
	private String school;
	/**
	 * 姓名
	 */
	private String userName;
	/**
	 * 学号
	 */
	private String userNo;
	
	/**
	 * 健康状态1
	 */
	private String heathinfo1;
	/**
	 * 健康状态2
	 */
	private Integer heathinfo2;
	/**
	 * 健康状态3
	 */
	private Integer heathinfo3;
	
	/**
	 * 提交时间
	 */
	private String  timestamp;

	/**
	 * 返校时间
	 */
	private String fxTime;
	/**
	 * 返校国家
	 */
	private String fxAddrCountry;
	/**
	 * 返校省份
	 */
	private String fxAddrProvince;
	/**
	 * 返校城市
	 */
	private String fxAddrCity;
	/**
	 * 返校区
	 */
	private String fxAddrTown;
	/**
	 * 返校交通
	 */
	private String fxVehicl;
	/**
	 * 是否经过疫区
	 */
	private Integer isThseriousarea;
	
	/**
	 * 地址
	 */
	private String addr;
	
	/**
	 * 经度
	 */
	private double longitude;
	/**
	 * 维度
	 */
	private double dimension;
	/**
	 * 学院
	 */
	private String college;
	
	private String phone;
	
	private String classes;
	
	private String md5Password;
	
	private String toname;
	
	private String tophone;
	
	private String visitinfo;
	
	private String temperature;
	
	private String notes;
	
    private String fxjtSm;
	
	private String fxjk;
	
	private String department;
	
	private String parentPhone;
	/**
	 * 学生类型
	 */
	private Integer personType;
	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getHeathinfo1() {
		return heathinfo1;
	}

	public void setHeathinfo1(String heathinfo1) {
		this.heathinfo1 = heathinfo1;
	}

	public Integer getHeathinfo2() {
		return heathinfo2;
	}

	public void setHeathinfo2(Integer heathinfo2) {
		this.heathinfo2 = heathinfo2;
	}

	public Integer getHeathinfo3() {
		return heathinfo3;
	}

	public void setHeathinfo3(Integer heathinfo3) {
		this.heathinfo3 = heathinfo3;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}


	public Integer getIsThseriousarea() {
		return isThseriousarea;
	}

	public void setIsThseriousarea(Integer isThseriousarea) {
		this.isThseriousarea = isThseriousarea;
	}

	public String getFxVehicl() {
		return fxVehicl;
	}

	public void setFxVehicl(String fxVehicl) {
		this.fxVehicl = fxVehicl;
	}

	public String getFxAddrCity() {
		return fxAddrCity;
	}

	public void setFxAddrCity(String fxAddrCity) {
		this.fxAddrCity = fxAddrCity;
	}

	public String getFxAddrProvince() {
		return fxAddrProvince;
	}

	public void setFxAddrProvince(String fxAddrProvince) {
		this.fxAddrProvince = fxAddrProvince;
	}

	public String getFxAddrCountry() {
		return fxAddrCountry;
	}

	public void setFxAddrCountry(String fxAddrCountry) {
		this.fxAddrCountry = fxAddrCountry;
	}

	public String getFxTime() {
		return fxTime;
	}

	public void setFxTime(String fxTime) {
		this.fxTime = fxTime;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getDimension() {
		return dimension;
	}

	public void setDimension(double dimension) {
		this.dimension = dimension;
	}

	public String getCollege() {
		return college;
	}

	public void setCollege(String college) {
		this.college = college;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}


	public String getClasses() {
		return classes;
	}

	public void setClasses(String classes) {
		this.classes = classes;
	}

	public String getMd5Password() {
		return md5Password;
	}

	public void setMd5Password(String md5Password) {
		this.md5Password = md5Password;
	}

	public Integer getPersonType() {
		return personType;
	}

	public void setPersonType(Integer personType) {
		this.personType = personType;
	}

	public String getFxAddrTown() {
		return fxAddrTown;
	}

	public void setFxAddrTown(String fxAddrTown) {
		this.fxAddrTown = fxAddrTown;
	}

	public String getToname() {
		return toname;
	}

	public void setToname(String toname) {
		this.toname = toname;
	}

	public String getTophone() {
		return tophone;
	}

	public void setTophone(String tophone) {
		this.tophone = tophone;
	}

	public String getVisitinfo() {
		return visitinfo;
	}

	public void setVisitinfo(String visitinfo) {
		this.visitinfo = visitinfo;
	}

	public String getTemperature() {
		return temperature;
	}

	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getFxjtSm() {
		return fxjtSm;
	}

	public void setFxjtSm(String fxjtSm) {
		this.fxjtSm = fxjtSm;
	}

	public String getFxjk() {
		return fxjk;
	}

	public void setFxjk(String fxjk) {
		this.fxjk = fxjk;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getParentPhone() {
		return parentPhone;
	}

	public void setParentPhone(String parentPhone) {
		this.parentPhone = parentPhone;
	}

	public String getIsQuarantine() {
		return isQuarantine;
	}

	public void setIsQuarantine(String isQuarantine) {
		this.isQuarantine = isQuarantine;
	}
}
