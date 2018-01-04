package com.yzk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/spoorDevice")
public class spoorDeviceController {
//	private @Autowired SpoorDeviceService spoorDeviceService;
//	@RequestMapping(value = "/deleteSpoorDevice", method = RequestMethod.GET)
//	@ResponseBody
//	public Map<String,Object>  deleteSpoorDevice(HttpServletRequest request,HttpServletResponse response) {
//		Map<String,Object> map = new HashMap<String, Object>();
//		String  registrationId = request.getParameter("registrationId");
//		if (null == registrationId || "".equals(registrationId)) {
//			map.put("code", 0);
//			map.put("message", "请允许通知权限");
//			return map;
//		}
//		int count = spoorDeviceService.deleteByRegistrationId(registrationId);
//		if(count > 0 ){}
//		return map;
//
//	}
}
