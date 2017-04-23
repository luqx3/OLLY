package com.example.cyhtest;

import java.util.ArrayList;

import com.example.fragment.BarFragment;
import com.example.fragment.FourFragment;
import com.example.fragment.OneFragment;
import com.example.fragment.FragmentSum;
import com.example.fragment.SlideBarFragment;
import com.example.fragment.ThreeFragment;
import com.example.fragment.TwoFragment;
import com.example.view.CatLoadingView;
import com.example.view.CircleRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {
	private ViewPager viewPager;// 页卡内容
	private ImageView imageView;// 动画图片
	private TextView textView1, textView2, textView3, textView4;
	private ArrayList<Fragment> fragmentsList;
	private int offset = 0;// 动画图片偏移量
	private int currIndex = 0;// 当前页卡编号
	private int bmpW;// 动画图片宽度
	private OneFragment tecInfroFragment;
	private TwoFragment courseReqFragment;
	private ThreeFragment courseContentFragment;
	private FourFragment placeTimeFragment;


	public String student_id="1";



	public CatLoadingView mView;
	public FragmentManager fm2 = getSupportFragmentManager();


	private Toolbar mToolbar;
	public DrawerLayout mDrawerLayout;



	//private CircleRefreshLayout mRefreshLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bbbb_layout);
		initFirstFragment();
		initSecondFragment();

		initView();
		InitDraw();
		login();
		ConnectivityManager connMgr = (ConnectivityManager)
				getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if(networkInfo != null && networkInfo.isConnected()) //表示网络已连接
		{
			Toast.makeText(getApplicationContext(), "网络可用", Toast.LENGTH_SHORT).show();
			//new MyAsyncTask().execute(editText.getText().toString());
			//创建AsyncTask实例并执行
		}
		//initView();
	}
	// 第三种情况
	private void initSecondFragment() {
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		FragmentSum mFragmentSum = new FragmentSum();
		ft.add(R.id.fl_secondbar, mFragmentSum);
		ft.commit();
	}

	// 第三种情况
	private void initFirstFragment() {
		mView = new CatLoadingView();
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		BarFragment mBarFragment = new BarFragment();
		ft.add(R.id.fl_firstbar, mBarFragment);
		ft.commit();
	}

	void initView(){
		ImageButton imageView=(ImageButton)findViewById(R.id.head_portrait);
		Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.head_portrait);
		imageView.setImageBitmap(toRoundBitmap(b));
	}

	void login(){
		//跳到注册界面
		TextView head_portrait_name = (TextView) findViewById(R.id.head_portrait_name);
		head_portrait_name.setText("登录");
		head_portrait_name.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View view){
				Intent intent = new Intent(MainActivity.this, LoginActivity.class);
				startActivity(intent);
			}
		});

	}

	void about_us(){
		//关于我们界面

	}

	public Bitmap toRoundBitmap(Bitmap bitmap){
		int width=bitmap.getWidth();
		int height=bitmap.getHeight();
		int r=0;
		//取最短边做边长
		if(width<height){
			r=width;
		}else{
			r=height;
		}
		//构建一个bitmap
		Bitmap backgroundBm=Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);
		//new一个Canvas，在backgroundBmp上画图
		Canvas canvas=new Canvas(backgroundBm);
		Paint p=new Paint();
		//设置边缘光滑，去掉锯齿
		p.setAntiAlias(true);
		RectF rect=new RectF(0, 0, r, r);
		//通过制定的rect画一个圆角矩形，当圆角X轴方向的半径等于Y轴方向的半径时，
		//且都等于r/2时，画出来的圆角矩形就是圆形
		canvas.drawRoundRect(rect, r/2, r/2, p);
		//设置当两个图形相交时的模式，SRC_IN为取SRC图形相交的部分，多余的将被去掉
		p.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		//canvas将bitmap画在backgroundBmp上
		canvas.drawBitmap(bitmap, null, rect, p);
		return backgroundBm;
	}

	private void InitDraw(){
		//mToolbar = (Toolbar) findViewById(R.id.toolbar);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

		//mToolbar.setTitleTextColor(Color.WHITE);
		//setSupportActionBar(mToolbar);
		//setActionbar()
		ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.string.drawer_open, R.string.drawer_close);
		//mDrawerToggle=new ActionBarDrawerToggle(this,)

		mDrawerToggle.syncState();//初始化状态
		mDrawerLayout.setDrawerListener(mDrawerToggle);



		NavigationView mNavigationView = (NavigationView) findViewById(R.id.navi);
		mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
			@Override
			public boolean onNavigationItemSelected(MenuItem menuItem) {
				Intent intent = new Intent(MainActivity.this,Borrow_bookshelf.class);
				Bundle bundle = new Bundle();
				bundle.putString("student_id",student_id);
				intent.putExtras(bundle);
				switch (menuItem.getItemId())
				{
					case R.id.book_borrow :
					 Toast.makeText(getApplicationContext(), "点击bookborrow", Toast.LENGTH_SHORT).show();
					//getSupportFragmentManager().beginTransaction().replace(R.id.frame_content,new FragmentOne()).commit();
					//mToolbar.setTitle("我的动态");

						startActivity(intent);
						break;
					case R.id.book_borrow_his:
						startActivity(intent);
						break;
					case R.id.Search_in_DB:
						Intent intent2 = new Intent(MainActivity.this,Search_in_DB.class);
						startActivity(intent2);
						break;
					case R.id.my_info:
						Intent intent4 = new Intent(MainActivity.this,my_info_login.class);
						startActivity(intent4);
						break;
					case R.id.aboutus:
						Intent intent5 = new Intent(MainActivity.this, ActContentByDefinedView.class);
						startActivity(intent5);
						break;

				}
				menuItem.setChecked(true);//点击了把它设为选中状态

				mDrawerLayout.closeDrawers();//关闭抽屉


				return true;
			}
		});

	}

}
