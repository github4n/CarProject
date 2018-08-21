package com.mh.core.tools;

import android.content.Context;
import android.os.Looper;
import android.widget.Toast;

public class ThreadUtil {

	public static boolean isUiThread() {
		return Looper.myLooper() == Looper.getMainLooper();
	}

	public static void checkUiThread(Context context) {
		if (!isUiThread()) {
			MHLogUtil.logE("mh", "Calling this must from your main thread");
			Toast.makeText(context, "Calling this must from your main thread", Toast.LENGTH_SHORT).show();
			throw new IllegalStateException("Calling this must from your main thread");
		}
	}
	public static void checkUiThread() {
		if (!isUiThread()) {
			MHLogUtil.logE("mh", "Calling this must from your main thread");
			throw new IllegalStateException("Calling this must from your main thread");
		}
	}

}
