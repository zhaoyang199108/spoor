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
import com.yzk.entity.SpoorBenefit;
import com.yzk.entity.SpoorUser;
import com.yzk.service.SpoorBenefitService;
import com.yzk.service.UserService;

@Controller
@RequestMapping("/spoorBenefit")
public class spoorBenefitController {
	@SuppressWarnings("unused")
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private @Autowired UserService userService;
	private @Autowired SpoorBenefitService spoorBenefitService;
	@RequestMapping(value = "/spoorBenefit", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> spoorBenefit(HttpServletRequest request,
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
		String benefit = request.getParameter("benefit");
		if (benefit == null || "".equals(benefit)) {
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
		if (benefit.equals("1")) {
			SpoorBenefit sb= new SpoorBenefit();
			sb.setUserId(user.getUserId());
			sb.setSpoorId(Long.parseLong(spoorId));
			sb.setUserBenefit(benefit);
			SpoorBenefit sbFind = spoorBenefitService.findBenefitByUserIdAndSpoorId(Long.parseLong(spoorId), user.getUserId());
			if(sbFind == null){
				int count = spoorBenefitService.spoorBenefit(sb);
				if (count > 0) {
					map.put("code", 0);
					map.put("message", "受益成功");
					map.put("data", count);
					return map;
				}
				map.put("code", 1);
				map.put("message", "受益失败");
				return map;
			}else
			{
				map.put("code", 1);
				map.put("message", "受益失败,您已受益，不能再次受益");
				return map;
			}
		} else {
			int count = spoorBenefitService.deleteBenefit(user.getUserId(), Long.parseLong(spoorId));
			if (count > 0) {
				SpoorUser userFind = userService.findUserByUserName(mobile);
				if (userFind == null) {
					map.put("code", 1);
					map.put("message", "登陆用户名不存在");
					return map;
				}
//				List<SpoorResDto> list = spoorBenefitService.findBenefitByMobile(userFind
//						.getUserId(),Integer.parseInt(currentPage)*Integer.parseInt(pageSize),Integer.parseInt(pageSize));
				int countBenefits = spoorBenefitService.findAllBenefits(Long.parseLong(spoorId));
				map.put("code", 0);
				map.put("message", "取消受益成功");
				map.put("data", countBenefits);
				return map;
			}
			map.put("code", 1);
			map.put("message", "取消受益失败");
			return map;
		}
	}

	@RequestMapping(value = "/findSpoorBenefit", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findSpoorBenefit(HttpServletRequest request,
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
		List<SpoorResDto> list = spoorBenefitService.findBenefitByMobile(user
				.getUserId(),Integer.parseInt(currentPage)*Integer.parseInt(pageSize),Integer.parseInt(pageSize));
		map.put("code", 0);
		map.put("message", "查询成功");
		map.put("data", list);
		return map;
	}
	
	
	
}
