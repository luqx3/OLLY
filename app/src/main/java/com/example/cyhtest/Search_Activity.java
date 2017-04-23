package com.example.cyhtest;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Adapter.InfoAdapter;
//import com.example.Book.MyBookAsyncTask;
import com.example.entity.SingleEntity;
import com.example.loader.SimpleInfoLoder;
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
 * Created by HP on 2016/12/16.
 */

public class Search_Activity extends FragmentActivity implements View.OnClickListener{

    private ImageButton  back;
    private ImageButton  Search;
    private EditText Search_editText;
    private InfoAdapter myAdapater;// 用来加载BaseAdapater
    private ListView search_list_view;
    final static int LOAD_DATA = 1;
    public List<SingleEntity> aList;// MovieBriefPojo 返回的泛型LIST
    public CatLoadingView mView;

   // SingleEntity mbp;// 传递点击事件的 击点

   // public Spinner sp;// 选择要搜索的类型
    public TextView tv_search_group;// 搜索框左边组合框

    //ProgressDialog proDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity_layout);
        initViewById();
       // ConnectivityManager connMgr = (ConnectivityManager)
       //         getSystemService(Context.CONNECTIVITY_SERVICE);
      // NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
       // if(networkInfo != null && networkInfo.isConnected()) //表示网络已连接
      //  {
       //     Toast.makeText(getApplicationContext(), "网络可用", Toast.LENGTH_SHORT).show();
            //new MyAsyncTask().execute(editText.getText().toString());
            //创建AsyncTask实例并执行
       // }


    }

    void initViewById(){
        back=(ImageButton)findViewById(R.id.search_back);
        Search=(ImageButton)findViewById(R.id.search);
        Search_editText=(EditText)findViewById(R.id.search_edit_text);
        tv_search_group=(EditText)findViewById(R.id.search_edit_text);
        search_list_view=(ListView)findViewById(R.id.search_list_view);
        mView=new CatLoadingView();
        back.setOnClickListener(this);
        Search.setOnClickListener(this);

        search_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SingleEntity mbp = aList.get(position);
                Intent intent = new Intent(Search_Activity.this, book_Detail_Activity.class);
                Bundle bundle = new Bundle();
                bundle.putString("url", mbp.getFirstUrl().toString());
                bundle.putString("imageurl", mbp.getImageUrl().toString());

                intent.putExtras(bundle);

                startActivity(intent);
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.search_back:
                //Intent intent = new Intent(Search_Activity.this,MainActivity.class);
                //startActivity(intent);
                finish();
                break;
            case R.id.search:

                //mHandler.sendEmptyMessage(LOAD_DATA);
                //Simple();
                String ch="";
                String keyworld=Search_editText.getText().toString();
                if(keyworld.equals("")){
                    Toast.makeText(getApplicationContext(), "请输入查找关键词", Toast.LENGTH_SHORT).show();
                }else{
                    try{

                        ch= URLEncoder.encode(keyworld, "utf-8");
                    }catch(Exception e){

                    }
                    //MyBookAsyncTask  myBookAsyncTask=new MyBookAsyncTask("q","ch","Breif_data");
                    //Intent intent=new Intent(this,book_Detail_Activity.class);
                    //startActivity(intent);
                    mView.show(getSupportFragmentManager(),"");
                    new MyBookAsyncTask().execute("q",ch,"Breif_data");
                }

                break;
        }
    }

    public class MyBookAsyncTask extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] objects) {

            String uString = "https://api.douban.com/v2/book/search?"+objects[0].toString()+"=" + objects[1].toString()+"&count=100";
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
            List<SingleEntity> result = (List<SingleEntity>) o;
            aList = result;
            System.out.println("信息：" + result.get(0).getAuthorName() + " " + result.get(0).getFirstUrl() + " " + result.get(0).getImageUrl());
            if(result!=null){
            myAdapater = new InfoAdapter(Search_Activity.this, search_list_view, result);
            search_list_view.setAdapter(myAdapater);
            search_list_view.setOnScrollListener(mScrollListener);
            myAdapater.setFlagBusy(false);
            mView.dismiss();
             }
            else{
                Toast.makeText(getApplicationContext(), "查询结果为空", Toast.LENGTH_SHORT).show();
            }



            //InfoAdapter myAdapater=;// 用来加载BaseAdapater

            //myAdapater = new InfoAdapter(this, mlistView);
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
/*
    Handler mHandler=new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what) {
                case LOAD_DATA:
                    //String response = (String) msg.obj;
                    //infomation.setText(response);
                    ArrayList<String> list= (ArrayList<String>) msg.obj;
                    String str="";
                    for(int i=0;i<list.size();i++){
                        str+=" "+list.get(i);
                    }
                    System.out.println(str);
                    //infomation.setText(str);

                    break;
                default:
                    break;
            }


        }
    };

    /*void Simple(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    String keyworld=Search_editText.getText().toString();
                    List<SingleEntity> result = new ArrayList<SingleEntity>();
                    String ch = URLEncoder.encode(keyworld, "utf-8");

                    String uString = "https://api.douban.com/v2/book/search?q=" + ch+"&count=1";

                    URL url = new URL(uString);

                    StringBuilder builder = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(
                            new InputStreamReader(url.openStream()));
                    for (String s = bufferedReader.readLine(); s != null; s = bufferedReader
                            .readLine()) {
                        builder.append(s);
                    }
                    System.out.println(builder.toString());

                    JSONObject jsonObject = new JSONObject(builder.toString());
                    JSONArray books = jsonObject.getJSONArray("books");

                    for (int i = 0; i < books.length(); i++) {
                        JSONObject book = (JSONObject) books.opt(i);

                        SingleEntity bookSingleEntity = new SingleEntity();
                        bookSingleEntity.setMovieName(book.getString("title"));//书本名称
                        bookSingleEntity.setFirstUrl(book.getString("id"));//书籍具体的地址
                        bookSingleEntity.setImageUrl(book.getString("image"));//书籍图片
                        bookSingleEntity.setAuthorName(book.getJSONArray("author").optString(0));//作者
                        System.out.println(book.getString("title"));

                        result.add(bookSingleEntity);
                    }

                    Message message = new Message();
                    message.what = LOAD_DATA;
                    //XmlPullParser操作
                    //message.obj = parseXMLWithPull(response.toString());
                    message.obj = result;
                    mHandler.sendMessage(message);
                }catch(Exception e){

                }

            }
        }).start();
    }*/

}
