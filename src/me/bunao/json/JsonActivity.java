package me.bunao.json;

import com.example.httptest.R;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ListView;

public class JsonActivity extends Activity {
	private ListView listView;
	private JsonAdapter adapter;
	private Handler handler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		setContentView(R.layout.jsontest);
		listView = (ListView) findViewById(R.id.listview);
		adapter = new JsonAdapter(this);
		String url = "http://192.168.56.1:8080/httptest/servlet/JsonServlet";
		new HttpJson(this,url,listView,adapter,handler).start();
	}
}
