package com.mh.core.tools;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.util.Map;
import java.util.Properties;

public class MHLogUtil {
	private static final String TAG = "mhLog";

	private static final int INIT_LOG = 1;
	private static final int CAN_LOG = 2;
	private static final int CAN_NOT_LOG = 3;
	
	public static int logFlag = INIT_LOG;

	private static boolean canToast = false;
	private static boolean isInit = false;

	/**
	 * 默认不开启日志打印
	 */
	private static int mLogLevel = Log.ASSERT;

	/**
	 * 文件配置的日志打印等级
	 */
	private static int mFileLogLevel = Log.ASSERT;

	/**
	 * 打开Debug以上等级的调试
	 * @param debug 调试等级
     */
	public static void enableDebug(boolean debug){
		mLogLevel = Log.ASSERT;
	}

	/**
	 * 打开info以上等级的调试
	 * @param mInfo
     */
	public static void enableInfo(boolean mInfo){
		mLogLevel = Log.ASSERT;
	}

	/**
	 * 是否调试模式，如果是，则开放error以上级别的日志输出
	 * @param isDebug 是否调试模式
     */
	public static void mhDebugMode(boolean isDebug){
		if(isDebug){
			mLogLevel = Log.ERROR;
		} else{
			mLogLevel = Log.ASSERT;
		}
	}

	/**
	 * 定义日志输出等级
	 * @param logLevel 日志输出等级，取值范围参考{@link android.util.Log}
     */
	public static void enableLogLevel(int logLevel){
		if(logLevel >= Log.VERBOSE && logLevel <= Log.ASSERT){
			mLogLevel = logLevel;
		}
	}

	public static void logV(String msg) {
		logV(TAG, msg);
	}

	public static void logV(String tag, String msg) {
		if(msg == null){
			return;
		}
		if (mLogLevel <= Log.VERBOSE) {
			Log.v(tag, msg);
		}else {
			mhDebug(Log.VERBOSE, tag, msg, null);
		}
	}

	public static void logD(String msg) {
        logD(TAG, msg);
    }
	
	public static  void logD(String tag, String msg) {
		if(msg == null){
			return;
		}
        if (mLogLevel <= Log.DEBUG) {
			Log.d(tag, msg);
		} else {
			mhDebug(Log.DEBUG, tag, msg, null);
		}
    }
	
	public static void logI(String msg) {
		logI(TAG, msg);
	}
	
	public static void logI(String tag, String msg) {
		if(msg == null){
			return;
		}
		if (mLogLevel <= Log.INFO){
			Log.i(tag, msg);
		} else {
			mhDebug(Log.INFO, tag, msg, null);
		}
	}

	public static void logW(String msg) {
		logW(TAG, msg);
	}

	public static void logW(String tag, String msg) {
		if(msg == null){
			return;
		}
		if (mLogLevel <= Log.WARN){
			Log.w(tag, msg);
		} else {
			mhDebug(Log.WARN, tag, msg, null);
		}
	}
	
	public static void logE(String msg) {
		logE(TAG, msg, null);
	}

	public static void logE(String tag, String msg) {
		logE(tag, msg, null);
	}

	public static void logE(String msg, Throwable tr) {
		logE(TAG, msg, tr);
	}

	public static void logE(String tag, String msg, Throwable tr) {
		if(msg == null){
			return;
		}
		if (mLogLevel <= Log.ERROR){
			Log.e(tag, msg, tr);
		} else {
			mhDebug(Log.ERROR, tag, msg, tr);
		}
	}

	
	public static void logUrl(final String url, final Map<String, String> params){
		if (params == null || params.isEmpty()) {
			return;
		}
		
		String urlString = url + "?" + MHStringUtil.map2strData(params);
		
		if (mLogLevel >= Log.DEBUG) {
			MHLogUtil.logD("http url:" + urlString);
		}else{
			mhDebug(Log.INFO, "", "http url:" + urlString, null);
		}
	}
	
	private static void mhDebug(int logLevel, String tag, String msg, Throwable tr) {
		if (logFlag == INIT_LOG) {
			initLog();
		}
		if (logFlag == CAN_LOG && mFileLogLevel <= logLevel) {
			switch (logLevel){
				case Log.VERBOSE:
					if(null != tr){
						Log.v(tag, msg, tr);
					} else {
						Log.v(tag, msg);
					}
					break;
				case Log.DEBUG:
					if(null != tr){
						Log.d(tag, msg, tr);
					} else {
						Log.d(tag, msg);
					}
					break;
				case Log.INFO:
					if(null != tr){
						Log.i(tag, msg, tr);
					} else {
						Log.i(tag, msg);
					}
					break;
				case Log.WARN:
					if(null != tr){
						Log.w(tag, msg, tr);
					} else {
						Log.w(tag, msg);
					}
					break;
				case Log.ERROR:
					if(null != tr){
						Log.e(tag, msg, tr);
					} else {
						Log.e(tag, msg);
					}
					break;
			}
		}
	}

	public static void mhToast(Context context,String message){
		//Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
		if (context == null || message == null ) {
			return;
		}
		if (!isInit) {
			initLog();
		}
		if (canToast) {
			Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
		}
	}
	
	private static void initLog() {
		try {
			logFlag = CAN_NOT_LOG;
			String logPath = MHTestConfig.getMHTestSettingFilePath();
			if (null != logPath) {
				Properties prop = MHFileUtil.readProterties(logPath);
				if (null != prop) {
					if(MHTestConfig.CONFIG_TRUE.equals(prop.getProperty("error", ""))){
						mFileLogLevel = Log.ERROR;
						logFlag = CAN_LOG;
					}
					if (MHTestConfig.CONFIG_TRUE.equals(prop.getProperty("warn", ""))){
						mFileLogLevel = Log.WARN;
						logFlag = CAN_LOG;
					}
					if (MHTestConfig.CONFIG_TRUE.equals(prop.getProperty("info", ""))) {
						mFileLogLevel = Log.INFO;
						logFlag = CAN_LOG;
					}
					if (MHTestConfig.CONFIG_TRUE.equals(prop.getProperty("debug", ""))) {
						mFileLogLevel = Log.DEBUG;
						logFlag = CAN_LOG;
					}
					if(prop.getProperty("toast", "").equals("true")){
						canToast = true;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logFlag = CAN_NOT_LOG;
		}
		
		isInit = true;
	}
	
}
