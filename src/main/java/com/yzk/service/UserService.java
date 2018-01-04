package com.yzk.service;

import org.springframework.stereotype.Service;

import com.yzk.entity.SpoorUser;

@Service
public interface UserService {
	SpoorUser findUserByUserName(String userName);
	int insertUser(SpoorUser user);
	int updateUserSetUserPicture(String picture,String mobile);
	int modifyPassword(String mobile,String nPsw);
	int updateUser(SpoorUser user);
	int modifyMobile(String mobile,String oMobile);
	int forgetPassword(String mobile,String nPsw);
	int setTags(String tagOne,String tagTwo,String tagThree,String setting,String mobile);
	int closeSetting(String setting,String mobile);
	int modifyUserIcon(SpoorUser user);
	int modifyUserGender(SpoorUser user);
	int modifyUserNickName(SpoorUser user);
	int modifyUserBirthday(SpoorUser user);
	SpoorUser findUserByUserId(long userId);
}
