package com.example.fragment;

import com.example.Adapter.InfoAdapter;
import com.example.Adapter.MyRecyclerViewAdapter;
import com.example.cyhtest.MainActivity;
import com.example.cyhtest.R;
import com.example.cyhtest.R.layout;
import com.example.cyhtest.Search_Activity;
import com.example.cyhtest.book_Detail_Activity;
import com.example.cyhtest.classfiy_search;
import com.example.entity.SingleEntity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


public class ThreeFragment extends Fragment  implements View.OnClickListener {



	private Context mcontext;
	private View mBaseview;
	private RecyclerView mRecyclerView;
	private MyRecyclerViewAdapter myAdapter;
	private Button kind_1;
	private Button kind_2;
	private Button kind_3;
	private Button kind_4;
	private Button kind_5;
	private Button kind_6;
	private Button kind_gril_1;
	private Button kind_gril_2;
	private Button kind_gril_3;
	private Button kind_gril_4;
	private Button kind_gril_5;
	private Button kind_gril_6;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mcontext=getActivity();

		mBaseview = inflater.inflate(R.layout.fragment_three, container, false);
		initView();
		//mRecyclerView = (RecyclerView) mBaseview.findViewById(R.id.recycler_view);
		//mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
		//mRecyclerView.setItemAnimator(new DefaultItemAnimator());
		//new MyBookAsyncTask().execute();
		//mRecyclerView.setAdapter(new MyRecyclerViewAdapter(getActivity(), datas));
		return mBaseview;
	}
	void initView(){
		kind_1=(Button) mBaseview.findViewById(R.id.kind_1);
		kind_1.setOnClickListener(this);
		kind_2=(Button) mBaseview.findViewById(R.id.kind_2);
		kind_2.setOnClickListener(this);
		kind_3=(Button) mBaseview.findViewById(R.id.kind_3);
		kind_3.setOnClickListener(this);
		kind_4=(Button) mBaseview.findViewById(R.id.kind_4);
		kind_4.setOnClickListener(this);
		kind_5=(Button) mBaseview.findViewById(R.id.kind_5);
		kind_5.setOnClickListener(this);
		kind_6=(Button) mBaseview.findViewById(R.id.kind_6);
		kind_6.setOnClickListener(this);
		kind_gril_1=(Button) mBaseview.findViewById(R.id.kind_gril_1);
		kind_gril_1.setOnClickListener(this);
		kind_gril_2=(Button) mBaseview.findViewById(R.id.kind_gril_2);
		kind_gril_2.setOnClickListener(this);
		kind_gril_3=(Button) mBaseview.findViewById(R.id.kind_gril_3);
		kind_gril_3.setOnClickListener(this);
		kind_gril_4=(Button) mBaseview.findViewById(R.id.kind_gril_4);
		kind_gril_4.setOnClickListener(this);
		kind_gril_5=(Button) mBaseview.findViewById(R.id.kind_gril_5);
		kind_gril_5.setOnClickListener(this);
		kind_gril_6=(Button) mBaseview.findViewById(R.id.kind_gril_6);
		kind_gril_6.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		Intent intent = new Intent(mcontext,classfiy_search.class);
		Bundle bundle = new Bundle();
		switch (id){

			case  R.id.kind_1:
				bundle.putString("classify","东方玄幻" );
				break;
			case  R.id.kind_2:
				bundle.putString("classify","古典仙侠" );
				break;
			case  R.id.kind_3:
				bundle.putString("classify","都市生活" );
				break;
			case  R.id.kind_4:
				bundle.putString("classify","修真" );
				break;
			case  R.id.kind_5:
				bundle.putString("classify","武侠" );
				break;
			case  R.id.kind_6:
				bundle.putString("classify","灵异" );
				break;
			case  R.id.kind_gril_1:
				bundle.putString("classify","古代言情" );
				break;
			case  R.id.kind_gril_2:
				bundle.putString("classify","现代言情" );
				break;
			case  R.id.kind_gril_3:
				bundle.putString("classify","现代修真" );
				break;
			case  R.id.kind_gril_4:
				bundle.putString("classify","穿越" );
				break;
			case  R.id.kind_gril_5:
				bundle.putString("classify","重生" );
				break;
			case  R.id.kind_gril_6:
				bundle.putString("classify","宫廷" );
				break;
		}
		intent.putExtras(bundle);
		startActivity(intent);
	}
/*
	public class MyBookAsyncTask extends AsyncTask {
		@Override
		protected Object doInBackground(Object[] objects) {


			try{
				String ch= URLEncoder.encode("经典", "utf-8");
				String uString = "https://api.douban.com/v2/book/search?tag="+ch+"&count=50";
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
						//bookSingleEntity.setFirstUrl(book.getString("id"));//书籍具体的地址
						bookSingleEntity.setImageUrl(book.getString("image"));//书籍图片
						//bookSingleEntity.setAuthorName(book.getJSONArray("author").optString(0));//作者
						//bookSingleEntity.setPublisher(book.getString("publisher"));
						//bookSingleEntity.setPubdate(book.getString("pubdate"));
						//bookSingleEntity.setSummary(book.getString("summary"));
						//bookSingleEntity.setRating_average(book.getJSONObject("rating").getString("average"));

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
			//aList_OneFragment=result;
			//System.out.print(aList_OneFragment.size());
			//System.out.println(result.get(0).getAuthorName()+" "+result.get(0).getFirstUrl()+" "+result.get(0).getImageUrl());
			//mRecyclerView.setAdapter(new MyRecyclerViewAdapter(getActivity(), datas));
			myAdapter = new MyRecyclerViewAdapter(mcontext,result);
			mRecyclerView.setAdapter(myAdapter);
			//myAdapter.notifyDataSetChanged();
			//mRecyclerView.setOnScrollListener(mScrollListener);
			//myAdapter.setFlagBusy(false);
			//if(((MainActivity)mContext).mView.isVisible())
			//((MainActivity)mcontext).mView.dismiss();



			//InfoAdapter myAdapater=;// 用来加载BaseAdapater

			//myAdapater = new InfoAdapter(this, mlistView);
		}
	}
	AbsListView.OnScrollListener mScrollListener = new AbsListView.OnScrollListener() {

		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			switch (scrollState) {
				case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
					myAdapter.setFlagBusy(true);
					System.out.println("SCROLL_STATE_FLING");
					break;
				case AbsListView.OnScrollListener.SCROLL_STATE_IDLE://停止滚动
					System.out.println("SCROLL_STATE_IDLE");
					myAdapter.setFlagBusy(false);
					break;
				case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL://滚动
					System.out.println("SCROLL_STATE_TOUCH_SCROLL");
					myAdapter.setFlagBusy(false);
					break;
				default:
					break;
			}
			myAdapter.notifyDataSetChanged();
		}
		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
							 int visibleItemCount, int totalItemCount) {

		}
	};*/
}
