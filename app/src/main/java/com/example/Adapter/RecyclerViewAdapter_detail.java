package com.example.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.Cache.ImageLoader;
import com.example.cyhtest.R;
import com.example.entity.SingleEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HP on 2016/12/19.
 */

public class RecyclerViewAdapter_detail  extends RecyclerView.Adapter<MyViewHolder> {
    private List<Integer> datas;
    private Context context;
    private List<Integer> lists;
    private ImageLoader mImageLoader;
    private List<SingleEntity> aList;
    private boolean mBusy = false;

   /*  public RecyclerViewAdapter_detail(Context context,List<SingleEntity> aList){
        this.context = context;
        this.aList=aList;
        mImageLoader = new ImageLoader(context);
    }
   public MyRecyclerViewAdapter(Context context,List<SingleEntity> aList) {
        this.aList=aList;
        //this.datas = datas;
        this.context = context;
        //getRandomHeights(datas);
        }*/
   public RecyclerViewAdapter_detail(Context context,List<Integer> datas) {
        //this.aList=datas;
        this.datas = datas;
        this.context = context;
        getRandomHeights(datas);
    }

    private void getRandomHeights(List<Integer> datas) {
        lists = new ArrayList<>();
        for (int i = 0; i < datas.size(); i++) {
            lists.add((int) (250 + Math.random() * 100));
        }
    }
    public void setFlagBusy(boolean busy) {
        this.mBusy = busy;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.myrecycleraiew_layout, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
         params.height = lists.get(position);//把随机的高度赋予item布局
        // params.height=(int)(250 + Math.random() * 400);
        holder.itemView.setLayoutParams(params);
        holder.mTextView.setText(position+" ");
        //holder.mImageView.setMaxHeight(params.height-100);
        //holder.mTextView.setText(aList.get(position).getBookName());
        //holder.mImageView.setBackgroundResource(R.mipmap.empty_book_img);
        //String url=aList.get(position).getImageUrl();
        //holder.mImageView.setBackgroundResource(R.mipmap.ic_launcher);
        //if (!mBusy) {
        //mImageLoader.DisplayImage(url, holder.mImageView, false);
        //} else {
        //   mImageLoader.DisplayImage(url, holder.mImageView, false);
        //}
    }

    @Override
    public int getItemCount() {
        //return aList.size();
        return datas.size();
    }
}

class MyViewHolder2 extends RecyclerView.ViewHolder {
    TextView mTextView;
    ImageView mImageView;
    public MyViewHolder2(View itemView) {
        super(itemView);
        mTextView = (TextView) itemView.findViewById(R.id.tv_book_title_recy);
        mImageView=(ImageView) itemView.findViewById(R.id.iv_book_img_recy);
        //.setBackgroundResource(R.mipmap.ic_launcher);
    }
}
