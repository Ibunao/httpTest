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

/*注册访问网络的权限和内存卡的权限
 * 加载图片
 * 将图片从网络上读取下来保存到内存卡上，在从内存卡上加载到控件
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
//		// TODO 自动生成的方法存根
//		super.run();
		try {
			//获得url对象
			URL httpUrl=new URL(url);
			//获得链接对象   使用带Http的可以设置请求方式
			HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();

			//设定读取时间
//			conn.setReadTimeout(10000);
			//设定请求方式
			conn.setRequestMethod("GET");
			//设置可读取流
			conn.setDoInput(true);
			//获取输入流
//			BufferedReader reader=new BufferedReader(new InputStreamReader(conn.getInputStream()));
			//使用字节流
			InputStream reader=conn.getInputStream();
			//创建输出流
			FileOutputStream out=null;
			File downloadFile=null;
			//获取当前时间
			String fileName=String.valueOf(System.currentTimeMillis());
			//判断内存卡是否安装
			Log.i("xyz", String.valueOf(Environment.getExternalStorageDirectory()));
			if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
				//获取内存卡的路径（为本程序开辟的）
				File parent=Environment.getExternalStorageDirectory();
				//创建子路径,输出目录
				downloadFile=new File(parent, fileName);
				
				//初始化输出流
				out=new FileOutputStream(downloadFile);
				Log.i("xyz", downloadFile.toString());
			}
			byte[] b=new byte[2*1024];
			int len;
			if (out!=null) {
				while ((len=reader.read(b))!=-1) {
					//写入到内存卡
					out.write(b,0,len);
					Log.i("xyz", "ok");
				}
			}
			//将内存卡的数据创建成bitmap
			final Bitmap bitmap=BitmapFactory.decodeFile(downloadFile.getAbsolutePath());
			//传到主线程
			handler.post(new Runnable() {
				
				@Override
				public void run() {
					// TODO 自动生成的方法存根
					//第二个参数为网页的content中的内容
					imgView.setImageBitmap(bitmap);
				}
			});
		} catch (MalformedURLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} 
	}
}
