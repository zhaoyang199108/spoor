package com.yzk.util;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class SendMessageUtil {

	public String  sendMessage(String mobile){
		 String reMessage = null;
		 DefaultHttpClient httpClient = new DefaultHttpClient();
	        String url = "https://api.netease.im/sms/sendcode.action";
	        HttpPost httpPost;
	        HttpResponse response = null;
			try {
				httpPost = new HttpPost(url);
	        String appKey = "222f78ad503e74f8c55527834497bf73";
	        String appSecret = "5eb37299dbe4";
	        String nonce =  "12345";
	        String curTime = String.valueOf((new Date()).getTime() / 1000L);
	        String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce ,curTime);//参考 计算CheckSum的java代码

	        // 设置请求的header
	        httpPost.addHeader("AppKey", appKey);
	        httpPost.addHeader("Nonce", nonce);
	        httpPost.addHeader("CurTime", curTime);
	        httpPost.addHeader("CheckSum", checkSum);
	        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

	        // 设置请求的参数
	        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
	        nvps.add(new BasicNameValuePair("mobile", mobile));
	        nvps.add(new BasicNameValuePair("codeLen", "6"));
	        httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));

	        // 执行请求
	        response = httpClient.execute(httpPost);
	        reMessage = EntityUtils.toString(response.getEntity(), "utf-8");
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
			} catch (HttpException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
			}
			return reMessage;
	}
	public String volidateMessage(String mobile,String code){
		 String reMessage = null;
		 DefaultHttpClient httpClient = new DefaultHttpClient();
	        String url = "https://api.netease.im/sms/verifycode.action";
	        HttpPost httpPost;
	        HttpResponse response = null;
			try {
				httpPost = new HttpPost(url);
	        String appKey = "222f78ad503e74f8c55527834497bf73";
	        String appSecret = "5eb37299dbe4";
	        String nonce =  "12345";
	        String curTime = String.valueOf((new Date()).getTime() / 1000L);
	        String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce ,curTime);//参考 计算CheckSum的java代码

	        // 设置请求的header
	        httpPost.addHeader("AppKey", appKey);
	        httpPost.addHeader("Nonce", nonce);
	        httpPost.addHeader("CurTime", curTime);
	        httpPost.addHeader("CheckSum", checkSum);
	        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

	        // 设置请求的参数
	        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
	        nvps.add(new BasicNameValuePair("mobile", mobile));
	        nvps.add(new BasicNameValuePair("code", code));
	        httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));

	        // 执行请求
	        response = httpClient.execute(httpPost);
	        reMessage = EntityUtils.toString(response.getEntity(), "utf-8");
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
			} catch (HttpException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
			}
			return reMessage;
	}
}