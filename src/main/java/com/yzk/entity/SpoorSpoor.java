package com.yzk.entity;

import java.util.Date;

public class SpoorSpoor {
	private long spoorId;
	private Date createTime;
	private String spoorContent;
	private String spoorTagOne;
	private String spoorTagTwo;
	private String spoorTagThree;
	private String isSetting;
	public String getIsSetting() {
		return isSetting;
	}
	public void setIsSetting(String isSetting) {
		this.isSetting = isSetting;
	}
	public String getSpoorTagOne() {
		return spoorTagOne;
	}
	public void setSpoorTagOne(String spoorTagOne) {
		this.spoorTagOne = spoorTagOne;
	}
	public String getSpoorTagTwo() {
		return spoorTagTwo;
	}
	public void setSpoorTagTwo(String spoorTagTwo) {
		this.spoorTagTwo = spoorTagTwo;
	}
	public String getSpoorTagThree() {
		return spoorTagThree;
	}
	public void setSpoorTagThree(String spoorTagThree) {
		this.spoorTagThree = spoorTagThree;
	}
	private long userId;
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public long getSpoorId() {
		return spoorId;
	}
	public void setSpoorId(long spoorId) {
		this.spoorId = spoorId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getSpoorContent() {
		return spoorContent;
	}
	public void setSpoorContent(String spoorContent) {
		this.spoorContent = spoorContent;
	}

}
