package com.example.httptest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.os.Handler;
import android.webkit.WebView;

public class HttpTest1Thread extends Thread {
	private Handler handler;
	private String url;
	private WebView webView;
	public HttpTest1Thread(Handler handler,String url,WebView webView) {
		this.handler=handler;
		this.url=url;
		this.webView=webView;
	}
	@Override
	public void run() {
//		// TODO 自动生成的方法存根
//		super.run();
		try {
			//获得url对象
			URL httpUrl=new URL(url);
			//获得链接对象   使用带Http的可以设置请求方式
			HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
			//设定读取时间
			conn.setReadTimeout(5000);
			//设定请求方式
			conn.setRequestMethod("GET");
			//创建缓存
			final StringBuffer sb=new StringBuffer();
			//获取输入流    conn.getInputStream()获取请求网页的字节流
			BufferedReader reader=new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String str;
			while((str=reader.readLine())!=null){
				//将读取到的添加到创建的StringBuffer
				sb.append(str);
			}
			//传到主线程
			handler.post(new Runnable() {
				
				@Override
				public void run() {
					// TODO 自动生成的方法存根
					//第二个参数为网页的content中的内容
					webView.loadData(sb.toString(),"text/html;charset=utf-8", null);
				}
			});
		} catch (MalformedURLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} 
	}
}
