package com.example.fragment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import com.example.Adapter.InfoAdapter;
import com.example.cyhtest.MainActivity;
import com.example.cyhtest.book_Detail_Activity;
import com.example.cyhtest.R;
import com.example.cyhtest.R.layout;
import com.example.cyhtest.Search_Activity;
import com.example.cyhtest.Search_test2;
import com.example.entity.BookDetailEntity;
import com.example.entity.SingleEntity;
import com.example.view.CatLoadingView;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


public class OneFragment extends Fragment implements AdapterView.OnItemClickListener {
	//CatLoadingView mView;
	private View mBaseView;
	private Context mContext;
	private ListView listview;
	private InfoAdapter myAdapater_OneFragment;
	private List<SingleEntity> aList_OneFragment;// MovieBriefPojo 返回的泛型LIST
	private MyBookAsyncTask myBookAsyncTask;
	//private CatLoadingView mView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mBaseView=inflater.inflate(R.layout.fragment_one, container, false);
		mContext=getActivity();

		initView();
		return mBaseView;
	}
	void initView(){
		listview=(ListView) mBaseView.findViewById(R.id.fragement_list_view);
		//((MainActivity)mContext).mView = new CatLoadingView();
		//myBookAsyncTask=new MyBookAsyncTask();
		//myBookAsyncTask.execute();


		myBookAsyncTask=new MyBookAsyncTask();
		myBookAsyncTask.execute();
		//if(!((MainActivity)mContext).mView.isVisible())
		((MainActivity)mContext).mView.show(((MainActivity)mContext).fm2,"");
		listview.setOnItemClickListener(this);
		/*listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(mContext,book_Detail_Activity.class);
				startActivity(intent);

				Bundle bundle = new Bundle();
				bundle.putString("url", aList_OneFragment.get(position).getFirstUrl().toString());
				bundle.putString("imageurl", aList_OneFragment.get(position).getImageUrl().toString());

				intent.putExtras(bundle);

				startActivity(intent);
			}
		});*/

		//mView = new CatLoadingView();


	}
	void initView2(){

	}

	@Override
	public void onStart() {
		super.onStart();
		//mView.show(getSupportFragmentManager(), "");
		//mView.setShowsDialog(true);


		//mView.dismiss();
		//getFragmentManager
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Intent intent = new Intent(mContext,book_Detail_Activity.class);
		//startActivity(intent);
		Bundle bundle = new Bundle();
		bundle.putString("url", aList_OneFragment.get(position).getFirstUrl().toString());
		bundle.putString("imageurl", aList_OneFragment.get(position).getImageUrl().toString());
		intent.putExtras(bundle);
		startActivity(intent);
	}

	public class MyBookAsyncTask extends AsyncTask {
		@Override
		protected Object doInBackground(Object[] objects) {


			try{
				String ch= URLEncoder.encode("文学", "utf-8");
				String uString = "https://api.douban.com/v2/book/search?tag="+ch+"&count=40";
				//System.out.println(uString);
				URL url = new URL(uString);

				StringBuilder builder = new StringBuilder();
				BufferedReader bufferedReader = new BufferedReader(
						new InputStreamReader(url.openStream()));
				for (String s = bufferedReader.readLine(); s != null; s = bufferedReader
						.readLine()) {
					builder.append(s);
				}
				System.out.print(builder.toString());
				JSONObject jsonObject = new JSONObject(builder.toString());
				JSONArray books = jsonObject.getJSONArray("books");
				if(true) {
					List<SingleEntity> result = new ArrayList<SingleEntity>();
					for (int i = 0; i < books.length(); i++) {
						JSONObject book = (JSONObject) books.opt(i);

						SingleEntity bookSingleEntity = new SingleEntity();
						bookSingleEntity.setBookName(book.getString("title"));//书本名称
						bookSingleEntity.setFirstUrl(book.getString("id"));//书籍具体的地址
						bookSingleEntity.setImageUrl(book.getString("image"));//书籍图片
						bookSingleEntity.setAuthorName(book.getJSONArray("author").optString(0));//作者
						bookSingleEntity.setPublisher(book.getString("publisher"));
						bookSingleEntity.setPubdate(book.getString("pubdate"));
						bookSingleEntity.setSummary(book.getString("summary"));
						bookSingleEntity.setRating_average(book.getJSONObject("rating").getString("average"));

						result.add(bookSingleEntity);
					}
					System.out.print("size:"+result.size());

					return result;
				}
			}catch(Exception e){
				System.out.println("Exception In doInBackground");
			}




			return null;
		}

		@Override
		protected void onPostExecute(Object o) {
			super.onPostExecute(o);
			System.out.println("onPostExecute");
			List<SingleEntity> result=(List<SingleEntity> )o;
			aList_OneFragment=result;
			//System.out.print(aList_OneFragment.size());
			//System.out.println(result.get(0).getAuthorName()+" "+result.get(0).getFirstUrl()+" "+result.get(0).getImageUrl());
			if(result==null||result.size()==0){
				//Toast.makeText(getApplicationContext(), "数据库连接失败", Toast.LENGTH_SHORT).show();
			}
			else if(result.size()!=0) {
				myAdapater_OneFragment = new InfoAdapter(mContext, listview, result);
				listview.setAdapter(myAdapater_OneFragment);
				listview.setOnScrollListener(mScrollListener);
				myAdapater_OneFragment.setFlagBusy(false);
				//if(((MainActivity)mContext).mView.isVisible())
			}
				((MainActivity)mContext).mView.dismiss();



			//InfoAdapter myAdapater=;// 用来加载BaseAdapater

			//myAdapater = new InfoAdapter(this, mlistView);
		}
	}

	//第1次：scrollState = SCROLL_STATE_TOUCH_SCROLL(1) 正在滚动
	//第2次：scrollState = SCROLL_STATE_FLING(2) 手指做了抛的动作（手指离开屏幕前，用力滑了一下）
	//第3次：scrollState = SCROLL_STATE_IDLE(0) 停止滚动
	AbsListView.OnScrollListener mScrollListener = new AbsListView.OnScrollListener() {

		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			switch (scrollState) {
				case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
					myAdapater_OneFragment.setFlagBusy(true);
					System.out.println("SCROLL_STATE_FLING");
					break;
				case AbsListView.OnScrollListener.SCROLL_STATE_IDLE://停止滚动
					System.out.println("SCROLL_STATE_IDLE");
					myAdapater_OneFragment.setFlagBusy(false);
					break;
				case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL://滚动
					System.out.println("SCROLL_STATE_TOUCH_SCROLL");
					myAdapater_OneFragment.setFlagBusy(false);
					break;
				default:
					break;
			}
			myAdapater_OneFragment.notifyDataSetChanged();
		}

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
							 int visibleItemCount, int totalItemCount) {

		}
	};
}
