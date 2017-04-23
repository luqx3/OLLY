package com.example.fragment;

import java.util.ArrayList;


import com.example.cyhtest.MainActivity;
import com.example.cyhtest.R;
import com.example.cyhtest.R.drawable;
import com.example.cyhtest.R.id;
import com.example.cyhtest.R.layout;
import com.example.util.SystemMethod;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class FragmentSum extends Fragment{
	private ViewPager viewPager;// 页卡内容
	private ImageView imageView;// 动画图片即底部指示器
	private TextView textView1, textView2, textView3, textView4;
	private ArrayList<Fragment> fragmentsList;
	private int offset = 0;// 动画图片偏移量
	private int currIndex = 0;// 当前页卡编号
	private int bmpW;// 动画图片宽度
	// 4个fragment布局切换
	private OneFragment mOneFragment;
	private TwoFragment mTwoFragment;
	private ThreeFragment mThreeFragment;
	private FourFragment mFourFragment;
	// //////// /// /// /// ///
	private View mBaseView;
	private Context mContext;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		mContext = getActivity();
		mBaseView = inflater.inflate(R.layout.activity_main, null);

		initImageView();
		initTextView();
		initViewPager();
		return mBaseView;
	}

	/**
	 * 1 * 初始化动画，这个就是页卡滑动时，下面的横线也滑动的效果，在这里需要计算一些数据
	 */
	private void initImageView() {
		imageView = (ImageView) mBaseView.findViewById(R.id.cursor);//页卡滑动控件
		bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.line2)
				.getWidth();// 获取图片宽度
		int screenW = SystemMethod.getScreenWidth(mContext);// 获取分辨率宽度
		offset = (screenW / 4 - bmpW) / 2;// 计算偏移量
		Matrix matrix = new Matrix();
		matrix.postTranslate(offset, 0);
		imageView.setImageMatrix(matrix);// 设置动画初始位置

	}

	/**
	 * 2、初始化头标
	 */
	private void initTextView() {
		textView1 = (TextView) mBaseView.findViewById(R.id.text1);
		textView2 = (TextView) mBaseView.findViewById(R.id.text2);
		textView3 = (TextView) mBaseView.findViewById(R.id.text3);
		textView4 = (TextView) mBaseView.findViewById(R.id.text4);
		/*textView1.setOnClickListener(new MyOnClickListener(0));
		textView2.setOnClickListener(new MyOnClickListener(1));
		textView3.setOnClickListener(new MyOnClickListener(2));
		textView4.setOnClickListener(new MyOnClickListener(3));
		*/
		textView1.setOnClickListener(new MyOnClickListener(0));
		textView2.setOnClickListener(new MyOnClickListener(1));
		textView3.setOnClickListener(new MyOnClickListener(2));
		textView4.setOnClickListener(new MyOnClickListener(3));
	}

	/**
	 *
	 * 3、 头标点击监听
	 */
	private class MyOnClickListener implements OnClickListener {
		private int index = 0;

		public MyOnClickListener(int i) {
			index = i;
		}

		public void onClick(View v) {
			viewPager.setCurrentItem(index);
		}

	}

	private void initViewPager() {
		viewPager = (ViewPager) mBaseView.findViewById(R.id.viewpager);
		fragmentsList = new ArrayList<Fragment>();

		mOneFragment = new OneFragment();
		mTwoFragment = new TwoFragment();
		mThreeFragment = new ThreeFragment();
		mFourFragment = new FourFragment();

		fragmentsList.add(mOneFragment);
		fragmentsList.add(mTwoFragment);
		fragmentsList.add(mThreeFragment);
		fragmentsList.add(mFourFragment);

		viewPager.setAdapter(new MyFragmentPagerAdapter(getFragmentManager(),
				fragmentsList));
		viewPager.setCurrentItem(0);
		// 若不设置这个，标题下边的线不会随着页卡滑动而滑动
		viewPager.setOnPageChangeListener(new MyOnPageChangeListener());

	}

	public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

		private ArrayList<Fragment> fragmentsList;

		public MyFragmentPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		public MyFragmentPagerAdapter(FragmentManager fm,
									  ArrayList<Fragment> fragments) {
			super(fm);
			this.fragmentsList = fragments;
		}

		@Override
		public int getCount() {
			return fragmentsList.size();
		}

		@Override
		public Fragment getItem(int arg0) {
			return fragmentsList.get(arg0);
		}

		@Override
		public int getItemPosition(Object object) {
			return super.getItemPosition(object);
		}
	}

	public class MyOnPageChangeListener implements OnPageChangeListener {
		int one = offset * 2 + bmpW;// 页卡1 -> 页卡2 偏移量
		int two = one * 2;// 页卡1 -> 页卡3 偏移量
		int three = one * 3;

		public void onPageScrollStateChanged(int arg0) {
			// arg0 ==1的时辰默示正在滑动，arg0==2的时辰默示滑动完毕了，arg0==0的时辰默示什么都没做，就是停在那。

		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// 默示在前一个页面滑动到后一个页面的时辰，在前一个页面滑动前调用的办法。
		}

		public void onPageSelected(int arg0) {

			// arg0是默示你当前选中的页面，这事务是在你页面跳转完毕的时辰调用的。
			//TranslateAnimation(float fromXDelta, float toXDelta, float fromYDelta, float toYDelta)
			/*Animation animation = new TranslateAnimation(one*currIndex,one*arg0,0,0);// 显然这个比较简洁，只有一行代码。

			currIndex = arg0;
			animation.setFillAfter(true);// True:图片停在动画结束位置
			animation.setDuration(300);
			imageView.startAnimation(animation);*/
			Animation animation = null;
			switch (arg0) {
				case 0:
					if (currIndex == 1) {
						animation = new TranslateAnimation(one, 0, 0, 0);
					} else if (currIndex == 2) {
						animation = new TranslateAnimation(two, 0, 0, 0);
					}else if(currIndex == 3)
						animation = new TranslateAnimation(three, 0,0,0);
					break;
				case 1:
					if(currIndex == 0){
						animation = new TranslateAnimation(offset, one, 0, 0);
					}else if(currIndex == 2){
						animation = new TranslateAnimation(two, one, 0, 0);
					}else if(currIndex == 3)
						animation = new TranslateAnimation(three, one, 0, 0);
					break;
				case 2:
					if(currIndex == 0){
						animation = new TranslateAnimation(offset, two, 0, 0);
					}else if(currIndex == 1){
						animation = new TranslateAnimation(one, two, 0, 0);
					}else if(currIndex == 3)
						animation = new TranslateAnimation(three, two, 0, 0);
					break;
				case 3:
					if(currIndex == 0){
						animation = new TranslateAnimation(offset, three, 0, 0);
					}else if(currIndex == 1){
						animation = new TranslateAnimation(one, three, 0, 0);
					}else if(currIndex == 2){
						animation = new TranslateAnimation(two, three, 0, 0);
					}
					break;
			}
			currIndex = arg0;//保存当前页
			animation.setFillAfter(true);// True:图片停在动画结束位置
			animation.setDuration(300);
			imageView.startAnimation(animation);

		}

	}
}