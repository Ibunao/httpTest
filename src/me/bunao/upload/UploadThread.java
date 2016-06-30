package me.bunao.upload;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
//未完成
public class UploadThread extends Thread{
	
	private String fileName,url;
	
	
	@Override
	public void run() {
		try {
			//boundary的内容可以在开发者工具中查看，通过网页提交的图片的时候会显示，在网络中的消息头查看（火狐）
			String boundary="---------------------------70022658124816";
			String prefix="--";
			String end="\r\n";
			URL httpUrl=new URL(url);
			HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
			conn.setRequestMethod("POST");
//			conn.setConnectTimeout(5000);
			//post方式的请求必须设置的conn.setDoInput(true);conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			//设置请求头
			conn.setRequestProperty("Content-Type", "multipart/form-data;boundary="+boundary);
			//创建输出流
			DataOutputStream out=new DataOutputStream(conn.getOutputStream());
			out.writeBytes(prefix+boundary+end);
			//将一些参数和转化为流的文件写入然后flush输入到服务器,这是仿照客户浏览器表单传递数据时服务器接收到的形式写的
			//可以查看myeclipse中的上传下载项目中的上传接收到的表单内容的读取，这里就是按照读取的内容的形式写一下
			out.flush();
			
		} catch (MalformedURLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
}

