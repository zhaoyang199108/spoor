package com.yzk.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

public class UploadFileUtil {
	private static final String path = PropertiesUtil
			.getProperty("http.server.img.ip");
	public String uploadFile(MultipartFile file){
		String message="";
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
			message=uploadFileName;
			is.close();
			fos.close();
		} catch (IOException e) {
			message="上传失败";
			e.printStackTrace();
		} catch (Exception e) {
			message="上传失败";
			e.printStackTrace();

		}
		return message;
	}
}
