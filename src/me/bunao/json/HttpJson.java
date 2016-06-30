package me.bunao.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Handler;
import android.widget.ListView;
import android.widget.Toast;
import me.bunao.json.person.Person;
import me.bunao.json.person.SchoolInfo;

/**
 * ���������ɵ�json
{
    "result": 1,
    "personData": [
        {
            "name": "nate",
            "age": 12,
            "url": "http://pic2.sc.chinaz.com/Files/pic/pic9/201605/apic20824_s.jpg",
            "school_info": [
                {
                    "school_name": "�廪"
                },
                {
                    "school_name": "�й�"
                }
            ]
        }
    ]
}
 * 
 * 
 */
//��ȡjson����
public class HttpJson extends Thread {
	private String url;
	private Context context;
	private ListView listView;
	private JsonAdapter adapter;
	private Handler handler;
	
	public HttpJson(Context context,String url, ListView listView, JsonAdapter adapter,Handler handler) {
		this.url = url;
		this.context = context;
		this.listView = listView;
		this.adapter = adapter;
		this.handler=handler;
	}

	@Override
	public void run() {
		URL httpUrl;
		try {
			httpUrl = new URL(url);
			HttpURLConnection conn=(HttpURLConnection) httpUrl.openConnection();
//			conn.setReadTimeout(5000);
			conn.setRequestMethod("GET");
			InputStream in = conn.getInputStream();
			BufferedReader reader=new BufferedReader(new InputStreamReader(in));
			StringBuffer sb=new StringBuffer();
			String str;
			while ((str=reader.readLine())!=null) {
				sb.append(str);
			}
			System.out.println(sb.toString());
			final List<Person> data=parseJson(sb.toString());
			//�����ݴ��أ���Ϊ�������̣߳�Ҫ�����̴߳������ݣ���Ҫ�õ�handler
			handler.post(new Runnable() {
				
				@Override
				public void run() {
					//�����ݴ���ȥ
					System.out.println(data);
					adapter.setData(data);
					//�󶨼�����
					listView.setAdapter(adapter);
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
	/*json�ĸ�ʽ
	 * {"result":1,"personData":[{"name":"nate","age":12,"url":"http://www.wzfzl.cn/uploads/allimg/130918/1_130918035002_4.jpg","school_info":[{"school_name":"�廪"},{"school_name":"�й�"}]}]}
	 */
	//��json���н���
	private List<Person> parseJson(String json){
		List<Person>persons=new ArrayList<Person>();
		try {
			JSONObject object=new JSONObject(json);
			int result=object.getInt("result");
			if(result==1){
				//��ʼ������Ҫ������
				JSONArray personData=object.getJSONArray("personData");
				//ѭ������
				for (int i = 0; i < personData.length(); i++) {
					Person personObject=new Person();
					
					JSONObject person=personData.getJSONObject(i);
					String name=person.getString("name");
					int age=person.getInt("age");
					String url=person.getString("url");
					JSONArray schoolInfo=person.getJSONArray("school_info");
					String school1=schoolInfo.getJSONObject(0).getString("school_name");
					String school2=schoolInfo.getJSONObject(1).getString("school_name");
					//��ӵ�����
					List<SchoolInfo> schInfos=new ArrayList<SchoolInfo>();
					SchoolInfo info = new SchoolInfo();
					info.setSchool_name(school1);
					schInfos.add(info);
					SchoolInfo info1 = new SchoolInfo();
					info1.setSchool_name(school2);
					schInfos.add(info1);
					personObject.setAge(age);
					personObject.setName(name);
					personObject.setUrl(url);
					personObject.setSchool_info(schInfos);
					persons.add(personObject);
				}
				return persons;
			}else{
				Toast.makeText(context, "error"	, 1).show();
			}
		} catch (JSONException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		return null;
	}
}
