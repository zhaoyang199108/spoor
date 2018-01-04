package com.yzk.dto;

import java.util.Date;
import java.util.List;

import com.yzk.entity.SpoorPicture;
import com.yzk.entity.SpoorVideo;

public class SpoorDetailResDto {
	public long spoorId;
	public Date createTime;
	public String spoorContent;
	public String spoorTagOne;
	public String spoorTagTwo;
	public String spoorTagThree;
	public String benefitCount;//受益数
	public String isBenefit;
	public String iscollect;
	public String userIcon;
	public String nickName;
	public String comments;
	public String userPhone;
	public List<SpoorPicture> spoorPictures;
	public List<SpoorVideo> spoorVideos;
	public List<SpoorCommentResDto> spoorComment;
}
