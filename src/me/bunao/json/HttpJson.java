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
 * 服务器生成的json
{
    "result": 1,
    "personData": [
        {
            "name": "nate",
            "age": 12,
            "url": "http://pic2.sc.chinaz.com/Files/pic/pic9/201605/apic20824_s.jpg",
            "school_info": [
                {
                    "school_name": "清华"
                },
                {
                    "school_name": "中工"
                }
            ]
        }
    ]
}
 * 
 * 
 */
//读取json数据
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
			//将数据传回，因为这是子线程，要向主线程传递数据，需要用到handler
			handler.post(new Runnable() {
				
				@Override
				public void run() {
					//把数据传进去
					System.out.println(data);
					adapter.setData(data);
					//绑定加载器
					listView.setAdapter(adapter);
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
	/*json的格式
	 * {"result":1,"personData":[{"name":"nate","age":12,"url":"http://www.wzfzl.cn/uploads/allimg/130918/1_130918035002_4.jpg","school_info":[{"school_name":"清华"},{"school_name":"中工"}]}]}
	 */
	//对json进行解析
	private List<Person> parseJson(String json){
		List<Person>persons=new ArrayList<Person>();
		try {
			JSONObject object=new JSONObject(json);
			int result=object.getInt("result");
			if(result==1){
				//开始解析需要的内容
				JSONArray personData=object.getJSONArray("personData");
				//循环遍历
				for (int i = 0; i < personData.length(); i++) {
					Person personObject=new Person();
					
					JSONObject person=personData.getJSONObject(i);
					String name=person.getString("name");
					int age=person.getInt("age");
					String url=person.getString("url");
					JSONArray schoolInfo=person.getJSONArray("school_info");
					String school1=schoolInfo.getJSONObject(0).getString("school_name");
					String school2=schoolInfo.getJSONObject(1).getString("school_name");
					//添加到对象
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
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return null;
	}
}
