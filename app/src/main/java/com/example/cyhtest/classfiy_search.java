package com.example.cyhtest;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Adapter.InfoAdapter;
import com.example.entity.SingleEntity;
import com.example.view.CatLoadingView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by HP on 2016/12/22.
 */

public class classfiy_search extends FragmentActivity  {

    private TextView TextView;
    private ImageButton classify_search_back;
    private ListView classify_search_list_view;
    private InfoAdapter myAdapater;
    private String classify_tag;
    public CatLoadingView mView;
    public List<SingleEntity> aList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.classify_search_layout);
        initView();
    }

    void initView(){
        classify_search_back=(ImageButton)findViewById(R.id.classify_search_back);
        classify_search_list_view=(ListView)findViewById(R.id.classify_search_list_view);
        TextView=(TextView)findViewById(R.id.classify_tab) ;
        Bundle bundle = getIntent().getExtras();
        classify_tag = bundle.getString("classify");

        TextView.setText(classify_tag);
        mView=new CatLoadingView();
        classify_search_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        classify_search_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SingleEntity mbp = aList.get(position);
                Intent intent = new Intent(classfiy_search.this, book_Detail_Activity.class);
                Bundle bundle = new Bundle();
                bundle.putString("url", mbp.getFirstUrl().toString());
                bundle.putString("imageurl", mbp.getImageUrl().toString());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });



        try {
            classify_tag = URLEncoder.encode(classify_tag, "utf-8");
        }catch (Exception e){

        }

        mView.show(getSupportFragmentManager(),"");
        new MyBookAsyncTask_classify().execute("tag",classify_tag ,"Breif_data");


    }
    public class MyBookAsyncTask_classify extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] objects) {

            String uString = "https://api.douban.com/v2/book/search?"+objects[0].toString()+"=" + objects[1].toString()+"&count=100";
            System.out.println("wangluo dizhi "+uString);
            System.out.println(uString);
            try{
                URL url = new URL(uString);

                StringBuilder builder = new StringBuilder();
                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(url.openStream()));
                for (String s = bufferedReader.readLine(); s != null; s = bufferedReader
                        .readLine()) {
                    builder.append(s);
                }
                JSONObject jsonObject = new JSONObject(builder.toString());
                JSONArray books = jsonObject.getJSONArray("books");
                if(objects[2].toString().equals("Breif_data")) {
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
                        System.out.print(book.getString("title")+"   "+book.getString("publisher")+"   "+book.getString("pubdate"));
                        result.add(bookSingleEntity);
                    }

                    return result;
                }
            }catch(Exception e){
                System.out.println("error in DoinBackgroun serache_activity");
            }




            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            System.out.println("onPostExecute");
            List<SingleEntity> result=(List<SingleEntity> )o;
            aList=result;
            mView.dismiss();
            //aList=result;
            if(result!=null) {
                myAdapater = new InfoAdapter(classfiy_search.this, classify_search_list_view, result);
                classify_search_list_view.setAdapter(myAdapater);
                classify_search_list_view.setOnScrollListener(mScrollListener);
                myAdapater.setFlagBusy(false);
            }else{
                Toast.makeText(getApplicationContext(), "查询结果为空", Toast.LENGTH_SHORT).show();
            }

        }
    }
    AbsListView.OnScrollListener mScrollListener = new AbsListView.OnScrollListener() {

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            switch (scrollState) {
                case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
                    myAdapater.setFlagBusy(true);
                    System.out.println("SCROLL_STATE_FLING");
                    break;
                case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                    System.out.println("SCROLL_STATE_IDLE");
                    myAdapater.setFlagBusy(false);
                    break;
                case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                    System.out.println("SCROLL_STATE_TOUCH_SCROLL");
                    myAdapater.setFlagBusy(false);
                    break;
                default:
                    break;
            }
            myAdapater.notifyDataSetChanged();
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem,
                             int visibleItemCount, int totalItemCount) {

        }
    };

}