package me.bunao.json;

import java.util.List;

import com.example.httptest.R;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import me.bunao.json.person.Person;

public class JsonAdapter extends BaseAdapter {
	
	private List<Person>list;
	private Context context;
	private LayoutInflater inflater;
	//这是在主线程
	private Handler handler=new Handler();
	
	public  JsonAdapter(Context context,List<Person> list) {
		this.context=context;
		this.list=list;
		inflater=LayoutInflater.from(context);
	}
	public  JsonAdapter(Context context) {
		this.context=context;
		inflater=LayoutInflater.from(context);
	}
	public void setData(List<Person>data){
		this.list=data;
	}
	@Override
	public int getCount() {
		// TODO 自动生成的方法存根
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO 自动生成的方法存根
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO 自动生成的方法存根
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		Holder holder=null;
		if (convertView==null) {
			convertView=inflater.inflate(R.layout.listitem, null);
			holder=new Holder(convertView);
			convertView.setTag(holder);
		}else{
			holder=(Holder) convertView.getTag();
		}
		Person person=list.get(position);
		holder.name.setText(person.getName());
		holder.age.setText(person.getAge()+"");//这个，必须是字符串形式的，不然程序崩溃
		holder.school1.setText(person.getSchool_info().get(0).getSchool_name());
		holder.school2.setText(person.getSchool_info().get(1).getSchool_name());
		new ImageThread(person.getUrl(), handler, holder.imageView).start();
		
		return convertView;
	}
	class Holder{
		private TextView name,age,school1,school2;
		private ImageView imageView;

		public Holder(View view) {
			
			this.name = (TextView) view.findViewById(R.id.name);
			this.age = (TextView) view.findViewById(R.id.age);
			this.school1 = (TextView) view.findViewById(R.id.school1);
			this.school2 = (TextView) view.findViewById(R.id.school2);
			imageView=(ImageView) view.findViewById(R.id.image);
		}
		
	}
}
