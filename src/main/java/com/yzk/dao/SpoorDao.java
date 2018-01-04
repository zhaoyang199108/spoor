package com.yzk.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yzk.entity.SpoorSpoor;

public interface SpoorDao {
	int insertSpoor(SpoorSpoor ss);
	SpoorSpoor findSpoorByTime();
	List<SpoorSpoor> findSpoorByMobile(@Param("mobile")Long mobile);
	List<SpoorSpoor> findAll();
	List<SpoorSpoor> findTagOne(@Param("tagOne")String tagOne);
	List<SpoorSpoor> findTagTwo(@Param("tagTwo")String tagTwo);
	List<SpoorSpoor> findTagThree(@Param("tagThree")String tagThree);
	List<SpoorSpoor> findAllByTags(@Param("tagOne")String tagOne,@Param("tagTwo")String tagTwo,@Param("tagThree")String tagThree);
	SpoorSpoor findById(@Param("spoorId")Long spoorId);
	List<SpoorSpoor> findByTag(@Param("tag")String tag);
	int deleteSpoorId(@Param("spoorId")Long spoorId);
	SpoorSpoor findSpoorBySpoorId(@Param("spoorId")Long spoorId);
}
