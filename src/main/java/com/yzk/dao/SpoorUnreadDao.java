package com.yzk.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yzk.entity.SpoorUnread;

public interface SpoorUnreadDao {
	int insertIntoSpoorUnread(SpoorUnread su);
	int deleteSpoorUnreadByUserId(@Param("userId")Long userId);
	int selectCountUnreadByUserId(@Param("userId")Long userId);
	List<SpoorUnread> selectSpoorUnreadByUserId(@Param("userId")Long userId);
	int deleteSpoorUnreadById(@Param("id")Long id);
	int deleteSpoorUnreadByCommentId(@Param("commentId")Long commentId);
}
