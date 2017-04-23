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
import com.example.cyhtest.R;
import com.example.entity.SingleEntity;

import java.util.List;

/**
 * Created by HP on 2016/12/22.
 */

public class Book_adpter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<SingleEntity> aList;
    private ImageLoader mImageLoader;
    private Context context;
    private String mText;

    private boolean mBusy = false;

    public Book_adpter(Context context, ListView listView, List<SingleEntity> mlist) {
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
        InfoAdapter.ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.book_list_item_layout_borrow, null);
            viewHolder = new InfoAdapter.ViewHolder();
            viewHolder.book_description = (TextView) convertView
                    .findViewById(R.id.tv_book_description_borrow);
            viewHolder.mImageView = (ImageView) convertView
                    .findViewById(R.id.iv_book_img_borrow);
            viewHolder.Title = (TextView) convertView
                    .findViewById(R.id.tv_book_title_borrow);
            viewHolder.book_info = (TextView) convertView
                    .findViewById(R.id.tv_book_info_borrow);
            viewHolder.hots_num=(TextView) convertView
                    .findViewById(R.id.tv_hots_num_borrow);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (InfoAdapter.ViewHolder) convertView.getTag();
        }

        SingleEntity BookBriefPojo = aList.get(position);

        // String str = "作者:";


        //viewHolder.mImageView.setBackgroundResource(R.mipmap.empty_book_img);
        viewHolder.Title.setText( BookBriefPojo.getBookName());
        viewHolder.book_info.setText("作者："+BookBriefPojo.getAuthorName());
        viewHolder.hots_num.setText("书总数:"+ BookBriefPojo.getLeave_num()+"  "+"可借数:"+ BookBriefPojo.getLeave_num()); //
        viewHolder.book_description.setText("出版社："+BookBriefPojo.getPublisher()+"\n"+"出版时间："+BookBriefPojo.getPubdate()+"\n"+"书目位置："+BookBriefPojo.getLocation()+"\n");
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

