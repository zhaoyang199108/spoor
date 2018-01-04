package com.yzk.dto;

import java.util.Date;
import java.util.List;

public class SpoorCommentFirstResDto {
	public Long id;
	public String userPhone;
	public String userIcon;
	public String nickName;
	public Date createTime;
	public String userComment;
	public List<SpoorCommentResDto> scList;
}
