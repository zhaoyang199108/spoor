package com.yzk.entity;

import java.util.Date;

public class SpoorPicture {
	private long pictureId;
	private Date createTime;
	private long spoorId;
	private String pictureUrl;
	public long getPictureId() {
		return pictureId;
	}
	public void setPictureId(long pictureId) {
		this.pictureId = pictureId;
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
	public String getPictureUrl() {
		return pictureUrl;
	}
	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}
	
	
}
