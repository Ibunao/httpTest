package me.bunao.xml;

import com.example.httptest.R;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

public class XmlActivity extends Activity {
	private TextView textView;
	private Handler handler=new Handler();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO �Զ����ɵķ������
		super.onCreate(savedInstanceState);
		setContentView(R.layout.xmltest);
		textView=(TextView) findViewById(R.id.text);
		String url="http://192.168.56.1:8080/httptest/myxml.xml";
		new XmlThread(textView, handler,url).start();
	}
}
