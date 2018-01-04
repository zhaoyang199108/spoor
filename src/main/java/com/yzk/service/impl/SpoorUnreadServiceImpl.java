package com.yzk.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yzk.dao.SpoorUnreadDao;
import com.yzk.entity.SpoorUnread;
import com.yzk.service.SpoorUnreadService;
@Service
public class SpoorUnreadServiceImpl implements SpoorUnreadService {
	private @Autowired SpoorUnreadDao spoorUnreadDao;
	public int insertIntoSpoorUnread(SpoorUnread su) {
		return spoorUnreadDao.insertIntoSpoorUnread(su);
	}

	public int deleteSpoorUnreadByUserId(Long userId) {
		return spoorUnreadDao.deleteSpoorUnreadByUserId(userId);
	}

	public int selectCountUnreadByUserId(Long userId) {
		return spoorUnreadDao.selectCountUnreadByUserId(userId);
	}

	public List<SpoorUnread> selectSpoorUnreadByUserId(Long userId) {
		return spoorUnreadDao.selectSpoorUnreadByUserId(userId);
	}

	public int deleteSpoorUnreadById(Long id) {
		// TODO Auto-generated method stub
		return spoorUnreadDao.deleteSpoorUnreadById(id);
	}

}
