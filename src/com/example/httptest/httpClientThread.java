package com.example.httptest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.content.Entity;

/*
 * httpClient是apache的一个开源项目，可以进行查询学习，android中集成了
 */
public class httpClientThread extends Thread {
	private String name,age,url;
	
	public httpClientThread(String name, String age, String url) {
		this.name = name;
		this.age = age;
		this.url = url;
	}
	//get方式请求
	private void doGet(){
		url=url+"?name="+name+"&age="+age;
		//创建HttpGet对象
		HttpGet httpGet=new HttpGet(url);
		//创建HttpClient对象
		HttpClient client=new DefaultHttpClient();
		HttpResponse respose;
		try {
			//发送请求
			respose=client.execute(httpGet);
			//判断请求结果码，如果ok这执行
			if (respose.getStatusLine().getStatusCode()==HttpStatus.SC_OK) {
				//取出服务器返回的数据
				String content=EntityUtils.toString(respose.getEntity());
				System.out.println("content---->"+content);
			}
		} catch (ClientProtocolException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	//post方式请求
	private void doPost(){
		HttpClient client=new DefaultHttpClient();
		HttpPost post=new HttpPost(url);
		//添加要传递的参数，通过BasicNameValuePair存储数据
		ArrayList<NameValuePair>list=new ArrayList<NameValuePair>();
		list.add(new BasicNameValuePair("name",name));
		list.add(new BasicNameValuePair("age",age));
		try {
			//httpclient方式的post传递中文需要转码
			post.setEntity(new UrlEncodedFormEntity(list,HTTP.UTF_8));
			//发送请求
			HttpResponse respose=client.execute(post);
			if (respose.getStatusLine().getStatusCode()==HttpStatus.SC_OK) {
				//获取返回的值
				String content=EntityUtils.toString(respose.getEntity());
				System.out.println("content---->"+content);
			}
		} catch (UnsupportedEncodingException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	@Override
	public void run() {
		// TODO 自动生成的方法存根
		super.run();
//		doGet();
		doPost();
	}
}
