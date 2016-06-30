package me.bunao.xml;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.os.Handler;
import android.widget.TextView;

public class XmlThread extends Thread {
	private String url;
	private List<Girl>list=new ArrayList<Girl>();
	private Girl girl;
	private TextView textView;
	private Handler handler;
	
	
	public XmlThread(TextView textView, Handler handler,String url) {
		super();
		this.textView = textView;
		this.handler = handler;
		this.url=url;
	}


	@Override
	public void run() {
		try {
			URL httpUrl=new URL(url);
			HttpURLConnection conn=(HttpURLConnection) httpUrl.openConnection();
			conn.setReadTimeout(5000);
			conn.setRequestMethod("GET");
			InputStream in=conn.getInputStream();
			//ʹ�ð�׿ԭ������xml���н���    �����¼�������һ��һ��������
			XmlPullParserFactory factory=XmlPullParserFactory.newInstance();
			XmlPullParser parser=factory.newPullParser();
			parser.setInput(in, "UTF-8");
			//��ȡ��ǩ�����ͣ������ж�
			int eventType=parser.getEventType();
			//������ǽ�����ǩ����ѭ��
			while(eventType!=XmlPullParser.END_DOCUMENT){
				//��ȡ��ǩ������
				String data=parser.getName();
				switch (eventType) {
				//��ʼ��ǩ
				case XmlPullParser.START_TAG:
					if ("girl".equals(data)) {
						girl=new Girl();
					}
					if ("name".equals(data)) {
						//��ȡ�ı���Ϣ
						girl.setName(parser.nextText());
					}
					if ("age".equals(data)) {
						//��ȡ�ı���Ϣ
						girl.setAge(Integer.parseInt(parser.nextText()));
					}
					if ("school".equals(data)) {
						girl.setSchool(parser.nextText());
					}
					break;
				//������ǩ
				case XmlPullParser.END_TAG:
					if ("girl".equals(data)&&girl!=null) {
						list.add(girl);
					}
					break;
				}
				//������
				eventType=parser.next();
			}
			handler.post(new Runnable() {
				
				@Override
				public void run() {
					textView.setText(list.toString());
				}
			});
			
		} catch (MalformedURLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	}
}
