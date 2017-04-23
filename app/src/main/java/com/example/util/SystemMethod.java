package com.example.util;

import android.content.Context;

public class SystemMethod {

	/**
	 * 将dip转为px
	 *
	 * @param dip
	 * @return
	 */
	public static int dip2px(Context context, float dipValue) {
		float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	/**
	 * 将px转为dip
	 *
	 * @param px
	 * @return
	 */
	public static int px2dip(Context context, float pxValue) {
		float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);

	}

	/**
	 * 获取屏幕的宽度 px
	 *
	 * @return
	 */
	public static int getScreenWidth(Context context) {
		return context.getResources().getDisplayMetrics().widthPixels;
	}

	/**
	 * 获取屏幕高度px
	 *
	 * @return
	 */
	public static int getScreenHeight(Context context) {
		return context.getResources().getDisplayMetrics().heightPixels;
	}

	// 获取屏幕宽度
	// public static int getScreenWidth(Context context) {
	// DisplayMetrics dm = new DisplayMetrics();
	// ((Activity) context).getWindowManager().getDefaultDisplay()
	// .getMetrics(dm);
	// return dm.widthPixels;
	// }

}
