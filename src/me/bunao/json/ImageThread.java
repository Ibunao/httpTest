package me.bunao.json;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.widget.ImageView;

public class ImageThread extends Thread {
	private String url;
	private Handler handler;
	private ImageView imageView;
	public ImageThread(String url, Handler handler, ImageView imageView) {
		this.url = url;
		this.handler = handler;
		this.imageView = imageView;
	}
	Bitmap bitmap;
	@Override
	public void run() {
		try {
			System.out.println(url);
			URL httpUrl=new URL(url);
			HttpURLConnection conn=(HttpURLConnection) httpUrl.openConnection();
//			conn.setReadTimeout(5000);
			conn.setRequestMethod("GET");
			InputStream in = conn.getInputStream();
			bitmap=BitmapFactory.decodeStream(in);
			System.out.println(bitmap);
			handler.post(new Runnable() {
				
				@Override
				public void run() {
					// TODO �Զ����ɵķ������
					imageView.setImageBitmap(bitmap);
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
