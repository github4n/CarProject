package com.mh.core.js;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.webkit.JavascriptInterface;

import com.mh.core.component.MHWebView;
import com.mh.core.tools.MHLogUtil;

import org.json.JSONObject;

public class PlatNative2JS extends Native2JS {

	Handler handler = new Handler();
	public PlatNative2JS(Context context) {
		super(context);
	}

	public PlatNative2JS(Context context,MHWebView mhWebView) {
		super(context, mhWebView);
		
	}

	@JavascriptInterface
	public String getSdkInfo(String jsJson) {//callback
		MHLogUtil.logD("mh", "keyJson:" + jsJson);
		if (TextUtils.isEmpty(jsJson)) {
			return "";
		}
		try {
			JSONObject jsonObject = new JSONObject(jsJson);
			String keyValue = jsonObject.optString("key", "");
			String func = jsonObject.optString("callback", "");
			if (TextUtils.isEmpty(keyValue)) {
				return "";
			}

			String jsonResult = getInfoByKey(keyValue);
			//如果子类方法没能获得json串，走旧有的路径获取info信息
			if(jsonResult == null || jsonResult.trim().length() == 0){
				if(map != null && map.containsKey(keyValue)){
					jsonResult = map.get(keyValue);
				}
			}
			
			if (jsonResult != null && jsonResult.trim().length() != 0 && !TextUtils.isEmpty(func)) {
				final String js = func + "(" + jsonResult + ")";
				handler.post(new Runnable() {
					
					@Override
					public void run() {
						mhWebView.executeJavascript(js);
					}
				});
				return jsonResult;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 供子类继承的方法，用于实现JS约定的json串获取
	 * @param key 关键字
	 * @return json
	 */
	public String getInfoByKey(String key){
		return "";
	}

}
