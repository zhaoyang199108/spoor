package com.yzk.service;

import java.util.List;

import com.yzk.entity.SpoorUnread;

public interface SpoorUnreadService {
	int insertIntoSpoorUnread(SpoorUnread su);
	int deleteSpoorUnreadByUserId(Long userId);
	int selectCountUnreadByUserId(Long userId);
	List<SpoorUnread> selectSpoorUnreadByUserId(Long userId);
	
	int deleteSpoorUnreadById(Long id);
}
