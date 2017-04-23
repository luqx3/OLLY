package com.example.view;

import com.example.cyhtest.R;
import com.example.util.SystemMethod;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TitleBarView extends RelativeLayout {
	private static final String TAG = "TitleBarView";
	private Context mContext;
	private Button btn_titleLeft;
	private Button btn_titleRight;
	private TextView tv_center;

	public TitleBarView(Context context) {
		super(context);
		mContext = context;
		initView();
	}

	public TitleBarView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		initView();
	}

	private void initView() {
		LayoutInflater.from(mContext).inflate(R.layout.common_title_bar, this);
		btn_titleLeft = (Button) findViewById(R.id.title_btn_left);
		btn_titleRight = (Button) findViewById(R.id.title_btn_right);
		tv_center = (TextView) findViewById(R.id.title_txt);
	}

	public void setCommonTitle(int leftVisibility, int centerVisibility,
							   int rightVisibility) {
		btn_titleLeft.setVisibility(leftVisibility);
		btn_titleRight.setVisibility(rightVisibility);
		tv_center.setVisibility(centerVisibility);
	}

	public void setLeftTitle(int leftVisibility) {
		btn_titleLeft.setVisibility(leftVisibility);
	}

	public void setCenterTitle(int centerVisibility) {
		tv_center.setVisibility(centerVisibility);
	}

	public void setBtnTitleLeft(int icon) {
		Drawable img = mContext.getResources().getDrawable(icon);
		int height = SystemMethod.px2dip(mContext, 80);// 图像是50*45px,转化为dip
		int width = img.getIntrinsicWidth() * height / img.getIntrinsicHeight();
		img.setBounds(0, 0, width, height);
		btn_titleLeft.setCompoundDrawables(img, null, null, null);
	}

	public void setBtnTitleRight(int icon) {
		Drawable img = mContext.getResources().getDrawable(icon);
		int height = SystemMethod.px2dip(mContext, 80);// 图像是46*42px,转化为dip
		int width = img.getIntrinsicWidth() * height / img.getIntrinsicHeight();
		img.setBounds(0, 0, width, height);
		btn_titleRight.setCompoundDrawables(img, null, null, null);
	}

	public void setTitle(int resid) {
		tv_center.setText(resid);

	}

	public void setBtnTitleLeftOnClickListener(OnClickListener listener) {
		btn_titleLeft.setOnClickListener(listener);

	}

	public void setBtnTitleRightOnClickListener(OnClickListener listener) {
		btn_titleRight.setOnClickListener(listener);

	}

	public Button getTitleLeft() {
		return btn_titleLeft;
	}

	public Button getTitleRight() {
		return btn_titleRight;
	}

	public void destoryView() {
		btn_titleLeft.setText(null);
		btn_titleRight.setText(null);
		tv_center.setText(null);

	}

}
