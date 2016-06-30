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
 * httpClient��apache��һ����Դ��Ŀ�����Խ��в�ѯѧϰ��android�м�����
 */
public class httpClientThread extends Thread {
	private String name,age,url;
	
	public httpClientThread(String name, String age, String url) {
		this.name = name;
		this.age = age;
		this.url = url;
	}
	//get��ʽ����
	private void doGet(){
		url=url+"?name="+name+"&age="+age;
		//����HttpGet����
		HttpGet httpGet=new HttpGet(url);
		//����HttpClient����
		HttpClient client=new DefaultHttpClient();
		HttpResponse respose;
		try {
			//��������
			respose=client.execute(httpGet);
			//�ж��������룬���ok��ִ��
			if (respose.getStatusLine().getStatusCode()==HttpStatus.SC_OK) {
				//ȡ�����������ص�����
				String content=EntityUtils.toString(respose.getEntity());
				System.out.println("content---->"+content);
			}
		} catch (ClientProtocolException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	}
	//post��ʽ����
	private void doPost(){
		HttpClient client=new DefaultHttpClient();
		HttpPost post=new HttpPost(url);
		//���Ҫ���ݵĲ�����ͨ��BasicNameValuePair�洢����
		ArrayList<NameValuePair>list=new ArrayList<NameValuePair>();
		list.add(new BasicNameValuePair("name",name));
		list.add(new BasicNameValuePair("age",age));
		try {
			//httpclient��ʽ��post����������Ҫת��
			post.setEntity(new UrlEncodedFormEntity(list,HTTP.UTF_8));
			//��������
			HttpResponse respose=client.execute(post);
			if (respose.getStatusLine().getStatusCode()==HttpStatus.SC_OK) {
				//��ȡ���ص�ֵ
				String content=EntityUtils.toString(respose.getEntity());
				System.out.println("content---->"+content);
			}
		} catch (UnsupportedEncodingException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	}
	@Override
	public void run() {
		// TODO �Զ����ɵķ������
		super.run();
//		doGet();
		doPost();
	}
}
