package com.example.httptest;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.webkit.WebView;
import android.widget.ImageView;

public class HttpTest1 extends Activity{
	private WebView webView;
	private ImageView img;
	private Handler handler=new Handler();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		webView=(WebView) findViewById(R.id.webView1);
		img=(ImageView) findViewById(R.id.imageView1);
		
		//�����̡߳�    ��ַһ������http
//		new HttpTest1Thread(handler, "http://www.baidu.com", webView).start();
		
		//�����̣߳�����ͼƬ
		new HttpTest1Thread2(handler, "http://h.hiphotos.baidu.com/image/pic/item/6c224f4a20a446239e8d311c9b22720e0cf3d70d.jpg", img).start();
		
	}

}
