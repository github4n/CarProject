package com.mh.core.tools;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;

import java.lang.ref.WeakReference;

/**
 * 屏幕帮助类
 * @author 李小健
 *
 */
public class MHScreenUtil {

	DisplayMetrics dm = null;
	Display display = null;
	static MHScreenUtil util = null;
	/** 缓存activity实例 */
	private static WeakReference<Activity> mActivity;

	private MHScreenUtil(Activity activity){
		dm = new DisplayMetrics();
		display = activity.getWindowManager().getDefaultDisplay();
		display.getMetrics(dm);
	}


	/**
	 * 获取util实例
	 * @param activity
	 * @return
	 */
	@Deprecated
	public static MHScreenUtil getInStance(Activity activity){
		if(util == null){
			util = new MHScreenUtil(activity);
		}
		mActivity = new WeakReference<>(activity);
		return util;
	}

	/**
	 * 获取Activity当前的Display参数
	 * @param activity activity
	 * @return
	 */
	public static Display getDisplay(Activity activity){
		if(activity != null && !activity.isFinishing()) {
			return activity.getWindowManager().getDefaultDisplay();
		} else{
			return null;
		}
	}

	/**
	 * 获取当前Activity的DisplayMetrics
	 * @param activity activity
	 * @return
	 */
	private static DisplayMetrics getActivityDisplayMetrics(Activity activity){
		DisplayMetrics dm = new DisplayMetrics();
		Display display = getDisplay(activity);
		if(display != null) {
			getDisplay(activity).getMetrics(dm);
			return dm;
		} else{
			return null;
		}
	}

	/**
	 * 得到手机屏幕宽度
	 * @return
	 */
	@Deprecated
	public int getPxWidth(){
		Display display = getDisplay(mActivity.get());
		if(display != null) {
			return display.getWidth();
		} else{
			return 0;
		}
	}

	/**
	 * 得到手机屏幕宽度
	 * @return
	 */
	public static int getPxWidth(Activity activity){
		Display display = getDisplay(activity);
		if(display != null) {
			return display.getWidth();
		}else{
			return 0;
		}
	}
	
	/**
	 * 获得手机屏幕宽度，单位dip
	 * @return
	 */
	@Deprecated
	public int getDipWidth(){
		DisplayMetrics dm = getActivityDisplayMetrics(mActivity.get());
		Display display = getDisplay(mActivity.get());
		if(display != null && dm != null) {
			return display.getWidth() / (int) dm.density;
		} else{
			return 0;
		}
	}

	/**
	 * 获得手机屏幕宽度，单位dip
	 * @return
	 */
	public static int getDipWidth(Activity activity){
		DisplayMetrics dm = getActivityDisplayMetrics(activity);
		Display display = getDisplay(activity);
		if(display != null && dm != null) {
			return display.getWidth() / (int) dm.density;
		} else{
			return 0;
		}
	}
	
	/**
	 * 得到手机屏幕高度
	 * @return
	 */
	@Deprecated
	public int getPxHeight(){
		Display display = getDisplay(mActivity.get());
		if(display != null) {
			return display.getHeight();
		} else{
			return 0;
		}
	}

	/**
	 * 得到手机屏幕高度
	 * @return
	 */
	public static int getPxHeight(Activity activity){
		Display display = getDisplay(activity);
		if(display != null) {
			return display.getHeight();
		} else{
			return 0;
		}
	}


	/**
	 * 将dp转换成px
	 * @param dp_value
	 * @return
	 */
	@Deprecated
	public int getPxValue(int dp_value){
		DisplayMetrics dm = getActivityDisplayMetrics(mActivity.get());
		if(dm != null) {
			return (int) (dm.density * dp_value);
		} else{
			return 0;
		}
	}

	/**
	 * 将dp转换成px
	 * @param dp_value
	 * @return
	 */
	public static int getPxValue(Activity activity, int dp_value){
		DisplayMetrics dm = getActivityDisplayMetrics(activity);
		if(dm != null) {
			return (int) (dm.density * dp_value);
		} else{
			return 0;
		}
	}

	/**
	 * 将px转换成dp
	 * @param px_value
	 * @return
	 */
	@Deprecated
	public int getDpValue(int px_value){
		DisplayMetrics dm = getActivityDisplayMetrics(mActivity.get());
		if(dm != null) {
			return (int) (px_value / dm.density);
		} else{
			return 0;
		}
	}

	/**
	 * 将px转换成dp
	 * @param px_value
	 * @return
	 */
	public static int getDpValue(Activity activity, int px_value){
		DisplayMetrics dm = getActivityDisplayMetrics(activity);
		if(dm != null) {
			return (int) (px_value / dm.density);
		} else{
			return 0;
		}
	}

	/**
	 * 获取状态栏高度
	 * @param activity
	 * @return
	 */
	public int getStatuHeight(Activity activity){
		Rect frame = new Rect();  
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);  
		int statusBarHeight = frame.top;
		return statusBarHeight;
	}
	/**
	 * 获取横屏还是竖屏
	 * @param activity
	 * @return true为竖屏，false为横屏
	 */
	public boolean isPortrait(Activity activity)
	{
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		double mWidth = dm.widthPixels;
		double mHeight = dm.heightPixels;
		if(mHeight>mWidth){
			return true;
		}else if(mHeight<mWidth){
			return false;
		}
		return true;
	}

	/**
	 * 判断是否是平板
	 * 
	 * @param context
	 * @return  true是手机，false 是平板
	 */
	public boolean isPhone(Context context) {
		TelephonyManager telManager = (TelephonyManager) context
				.getSystemService(context.TELEPHONY_SERVICE);
		// 平板
		/*if (telManager.getPhoneType() == TelephonyManager.PHONE_TYPE_NONE) {
			return false;
		}*/
		if (TextUtils.isEmpty(MHLocalUtil.getLocalImeiAddress(context))) {
			return false;
		}
		// 手机，利用分辨率防止一些可以打电话的平板
		DisplayMetrics dm = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenX = dm.widthPixels;
		int screenY = dm.heightPixels;
		int dpi = dm.densityDpi;
		float a = screenX / dpi;
		float b = screenY / dpi;
		double screenIn = Math.sqrt((a * a) + (b * b));
		if (screenIn > 5) {
			return false;
		}
		return true;
	}
}
