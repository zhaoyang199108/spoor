package com.yzk.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yzk.dao.UserDao;
import com.yzk.entity.SpoorUser;
import com.yzk.service.UserService;
@Service
public class UserServiceImpl implements UserService{

	private @Autowired UserDao userDao;
	public SpoorUser findUserByUserName(String userName) {
		return userDao.findUserByUserName(userName);
	}
	public int insertUser(SpoorUser user) {
		return userDao.insertUser(user);
	}
	public int updateUserSetUserPicture(String picture,String mobile) {
		return userDao.updateUserSetUserPicture(picture,mobile);
	}
	public int modifyPassword(String mobile, String nPsw) {
		return userDao.modifyPassword(mobile,nPsw);
	}
	public int updateUser(SpoorUser user) {
		return userDao.updateUser(user);
	}
	public int modifyMobile(String mobile,String oMobile) {
		// TODO Auto-generated method stub
		return userDao.modifyMobile(mobile,oMobile);
	}
	public int forgetPassword(String mobile, String nPsw) {
		// TODO Auto-generated method stub
		return userDao.forgetPassword(mobile, nPsw);
	}
	public int setTags(String tagOne, String tagTwo, String tagThree,
			String setting,String mobille) {
		// TODO Auto-generated method stub
		return userDao.setTags(tagOne,tagTwo,tagThree,setting,mobille);
	}
	public int closeSetting(String setting, String mobile) {
		// TODO Auto-generated method stub
		return userDao.closeSetting(setting,mobile);
	}
	public int modifyUserIcon(SpoorUser user) {
		// TODO Auto-generated method stub
		return userDao.modifyUserIcon(user);
	}
	public int modifyUserGender(SpoorUser user) {
		// TODO Auto-generated method stub
		return userDao.modifyUserGender(user);
	}
	public int modifyUserNickName(SpoorUser user) {
		// TODO Auto-generated method stub
		return userDao.modifyUserNickName(user);
	}
	public int modifyUserBirthday(SpoorUser user) {
		// TODO Auto-generated method stub
		return userDao.modifyUserBirthday(user);
	}
	public SpoorUser findUserByUserId(long userId) {
		return userDao.findUserByUserId(userId);
	}
	

}
