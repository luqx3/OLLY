package com.example.fragment;


import com.example.Adapter.MyRecyclerViewAdapter;
import com.example.cyhtest.R;
import com.example.cyhtest.R.layout;
import com.example.cyhtest.book_Detail_Activity;
import com.example.entity.SingleEntity;
import com.example.entity.TimerEntity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class TwoFragment extends Fragment implements AdapterView.OnItemClickListener{
	private Context mcontext;
	private View mBaseview;
	private RecyclerView mRecyclerView;
	private MyRecyclerViewAdapter myAdapter;
	private List<SingleEntity> aList_TwoFragment;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		mcontext=getActivity();

		mBaseview = inflater.inflate(R.layout.fragment_two, container, false);
		mRecyclerView = (RecyclerView) mBaseview.findViewById(R.id.recycler_view_two);
		mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
		mRecyclerView.setItemAnimator(new DefaultItemAnimator());
		mRecyclerView.setAdapter(myAdapter);
		new MyBookAsyncTask().execute();

		//mRecyclerView.setOnClickListener(this);

		//mRecyclerView.setAdapter(new MyRecyclerViewAdapter(getActivity(), datas));
		return mBaseview;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

	}

	public class MyBookAsyncTask extends AsyncTask {
		@Override
		protected Object doInBackground(Object[] objects) {


			try{
				String ch= URLEncoder.encode("言情", "utf-8");
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
						bookSingleEntity.setFirstUrl(book.getString("id"));//书籍具体的地址
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
			aList_TwoFragment=result;
			//aList_OneFragment=result;
			//System.out.print(aList_OneFragment.size());
			//System.out.println(result.get(0).getAuthorName()+" "+result.get(0).getFirstUrl()+" "+result.get(0).getImageUrl());
			//mRecyclerView.setAdapter(new MyRecyclerViewAdapter(getActivity(), datas));

			if(result!=null){
				myAdapter = new MyRecyclerViewAdapter(mcontext,aList_TwoFragment);
				mRecyclerView.setAdapter(myAdapter);
				myAdapter.setOnItemClickListener(new MyRecyclerViewAdapter.onItemClickListener() {
					@Override
					public void onItemClick(View view, int position) {
						System.out.println("xjjxklkaskxlas;asxas");
						Intent intent2 = new Intent(mcontext,book_Detail_Activity.class);
						Bundle bundle2 = new Bundle();
						bundle2.putString("url", aList_TwoFragment.get(position).getFirstUrl().toString());
						bundle2.putString("imageurl", aList_TwoFragment.get(position).getImageUrl().toString());
						intent2.putExtras(bundle2);
						startActivity(intent2);
					}
					@Override
					public void onItemLongClick(View view, final int position) {

						new AlertDialog.Builder(mcontext)
								.setTitle("o(^▽^)o")
								.setMessage("是否删除")
								.setPositiveButton("确定", new DialogInterface.OnClickListener(){
									@Override
									public void onClick(DialogInterface dialog, int which) {
										aList_TwoFragment.remove(position);
										myAdapter.notifyDataSetChanged();
									}
								})
								.show();

					}
				});
			}

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
					myAdapter.setFlagBusy(false);
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
	};
}
