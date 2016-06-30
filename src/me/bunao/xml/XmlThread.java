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
			//使用安卓原生进行xml进行解析    基于事件驱动的一步一步往下走
			XmlPullParserFactory factory=XmlPullParserFactory.newInstance();
			XmlPullParser parser=factory.newPullParser();
			parser.setInput(in, "UTF-8");
			//获取标签的类型，进行判断
			int eventType=parser.getEventType();
			//如果不是结束标签进行循环
			while(eventType!=XmlPullParser.END_DOCUMENT){
				//获取标签的名称
				String data=parser.getName();
				switch (eventType) {
				//开始标签
				case XmlPullParser.START_TAG:
					if ("girl".equals(data)) {
						girl=new Girl();
					}
					if ("name".equals(data)) {
						//获取文本信息
						girl.setName(parser.nextText());
					}
					if ("age".equals(data)) {
						//获取文本信息
						girl.setAge(Integer.parseInt(parser.nextText()));
					}
					if ("school".equals(data)) {
						girl.setSchool(parser.nextText());
					}
					break;
				//结束标签
				case XmlPullParser.END_TAG:
					if ("girl".equals(data)&&girl!=null) {
						list.add(girl);
					}
					break;
				}
				//往下走
				eventType=parser.next();
			}
			handler.post(new Runnable() {
				
				@Override
				public void run() {
					textView.setText(list.toString());
				}
			});
			
		} catch (MalformedURLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
}
