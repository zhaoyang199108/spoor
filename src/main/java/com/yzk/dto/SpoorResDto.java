package com.yzk.dto;

import java.util.Date;
import java.util.List;

import com.yzk.entity.SpoorPicture;
import com.yzk.entity.SpoorVideo;

public class SpoorResDto {
//	public SpoorSpoor spoorSpoor;
	public long spoorId;
	public Date createTime;
	public String spoorContent;
	public String spoorTagOne;
	public String spoorTagTwo;
	public String spoorTagThree;
//	public String isSetting;
	public String benefitCount;//受益数
//	public String collectCount;
	public String isBenefit;
	public String iscollect;
	public String userIcon;
	public String nickName;
	public String comments;
	public String userPhone;
	public List<SpoorPicture> spoorPictures;
	public List<SpoorVideo> spoorVideos;
}
