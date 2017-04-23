package com.example.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cyhtest.R;
import com.example.entity.TimerEntity;

import java.util.List;
import java.util.Map;

/**
 * Created by HP on 2017/1/13.
 */

public class TimelineAdapter extends BaseAdapter {

    private Context context;
    private List<TimerEntity> list;
    private LayoutInflater inflater;

    public TimelineAdapter(Context context, List<TimerEntity> list) {
        super();
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {

        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.timer_line_item_layout, null);
            viewHolder = new ViewHolder();

            viewHolder.title = (TextView) convertView.findViewById(R.id.title);
            viewHolder.date=(TextView)convertView.findViewById(R.id.show_time);
            viewHolder.image_1=(ImageView)convertView.findViewById(R.id.image_1);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        String titleStr = list.get(position).getTitle().toString();


        viewHolder.title.setText(titleStr);
        viewHolder.date.setText(list.get(position).getDate());
        if(list.get(position).getResourceId()==0){
            viewHolder.image_1.setVisibility(viewHolder.image_1.GONE);
        }
        else{
            viewHolder.image_1.setImageResource((list.get(position).getResourceId()));
            viewHolder.image_1.setVisibility(viewHolder.image_1.VISIBLE);
        }

        return convertView;
    }

    static class ViewHolder {
        public TextView year;
        public TextView month;
        public TextView date;
        public TextView title;
        public ImageView image_1;
    }
}

