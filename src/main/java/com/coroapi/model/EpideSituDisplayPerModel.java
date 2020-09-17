package com.coroapi.model;

import java.sql.Date;
import java.util.List;
import javax.xml.crypto.Data;

public class EpideSituDisplayPerModel {
	/**
	 * 主键Id
	 */
	private Integer id;
	
	/**
	 * 教师数据
	 */
	private Integer teachercount;
	
	/**
	 * 学生数目
	 */
	private Integer studentcount;
	
	/**
	 * 留学生
	 */
	private Integer foreigner;
	
	/**
	 * 其他
	 */
	private Integer other;
	
	/**
	 * 境外输入
	 */
	private Integer fromOtherCountry;
	
	/**
	 * 武汉来源
	 */
	private Integer fromWuHan;
	/**
	 * 重点关注
	 */
	private Integer focusObservation;

	/**
	 * 时间戳
	 */
	private String timestamp;
	/**
	 * 计数
	 */
	private Integer count;
	
	/**
	 * 姓名
	 */
	private String userName;
	/**
	 * 学号
	 */
	private String userNo;
	/**
	 * 性别
	 */
	private String sex;
	
	/**
	 * 年龄
	 */
	private String age;
	/**
	 * 班级
	 */
	private String classes;
	/**
	 * 返校时间
	 */
	private String fxTime;
	
	/**
	 * 学校中心经度
	 */
	private String centerLongitude;
	
	/**
	 * 学校中心维度
	 */
	private String centerDimension;
	/**
	 * 经度
	 */
	private Double longitude;
	
	/**
	 * 扫码时间
	 */
	private String time;
	/**
	 * 维度
	 */
	private Double dimension;
	/**
	 * 位置
	 */
	private String addr;
	
	/**
	 * 返校交通
	 */
	private String fxVehicl;
	private String fxjtSm;
	
	private String phone;
	
	private String parentPhone;
	/**
	 * 扫码时间
	 */
	private String heathinfo1;
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTeachercount() {
		return teachercount;
	}

	public void setTeachercount(Integer teachercount) {
		this.teachercount = teachercount;
	}

	public Integer getStudentcount() {
		return studentcount;
	}

	public void setStudentcount(Integer studentcount) {
		this.studentcount = studentcount;
	}

	public Integer getForeigner() {
		return foreigner;
	}

	public void setForeigner(Integer foreigner) {
		this.foreigner = foreigner;
	}

	public Integer getOther() {
		return other;
	}

	public void setOther(Integer other) {
		this.other = other;
	}

	public Integer getFromOtherCountry() {
		return fromOtherCountry;
	}

	public void setFromOtherCountry(Integer fromOtherCountry) {
		this.fromOtherCountry = fromOtherCountry;
	}

	public Integer getFromWuHan() {
		return fromWuHan;
	}

	public void setFromWuHan(Integer fromWuHan) {
		this.fromWuHan = fromWuHan;
	}

	public Integer getFocusObservation() {
		return focusObservation;
	}

	public void setFocusObservation(Integer focusObservation) {
		this.focusObservation = focusObservation;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}


	public String getFxTime() {
		return fxTime;
	}

	public void setFxTime(String fxTime) {
		this.fxTime = fxTime;
	}

	public String getClasses() {
		return classes;
	}

	public void setClasses(String classes) {
		this.classes = classes;
	}

	public String getCenterLongitude() {
		return centerLongitude;
	}

	public void setCenterLongitude(String centerLongitude) {
		this.centerLongitude = centerLongitude;
	}

	public String getCenterDimension() {
		return centerDimension;
	}

	public void setCenterDimension(String centerDimension) {
		this.centerDimension = centerDimension;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getDimension() {
		return dimension;
	}

	public void setDimension(Double dimension) {
		this.dimension = dimension;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getHeathinfo1() {
		return heathinfo1;
	}

	public void setHeathinfo1(String heathinfo1) {
		this.heathinfo1 = heathinfo1;
	}

	public String getFxVehicl() {
		return fxVehicl;
	}

	public void setFxVehicl(String fxVehicl) {
		this.fxVehicl = fxVehicl;
	}

	public String getFxjtSm() {
		return fxjtSm;
	}

	public void setFxjtSm(String fxjtSm) {
		this.fxjtSm = fxjtSm;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getParentPhone() {
		return parentPhone;
	}

	public void setParentPhone(String parentPhone) {
		this.parentPhone = parentPhone;
	}
	
	
}
