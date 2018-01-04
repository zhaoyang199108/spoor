package com.yzk.entity;

import java.util.Date;

public class SpoorBenefit {

	private long benefitId;
	private Date createTime;
	private long spoorId;
	private long userId;
	private String userBenefit;
	public long getBenefitId() {
		return benefitId;
	}
	public void setBenefitId(long benefitId) {
		this.benefitId = benefitId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public long getSpoorId() {
		return spoorId;
	}
	public void setSpoorId(long spoorId) {
		this.spoorId = spoorId;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getUserBenefit() {
		return userBenefit;
	}
	public void setUserBenefit(String userBenefit) {
		this.userBenefit = userBenefit;
	}
}
