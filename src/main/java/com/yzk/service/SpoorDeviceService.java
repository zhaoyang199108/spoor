package com.yzk.service;

import java.util.List;

import com.yzk.entity.SpoorDevice;

public interface SpoorDeviceService {
	SpoorDevice findByRegistrationId(String registrationId,Long userId);
	int loginInsertRegistrationId(SpoorDevice sd);
	int deleteByRegistrationId(String registrationId,long userId);
	List<SpoorDevice> findByUserId(Long userId);
}
