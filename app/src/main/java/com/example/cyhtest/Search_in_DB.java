package com.example.cyhtest;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.Adapter.Book_adpter;
import com.example.Adapter.bookslefAdapter;
import com.example.entity.SingleEntity;
import com.example.view.CatLoadingView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by HP on 2016/12/22.
 */

public class Search_in_DB extends FragmentActivity implements AdapterView.OnItemClickListener{
    private ImageButton back;
    private ImageButton  Search;
    private EditText Search_editText;
    private ListView bookself_list;
    private Book_adpter Borrow_bookshelf_Adapter;
    private FloatingActionButton  mylibary_borrow_bookself_Floating_db;

    private List<SingleEntity> result_of_search;

    private String key_word;
    public CatLoadingView mView;
    private String student_id="1";
    public CatLoadingView mViewcat;




    private String Base_url="https://api.douban.com/v2/book/";
    static private Connection conn;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_in_db_layout);
        initView();

    }

    void initView(){
        back=(ImageButton)findViewById(R.id.search_back_db);
        Search=(ImageButton)findViewById(R.id.search_db);
        Search_editText=(EditText)findViewById(R.id.search_edit_text_db);
        bookself_list=(ListView)findViewById(R.id.search_list_view_db);
        mylibary_borrow_bookself_Floating_db=(FloatingActionButton)findViewById(R.id.mylibary_borrow_bookself_Floating_db);
        mViewcat=new CatLoadingView();

        mView=new CatLoadingView();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        bookself_list.setOnItemClickListener(this);

        Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                key_word=Search_editText.getText().toString();
                if(key_word.equals("")){
                    Toast.makeText(getApplicationContext(), "请输入查找关键词", Toast.LENGTH_SHORT).show();
                }else {
                    // mView.show(getSupportFragmentManager(),"");
                    new MyBookAsyncTask_Borrow_db().execute();
                    mView.show(getSupportFragmentManager(),"");
                }
            }
        });

        mylibary_borrow_bookself_Floating_db.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        System.out.println(result_of_search.get(position).getFirstUrl());
        new MyBookAsyncTask_Borrow_db_insert().execute(student_id,result_of_search.get(position).getFirstUrl());
    }

    public class MyBookAsyncTask_Borrow_db_insert extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] objects) {
            if(connect()){
                System.out.println("数据库连接成功");
                try{
                    Statement stmt=conn.createStatement();
                    //Date now=new Date();
                    String sql="insert into borrow(sno,bno,br_in) values('"+objects[0]+"','"+objects[1]+"','"+"2016-12-22"+"')";
                    System.out.println(sql);
                    stmt.execute(sql);
                    //ResultSet rs=stmt.executeQuery(sql);
                }catch (Exception e){
                    System.out.println("查询借书数据异常");
                }
            }
            else{
                Toast.makeText(getApplicationContext(), "数据库连接失败", Toast.LENGTH_SHORT).show();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            System.out.println("onPostExecute");
            Toast.makeText(getApplicationContext(), "借书成功", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent("com.example.lab4.staticreceiver");
            //intent.putExtra("id",fruit_list.get(position).getResourceId());
            //intent.putExtra("name",fruit_list.get(position).getName());
            sendBroadcast(intent);

        }
    }

    public class MyBookAsyncTask_Borrow_db extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] objects) {
            if(connect()){
                System.out.println("数据库连接成功");
                try{
                    Statement stmt=conn.createStatement();
                    String sql="select * from book where bna like '%"+key_word+"%';";// or name like '%"+line+"%'
                    System.out.println(sql);
                    ResultSet rs=stmt.executeQuery(sql);
                    List<SingleEntity> result = new ArrayList<SingleEntity>();
                    while(rs.next()){

                        SingleEntity bookSingleEntity = new SingleEntity();

                        //System.out.println("查找到记录"+rs.getString(0)+"/"+rs.getString(1)+"/"+rs.getString(3));

                        if(rs.getString("bno")!=null)bookSingleEntity.setFirstUrl(rs.getString("bno"));
                        if(rs.getString("bna")!=null)bookSingleEntity.setBookName(rs.getString("bna"));
                        if(rs.getString("bda")!=null)bookSingleEntity.setPubdate(rs.getString("bda"));
                        if(rs.getString("bpu")!=null)bookSingleEntity.setPublisher(rs.getString("bpu"));
                        //if(rs.getString("br_yhdate")!=null)bookSingleEntity.setBorrow_ddl(rs.getString("br_yhdate"));
                        if(rs.getString("bpl")!=null)bookSingleEntity.setLocation(rs.getString("bpl"));
                        if(rs.getString("ble_nu")!=null)bookSingleEntity.setLeave_num(rs.getString("ble_nu"));
                        if(rs.getString("bnu")!=null)bookSingleEntity.setTotal_num(rs.getString("bnu"));
                        String uString = Base_url+rs.getString("bno");
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
                        bookSingleEntity.setImageUrl(jsonObject.getString("image"));//书籍图片
                        bookSingleEntity.setAuthorName(jsonObject.getJSONArray("author").optString(0));//作者
                        bookSingleEntity.setSummary(jsonObject.getString("summary"));
                        result.add(bookSingleEntity);
                    }
                    return result;
                }catch (Exception e){
                    System.out.println("查询借书数据异常");
                }
            }
            else{
                Toast.makeText(getApplicationContext(), "数据库连接失败", Toast.LENGTH_SHORT).show();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            System.out.println("onPostExecute");
            // mView.dismiss();

            List<SingleEntity> result=(List<SingleEntity> )o;
            result_of_search=result;
            if(result==null||result.size()==0) {
                Toast.makeText(getApplicationContext(), "查询结果为空", Toast.LENGTH_SHORT).show();
            }
            else if(result.size()!=0) {
                mView.dismiss();
                Borrow_bookshelf_Adapter = new Book_adpter(getApplicationContext(), bookself_list, result);
                bookself_list.setAdapter(Borrow_bookshelf_Adapter);
                bookself_list.setOnScrollListener(mScrollListener);
                Borrow_bookshelf_Adapter.setFlagBusy(false);
            }
        }
    }

    AbsListView.OnScrollListener mScrollListener = new AbsListView.OnScrollListener() {

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            switch (scrollState) {
                case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
                    Borrow_bookshelf_Adapter.setFlagBusy(false);
                    System.out.println("SCROLL_STATE_FLING");
                    break;
                case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                    System.out.println("SCROLL_STATE_IDLE");
                    Borrow_bookshelf_Adapter.setFlagBusy(false);
                    break;
                case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                    System.out.println("SCROLL_STATE_TOUCH_SCROLL");
                    Borrow_bookshelf_Adapter.setFlagBusy(false);
                    break;
                default:
                    break;
            }
            Borrow_bookshelf_Adapter.notifyDataSetChanged();
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem,
                             int visibleItemCount, int totalItemCount) {

        }
    };
}
