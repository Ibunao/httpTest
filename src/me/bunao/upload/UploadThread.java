package me.bunao.upload;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
//δ���
public class UploadThread extends Thread{
	
	private String fileName,url;
	
	
	@Override
	public void run() {
		try {
			//boundary�����ݿ����ڿ����߹����в鿴��ͨ����ҳ�ύ��ͼƬ��ʱ�����ʾ���������е���Ϣͷ�鿴�������
			String boundary="---------------------------70022658124816";
			String prefix="--";
			String end="\r\n";
			URL httpUrl=new URL(url);
			HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
			conn.setRequestMethod("POST");
//			conn.setConnectTimeout(5000);
			//post��ʽ������������õ�conn.setDoInput(true);conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			//��������ͷ
			conn.setRequestProperty("Content-Type", "multipart/form-data;boundary="+boundary);
			//���������
			DataOutputStream out=new DataOutputStream(conn.getOutputStream());
			out.writeBytes(prefix+boundary+end);
			//��һЩ������ת��Ϊ�����ļ�д��Ȼ��flush���뵽������,���Ƿ��տͻ����������������ʱ���������յ�����ʽд��
			//���Բ鿴myeclipse�е��ϴ�������Ŀ�е��ϴ����յ��ı����ݵĶ�ȡ��������ǰ��ն�ȡ�����ݵ���ʽдһ��
			out.flush();
			
		} catch (MalformedURLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	}
}

