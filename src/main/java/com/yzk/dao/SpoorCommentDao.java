package com.yzk.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yzk.entity.SpoorComment;

public interface SpoorCommentDao {
	int findSpoorCommentCounts(@Param("spoorId")Long spoorId);
	int addComment(SpoorComment sc);
	List<SpoorComment> findCommentList(@Param("parentId")Long parentId);
	List<SpoorComment> findRootCommentList(@Param("spoorId")Long spoorId);
	SpoorComment findSpoorCommentById(@Param("id") Long id);
	List<Long> findMyCommentList(@Param("userId")Long userId);
	List<Long> findNoticeByUser(@Param("userId")Long userId);
	int deleteCommentBySpoorId(@Param("spoorId")Long spoorId);
	int selectCountUnRead(@Param("userId")Long userId);
	SpoorComment findCommentIdByUserId(SpoorComment sc);
	SpoorComment findCommentByCommentId(@Param("commentId")Long commentId);
	
	List<SpoorComment> findCommentBySpoorId(@Param("spoorId")Long spoorId);
	
}
