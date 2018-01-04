package com.yzk.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yzk.entity.SpoorTag;

public interface SpoorTagDao {
	int insertTag(SpoorTag spoorTag);
	int updateTime(SpoorTag spoorTag);
	List<SpoorTag> findHotTag(@Param("hotRange")int hotRange);
	List<SpoorTag> findNewTag(@Param("newRange")int newRange);
	SpoorTag findRepeat(SpoorTag spoorTag);
}
