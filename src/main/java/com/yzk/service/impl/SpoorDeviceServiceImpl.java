package com.yzk.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yzk.dao.SpoorDeviceDao;
import com.yzk.entity.SpoorDevice;
import com.yzk.service.SpoorDeviceService;
@Service
public class SpoorDeviceServiceImpl implements SpoorDeviceService {
	private @Autowired SpoorDeviceDao spoorDeviceDao;
	public SpoorDevice findByRegistrationId(String registrationId,Long userId) {
		return spoorDeviceDao.findByRegistrationId(registrationId,userId);
	}

	public int loginInsertRegistrationId(SpoorDevice sd) {
		return spoorDeviceDao.loginInsertRegistrationId(sd);
	}

	public int deleteByRegistrationId(String registrationId,long userId) {
		return spoorDeviceDao.deleteByRegistrationId(registrationId,userId);
	}

	public List<SpoorDevice> findByUserId(Long userId) {
		return spoorDeviceDao.findByUserId(userId);
	}

}
