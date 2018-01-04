package com.yzk.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yzk.dao.SpoorTagDao;
import com.yzk.entity.SpoorTag;
import com.yzk.service.SpoorTagService;
@Service
public class SpoorTagServiceImpl implements SpoorTagService {

	private @Autowired SpoorTagDao spoorTagDao;
	public int insertTag(SpoorTag spoorTag) {
		return spoorTagDao.insertTag(spoorTag);
	}
	public int updateTime(SpoorTag spoorTag) {
		return spoorTagDao.updateTime(spoorTag);
	}
	public List<SpoorTag> findHotTag(int hotRange) {
		return spoorTagDao.findHotTag(hotRange);
	}
	public List<SpoorTag> findNewTag(int newRange) {
		return spoorTagDao.findNewTag(newRange);
	}
	public SpoorTag findRepeat(SpoorTag spoorTag) {
		return spoorTagDao.findRepeat(spoorTag);
	}

}
