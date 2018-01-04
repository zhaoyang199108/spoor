package com.yzk.service;

import java.util.List;
import java.util.Set;

import com.yzk.dto.SpoorCommentBySelfDto;
import com.yzk.dto.SpoorCommentResDto;
import com.yzk.entity.SpoorComment;

public interface SpoorCommentService {
	int addComment(SpoorComment sc);
	int findAllComment(Long spoorId);
	List<SpoorCommentResDto> findSpoorCommentDetail(Long spoorId);
	Set<Long> findMyCommentList(String mobile);
	List<SpoorCommentBySelfDto> findSpoorCommentSelf(Long spoorId);
//	List<SpoorCommentFirstResDto> findSpoorCommentDetail(Long spoorId);
	int selectCountUnRead(Long userId);
	SpoorComment findCommentIdByUserId(SpoorComment sc);
	SpoorComment findCommentByCommentId(Long commentId);
}
