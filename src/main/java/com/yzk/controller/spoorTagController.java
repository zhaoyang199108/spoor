package com.yzk.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yzk.dto.SpoorTagDto;
import com.yzk.entity.SpoorTag;
import com.yzk.service.SpoorTagService;

@Controller
@RequestMapping("/spoorTag")
public class spoorTagController {

	private @Autowired SpoorTagService spoorTagService;
	@RequestMapping(value = "/findTag", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object>  findTag(HttpServletRequest request,HttpServletResponse response) {
		String newRange = request.getParameter("newRange");
		String hotRange = request.getParameter("hotRange");
		if(null == newRange || "".equals(newRange)){
			newRange = "15";
		}
		if(null == hotRange || "".equals(hotRange)){
			hotRange = "15";
		}
		List<SpoorTag> listHot = spoorTagService.findHotTag(Integer.parseInt(hotRange));
		List<SpoorTag> listNew = spoorTagService.findNewTag(Integer.parseInt(newRange));
		SpoorTagDto std = new SpoorTagDto();
		std.hotTag = listHot;
		std.newTag = listNew;
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("code", 0);
		map.put("message", "查询成功");
		map.put("data", std);
		return map;
	}
}
