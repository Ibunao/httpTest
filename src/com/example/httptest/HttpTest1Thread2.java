package com.example.httptest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

/*ע����������Ȩ�޺��ڴ濨��Ȩ��
 * ����ͼƬ
 * ��ͼƬ�������϶�ȡ�������浽�ڴ濨�ϣ��ڴ��ڴ濨�ϼ��ص��ؼ�
 */
public class HttpTest1Thread2 extends Thread {
	private Handler handler;
	private String url;
	private ImageView imgView;
	public HttpTest1Thread2(Handler handler,String url,ImageView imgView) {
		this.handler=handler;
		this.url=url;
		this.imgView=imgView;
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
//			conn.setReadTimeout(10000);
			//�趨����ʽ
			conn.setRequestMethod("GET");
			//���ÿɶ�ȡ��
			conn.setDoInput(true);
			//��ȡ������
//			BufferedReader reader=new BufferedReader(new InputStreamReader(conn.getInputStream()));
			//ʹ���ֽ���
			InputStream reader=conn.getInputStream();
			//���������
			FileOutputStream out=null;
			File downloadFile=null;
			//��ȡ��ǰʱ��
			String fileName=String.valueOf(System.currentTimeMillis());
			//�ж��ڴ濨�Ƿ�װ
			Log.i("xyz", String.valueOf(Environment.getExternalStorageDirectory()));
			if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
				//��ȡ�ڴ濨��·����Ϊ�����򿪱ٵģ�
				File parent=Environment.getExternalStorageDirectory();
				//������·��,���Ŀ¼
				downloadFile=new File(parent, fileName);
				
				//��ʼ�������
				out=new FileOutputStream(downloadFile);
				Log.i("xyz", downloadFile.toString());
			}
			byte[] b=new byte[2*1024];
			int len;
			if (out!=null) {
				while ((len=reader.read(b))!=-1) {
					//д�뵽�ڴ濨
					out.write(b,0,len);
					Log.i("xyz", "ok");
				}
			}
			//���ڴ濨�����ݴ�����bitmap
			final Bitmap bitmap=BitmapFactory.decodeFile(downloadFile.getAbsolutePath());
			//�������߳�
			handler.post(new Runnable() {
				
				@Override
				public void run() {
					// TODO �Զ����ɵķ������
					//�ڶ�������Ϊ��ҳ��content�е�����
					imgView.setImageBitmap(bitmap);
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
