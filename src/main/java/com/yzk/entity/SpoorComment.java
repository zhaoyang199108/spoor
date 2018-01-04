package com.yzk.entity;

import java.util.Date;

public class SpoorComment {
	private long id;
	private Date createTime;//创建时间
	private long userId;
	private long spoorId;
	private String userComment;
	private long parentId;
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
	public String getUserComment() {
		return userComment;
	}
	public void setUserComment(String userComment) {
		this.userComment = userComment;
	}
	public long getParentId() {
		return parentId;
	}
	public void setParentId(long parentId) {
		this.parentId = parentId;
	}
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

}
