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
 * ���߳�����
 * �����̳߳� Executor
 * 
 * ��������ͷ�ֶ�conn.setRequestProperty("Range", "bytes="+start+"-"+end);����ʾ��ȡ�Ŀ�ʼ�ͽ���λ��
 * ʹ�������дRandomAccessFile������д��ָ�룬����ȡ������д��ָ��λ��
 * �������̣߳�ʹ���̳߳ص�execute����
 */
public class DownLoad {
	
	private Handler handler;
	public DownLoad(Handler handler){
		this.handler=handler;
	}
	
	//�����̳߳أ��̶����߳���3
	private Executor threadPool=Executors.newFixedThreadPool(3);
	//���صĵ����߳�
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
				//�ص㣬���߳������ص�
				//��������ͷ�ֶ�,����Range�ֶ�����ָ�����صĿ�ʼ�ͽ���λ��
				conn.setRequestProperty("Range", "bytes="+start+"-"+end);
				
				conn.setRequestMethod("GET");
				//���߳������ص�
				//�ɶ���д
				RandomAccessFile access=new RandomAccessFile(new File(fileName), "rwd");
				//����ָ��
				access.seek(start);
				InputStream in =conn.getInputStream();
				byte[]b=new byte[1024];
				int len=0;
				while ((len=in.read(b))!=-1) {
					access.write(b,0,len);
					
				}
				
				
				//�ر���Դ
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
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			} catch (IOException e) {
				// TODO �Զ����ɵ� catch ��
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
			//��ȡ������Դ�ĳ��ȣ���С
			int count=conn.getContentLength();
			//ÿ���߳����صĴ�С
			int block=count/3;
			String fileName=getFileName(url);
			File parent=Environment.getExternalStorageDirectory();
			File fileDownLoad=new File(parent,fileName);
			System.out.println(fileDownLoad);
			//���̳߳��ύ����
			for (int i = 0; i < 3; i++) {
				long start=i*block;
				long end=(i+1)*block-1;
				//��Ϊ���һ��ʱ���������λ�÷ŵ������Ϊ�п���������������˵11���ֽڵ�
				if (i==2) {
					end=count;
				}
				DownLoadRunnable runnable=new DownLoadRunnable(url, fileDownLoad.getAbsolutePath(), start, end,handler);
				//ִ���̳߳�
				threadPool.execute(runnable);
			}
		} catch (MalformedURLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	}
	//��ȡ�ļ���
	public String getFileName(String url){
		return url.substring(url.lastIndexOf("/")+1);
	} 
}
