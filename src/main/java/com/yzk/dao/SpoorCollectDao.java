package com.yzk.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yzk.entity.SpoorCollect;

public interface SpoorCollectDao {
	int spoorCollect(SpoorCollect sc);
	List<SpoorCollect> findCollectByMobile(@Param("userId")Long userId,@Param("currentPage")int currentPage,@Param("pageSize")int pageSize);
	int deleteCollect(@Param("userId")Long userId,@Param("spoorId")Long spoorId);
	int findCollectByUserIdAndSpoorId(@Param("userId")Long userId ,@Param("spoorId")Long spoorId);
	int deleteCollectBySpoorId(@Param("spoorId")Long spoorId);
}
