package com.yzk.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yzk.dto.SpoorCommentBySelfDto;
import com.yzk.dto.SpoorCommentResDto;
import com.yzk.entity.SpoorComment;
import com.yzk.entity.SpoorDevice;
import com.yzk.entity.SpoorSpoor;
import com.yzk.entity.SpoorUnread;
import com.yzk.entity.SpoorUser;
import com.yzk.service.SpoorCommentService;
import com.yzk.service.SpoorDeviceService;
import com.yzk.service.SpoorService;
import com.yzk.service.SpoorUnreadService;
import com.yzk.service.UserService;
import com.yzk.util.JpushClientUtil;

@Controller
@RequestMapping("/comment")
public class spoorCommentController {

	private @Autowired UserService userService;
	private @Autowired SpoorCommentService spoorCommentService;
	private @Autowired SpoorDeviceService spoorDeviceService;
	private @Autowired SpoorUnreadService spoorUnreadService;
	private @Autowired SpoorService spoorService;
	@RequestMapping(value = "/addComment", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addComment(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		String spoorId = request.getParameter("spoorId");
		String mobile = request.getParameter("mobile");
		String reply = request.getParameter("reply");
		String content = request.getParameter("content");
		if (mobile == null || "".equals(mobile)) {
			map.put("code", 1);
			map.put("message", "手机号为空");
			return map;
		}
		SpoorUser su = userService.findUserByUserName(mobile);
		if (su == null) {
			map.put("code", 1);
			map.put("message", "您输入的用户名不存在");
			return map;
		}
		if (spoorId == null || "".equals(spoorId)) {
			map.put("code", 1);
			map.put("message", "足迹id为空");
			return map;
		}
		if (reply == null || "".equals(reply)) {
			reply = "0";
		}
		if (content == null || "".equals(content)) {
			map.put("code", 1);
			map.put("message", "评论内容为空");
			return map;
		}
		SpoorComment sc = new SpoorComment();
		sc.setSpoorId(Long.parseLong(spoorId));
		sc.setUserComment(content);
		sc.setUserId(su.getUserId());
		sc.setParentId(Long.parseLong(reply));
		int count = spoorCommentService.addComment(sc);
		if (count > 0) {
			SpoorComment scTemp = new SpoorComment();
			scTemp.setUserId(su.getUserId());
			SpoorComment comment = spoorCommentService.findCommentIdByUserId(scTemp);
			
			List<SpoorCommentResDto> list = spoorCommentService
					.findSpoorCommentDetail(Long.parseLong(spoorId));
			Set<String> strStr = new HashSet<String>();
			for (SpoorCommentResDto i : list) {
				if (i.userPhone != null || !"".equals(i))
					strStr.add(i.userPhone);
				if (i.reUserPhone != null || !"".equals(i))
					strStr.add(i.reUserPhone);
			}
			//TODO
			SpoorSpoor ss = spoorService.findSpoorBySpoorId(Long.parseLong(spoorId));
			SpoorUser suSpoorOwn = userService.findUserByUserId(ss.getUserId());
			if(suSpoorOwn.getUserPhone().equals(mobile)){//自己
				for(String i : strStr){
					if(i.equals(mobile)){
						strStr.remove(i);
						break;
					}
				}
			}else{//别人评论
				for(String i : strStr){
					if(i.equals(mobile)){
						strStr.remove(mobile);
						break;
					}
				}
				strStr.add(suSpoorOwn.getUserPhone());
			}
			for (String i : strStr) {
				SpoorUser suFind = userService.findUserByUserName(i);
				// TODO
				if (suFind != null) {
					SpoorUnread sUnread = new SpoorUnread();
					sUnread.setCommentId(comment.getId());
					sUnread.setUserId(suFind.getUserId());
					spoorUnreadService.insertIntoSpoorUnread(sUnread);
					List<SpoorDevice> sd = spoorDeviceService.findByUserId(suFind
							.getUserId());
//					int unReadCount = spoorCommentService
//							.selectCountUnRead(suFind.getUserId());
//					int jPushCount = JpushClientUtil.sendToRegistrationId(
//							sd.getRegistrationId(), "内容", "评论", content, ""
//									+ unReadCount);
					if(sd != null){
					int findCount = spoorUnreadService.selectCountUnreadByUserId(suFind.getUserId());
					for(SpoorDevice temp : sd){
						JpushClientUtil.sendToRegistrationId(temp.getRegistrationId(), "", "", ""+findCount, "");
						}
					}
				}
			}
			map.put("code", 0);
			map.put("message", "评论成功");
			return map;
		}
		map.put("code", 1);
		map.put("message", "评论失败");
		return map;
	}

	@RequestMapping(value = "/findAllComment", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> findAllComment(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		String spoorId = request.getParameter("spoorId");
		if (spoorId == null || "".equals(spoorId)) {
			map.put("code", 1);
			map.put("message", "足迹id为空");
			return map;
		}
		int count = spoorCommentService.findAllComment(Long.parseLong(spoorId));
		map.put("code", 0);
		map.put("message", "查询成功");
		map.put("data", count);

		return map;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/findCommentList", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findCommentList(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		String mobile = request.getParameter("mobile");
		if (mobile == null || "".equals(mobile)) {
			map.put("code", 1);
			map.put("message", "手机号为空");
			return map;
		}
		SpoorUser su = userService.findUserByUserName(mobile);
		if (su == null) {
			map.put("code", 1);
			map.put("message", "您输入的用户名不存在");
			return map;
		}
		Set<Long> list = spoorCommentService.findMyCommentList(mobile);
		List<SpoorCommentBySelfDto> listRes = new ArrayList<SpoorCommentBySelfDto>();
		for (Long i : list) {
			listRes.addAll(spoorCommentService.findSpoorCommentSelf(i));
		}
		Collections.sort(listRes, new MySort());
		map.put("code", 0);
		map.put("message", "查询成功");
		map.put("data", listRes);
		return map;
	}

	@SuppressWarnings("rawtypes")
	class MySort implements Comparator {
		public int compare(Object object1, Object object2) {// 实现接口中的方法
			SpoorCommentBySelfDto p1 = ((SpoorCommentBySelfDto) object1); // 强制转换
			SpoorCommentBySelfDto p2 = ((SpoorCommentBySelfDto) object2);
			return p2.createTime.compareTo(p1.createTime);
		}
	}
}
