package com.yzk.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yzk.entity.SpoorPicture;

public interface PictureDao {
	int insertPicture(SpoorPicture sp);
	List<SpoorPicture> findPicBySpoorId(@Param("id")Long id);
	int deletePictureBySpoorId(@Param("spoorId")Long spoorId);
}
