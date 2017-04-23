package com.example.loader;

import com.example.entity.SingleEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by HP on 2016/12/16.
 */

public class SimpleInfoLoder {
    public List<SingleEntity> findBookXml(String keyworld) throws Exception {
        {
            List<SingleEntity> result = new ArrayList<SingleEntity>();
            String ch = URLEncoder.encode(keyworld, "utf-8");

            String uString = "https://api.douban.com/v2/book/search?q=" + ch;
            System.out.println(uString);

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

            for (int i = 0; i < books.length(); i++) {
                JSONObject book = (JSONObject) books.opt(i);

                SingleEntity bookSingleEntity = new SingleEntity();
                bookSingleEntity.setBookName(book.getString("title"));//书本名称
                bookSingleEntity.setFirstUrl(book.getString("id"));//书籍具体的地址
                bookSingleEntity.setImageUrl(book.getString("image"));//书籍图片
                bookSingleEntity.setAuthorName(book.getJSONArray("author").optString(0)+"|"+book.getJSONArray("publisher"));//作者
                bookSingleEntity.setPublisher(book.getJSONArray("publisher").optString(0));

                result.add(bookSingleEntity);
            }
            return result;
        }
    }
}
