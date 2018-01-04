package com.yzk.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.SocketException;
import java.util.List;

import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yzk.service.impl.FileServiceImpl;

public class FTPUtil {
	private static final  Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);
	private static String ftpIp = PropertiesUtil.getProperty("ftp.server.ip");
	private static String ftpUser = PropertiesUtil.getProperty("ftp.user");
	private static String ftpPass = PropertiesUtil.getProperty("ftp.pass");
	public FTPUtil(String ip,int port,String user,String psw){
		this.ip = ip ;
		this.port = port;
		this.user = user;
		this.psw = psw;
	}
	public static boolean uploadFile(List<File> fileList) throws IOException{
		FTPUtil ftpUtil = new FTPUtil(ftpIp, 21, ftpUser, ftpPass);
		logger.info("开始链接ftp服务器");
		boolean result = ftpUtil.uploadFile("img", fileList);
		logger.info("开始链接ftp服务器,结束上传,上传结果:{}",result);
		return result;
	}
	private boolean uploadFile(String remotePath,List<File> fileList) throws IOException{
		boolean uploaded = true;
		FileInputStream fis = null;
		//链接FTP服务器
		if(connectServer(this.ip, this.port, this.user, this.psw)){
			try {
				boolean flag = ftpclient.changeWorkingDirectory(remotePath);
				System.out.println("切换文件件--->"+flag);
				ftpclient.setBufferSize(1024);
				ftpclient.setControlEncoding("UTF-8");
				//防止乱码
				boolean fileType = ftpclient.setFileType(FTPClient.BINARY_FILE_TYPE);
				System.out.println("设置文件类型--->"+fileType);
				//打开被动模式
				//boolean passive = ftpclient.enterRemotePassiveMode();
				//System.out.println("打开被动模式--->"+passive);
				for(File fileItem : fileList){
					fis = new FileInputStream(fileItem);
					boolean FtpResult = ftpclient.storeFile(fileItem.getName(),fis);
//					boolean FtpResult = ftpclient.storeFile(fileItem.getName(), fis);
					System.out.println("上传文件的结果是-->"+FtpResult);

				}
			} catch (IOException e) {
				logger.error("上传文件异常",e);
				uploaded = false;
			}finally{
				fis.close();
				ftpclient.disconnect();
			}
		}
		return uploaded;
	}
	//链接FTP服务器
	private boolean connectServer(String ip,int port,String user,String psw){
		boolean isSuccess = false;
		ftpclient = new FTPClient();
		try {
			ftpclient.connect(ip);
			isSuccess = ftpclient.login(user, psw);
			
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			logger.error("链接服务器异常",e);
		}
		return isSuccess;
	}
	private String ip;
	private int port;
	private String user;
	private String psw;
	private FTPClient ftpclient;
	public static String getFtpIp() {
		return ftpIp;
	}
	public static void setFtpIp(String ftpIp) {
		FTPUtil.ftpIp = ftpIp;
	}
	public static String getFtpUser() {
		return ftpUser;
	}
	public static void setFtpUser(String ftpUser) {
		FTPUtil.ftpUser = ftpUser;
	}
	public static String getFtpPass() {
		return ftpPass;
	}
	public static void setFtpPass(String ftpPass) {
		FTPUtil.ftpPass = ftpPass;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPsw() {
		return psw;
	}
	public void setPsw(String psw) {
		this.psw = psw;
	}
	public FTPClient getFtpclient() {
		return ftpclient;
	}
	public void setFtpclient(FTPClient ftpclient) {
		this.ftpclient = ftpclient;
	}
	
} 
