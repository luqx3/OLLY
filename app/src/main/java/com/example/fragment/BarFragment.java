package com.example.fragment;

import com.example.cyhtest.MainActivity;
import com.example.cyhtest.R;
import com.example.cyhtest.Search_Activity;
import com.example.cyhtest.Search_test2;
import com.example.view.SlidingMenu;
import com.example.view.TitleBarView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;

public class BarFragment extends Fragment {
	private static final String TAG = "BarFragment";
	private Context mContext;
	private View mBaseView;
	// 定义标题栏弹窗按钮
	private TitleBarView mTitleBarView;
	private Button mButton;
	// 定义左侧滑
	private SlidingMenu mLeftMenu;
	private View mSlideView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		mContext = getActivity();
		mBaseView = inflater.inflate(R.layout.fragment_main_father, null);// titlebar左中右
		mSlideView = inflater.inflate(R.layout.aaaa, null);// 侧滑
		findTitleBarView();
		initTitleBarView();
		return mBaseView;
	}

	private void findTitleBarView() {
		// 右边的pop
		mTitleBarView = (TitleBarView) mBaseView.findViewById(R.id.title_bar);//fragment_main_father
		mButton = (Button) mBaseView.findViewById(R.id.title_btn_right);//common_title_bar
		// 左边的slide
		mLeftMenu = (SlidingMenu) mSlideView.findViewById(R.id.id_menu);//aaaaa
	}

	private void initTitleBarView() {
		mTitleBarView.setCommonTitle(View.VISIBLE, View.VISIBLE, View.VISIBLE);
		//mTitleBarView.setBtnTitleLeft(R.drawable.slide_menu);
		mTitleBarView.setBtnTitleLeftOnClickListener(new OnClickListener() {


			@Override
			public void onClick(View v) {
				if (((MainActivity)mContext).mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
					((MainActivity)mContext).mDrawerLayout.closeDrawer(Gravity.LEFT);
				} else {
					((MainActivity)mContext).mDrawerLayout.openDrawer(Gravity.LEFT);
				}
				//((MainActivity)mContext).mDrawerLayout.openDrawer(Gravity.LEFT);
				//mLeftMenu.toggle();// 切换
			}
		});
		mTitleBarView.setBtnTitleRightOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				//点击右边按键事件
				Intent intent = new Intent(mContext, Search_Activity.class);
				startActivity(intent);
				//Intent intent = new Intent(mContext, Search_test2.class);
				//startActivity(intent);

			}
		});
	}

}