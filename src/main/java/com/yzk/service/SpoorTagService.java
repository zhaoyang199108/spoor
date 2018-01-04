package com.yzk.service;

import java.util.List;

import com.yzk.entity.SpoorTag;

public interface SpoorTagService {
	int insertTag(SpoorTag spoorTag);
	int updateTime(SpoorTag spoorTag);
	List<SpoorTag> findHotTag(int hotRange);
	List<SpoorTag> findNewTag(int newRange);
	SpoorTag findRepeat(SpoorTag spoorTag);
}
