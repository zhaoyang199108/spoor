package com.yzk.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;
import com.yzk.service.IFileService;
import com.yzk.util.FTPUtil;
@Service
public class FileServiceImpl implements IFileService {
	private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);
	public String upload(MultipartFile file , String path){
		String fileName = file.getOriginalFilename();
		//扩展名
		String fileExtensionName = fileName.substring(fileName.lastIndexOf(".")+1);
		//防止重名覆盖
		String uploadFileName = UUID.randomUUID().toString()+"."+fileExtensionName;
		logger.info("开始上传文件,上传文件的文件名:{},上传的路径是:{},新文件名:{}",fileName,path,uploadFileName);
		File fileDir = new File(path);
		if(!fileDir.exists()){
			fileDir.setWritable(true);
			fileDir.mkdirs();
		}
		File targetFile = new File(path,uploadFileName);
		try {
			file.transferTo(targetFile);
			//文件上传成功 
			FTPUtil.uploadFile(Lists.newArrayList(targetFile));
			//已经上传到FTP文件服务器
			//删除upload下的文件
			//targetFile.delete();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			logger.error("上传文件异常",e);
		}
		
		return targetFile.getName();
	}
}
