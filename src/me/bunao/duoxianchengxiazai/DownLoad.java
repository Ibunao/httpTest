package me.bunao.duoxianchengxiazai;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import android.os.Environment;
import android.os.Handler;
import android.os.Message;
/*
 * 多线程下载
 * 创建线程池 Executor
 * 
 * 设置请求头字段conn.setRequestProperty("Range", "bytes="+start+"-"+end);来标示读取的开始和结束位置
 * 使用随机读写RandomAccessFile，设置写入指针，将读取的数据写入指定位置
 * 开启多线程，使用线程池的execute方法
 */
public class DownLoad {
	
	private Handler handler;
	public DownLoad(Handler handler){
		this.handler=handler;
	}
	
	//创建线程池，固定的线程数3
	private Executor threadPool=Executors.newFixedThreadPool(3);
	//下载的单个线程
	static class DownLoadRunnable implements Runnable{
		private String url;
		private String fileName;
		private long start;
		private long end;
		private Handler handler;
		
		
		public DownLoadRunnable(String url, String fileName, long start, long end,Handler handler) {
			
			this.url = url;
			this.fileName = fileName;
			this.start = start;
			this.end = end;
			this.handler=handler;
		}



		@Override
		public void run() {
			try {
				URL httpUrl=new URL(url);
				HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
//				conn.setReadTimeout(5000);
				//重点，多线程下载重点
				//设置请求头字段,设置Range字段用来指定下载的开始和结束位置
				conn.setRequestProperty("Range", "bytes="+start+"-"+end);
				
				conn.setRequestMethod("GET");
				//多线程下载重点
				//可读可写
				RandomAccessFile access=new RandomAccessFile(new File(fileName), "rwd");
				//设置指针
				access.seek(start);
				InputStream in =conn.getInputStream();
				byte[]b=new byte[1024];
				int len=0;
				while ((len=in.read(b))!=-1) {
					access.write(b,0,len);
					
				}
				
				
				//关闭资源
				if (access!=null) {
					access.close();
				}
				if (in!=null) {
					in.close();
				}
				Message message=new Message();
				message.what=1;
				handler.sendMessage(message);
			} catch (MalformedURLException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		
	}
	
	
	public void downloadFile(String url){
		try {
			URL httpUrl=new URL(url);
			HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
//			conn.setReadTimeout(5000);
			conn.setRequestMethod("GET");
			//获取连接资源的长度，大小
			int count=conn.getContentLength();
			//每个线程下载的大小
			int block=count/3;
			String fileName=getFileName(url);
			File parent=Environment.getExternalStorageDirectory();
			File fileDownLoad=new File(parent,fileName);
			System.out.println(fileDownLoad);
			//向线程池提交任务
			for (int i = 0; i < 3; i++) {
				long start=i*block;
				long end=(i+1)*block-1;
				//当为最后一个时，将其结束位置放到最后，因为有可能有余数，比如说11个字节的
				if (i==2) {
					end=count;
				}
				DownLoadRunnable runnable=new DownLoadRunnable(url, fileDownLoad.getAbsolutePath(), start, end,handler);
				//执行线程池
				threadPool.execute(runnable);
			}
		} catch (MalformedURLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	//获取文件名
	public String getFileName(String url){
		return url.substring(url.lastIndexOf("/")+1);
	} 
}
