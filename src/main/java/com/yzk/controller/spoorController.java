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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.yzk.dto.SpoorDetailResDto;
import com.yzk.dto.SpoorResDto;
import com.yzk.entity.SpoorSpoor;
import com.yzk.entity.SpoorTag;
import com.yzk.entity.SpoorUser;
import com.yzk.service.SpoorService;
import com.yzk.service.SpoorTagService;
import com.yzk.service.UserService;

@Controller
@RequestMapping("/spoor")
public class spoorController {
	@SuppressWarnings("unused")
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private @Autowired SpoorService spoorService;
	private @Autowired UserService userService;
	private @Autowired SpoorTagService spoorTagService;
	@RequestMapping(value = "/insertSpoor", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object>  insertSpoor(HttpServletRequest request,HttpServletResponse response) {
		MultipartHttpServletRequest multipartRequest  =  (MultipartHttpServletRequest) request;  
		List<MultipartFile> files  =  multipartRequest.getFiles("file");//音乐
		for(MultipartFile item : files){
			System.out.println(item.getOriginalFilename());
		}
//		String path = request.getSession().getServletContext().getRealPath("upload");
		Map<String,Object> map = new HashMap<String, Object>();
		String spoorContent = request.getParameter("spoorContent");
		String spoorTagOne = request.getParameter("spoorTagOne");
		if(null != spoorTagOne && !"".equals(spoorTagOne) ){
			SpoorTag st = new SpoorTag();
			st.setContext(spoorTagOne);
			SpoorTag stFind = spoorTagService.findRepeat(st);
			if(stFind == null){
				spoorTagService.insertTag(st);
			}else{
				int s = Integer.parseInt(stFind.getTime())+1;
				SpoorTag st1 = new SpoorTag();
				st1.setContext(spoorTagOne);
				st1.setTime(s+"");
				spoorTagService.updateTime(st1);
			}
		}
		String spoorTagTwo = request.getParameter("spoorTagTwo");
		if(null != spoorTagTwo && !"".equals(spoorTagTwo) ){
			SpoorTag st = new SpoorTag();
			st.setContext(spoorTagTwo);
			SpoorTag stFind = spoorTagService.findRepeat(st);
			if(stFind == null){
				spoorTagService.insertTag(st);
			}else{
				int s = Integer.parseInt(stFind.getTime())+1;
				SpoorTag st1 = new SpoorTag();
				st1.setContext(spoorTagTwo);
				st1.setTime(s+"");
				spoorTagService.updateTime(st1);
			}
		}
		String spoorTagThree = request.getParameter("spoorTagThree");
		if(null != spoorTagThree && !"".equals(spoorTagThree) ){
			SpoorTag st = new SpoorTag();
			st.setContext(spoorTagThree);
			SpoorTag stFind = spoorTagService.findRepeat(st);
			if(stFind == null){
				spoorTagService.insertTag(st);
			}else{
				int s = Integer.parseInt(stFind.getTime())+1;
				SpoorTag st1 = new SpoorTag();
				st1.setContext(spoorTagThree);
				st1.setTime(s+"");
				spoorTagService.updateTime(st1);
			}
		}
		String mobile = request.getParameter("mobile");
		SpoorUser su = userService.findUserByUserName(mobile);
		if(su == null){
			map.put("code", 1);
			map.put("message", "您输入的用户名不存在");
			return map;
		}
		long userId = su.getUserId();
		SpoorSpoor  ss = new SpoorSpoor();
		ss.setSpoorContent(spoorContent);
		ss.setSpoorTagOne(spoorTagOne);
		ss.setSpoorTagTwo(spoorTagTwo);
		ss.setSpoorTagThree(spoorTagThree);
		ss.setUserId(userId);
		int count = spoorService.insertSpoor(ss,files,"/upload/img/");
		if(count > 0){
			map.put("code", 0);
			map.put("message", "插入成功");
			return map;
		}
		map.put("code", 1);
		map.put("message", "插入失败");
		return map;
	}
	@RequestMapping(value = "/findSpoorByUser", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object>  findSpoorByUser(HttpServletRequest request,HttpServletResponse response) {
		Map<String,Object> map = new HashMap<String, Object>();
		String mobile = request.getParameter("mobile");
		if(mobile == null){
			List<SpoorResDto> listSpoor = spoorService.findAll();
			if(listSpoor.size() == 0){
				map.put("code", 1);
				map.put("message", "未查询到记录");
				return map;
			}
			map.put("code", 0);
			map.put("message", "查询成功");
			map.put("data", listSpoor);
			return map;
		}
		SpoorUser su = userService.findUserByUserName(mobile);
		if(su == null){
//			List<SpoorResDto> listSpoor = spoorService.findAll(mobile);
			map.put("code", 1);
			map.put("message", "用户名不存在");
//			map.put("data", listSpoor);
			return map;
		}
		if(su.getIsSetting().equals("1")){//1设置标签，0未设置
			String tagOne = su.getUserTagOne();
			String tagTwo = su.getUserTagTwo();
			String tagThree = su.getUserTagThree();
			List<SpoorResDto> listSpoorTag = spoorService.findSpoorByTags(tagOne,tagTwo,tagThree,mobile);
			map.put("code", 0);
			map.put("message", "查询成功");
			map.put("data", listSpoorTag);
			return map;
		}
		
		List<SpoorResDto> listSpoor = spoorService.findAll(mobile);
		map.put("code", 0);
		map.put("message", "查询成功");
		map.put("data", listSpoor);
		return map;
	}
	//TODO分页加载
	@RequestMapping(value = "/findSpoorByLoginId", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object>  findSpoorByLoginId(HttpServletRequest request,HttpServletResponse response) {
		Map<String,Object> map = new HashMap<String, Object>();
		String loginId = request.getParameter("loginId");//是否登录
		String mobile = request.getParameter("mobile");
		if(loginId == null){
			if (mobile == null || "".equals(mobile)) {
				map.put("code", 1);// 0成功1失败
				map.put("message", "手机号为空");
				return map;
			}
			SpoorUser su = userService.findUserByUserName(mobile);
			if(su == null){
				map.put("code", 1);
				map.put("message", "您输入的用户名不存在");
				return map;
			}
			List<SpoorResDto> list =  spoorService.findSpoorByMobile(mobile);
			map.put("code", 0);
			map.put("message", "查询成功");
			map.put("data", list);
			return map;	
		}else{
			if (mobile == null || "".equals(mobile)) {
				map.put("code", 1);// 0成功1失败
				map.put("message", "手机号为空");
				return map;
			}
			SpoorUser su = userService.findUserByUserName(mobile);
			if(su == null){
				map.put("code", 1);
				map.put("message", "您输入的用户名不存在");
				return map;
			}
			List<SpoorResDto> list =  spoorService.findSpoorByMobile(mobile,loginId);
			map.put("code", 0);
			map.put("message", "查询成功");
			map.put("data", list);
			return map;	
		}
		
	}
	@RequestMapping(value = "/findSpoorByTag", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object>  findSpoorByTag(HttpServletRequest request,HttpServletResponse response) {
		Map<String,Object> map = new HashMap<String, Object>();
		String loginId = request.getParameter("loginId");
		String tag = request.getParameter("tag");
		if(loginId == null){
			List<SpoorResDto> list = spoorService.findSpoorByTag(tag);
			map.put("code", 0);
			map.put("message", "查询成功");
			map.put("data", list);
			return map;	
		}
		else{
			List<SpoorResDto> list = spoorService.findSpoorByTag(tag,loginId);
			map.put("code", 0);
			map.put("message", "查询成功");
			map.put("data", list);
			return map;	
		}
		
	}
	
	@RequestMapping(value = "/findSpoorDetail", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object>  findSpoorDetail(HttpServletRequest request,HttpServletResponse response) {
		Map<String,Object> map = new HashMap<String, Object>();
		String spoorId = request.getParameter("spoorId");
		if (spoorId == null || "".equals(spoorId)) {
			map.put("code", 1);// 0成功1失败
			map.put("message", "spoorId不能为空");
			return map;
		}
		String mobile = request.getParameter("mobile");
//		if (mobile == null || "".equals(mobile)) {
//			map.put("code", 1);// 0成功1失败
//			map.put("message", "手机号为空");
//			return map;
//		}
		SpoorDetailResDto list = spoorService.findSpoorDetail(spoorId,mobile);
		map.put("code", 0);
		map.put("message", "查询成功");
		map.put("data", list);
		return map;
	}
	@RequestMapping(value = "/deleteSpoor", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object>  deleteSpoor(HttpServletRequest request,HttpServletResponse response) {
		Map<String,Object> map = new HashMap<String, Object>();
		String spoorId = request.getParameter("spoorId");
		if (spoorId == null || "".equals(spoorId)) {
			map.put("code", 1);// 0成功1失败
			map.put("message", "spoorId不能为空");
			return map;
		}
		int count = spoorService.deleteSpoorBySpoorId(Long.parseLong(spoorId));
		if(count > 0){
			map.put("code", 0);
			map.put("message", "删除成功");
			return map;
		}
		map.put("code", 1);
		map.put("message", "删除失败");
		return map;
	}
//	private FTPClient ftpclient;
//	public FTPClient getFtpclient() {
//		return ftpclient;
//	}
//	public void setFtpclient(FTPClient ftpclient) {
//		this.ftpclient = ftpclient;
//	}
//	@RequestMapping(value = "/ftp", method = RequestMethod.GET)
//	@ResponseBody
//	public Map<String,Object>  ftp(HttpServletRequest request,HttpServletResponse response) throws IOException {
//		FileInputStream fis = null;
//		//链接FTP服务器
//		if(connectServer("47.94.167.40", 21, "ftpuser", "123456")){
//			try {
//				boolean flag = ftpclient.changeWorkingDirectory("/img");
//				System.out.println("切换文件件--->"+flag);
//				ftpclient.setBufferSize(1024);
//				ftpclient.setControlEncoding("UTF-8");
//				//防止乱码
//				boolean fileType = ftpclient.setFileType(FTPClient.BINARY_FILE_TYPE);
//				System.out.println("设置文件类型--->"+fileType);
//				//打开被动模式
//				//boolean passive = ftpclient.enterRemotePassiveMode();
//				//System.out.println("打开被动模式--->"+passive);
//
//					fis = new FileInputStream(new File("E:/1.jpg"));
//					boolean FtpResult = ftpclient.storeFile("hello.jpg",fis);
//					System.out.println(ftpclient.getReplyCode());
//					System.out.println(ftpclient.getReplyString());
////					boolean FtpResult = ftpclient.storeFile(fileItem.getName(), fis);
//					System.out.println("上传文件的结果是-->"+FtpResult);
//
//				
//			} catch (IOException e) {
//				logger.error("上传文件异常",e);
//			}finally{
//				fis.close();
//				ftpclient.disconnect();
//			}
//		}
//		return null;
//	}
//	private boolean connectServer(String ip,int port,String user,String psw){
//		boolean isSuccess = false;
//		ftpclient = new FTPClient();
//		try {
//			ftpclient.connect(ip);
//			isSuccess = ftpclient.login(user, psw);
//			logger.info("链接服务器--->"+isSuccess);
//		} catch (SocketException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			logger.error("链接服务器异常",e);
//		}
//		return isSuccess;
//	}
}
