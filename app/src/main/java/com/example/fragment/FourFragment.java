package com.example.fragment;

import com.example.Adapter.InfoAdapter;
import com.example.Adapter.TimelineAdapter;
import com.example.cyhtest.MainActivity;
import com.example.cyhtest.R;
import com.example.cyhtest.R.layout;
import com.example.entity.SingleEntity;
import com.example.entity.TimerEntity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FourFragment extends Fragment {
	private String Base_url="https://api.douban.com/v2/book/";
	private View mBaseView;
	private ListView bookself;
	private FloatingActionButton floatingbutton;
	private Context mcontext;
	static private Connection conn;
	private InfoAdapter myAdapater_twoFragment;
	private TimelineAdapter timelineAdapter;
	List<TimerEntity> list;
	String student_id;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		mcontext=getActivity();
		mBaseView = inflater.inflate(layout.fragment_four, container, false);
		initView();
		return mBaseView;
	}

	void initView(){
		bookself=(ListView)mBaseView.findViewById(R.id.listview_bookself);
		floatingbutton=(FloatingActionButton)mBaseView.findViewById(R.id.fab_bookself);
		student_id=((MainActivity)mcontext).student_id;

		bookself.setDividerHeight(0);
		timelineAdapter = new TimelineAdapter(mcontext, getData());
		bookself.setAdapter(timelineAdapter);

		floatingbutton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(mcontext);//创建一个 AlertDialog.Builder
				builder.setTitle("o(^▽^)o");//设置标题
				View dialogview = LayoutInflater.from(mcontext).inflate(layout.dialog_add_layout, null);//得到dialoglayout的view
				builder.setView(dialogview);//设置对话框中的布局view
				final TextView dialog_date = (EditText) dialogview.findViewById(R.id.Dialog_date);
				final EditText dialog_event = (EditText) dialogview.findViewById(R.id.Dialog_event);
				builder.setPositiveButton("保存修改",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								TimerEntity timerEntity_d=new TimerEntity(dialog_date.getText().toString(),dialog_event.getText().toString());
								//timerEntity_d.setResourceId(R.drawable.head_portrait);
								list.add(timerEntity_d);
								timelineAdapter.notifyDataSetChanged();
							}
						}
				).setNegativeButton("放弃修改", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {

					}
				}).create();
				builder.show();
			}
		});
		bookself.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
				new AlertDialog.Builder(mcontext)
						.setTitle("o(^▽^)o")
						.setMessage("是否删除")
						.setPositiveButton("确定", new DialogInterface.OnClickListener(){
							@Override
							public void onClick(DialogInterface dialog, int which) {
								list.remove(position);
								timelineAdapter.notifyDataSetChanged();
							}
						})
						.show();
			}
		});
		//new MyBookAsyncTask_BS().execute();

	}

	private List<TimerEntity> getData() {
		list = new ArrayList<TimerEntity>();

		TimerEntity timerEntity;
		timerEntity=new TimerEntity("12-10","做project的第一天");
		timerEntity.setResourceId(R.drawable.head_portrait);
		list.add(timerEntity);
		list.add(new TimerEntity("12-12","完成了基本的布局"));
		list.add(new TimerEntity("12-13","开始连接豆瓣网络"));
		timerEntity=new TimerEntity("12-14","布局继续完善");
		timerEntity.setResourceId(R.drawable.img_4);
		list.add(timerEntity);
		list.add(new TimerEntity("12-17","获取到了图书"));
		list.add(new TimerEntity("12-19","完成了短信验证登陆"));
		list.add(new TimerEntity("12-20","基本上完成了连接数据库的部分"));
		timerEntity=new TimerEntity("12-24","开始准备期末复习");
		timerEntity.setResourceId(R.drawable.img_3);
		list.add(timerEntity);

		return list;
	}
/*
	private static boolean connect() {
		String connectString = "jdbc:mysql://192.168.253.1:3306/book_system"
				+ "?autoReconnect=true&useUnicode=true&characterEncoding=UTF-8&&useSSL=false";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(connectString, "root", "970419");
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return false;
	}


	public class MyBookAsyncTask_BS extends AsyncTask {
		@Override
		protected Object doInBackground(Object[] objects) {
			if(connect()){
				System.out.println("数据库连接成功");
				try{
					Statement stmt=conn.createStatement();
					String sql="select * from borrow where sno="+student_id;
					ResultSet rs=stmt.executeQuery(sql);
					List<SingleEntity> result = new ArrayList<SingleEntity>();
					while(rs.next()){
						SingleEntity bookSingleEntity = new SingleEntity();
						if(rs.getString(1)!=null)bookSingleEntity.setFirstUrl(rs.getString(1)); //书的id
						if(rs.getString(2)!=null)bookSingleEntity.setBorrow_time(rs.getString(2));
						if(rs.getString(3)!=null)bookSingleEntity.setBorrow_ddl(rs.getString(3));
						System.out.println(rs.getString(1)+"/"+rs.getString(2)+"/"+rs.getString(3));

						String uString = Base_url+rs.getString(2);
						URL url = new URL(uString);
						StringBuilder StringBuilder = new StringBuilder();
						BufferedReader bufferedReader = new BufferedReader(
								new InputStreamReader(url.openStream()));
						for (String s = bufferedReader.readLine(); s != null; s = bufferedReader
								.readLine()) {
							StringBuilder.append(s);
						}
						System.out.print(StringBuilder.toString());
						JSONObject jsonObject = new JSONObject(StringBuilder.toString());
						bookSingleEntity.setBookName(jsonObject.getString("title"));
						bookSingleEntity.setImageUrl(jsonObject.getString("image"));//书籍图片
						bookSingleEntity.setAuthorName(jsonObject.getJSONArray("author").optString(0));//作者
						bookSingleEntity.setPublisher(jsonObject.getString("publisher"));
						bookSingleEntity.setPubdate(jsonObject.getString("pubdate"));
						bookSingleEntity.setSummary(jsonObject.getString("summary"));
						bookSingleEntity.setRating_average(jsonObject.getJSONObject("rating").getString("average"));

						result.add(bookSingleEntity);
					}

					return result;
				}catch (Exception e){
					System.out.println("查询借书数据异常");
				}
			}
			else{
				Toast.makeText(mcontext, "数据库连接失败", Toast.LENGTH_SHORT).show();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Object o) {
			super.onPostExecute(o);
			System.out.println("onPostExecute");
			List<SingleEntity> result=(List<SingleEntity> )o;
			if(result.size()!=0) {
				myAdapater_twoFragment = new InfoAdapter(mcontext, bookself, result);
				bookself.setAdapter(myAdapater_twoFragment);
				//listview.setOnScrollListener(mScrollListener);
				//myAdapater_OneFragment.setFlagBusy(false);
				//if(((MainActivity)mContext).mView.isVisible())
			}
		}
	}*/
}
