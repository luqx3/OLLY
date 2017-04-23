package com.example.cyhtest;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.Adapter.MyRecyclerViewAdapter;
import com.example.Adapter.RecyclerViewAdapter_detail;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HP on 2016/12/16.
 */

public class Search_test2 extends AppCompatActivity {

    private ListView boo_detail_listview;
    private RecyclerView mRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_detail_layout);
       /* mRecyclerView=(RecyclerView) findViewById(R.id.recyclerView_detail);
        //boo_detail_listview=(ListView)findViewById(R.id.listview_test);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        List<Integer> datas = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            datas.add(i);
        }
        mRecyclerView.setAdapter(new RecyclerViewAdapter_detail(this, datas));*/
       // ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strs);
        //boo_detail_listview.setAdapter(adapter);
    }

}
