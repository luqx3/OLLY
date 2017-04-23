package com.example.cyhtest;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.Adapter.bookslefAdapter;
import com.example.Cache.ImageLoader;
import com.example.entity.SingleEntity;
import com.example.fragment.FourFragment;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by HP on 2016/12/21.
 */

public class Borrow_bookshelf extends AppCompatActivity {
    private String Base_url="https://api.douban.com/v2/book/";
    static private Connection conn;
    private String student_id;
    private ListView bookself_list;
    private FloatingActionButton floatingbutton;
    private bookslefAdapter Borrow_bookshelf_Adapter;
    private ImageButton mylibary_borrow_search_back;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_library_borrow_layout);
        initView();
    }
    void initView(){
        bookself_list=(ListView)findViewById(R.id.mylibary_borrow_list_view);
        floatingbutton=(FloatingActionButton)findViewById(R.id.mylibary_borrow_bookself_Floating);
        mylibary_borrow_search_back=(ImageButton)findViewById(R.id.mylibary_borrow_search_back);
        Bundle bundle = getIntent().getExtras();
        student_id=bundle.getString("student_id");
        new MyBookAsyncTask_Borrow().execute();
        floatingbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Borrow_bookshelf.this,Search_in_DB.class);
                //  Bundle bundle = new Bundle();
                //  bundle.putString("student_id",student_id);
                //  intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        mylibary_borrow_search_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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


    public class MyBookAsyncTask_Borrow extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] objects) {
            if(connect()){
                System.out.println("数据库连接成功");
                try{
                    Statement stmt=conn.createStatement();
                    String sql="select * from borrow natural join book where sno="+student_id;
                    ResultSet rs=stmt.executeQuery(sql);
                    List<SingleEntity> result = new ArrayList<SingleEntity>();
                    while(rs.next()){
                        SingleEntity bookSingleEntity = new SingleEntity();
                        if(rs.getString("bna")!=null)bookSingleEntity.setBookName(rs.getString("bna"));
                        if(rs.getString("bda")!=null)bookSingleEntity.setPubdate(rs.getString("bda"));
                        if(rs.getString("bpu")!=null)bookSingleEntity.setPublisher(rs.getString("bpu"));
                        if(rs.getString("br_yhdate")!=null)bookSingleEntity.setBorrow_ddl(rs.getString("br_yhdate"));
                        if(rs.getString("bpl")!=null)bookSingleEntity.setLocation(rs.getString("bpl"));
                        if(rs.getString("ble_nu")!=null)bookSingleEntity.setLeave_num(rs.getString("ble_nu"));
                        System.out.println(rs.getString("sno")+"/"+rs.getString("bno")+"/"+rs.getString(3));

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
            List<SingleEntity> result=(List<SingleEntity> )o;
            if(result.size()!=0) {
                Borrow_bookshelf_Adapter = new bookslefAdapter(getApplicationContext(), bookself_list, result);
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
                    Borrow_bookshelf_Adapter.setFlagBusy(true);
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
