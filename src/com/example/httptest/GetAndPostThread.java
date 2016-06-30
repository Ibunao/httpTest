package com.example.httptest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Properties;
/*�ڷ��ʵ�servlet���������������У�����app������Ի�ȡ��������println�����ݶ�ȡ����
 * PrintWriter out=response.getWriter();
	out.println("name="+name+"age="+age);
 */
public class GetAndPostThread extends Thread {
	
	private String name,age,url;
	
	public GetAndPostThread(String name, String age, String url) {
		this.name = name;
		this.age = age;
		this.url = url;
	}
	//ʹ��get��ʽ�����ύ
	private void doGet(){
		try {
			//�����ݵĲ�����ӵ�url��
			//ͨ��url��������ϢҪ����ת��
			url=url+"?name="+URLEncoder.encode(name, "utf-8")+"&age="+age;
			//ת����URL����
			URL httpUrl=new URL(url);
			//��ȡhttp����
			HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
			//��������ʽ
			conn.setRequestMethod("GET");
			//��������ʱ��
			conn.setReadTimeout(5000);
			/*//��ȡ���������������Post����
			OutputStream out = conn.getOutputStream();
			String content="name="+name+"&age="+age;
			//��post����ת��Ϊ�ֽ�           ����
			out.write(content.getBytes());*/
			//��ȡ���������ص�ֵ
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			StringBuffer sb=new StringBuffer();
			String str;
			while((str=reader.readLine())!=null){
				sb.append(str);
			}
			System.out.println("result:"+sb.toString());
		} catch (MalformedURLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	}
	//ʹ��post��ʽ�����ύ
	private void doPost(){
		try {
			//��post��ʽ����ת�룬��Ϊ��׿ϵͳĬ�ϵľ���utf-8
			//�鿴��׿��Ĭ��������Ϣ
			/*Properties property=System.getProperties();
			//���������̨
			property.list(System.out);*/
			//ת����URL����
			URL httpUrl=new URL(url);
			//��ȡhttp����
			HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
			//��������ʽ
			conn.setRequestMethod("POST");
			//��������ʱ��
			conn.setReadTimeout(5000);
			//��ȡ���������������Post����
			OutputStream out = conn.getOutputStream();
			String content="name="+name+"&age="+age;
			//��post����ת��Ϊ�ֽ�           ����
			out.write(content.getBytes());
			//��ȡ���������ص�ֵ
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			StringBuffer sb=new StringBuffer();
			String str;
			while((str=reader.readLine())!=null){
				sb.append(str);
			}
			System.out.println("result:"+sb.toString());
		} catch (MalformedURLException e) {
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
		doPost();
	}
}
