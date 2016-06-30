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
/*在访问的servlet中最后添加下面两行，则在app中则可以获取输入流将println的内容读取出来
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
	//使用get方式进行提交
	private void doGet(){
		try {
			//将传递的参数添加到url，
			//通过url，中文信息要进行转码
			url=url+"?name="+URLEncoder.encode(name, "utf-8")+"&age="+age;
			//转换成URL对象
			URL httpUrl=new URL(url);
			//获取http连接
			HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
			//设置请求方式
			conn.setRequestMethod("GET");
			//设置请求时间
			conn.setReadTimeout(5000);
			/*//获取输出流，用来传递Post参数
			OutputStream out = conn.getOutputStream();
			String content="name="+name+"&age="+age;
			//将post参数转化为字节           传出
			out.write(content.getBytes());*/
			//读取服务器返回的值
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			StringBuffer sb=new StringBuffer();
			String str;
			while((str=reader.readLine())!=null){
				sb.append(str);
			}
			System.out.println("result:"+sb.toString());
		} catch (MalformedURLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	//使用post方式进行提交
	private void doPost(){
		try {
			//用post方式不用转码，因为安卓系统默认的就是utf-8
			//查看安卓的默认配置信息
			/*Properties property=System.getProperties();
			//输出到控制台
			property.list(System.out);*/
			//转换成URL对象
			URL httpUrl=new URL(url);
			//获取http连接
			HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
			//设置请求方式
			conn.setRequestMethod("POST");
			//设置请求时间
			conn.setReadTimeout(5000);
			//获取输出流，用来传递Post参数
			OutputStream out = conn.getOutputStream();
			String content="name="+name+"&age="+age;
			//将post参数转化为字节           传出
			out.write(content.getBytes());
			//读取服务器返回的值
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			StringBuffer sb=new StringBuffer();
			String str;
			while((str=reader.readLine())!=null){
				sb.append(str);
			}
			System.out.println("result:"+sb.toString());
		} catch (MalformedURLException e) {
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
		doPost();
	}
}
