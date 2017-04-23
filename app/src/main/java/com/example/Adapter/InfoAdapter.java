package com.example.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.Cache.ImageLoader;
import com.example.cyhtest.MainActivity;
import com.example.cyhtest.R;
import com.example.cyhtest.Search_Activity;
import com.example.entity.SingleEntity;

import java.util.List;

/**
 * Created by HP on 2016/12/16.
 */

public class InfoAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<SingleEntity> aList;
    private ImageLoader mImageLoader;
    private Context context;
    private String mText;

    private boolean mBusy = false;

    public InfoAdapter(Context context, ListView listView,List<SingleEntity> mlist) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        aList=mlist;
        //aList = ((Search_Activity) context).aList;
       // mText = ((Search_Activity) context).tv_search_group.getText().toString();
        mImageLoader = new ImageLoader(context);
    }

    public void setFlagBusy(boolean busy) {
        this.mBusy = busy;
    }

    public int getCount() {
        return aList.size();
    }

    public Object getItem(int position) {
        if (position >= getCount()) {
            return null;
        }
        return aList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.book_list_item_layout, null);
            viewHolder = new ViewHolder();
            viewHolder.book_description = (TextView) convertView
                    .findViewById(R.id.tv_book_description);
            viewHolder.mImageView = (ImageView) convertView
                    .findViewById(R.id.iv_book_img);
            viewHolder.Title = (TextView) convertView
                    .findViewById(R.id.tv_book_title);
            viewHolder.book_info = (TextView) convertView
                    .findViewById(R.id.tv_book_info);
            viewHolder.hots_num=(TextView) convertView
                    .findViewById(R.id.tv_hots_num);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        SingleEntity BookBriefPojo = aList.get(position);

       // String str = "作者:";


        viewHolder.mImageView.setBackgroundResource(R.mipmap.empty_book_img);
        viewHolder.Title.setText( BookBriefPojo.getBookName());
        viewHolder.book_info.setText(BookBriefPojo.getAuthorName()+"/"+BookBriefPojo.getPublisher()+"/"+BookBriefPojo.getPubdate());
        viewHolder.book_description.setText("简介："+BookBriefPojo.getSummary());
        viewHolder.hots_num.setText("评分："+BookBriefPojo.getRating_average()); //
        String url = BookBriefPojo.getImageUrl();

        if (!mBusy) {
            mImageLoader.DisplayImage(url, viewHolder.mImageView, false);
        } else {
            mImageLoader.DisplayImage(url, viewHolder.mImageView, false);
        }

        return convertView;
    }

    static class ViewHolder {
        TextView book_description;
        ImageView mImageView;
        TextView Title;
        TextView book_info;
        TextView hots_num;
    }
}

