package com.coroapi.model;

import java.sql.Date;
import java.util.List;
import javax.xml.crypto.Data;

public class EpideSituDisplayModel {
	/**
	 * 主键Id
	 */
	private Integer id;
	
	/**
	 * 返校数目
	 */
	private Integer fxNum;
	
	/**
	 * 境外返校
	 */
	private Integer jwfxNum;
	
	/**
	 * 外省返校数目
	 */
	private Integer wsfxNum;
	
	/**
	 * 重点观察数据
	 */
	private Integer zdgcNum;
	
	/**
	 * 姓名
	 */
	private String userName;
	
	/**
	 * 来源城市
	 */
	private String originCity;
	/**
	 * 学号
	 */
	private String userNum;

	/**
	 * 楼名称
	 */
	private String lymc;
	/**
	 * 进入或者外出
	 */
	private String jcbz;
	
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
	 * 维度
	 */
	private Double dimension;
	/**
	 * 时间
	 */
	private String createTime;
	/**
	 * 学院
	 */
	private String college;
	/**
	 * 数目
	 */
	private Integer count;
	/**
	 * 名字
	 */
	private String name;
	
	private Integer flag;
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getFxNum() {
		return fxNum;
	}

	public void setFxNum(Integer fxNum) {
		this.fxNum = fxNum;
	}

	public Integer getJwfxNum() {
		return jwfxNum;
	}

	public void setJwfxNum(Integer jwfxNum) {
		this.jwfxNum = jwfxNum;
	}

	public Integer getWsfxNum() {
		return wsfxNum;
	}

	public void setWsfxNum(Integer wsfxNum) {
		this.wsfxNum = wsfxNum;
	}

	public Integer getZdgcNum() {
		return zdgcNum;
	}

	public void setZdgcNum(Integer zdgcNum) {
		this.zdgcNum = zdgcNum;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getOriginCity() {
		return originCity;
	}

	public void setOriginCity(String originCity) {
		this.originCity = originCity;
	}

	public String getUserNum() {
		return userNum;
	}

	public void setUserNum(String userNum) {
		this.userNum = userNum;
	}

	public String getLymc() {
		return lymc;
	}

	public void setLymc(String lymc) {
		this.lymc = lymc;
	}

	public String getJcbz() {
		return jcbz;
	}

	public void setJcbz(String jcbz) {
		this.jcbz = jcbz;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getCollege() {
		return college;
	}

	public void setCollege(String college) {
		this.college = college;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	
	
	
	
}
