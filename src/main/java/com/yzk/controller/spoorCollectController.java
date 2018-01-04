package com.yzk.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yzk.dto.SpoorResDto;
import com.yzk.entity.SpoorCollect;
import com.yzk.entity.SpoorUser;
import com.yzk.service.SpoorCollectService;
import com.yzk.service.UserService;

@Controller
@RequestMapping("/spoorCollect")
public class spoorCollectController {

	@SuppressWarnings("unused")
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private @Autowired SpoorCollectService spoorCollectService;
	private @Autowired UserService userService;

	@RequestMapping(value = "/spoorCollect", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> spoorCollect(HttpServletRequest request,
			HttpServletResponse response) {
		String currentPage = request.getParameter ("currentPage");
		String pageSize = request.getParameter("pageSize");
		if(currentPage == null||"".equals(currentPage)){
			currentPage = "0";
		}
		if(pageSize == null||"".equals(pageSize)){
			pageSize = "20";
		}
		Map<String, Object> map = new HashMap<String, Object>();
		String mobile = request.getParameter("mobile");
		if (mobile == null || "".equals(mobile)) {
			map.put("code", 1);// 0成功1失败
			map.put("message", "手机号为空");
			return map;
		}
		String collect = request.getParameter("collect");
		if (collect == null || "".equals(collect)) {
			map.put("code", 1);// 0成功1失败
			map.put("message", "参数错误");
			return map;
		}
		String spoorId = request.getParameter("spoorId");
		if (spoorId == null || "".equals(spoorId)) {
			map.put("code", 1);// 0成功1失败
			map.put("message", "spoorId不能为空");
			return map;
		}
		
		SpoorUser user = userService.findUserByUserName(mobile);
		if (user == null) {
			map.put("code", 1);
			map.put("message", "登陆用户名不存在");
			return map;
		}
		if (collect.equals("1")) {
			SpoorCollect sc = new SpoorCollect();
			sc.setUserId(user.getUserId());
			sc.setSpoorId(Long.parseLong(spoorId));
			sc.setCollect(collect);
			int count = spoorCollectService.spoorCollect(sc);
			if (count > 0) {
				map.put("code", 0);
				map.put("message", "收藏成功");
				return map;
			}
			map.put("code", 1);
			map.put("message", "收藏失败");
			return map;
		} else {
			int count = spoorCollectService.deleteCollect(user.getUserId(), Long.parseLong(spoorId));
			if (count > 0) {
				SpoorUser userFind = userService.findUserByUserName(mobile);
				if (userFind == null) {
					map.put("code", 1);
					map.put("message", "登陆用户名不存在");
					return map;
				}
				List<SpoorResDto> list = spoorCollectService.findCollectByMobile(userFind
						.getUserId(),Integer.parseInt(currentPage)*Integer.parseInt(pageSize),Integer.parseInt(pageSize));
				map.put("code", 0);
				map.put("message", "取消收藏成功");
				map.put("data", list);
				return map;
			}
			map.put("code", 1);
			map.put("message", "取消收藏失败");
			return map;
		}
	}

	@RequestMapping(value = "/findSpoorCollect", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findSpoorCollect(HttpServletRequest request,
			HttpServletResponse response) {
		String currentPage = request.getParameter ("currentPage");
		String pageSize = request.getParameter("pageSize");
		if(currentPage == null||"".equals(currentPage)){
			currentPage = "0";
		}
		if(pageSize == null||"".equals(pageSize)){
			pageSize = "20";
		}
		Map<String, Object> map = new HashMap<String, Object>();
		String mobile = request.getParameter("mobile");
		if (mobile == null || "".equals(mobile)) {
			map.put("code", 1);// 0成功1失败
			map.put("message", "手机号为空");
			return map;
		}
		SpoorUser user = userService.findUserByUserName(mobile);
		if (user == null) {
			map.put("code", 1);
			map.put("message", "登陆用户名不存在");
			return map;
		}
		List<SpoorResDto> list = spoorCollectService.findCollectByMobile(user
				.getUserId(),Integer.parseInt(currentPage)*Integer.parseInt(pageSize),Integer.parseInt(pageSize));
		map.put("code", 0);
		map.put("message", "查询成功");
		map.put("data", list);
		return map;
	}
}
