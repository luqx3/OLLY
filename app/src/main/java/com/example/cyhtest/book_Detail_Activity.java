package com.example.cyhtest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatRatingBar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.Cache.BitmapCache;
import com.example.Cache.ImageLoader;
import com.example.entity.BookDetailEntity;
import com.example.view.ExpandTextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by HP on 2016/12/16.
 */

public class book_Detail_Activity extends AppCompatActivity {

    private String Base_url="https://api.douban.com/v2/book/";
    private ImageLoader mImageLoader;
    private String imageUrl;
    private String id;
    private ImageView mImageView;
    private MyBookAsyncTask_detail myBookAsyncTask_detail;
    private MyBookAsyncTask_detail_getImage mMyBookAsyncTask_detail_getImage;
    private TextView detail_textview;
    private ImageView ImageView_bg;//背景
    private ImageView ImageView_img;//图片
    private Toolbar toolbar;
    private ImageButton detail_back_img_button;
    private ImageButton iv_more_info;
    private FloatingActionButton fab;


    private AppCompatRatingBar ratingBar_hots;
    private TextView tv_hots_num;
    private TextView tv_comment_num;

    private LinearLayout ll_publish_info;
    private TextView tv_book_info;
    private TextView tv_author;
    private TextView tv_publisher;
    private TextView tv_subtitle;
    private TextView tv_origin_title;
    private TextView tv_translator;
    private TextView tv_publish_date;
    private TextView tv_pages;
    private TextView tv_price;
    private TextView tv_binding;
    private TextView tv_isbn;
    private TextView tv_link;




    private ExpandTextView tv_author_intro_expand;
    private ExpandTextView  tv_etv_brief_expand;
    private TextView tv_author_intro;
    private TextView etv_brief;
    private ImageButton author_intro_img_button;
    private ImageButton etv_brief_img_button;

    android.support.v7.widget.Toolbar mToolbar;
    CollapsingToolbarLayout mCollapsingLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_detail_layout2);

        initData();
        initView();
       // DebugUtil.error(url);

    }

    void initView(){
        mToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_detail);
        mCollapsingLayout=(CollapsingToolbarLayout)findViewById(R.id.collapsingToolbarLayout);
        detail_back_img_button=(ImageButton)findViewById(R.id.detail_back_img_button);
        iv_more_info=(ImageButton)findViewById(R.id.iv_more_info);
        ll_publish_info=(LinearLayout)findViewById(R.id.ll_publish_info);
        fab=(FloatingActionButton )findViewById(R.id.fab);

        //评分条
        ratingBar_hots=(AppCompatRatingBar )findViewById(R.id.ratingBar_hots);
        tv_hots_num=(TextView)findViewById(R.id.tv_hots_num);
        tv_comment_num=(TextView)findViewById(R.id.tv_comment_num);

        tv_book_info=(TextView)findViewById(R.id.tv_book_info);
        tv_author=(TextView)findViewById(R.id.tv_author);
        tv_publisher=(TextView)findViewById(R.id.tv_publisher);
        tv_subtitle=(TextView)findViewById(R.id.tv_subtitle);
        tv_origin_title=(TextView)findViewById(R.id.tv_origin_title);
        tv_translator=(TextView)findViewById(R.id.tv_translator);
        tv_publish_date=(TextView)findViewById(R.id.tv_publish_date);
        tv_pages=(TextView)findViewById(R.id.tv_pages);
        tv_price=(TextView)findViewById(R.id.tv_price);
        tv_binding=(TextView)findViewById(R.id.tv_binding);
        tv_isbn=(TextView)findViewById(R.id.tv_isbn);
        tv_link=(TextView)findViewById(R.id.tv_link);


        //tv_author_intro=(TextView)findViewById(R.id.tv_author_intro);
        //etv_brief=(TextView)findViewById(R.id.etv_brief);
        tv_author_intro_expand=(ExpandTextView)findViewById(R.id.tv_author_intro_expand);
        tv_etv_brief_expand=(ExpandTextView)findViewById(R.id.tv_etv_brief_expand);
        author_intro_img_button=(ImageButton)findViewById(R.id.author_intro_img_button);
        etv_brief_img_button=(ImageButton)findViewById(R.id.etv_brief_img_button);



        //mCollapsingLayout.setTitle("test");
       // mToolbar.setTitleTextColor(Color.BLACK);//设置ToolBar的titl颜色

       // setSupportActionBar(mToolbar);

        //mImageLoader=new ImageLoader(this);
        ImageView_bg=(ImageView) findViewById(R.id.iv_book_bg);
        ImageView_img=(ImageView) findViewById(R.id.iv_book_img);
        //TextView text_view_test=(TextView)findViewById(R.id.id_test);
        myBookAsyncTask_detail=new MyBookAsyncTask_detail();
        mMyBookAsyncTask_detail_getImage=new MyBookAsyncTask_detail_getImage();
        myBookAsyncTask_detail.execute();
        mMyBookAsyncTask_detail_getImage.execute();

        detail_back_img_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Intent
                finish();
            }
        });
        iv_more_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ll_publish_info.getVisibility()==ll_publish_info.GONE){
                    ll_publish_info.setVisibility(ll_publish_info.VISIBLE );
                }
                else if(ll_publish_info.getVisibility()==ll_publish_info.VISIBLE){
                    ll_publish_info.setVisibility(ll_publish_info.GONE );
                }
            }
        });

        author_intro_img_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_author_intro_expand.switchs();
                //if()
            }
        });
        etv_brief_img_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_etv_brief_expand.switchs();
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_author_intro_expand.switchs();
                tv_etv_brief_expand.switchs();
            }
        });
        //getBg();
        //getImg();

    }
    void getBg(){
        RequestQueue mQueue = Volley.newRequestQueue(getApplicationContext());
        com.android.volley.toolbox.ImageLoader imageLoader = new com.android.volley.toolbox.ImageLoader(mQueue, new BitmapCache());
        com.android.volley.toolbox.ImageLoader.ImageListener listener = com.android.volley.toolbox.ImageLoader.getImageListener(ImageView_bg,R.drawable.book1, R.drawable.book1);
        imageLoader.get(imageUrl, listener);
        ImageView_bg.setAlpha(0.9f);
    }

    void getImg(){
        RequestQueue mQueue = Volley.newRequestQueue(getApplicationContext());
        com.android.volley.toolbox.ImageLoader imageLoader = new com.android.volley.toolbox.ImageLoader(mQueue, new BitmapCache());
        com.android.volley.toolbox.ImageLoader.ImageListener listener = com.android.volley.toolbox.ImageLoader.getImageListener(ImageView_img,R.drawable.book1, R.drawable.book1);
        imageLoader.get(imageUrl, listener,200,200);
        //ImageView_bg.setAlpha(0.9f);
    }

    void initData(){
        Bundle bundle = getIntent().getExtras();
        mImageLoader = new ImageLoader(this);

        try {
            id = bundle.getString("url");
            imageUrl = bundle.getString("imageurl");
            System.out.println("in init data          " + imageUrl + "       " + id);
        }
        catch(Exception e){
            this.finish();
        }
    }


    public class MyBookAsyncTask_detail extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] objects) {

            BookDetailEntity bookSingleEntity = new BookDetailEntity();
            try{

                String uString = Base_url+id;
                //System.out.println("detail activity:  "+uString);
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

                //JSONArray books = jsonObject.getJSONArray("books");

                //System.out.println("detail   "+jsonObject.getString("title"));
                if(jsonObject.has("title")) bookSingleEntity.setTitle(jsonObject.getString("title"));//书本名称
                if(jsonObject.has("image")) bookSingleEntity.setImageUrl(jsonObject.getString("image"));//书籍图片

                if(jsonObject.has("summary"))bookSingleEntity.setSummary(jsonObject.getString("summary"));
                if(jsonObject.has("author"))bookSingleEntity.setAuthor(jsonObject.getJSONArray("author").getString(0));
                if(jsonObject.has("publisher"))bookSingleEntity.setPublisher(jsonObject.getString("publisher"));
                if(jsonObject.has("subtitle")) bookSingleEntity.setSubtitle(jsonObject.getString("subtitle"));
                if(jsonObject.has("origin_title"))bookSingleEntity.setOrigin_title(jsonObject.getString("origin_title"));

                if(jsonObject.has("author_intro"))bookSingleEntity.setAuthor_intro(jsonObject.getString("author_intro"));
                //if(jsonObject.getString("summary")!=null)bookSingleEntity.setSummary(jsonObject.getString("summary")+"\n"+jsonObject.getString("ebook_url"));
                if(jsonObject.has("pubdate"))bookSingleEntity.setPubdate(jsonObject.getString("pubdate"));
                if(jsonObject.has("price"))bookSingleEntity.setPrice(jsonObject.getString("price"));
                if(jsonObject.has("pages"))bookSingleEntity.setPages(jsonObject.getString("pages"));
                if(jsonObject.has("binding"))bookSingleEntity.setBinding(jsonObject.getString("binding"));
                if(jsonObject.has("isbn10"))bookSingleEntity.setIsbn10(jsonObject.getString("isbn10"));

                if(jsonObject.has("rating")) bookSingleEntity.setRating_average(jsonObject.getJSONObject("rating").getDouble("average"));
                if(jsonObject.has("rating"))bookSingleEntity.setRating_numRaters(jsonObject.getJSONObject("rating").getInt("numRaters"));

                if(jsonObject.has("translator")) bookSingleEntity.setTranslator(jsonObject.getJSONArray("translator").getString(0));
                if(jsonObject.has("ebook_url"))bookSingleEntity.setEbook_url(jsonObject.getString("ebook_url"));

                //bookSingleEntity.setRating_average(jsonObject.getJSONObject("rating").getString("average"));
                //System.out.println("detail bookSingleEntity:  "+bookSingleEntity.getTitle());
                return bookSingleEntity;
            }catch(Exception e){
                System.out.println("Exception In doInBackground"+"   url:"+imageUrl);
            }




            return bookSingleEntity;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            System.out.println("onPostExecute");
            BookDetailEntity result=(BookDetailEntity) o;
            if(o!=null) {
                mCollapsingLayout.setTitle(result.getTitle());
                //mCollapsingLayout.setStatusBarScrimColor(Color.BLACK);
                //mCollapsingLayout.setBackgroundColor(Color.BLACK);
                //  mCollapsingLayout.setDrawingCacheBackgroundColor(Color.BLACK);
                mCollapsingLayout.setCollapsedTitleTextColor(Color.GRAY);
                ratingBar_hots.setRating((float)(result.getRating_average())/2);
                tv_hots_num.setText(""+result.getRating_average()+"分");
                tv_comment_num.setText(result.getRating_numRaters()+"人评分");

                tv_book_info.setText(result.getAuthor()+"/"+result.getPublisher()+"/");
                tv_author.setText("作者："+result.getAuthor());
                tv_publisher.setText("出版社:"+result.getPublisher());
                tv_subtitle.setText("副标题:"+result.getSubtitle());
                tv_origin_title.setText("原作名:"+result.getOrigin_title());
                tv_translator.setText("译者:"+result.getTranslator());
                tv_publish_date.setText("出版年:"+result.getPubdate());
                tv_pages.setText("页数:"+result.getPages());
                tv_price.setText("定价:"+result.getPrice());
                tv_binding.setText("装帧:"+result.getBinding());


                tv_link.setText("isbn:"+result.getIsbn10());
                if(result.getEbook_url()!=null){
                    String html="图书连接："+"<a href='"+result.getEbook_url()+"'>"+"点击这里"+"</a>";
                    //String html="<a href=\"http://www.google.com\">link</a>"
                    CharSequence charSequence = Html.fromHtml(html);
                    tv_link.setText(charSequence);
                    tv_link.setMovementMethod(LinkMovementMethod.getInstance());
                }else{
                    tv_link.setText("图书连接:"+"无");
                }



                //tv_author_intro.setText(result.getAuthor_intro());
                //etv_brief.setText(result.getSummary());

                tv_author_intro_expand.setText(result.getAuthor_intro());
                tv_etv_brief_expand.setText(result.getSummary());
                tv_author_intro_expand.setTextMaxLine(4);
                tv_etv_brief_expand.setTextMaxLine(4);




            }

            //detail_textview.setText(result.getRating_average()+result.getImageUrl()+result.getImageUrl());




            //InfoAdapter myAdapater=;// 用来加载BaseAdapater

            //myAdapater = new InfoAdapter(this, mlistView);
        }
    }


    public class MyBookAsyncTask_detail_getImage extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] objects) {


            try{

                URL url = new URL(imageUrl);
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                conn.setConnectTimeout(5000);
                conn.setRequestMethod("GET");
                if(conn.getResponseCode() == 200) {
                    InputStream inputStream = conn.getInputStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    return bitmap;
                }
                //return loadImageFromNetwork(imageUrl);
            }catch(Exception e){
                System.out.println("Exception In doInBackground while get Image");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            //Drawable mDrawable=(Drawable)o;
            Bitmap mbitmap=(Bitmap) o;
            ImageView_img.setImageBitmap(mbitmap);
            ImageView_bg.setImageBitmap(mbitmap);
            ImageView_bg.setAlpha(0.9f);
        }
    }







}
