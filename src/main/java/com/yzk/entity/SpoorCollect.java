package com.yzk.entity;

import java.util.Date;

public class SpoorCollect {
	private long id;
	private Date createTime;//创建时间
	private String collect;
	private long spoorId;
	private long userId;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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
	public String getCollect() {
		return collect;
	}
	public void setCollect(String collect) {
		this.collect = collect;
	}
}
