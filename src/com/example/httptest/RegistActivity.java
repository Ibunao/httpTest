package com.example.httptest;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
//��������Ҫ������myeclipse�е�httptest��Ŀ
public class RegistActivity extends Activity {
	private EditText name,age;
	private Button submit;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO �Զ����ɵķ������
		super.onCreate(savedInstanceState);
		setContentView(R.layout.regist);
		name=(EditText) findViewById(R.id.name);
		age=(EditText) findViewById(R.id.age);
		submit=(Button) findViewById(R.id.submit);
		submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//���µ��߳������get/post�ύ
				//���������ķ��������ڰ�׿���޷�ʹ��localhostҪ����������ip��ַ ��һ��ipv4��ַΪ����Ҫ��IP��ַ
				//��cmd����̨������ipconfig
				System.out.println("kaishi");
				String url="http://192.168.56.1:8080/httptest/servlet/httpServletforandroid";
//				new GetAndPostThread(name.getText().toString(), age.getText().toString(), url).start();
				new httpClientThread(name.getText().toString(), age.getText().toString(), url).start();
			}
		});
		
	}
}
