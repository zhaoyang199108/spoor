package com.yzk.dao;

import org.apache.ibatis.annotations.Param;

import com.yzk.entity.SpoorUser;


public interface UserDao {
	SpoorUser findUserByUserName(@Param("userName")String userName);
	int insertUser(SpoorUser user);
	int updateUserSetUserPicture(@Param("picture")String picture,@Param("mobile")String mobile);
	int modifyPassword(@Param("mobile")String mobile,@Param("nPsw")String nPsw);
	int updateUser(SpoorUser user);
	int modifyMobile(@Param("mobile")String mobile,@Param("oMobile")String oMobile);
	int forgetPassword(@Param("mobile")String mobile,@Param("nPsw")String nPsw);
	/**
	 * 更新 账号  密码
	 * @param model
	 */
//	@Update("update user set upwd=#{mdl.upwd}, password=#{mdl.password}  "
//			+ "WHERE ID=#{mdl.id}")
//	public int updatePwd(@Param("user")User user);
	public int updatePwd(SpoorUser user);
	int setTags(@Param("tagOne")String tagOne,@Param("tagTwo")String tagTwo,
			@Param("tagThree")String tagThree,
			@Param("setting")String setting,@Param("mobile")String mobile);
	int closeSetting(@Param("setting")String setting,@Param("mobile")String mobile);
	int modifyUserIcon(SpoorUser user);
	int modifyUserGender(SpoorUser user);
	int modifyUserNickName(SpoorUser user);
	int modifyUserBirthday(SpoorUser user);
	SpoorUser findUserByUserId(@Param("userId")Long userId);
	
}
