package com.example.Book;

import android.os.AsyncTask;
import android.widget.ListView;

import com.example.Adapter.InfoAdapter;
import com.example.entity.SingleEntity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by HP on 2016/12/16.
 */

public class MyBookAsyncTask extends AsyncTask {
    @Override
    protected Object doInBackground(Object[] objects) {

        String uString = "https://api.douban.com/v2/book/search?"+objects[0].toString()+"=" + objects[1].toString();
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

                    result.add(bookSingleEntity);
                }
                return result;
            }
        }catch(Exception e){
        }




        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        System.out.println("onPostExecute");

        //InfoAdapter myAdapater=;// 用来加载BaseAdapater

        //myAdapater = new InfoAdapter(this, mlistView);
    }
}
