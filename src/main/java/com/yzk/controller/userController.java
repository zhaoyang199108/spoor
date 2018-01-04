package com.yzk.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.yzk.dto.SpoorCommentResDto;
import com.yzk.entity.SpoorComment;
import com.yzk.entity.SpoorDevice;
import com.yzk.entity.SpoorUnread;
import com.yzk.entity.SpoorUser;
import com.yzk.service.SpoorCommentService;
import com.yzk.service.SpoorDeviceService;
import com.yzk.service.SpoorUnreadService;
import com.yzk.service.UserService;
import com.yzk.util.JpushClientUtil;
import com.yzk.util.MD5Util;
import com.yzk.util.PropertiesUtil;
import com.yzk.util.SendMessageUtil;

@Controller
@RequestMapping("/user")
public class userController {
	@SuppressWarnings("unused")
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private static final String path = PropertiesUtil
			.getProperty("http.server.head.ip");
	private @Autowired UserService userService;
	private @Autowired SpoorDeviceService spoorDeviceService;
	private @Autowired SpoorUnreadService spoorUnreadService;
	private @Autowired SpoorCommentService spoorCommentService;

	@RequestMapping(value = "/obtainMessage", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> obtainMessage(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();

		String mobile = request.getParameter("mobile");
		if (mobile == null || "".equals(mobile)) {
			map.put("code", 1);// 0成功1失败
			map.put("message", "手机号为空");
			return map;
		}
		SpoorUser user_find = userService.findUserByUserName(mobile);
		if (user_find == null) {
			map.put("code", "1");
			map.put("message", "该用户信息不存在");
			return map;
		}
		SendMessageUtil smu = new SendMessageUtil();
		String reback = smu.sendMessage(mobile);
		if (reback.contains("200")) {
			map.put("code", 0);// 0成功1失败
			map.put("message", "短信发送成功");
			return map;
		} else {
			map.put("code", 1);// 0成功1失败
			map.put("message", "短信发送失败");
			return map;
		}
	}

	@RequestMapping(value = "/obtainRegisterMessage", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> obtainRegisterMessage(
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();

		String mobile = request.getParameter("mobile");
		if (mobile == null || "".equals(mobile)) {
			map.put("code", 1);// 0成功1失败
			map.put("message", "手机号为空");
			return map;
		}
		SpoorUser user_find = userService.findUserByUserName(mobile);
		if (user_find != null) {
			map.put("code", "1");
			map.put("message", "该用户已注册");
			return map;
		}
		SendMessageUtil smu = new SendMessageUtil();
		String reback = smu.sendMessage(mobile);
		if (reback.contains("200")) {
			map.put("code", 0);// 0成功1失败
			map.put("message", "短信发送成功");
			return map;
		} else {
			map.put("code", 1);// 0成功1失败
			map.put("message", "短信发送失败");
			return map;
		}
	}

	@RequestMapping(value = "/userLogin", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> currentUser(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> resMap = new HashMap<String, Object>();
		String mobile = request.getParameter("mobile");
		String psw = request.getParameter("psw");
		// String registrationId = request.getParameter("registrationId");
		String newPwdMd5 = MD5Util.getStringMD5(psw);
		SpoorUser user = userService.findUserByUserName(mobile);
		if (user == null) {
			resMap.put("code", 1);
			resMap.put("message", "此手机号未注册");
			resMap.put("data", null);
			return resMap;
		}
		System.out.println(psw);
		System.out.print(newPwdMd5);
		if (user.getPassword().equals(newPwdMd5)) {
			// SpoorUser suFind = userService.findUserByUserName(mobile);
			/*
			 * if (suFind != null) { int findCount =
			 * spoorUnreadService.selectCountUnreadByUserId(suFind.getUserId());
			 * SpoorDevice sd = spoorDeviceService.findByUserId(suFind
			 * .getUserId());
			 * JpushClientUtil.sendToRegistrationId(sd.getRegistrationId(), "",
			 * "", "", ""+findCount); }
			 */
			// SpoorDevice sd = new SpoorDevice();
			// if (null == registrationId || "".equals(registrationId)) {
			// resMap.put("code", 0);
			// // resMap.put("message", "请允许通知权限");
			// return resMap;
			// }
			// sd.setRegistrationId(registrationId);
			// sd.setUserId(user.getUserId());
			// int c = spoorDeviceService.loginInsertRegistrationId(sd);
			// if (c > 0) {
			resMap.put("code", "0");
			resMap.put("message", "登录成功");
			resMap.put("data", user);
			return resMap;
			// } else {
			// resMap.put("code", 1);
			// resMap.put("message", "登录失败");
			// return resMap;
			// }
		} else {
			resMap.put("code", "1");
			resMap.put("message", "密码不正确");
			resMap.put("data", null);
			return resMap;
		}
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> register(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();

		String mobile = request.getParameter("mobile");

		String code = request.getParameter("code");
		String psw = request.getParameter("psw");
		String newPwdMd5 = MD5Util.getStringMD5(psw);
		SendMessageUtil smu = new SendMessageUtil();
		String reback = smu.volidateMessage(mobile, code);
		if (reback.contains("200")) {
			SpoorUser user = new SpoorUser();
			user.setUserPhone(mobile);
			user.setPassword(newPwdMd5);
			user.setCreateTime(new Date());
			SpoorUser user_find = userService.findUserByUserName(mobile);
			if (user_find != null) {
				map.put("code", "1");
				map.put("message", "该用户已注册");
				return map;
			}
			int count = userService.insertUser(user);
			if (count > 0) {
				map.put("code", "0");
				map.put("message", "注册成功");
				return map;
			} else {
				map.put("code", "1");
				map.put("message", "注册失败");
				return map;
			}
		} else {
			map.put("code", 1);
			map.put("message", "验证码错误");
			return map;
		}
	}

	// 修改密码
	@RequestMapping(value = "/modifyPassword", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> modifyPassword(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		String mobile = request.getParameter("mobile");
		String oPsw = request.getParameter("oPsw");
		String nPsw = request.getParameter("nPsw");
		String oPwdMd5 = MD5Util.getStringMD5(oPsw);
		String nPwdMd5 = MD5Util.getStringMD5(nPsw);
		SpoorUser user_find = userService.findUserByUserName(mobile);
		if (user_find == null || "".equals(user_find)) {
			map.put("code", "0");
			map.put("message", "未查询到该用户");
			return map;
		}
		String user_opsw = user_find.getPassword();
		if (user_opsw.equals(oPwdMd5)) {
			int count = userService.modifyPassword(mobile, nPwdMd5);
			if (count > 0) {
				map.put("code", "0");
				map.put("message", "修改成功");
				return map;
			} else {
				map.put("code", "1");
				map.put("message", "修改失败");
				return map;
			}
		} else {
			map.put("code", "1");
			map.put("message", "原密码输入错误");
			return map;
		}
	}

	@RequestMapping(value = "/findUser", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findUser(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();

		String mobile = request.getParameter("mobile");
		if (mobile == null || "".equals(mobile)) {
			map.put("code", 1);// 0成功1失败
			map.put("message", "手机号为空");
			return map;
		}
		SpoorUser user_find = userService.findUserByUserName(mobile);
		if (user_find == null) {
			map.put("code", 0);// 0成功1失败
			map.put("message", "不存在该用户信息");
			return map;
		}
		map.put("code", "0");
		map.put("message", "查询成功");
		map.put("data", user_find);
		return map;
	}

	@RequestMapping(value = "/forgetPassword", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> forgetPassword(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		String mobile = request.getParameter("mobile");
		String code = request.getParameter("code");
		String nPsw = request.getParameter("nPsw");
		SpoorUser user_find = userService.findUserByUserName(mobile);
		if (user_find == null || "".equals(user_find)) {
			map.put("code", "0");
			map.put("message", "未查询到该用户");
			return map;
		}
		if (mobile == null || "".equals(mobile)) {
			map.put("code", "1");
			map.put("message", "手机号不能为空");
			return map;
		}
		if (code == null || "".equals(code)) {
			map.put("code", "1");
			map.put("message", "code不能为空");
			return map;
		}
		if (nPsw == null || "".equals(nPsw)) {
			map.put("code", "1");
			map.put("message", "新密码不能为空");
			return map;
		}
		String nPwdMd5 = MD5Util.getStringMD5(nPsw);
		SendMessageUtil smu = new SendMessageUtil();
		String reback = smu.volidateMessage(mobile, code);
		if (reback.contains("200")) {

			int count = userService.forgetPassword(mobile, nPwdMd5);
			if (count > 0) {
				map.put("code", "0");
				map.put("message", "修改成功");
				return map;
			} else {
				map.put("code", "1");
				map.put("message", "修改失败");
				return map;
			}
		} else {
			map.put("code", 1);
			map.put("message", "验证码错误");
			return map;
		}
	}

	@RequestMapping(value = "/setTags", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> setTags(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		String setting = request.getParameter("setting");
		if (null == setting) {
			map.put("code", "1");
			map.put("message", "未设置标签");
			return map;
		}
		String mobile = request.getParameter("mobile");
		String tagOne = request.getParameter("tagOne");
		String tagTwo = request.getParameter("tagTwo");
		String tagThree = request.getParameter("tagThree");
		if (setting.equals("1")) {
			if (tagOne == null) {
				map.put("code", "1");
				map.put("message", "标签One不能为空");
				return map;
			}
			if (tagTwo == null) {
				map.put("code", "1");
				map.put("message", "标签Two不能为空");
				return map;
			}
			if (tagThree == null) {
				map.put("code", "1");
				map.put("message", "标签Three不能为空");
				return map;
			}

			int count = userService.setTags(tagOne, tagTwo, tagThree, setting,
					mobile);
			if (count > 0) {
				SpoorUser user_find = userService.findUserByUserName(mobile);
				map.put("code", "0");
				map.put("message", "添加标签成功");
				map.put("data", user_find);
				return map;

			}
		}
		if (setting.equals("0")) {
			int count = userService.closeSetting(setting, mobile);
			if (count > 0) {
				SpoorUser user_find = userService.findUserByUserName(mobile);
				map.put("code", "0");
				map.put("message", "设置成功");
				map.put("data", user_find);
				return map;

			}
		}
		map.put("code", "1");
		map.put("message", "参数传输错误 setting");
		return map;
	}

	@RequestMapping(value = "/modifyUserIcon", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> modifyUserIcon(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		String mobile = request.getParameter("mobile");
		if (mobile.equals("") || null == mobile) {
			map.put("code", "1");
			map.put("message", "手机号不能为空");
			return map;
		}
		SpoorUser sp = userService.findUserByUserName(mobile);
		if (sp == null) {
			map.put("code", "1");
			map.put("message", "您查找的用户不存在");
			return map;
		}
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile file = multipartRequest.getFile("file");// 头像
		if (file == null) {
			map.put("code", "1");
			map.put("message", "没有上传文件");
			return map;
		}
		String fileName = file.getOriginalFilename();

		// 扩展名
		String fileExtensionName = fileName
				.substring(fileName.lastIndexOf(".") + 1);
		// 防止重名覆盖
		String uploadFileName = UUID.randomUUID().toString() + "."
				+ fileExtensionName;
		byte[] b = new byte[1024 * 1024];
		try {
			InputStream is = file.getInputStream();
			File fileDir = new File(path);
			// 判断文件夹是否存在,如果不存在则创建文件夹
			if (!fileDir.exists()) {
				fileDir.mkdir();
			}
			FileOutputStream fos = new FileOutputStream(path + uploadFileName);

			while ((is.read(b)) != -1) {
				fos.write(b);
			}
			is.close();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();

		}
		String relativePath = "/upload/head/" + uploadFileName;
		SpoorUser su = new SpoorUser();
		su.setUserIcon(relativePath);
		su.setUserPhone(mobile);
		int count = userService.modifyUserIcon(su);
		SpoorUser spf = userService.findUserByUserName(mobile);
		if (count > 0) {
			map.put("code", "0");
			map.put("message", "修改成功");
			map.put("data", spf);
			return map;
		}
		map.put("code", "1");
		map.put("message", "修改失败");
		map.put("data", spf);
		return map;
	}

	@RequestMapping(value = "/modifyUserGender", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> modifyUserGender(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		String gender = request.getParameter("gender");
		String mobile = request.getParameter("mobile");
		if (mobile.equals("") || null == mobile) {
			map.put("code", "1");
			map.put("message", "手机号不能为空");
			return map;
		}
		if (gender.equals("") || null == gender) {
			map.put("code", "1");
			map.put("message", "性别不能为空");
			return map;
		}
		SpoorUser su = new SpoorUser();
		su.setUserGender(gender);
		su.setUserPhone(mobile);
		int count = userService.modifyUserGender(su);
		SpoorUser sp = userService.findUserByUserName(mobile);
		if (count > 0) {
			map.put("code", "0");
			map.put("message", "修改成功");
			map.put("data", sp);
			return map;
		}
		map.put("code", "1");
		map.put("message", "修改失败");
		map.put("data", sp);
		return map;
	}

	@RequestMapping(value = "/modifyUserNickName", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> modifyUserNickName(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		String nickName = request.getParameter("nickName");
		String mobile = request.getParameter("mobile");
		if (mobile.equals("") || null == mobile) {
			map.put("code", "1");
			map.put("message", "手机号不能为空");
			return map;
		}
		if (nickName.equals("") || null == nickName) {
			map.put("code", "1");
			map.put("message", "昵称不能为空");
			return map;
		}
		SpoorUser su = new SpoorUser();
		su.setNickName(nickName);
		su.setUserPhone(mobile);
		int count = userService.modifyUserNickName(su);
		SpoorUser sp = userService.findUserByUserName(mobile);
		if (count > 0) {
			map.put("code", "0");
			map.put("message", "修改成功");
			map.put("data", sp);
			return map;
		}
		map.put("code", "1");
		map.put("message", "修改失败");
		map.put("data", sp);
		return map;
	}

	@RequestMapping(value = "/modifyUserBirthday", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> modifyUserBirthday(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		String birthday = request.getParameter("birthday");
		String mobile = request.getParameter("mobile");
		if (mobile.equals("") || null == mobile) {
			map.put("code", "1");
			map.put("message", "手机号不能为空");
			return map;
		}
		if (birthday.equals("") || null == birthday) {
			map.put("code", "1");
			map.put("message", "昵称不能为空");
			return map;
		}
		SpoorUser su = new SpoorUser();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		Date date = null;
		try {
			date = sdf.parse(birthday);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		su.setUserBirthday(date);
		su.setUserPhone(mobile);
		int count = userService.modifyUserBirthday(su);
		SpoorUser sp = userService.findUserByUserName(mobile);
		if (count > 0) {
			map.put("code", "0");
			map.put("message", "修改成功");
			map.put("data", sp);
			return map;
		}
		map.put("code", "1");
		map.put("message", "修改失败");
		map.put("data", sp);
		return map;
	}

	@RequestMapping(value = "/removeRegistrationId", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> logOut(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		String registrationId = request.getParameter("registrationId");
		String loginId = request.getParameter("loginId");
		if (null == loginId || "".equals(loginId)) {
			map.put("code", 1);
			map.put("message", "loginId不能为空");
			return map;
		}
		if (null == registrationId || "".equals(registrationId)) {
			map.put("code", 0);
			// map.put("message", "参数不能为空");
			return map;
		}
		SpoorUser su = userService.findUserByUserName(loginId);
		if (su == null) {
			map.put("code", 1);
			map.put("message", "未查询到该用户");
			return map;
		}
		SpoorDevice sd = spoorDeviceService.findByRegistrationId(
				registrationId, su.getUserId());
		if (null == sd) {
			map.put("code", 1);
			map.put("message", "该用户未登录");
			return map;
		}

		int count = spoorDeviceService.deleteByRegistrationId(registrationId,
				su.getUserId());
		if (count > 0) {
			map.put("code", 0);
			map.put("message", "退出成功");
			return map;
		}
		map.put("code", 1);
		map.put("message", "退出失败");
		return map;
	}

	@RequestMapping(value = "/bandRegistrationId", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> bandRegistrationId(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		String mobile = request.getParameter("mobile");
		SpoorUser user = userService.findUserByUserName(mobile);
		if (user == null) {
			map.put("code", 1);
			map.put("message", "登录用户名不存在");
			return map;
		}
		String registrationId = request.getParameter("registrationId");
		SpoorDevice sdFind = spoorDeviceService.findByRegistrationId(
				registrationId, user.getUserId());
		if (sdFind == null) {
			SpoorDevice sd = new SpoorDevice();
			if (null == registrationId || "".equals(registrationId)) {
				map.put("code", 1);
				map.put("message", "registrationId不能为空");
				return map;
			}
			sd.setRegistrationId(registrationId);
			sd.setUserId(user.getUserId());
			int c = spoorDeviceService.loginInsertRegistrationId(sd);
			if (c > 0) {
				if (user != null) {
					int findCount = spoorUnreadService
							.selectCountUnreadByUserId(user.getUserId());
					// if(findCount>0){
					List<SpoorDevice> sdSend = spoorDeviceService
							.findByUserId(user.getUserId());
					for (SpoorDevice i : sdSend) {
						JpushClientUtil.sendToRegistrationId(
								i.getRegistrationId(), "", "", "" + findCount,
								"");
					}

					// }
				}
				map.put("code", "0");
				map.put("message", "绑定成功");
				return map;
			} else {
				map.put("code", 1);
				map.put("message", "绑定失败");
				return map;
			}
		} else {
			if (user != null) {
				int findCount = spoorUnreadService
						.selectCountUnreadByUserId(user.getUserId());
				// if(findCount>0){
				List<SpoorDevice> sdSend = spoorDeviceService.findByUserId(user
						.getUserId());
				for (SpoorDevice i : sdSend) {
					JpushClientUtil.sendToRegistrationId(i.getRegistrationId(),
							"", "", "" + findCount, "");
					// }
				}
			}
			map.put("code", 1);
			map.put("message", "该设备已绑定");
			return map;
		}
	}

	@RequestMapping(value = "/removeSendMessage", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> removeSendMessage(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		String mobile = request.getParameter("mobile");
		String unreadIds = request.getParameter("unreadIds");
		String[] unreadId = unreadIds.split(";");
		SpoorUser user = userService.findUserByUserName(mobile);
		if (user == null) {
			map.put("code", 1);
			map.put("message", "登录用户名不存在");
			return map;
		}
		// int count =
		// spoorUnreadService.deleteSpoorUnreadByUserId(user.getUserId());
		if (unreadId.length > 0) {
			int count = 0;
			for (String i : unreadId) {
				count = count
						+ spoorUnreadService.deleteSpoorUnreadById(Long
								.parseLong(i));
			}
			if (count > 0) {
				if (user != null) {
					int findCount = spoorUnreadService
							.selectCountUnreadByUserId(user.getUserId());
					List<SpoorDevice> sdSend = spoorDeviceService
							.findByUserId(user.getUserId());
					for (SpoorDevice i : sdSend) {
						JpushClientUtil.sendToRegistrationId(
								i.getRegistrationId(), "", "", "" + findCount,
								"");
					}
				}
				map.put("code", 0);
				return map;
			}
		}
		map.put("code", 1);
		return map;
	}

	@RequestMapping(value = "/findUnreadMessage", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findUnreadMessage(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		String mobile = request.getParameter("mobile");
		SpoorUser user = userService.findUserByUserName(mobile);
		if (user == null) {
			map.put("code", 1);
			map.put("message", "登录用户名不存在");
			return map;
		}
		List<SpoorUnread> su = spoorUnreadService
				.selectSpoorUnreadByUserId(user.getUserId());
		List<SpoorComment> list = new ArrayList<SpoorComment>();
		List<SpoorCommentResDto> scrdList = new ArrayList<SpoorCommentResDto>();
		for (SpoorUnread i : su) {
			SpoorComment sc = spoorCommentService.findCommentByCommentId(i
					.getCommentId());
			SpoorCommentResDto scrd = new SpoorCommentResDto();
			scrd.id = sc.getId();
			scrd.unreadId = i.getId();
			scrd.spoorId = sc.getSpoorId();
			scrd.createTime = sc.getCreateTime();
			scrd.userComment = sc.getUserComment();
			SpoorUser suFind = userService.findUserByUserId(sc.getUserId());
			scrd.nickName = suFind.getNickName();
			scrd.userIcon = suFind.getUserIcon();
			scrd.userPhone = suFind.getUserPhone();
			if (sc.getParentId() == 0) {
				scrd.reNickName = "";
				scrd.reUserIcon = "";
				scrd.reUserPhone = "";
			} else {
				SpoorComment scParent = spoorCommentService
						.findCommentByCommentId(sc.getParentId());
				SpoorUser suFindParent = userService.findUserByUserId(scParent
						.getUserId());
				scrd.reNickName = suFindParent.getNickName();
				scrd.reUserIcon = suFindParent.getUserIcon();
				scrd.reUserPhone = suFindParent.getUserPhone();
			}
			scrdList.add(scrd);
			// list.add(sc);
		}
		if (scrdList.size() > 0) {
			map.put("code", 0);
			map.put("message", "查询成功");
			map.put("data", scrdList);
			return map;
		} else {
			map.put("code", 1);
			map.put("message", "未查询到结果");
			return map;
		}

	}
	// @RequestMapping(value = "/modifyUser", method = RequestMethod.GET)
	// @ResponseBody
	// public Map<String,Object> modifyUser(HttpServletRequest
	// request,HttpServletResponse response) {
	// //保存图片
	// MultipartHttpServletRequest multipartRequest =
	// (MultipartHttpServletRequest) request;
	// MultipartFile file = multipartRequest.getFile("file");//头像
	// String fileName = file.getOriginalFilename();
	// //扩展名
	// String fileExtensionName =
	// fileName.substring(fileName.lastIndexOf(".")+1);
	// //防止重名覆盖
	// String uploadFileName =
	// UUID.randomUUID().toString()+"."+fileExtensionName;
	// byte[] b = new byte[1024*1024];
	// try {
	// InputStream is = file.getInputStream();
	// File fileDir = new File(path);
	// //判断文件夹是否存在,如果不存在则创建文件夹
	// if (!fileDir.exists()) {
	// fileDir.mkdir();
	// }
	// FileOutputStream fos = new FileOutputStream(path + uploadFileName);
	//
	// while((is.read(b)) != -1){
	// fos.write(b);
	// }
	// is.close();
	// fos.close();
	// } catch (IOException e) {
	// e.printStackTrace();
	// } catch (Exception e) {
	// e.printStackTrace();
	//
	// }
	//
	//
	//
	// //保存信息
	// Map<String,Object> map = new HashMap<String, Object>();
	// String mobile = request.getParameter("mobile");
	// if(mobile.equals("") || null == mobile){
	// map.put("code", "1");
	// map.put("message", "手机号不能为空");
	// return map;
	// }
	// SpoorUser sp = userService.findUserByUserName(mobile);
	// if(sp == null){
	// map.put("code", "1");
	// map.put("message", "您查找的用户不存在");
	// return map;
	// }
	// String nickname = request.getParameter("nickname");
	// String gender = request.getParameter("gender");
	// String blood = request.getParameter("blood");
	// String birthday = request.getParameter("birthday");
	// String wx = request.getParameter("wx");
	// String qq = request.getParameter("qq");
	// String mail = request.getParameter("mail");
	// SpoorUser su = new SpoorUser();
	// su.setNickName(nickname);
	// su.setUserGender(gender);
	// su.setBloodType(blood);
	// /// SimpleDateFormat sdf = new SimpleDateFormat();
	// SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy年MM月dd日");
	// Date date_birthday = null;
	// try {
	// date_birthday = sdf1.parse(birthday);
	// System.out.println(date_birthday.toString());
	// } catch (ParseException e1) {
	// // TODO Auto-generated catch block
	// e1.printStackTrace();
	// }
	// su.setUserBirthday(date_birthday);
	// su.setUserWx(wx);
	// su.setUserQq(qq);
	// su.setUserMail(mail);
	// su.setUserIcon(path+uploadFileName);
	// su.setUserPhone(Long.parseLong(mobile));
	// int count = userService.updateUser(su);
	// SpoorUser sp_update = userService.findUserByUserName(mobile);
	// if(count > 0 ){
	// map.put("code", "0");
	// map.put("message", "修改成功");
	// map.put("data", sp_update);
	// return map;
	// }
	// map.put("code", "1");
	// map.put("message", "修改失败");
	// map.put("data", sp_update);
	// return map;
	// }
	//
}
