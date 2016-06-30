package me.bunao.duoxianchengxiazai;

import com.example.httptest.R;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DownLoadActivity extends Activity {

	private Button button;
	private TextView textView;
	private String url="http://192.168.56.1:8080/httptest/tupian.jpg";
	private int count=0;
	private Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			int result=msg.what;
			count+=result;
			if (count==3) {
				textView.setText("�������");
			}
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO �Զ����ɵķ������
		super.onCreate(savedInstanceState);
		setContentView(R.layout.download);
		button=(Button) findViewById(R.id.down);
		textView=(TextView) findViewById(R.id.textdown);
		
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//���̲߳������������
				new Thread(){
					@Override
					public void run() {
						DownLoad downLoad=new DownLoad(handler);
						downLoad.downloadFile(url);
					}
				}.start();
				Toast.makeText(getApplicationContext(), "en", 1).show();
			}
		});
	}
}
