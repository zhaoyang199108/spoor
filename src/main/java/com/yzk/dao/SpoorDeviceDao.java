package com.yzk.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yzk.entity.SpoorDevice;

public interface SpoorDeviceDao {
	SpoorDevice findByRegistrationId(@Param("registrationId")String registrationId,@Param("userId")Long userId);
	int loginInsertRegistrationId(SpoorDevice sd);
	int deleteByRegistrationId(@Param("registrationId")String registrationId,@Param("userId")Long userId);
	List<SpoorDevice> findByUserId(@Param("userId")Long userId);
}
