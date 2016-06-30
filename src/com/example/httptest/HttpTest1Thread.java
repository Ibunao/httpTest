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
//		// TODO �Զ����ɵķ������
//		super.run();
		try {
			//���url����
			URL httpUrl=new URL(url);
			//������Ӷ���   ʹ�ô�Http�Ŀ�����������ʽ
			HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
			//�趨��ȡʱ��
			conn.setReadTimeout(5000);
			//�趨����ʽ
			conn.setRequestMethod("GET");
			//��������
			final StringBuffer sb=new StringBuffer();
			//��ȡ������    conn.getInputStream()��ȡ������ҳ���ֽ���
			BufferedReader reader=new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String str;
			while((str=reader.readLine())!=null){
				//����ȡ������ӵ�������StringBuffer
				sb.append(str);
			}
			//�������߳�
			handler.post(new Runnable() {
				
				@Override
				public void run() {
					// TODO �Զ����ɵķ������
					//�ڶ�������Ϊ��ҳ��content�е�����
					webView.loadData(sb.toString(),"text/html;charset=utf-8", null);
				}
			});
		} catch (MalformedURLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		} 
	}
}
