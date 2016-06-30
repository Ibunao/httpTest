package com.example.httptest;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
//测试它，要先启动myeclipse中的httptest项目
public class RegistActivity extends Activity {
	private EditText name,age;
	private Button submit;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		setContentView(R.layout.regist);
		name=(EditText) findViewById(R.id.name);
		age=(EditText) findViewById(R.id.age);
		submit=(Button) findViewById(R.id.submit);
		submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//在新的线程中完成get/post提交
				//本机常见的服务器，在安卓中无法使用localhost要换成主机的ip地址 第一个ipv4地址为所需要的IP地址
				//在cmd控制台中输入ipconfig
				System.out.println("kaishi");
				String url="http://192.168.56.1:8080/httptest/servlet/httpServletforandroid";
//				new GetAndPostThread(name.getText().toString(), age.getText().toString(), url).start();
				new httpClientThread(name.getText().toString(), age.getText().toString(), url).start();
			}
		});
		
	}
}
