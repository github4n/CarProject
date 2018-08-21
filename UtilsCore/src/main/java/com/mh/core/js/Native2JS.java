package com.mh.core.js;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.webkit.JavascriptInterface;

import com.mh.core.component.MHWebView;
import com.mh.core.db.MHDatabase;
import com.mh.core.res.MHResConfiguration;
import com.mh.core.tools.MHLocalUtil;
import com.mh.core.tools.MHLogUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.Map;

public class Native2JS {

	WeakReference<Context> mContext;
	String commonString = "";
	Map<String, String> map;
	MHWebView mhWebView;
	
	
	public Native2JS(Context context) {
		this(context, null);
	}
	
	public Native2JS(Context context, MHWebView mhWebView) {
		this.mContext = new WeakReference<>(context);
		this.mhWebView = mhWebView;
	}

	/**
	 * @return the commonString
	 */
	@JavascriptInterface
	public String getCommonString() {
		return commonString;
	}
	
	@JavascriptInterface
	public String getCommonString(String key) {
		if (!TextUtils.isEmpty(key) && map != null && map.containsKey(key)) {
			return map.get(key);
		}
		return "";
	}
	
	
	/**
	 * @return the map
	 */
	public Map<String, String> getMap() {
		return map;
	}

	/**
	 * @param map the map to set
	 */
	public void setMap(Map<String, String> map) {
		this.map = map;
	}

	/**
	 * @param commonString the commonString to set
	 */
	public void setCommonString(String commonString) {
		this.commonString = commonString;
	}

	
	@JavascriptInterface
	public String getPackageName(){
		if(getContext() != null) {
			return getContext().getPackageName();
		} else{
			return null;
		}
	}
	
	@JavascriptInterface
	public String getVersionCode(){
		if(getContext() != null) {
			return MHLocalUtil.getVersionCode(getContext());
		} else{
			return null;
		}
	}
	
	@JavascriptInterface
	public String getDeviceType(){
		return MHLocalUtil.getDeviceType();
	}
	@JavascriptInterface
	public String getAndroidId(){
		if(getContext() != null) {
			return MHLocalUtil.getLocalAndroidId(getContext());
		}else{
			return null;
		}
	}
	
	@JavascriptInterface
	public String getImei(){
		if(getContext() != null) {
			return MHLocalUtil.getLocalImeiAddress(getContext());
		} else {
			return null;
		}
	}
	
	@JavascriptInterface
	public String getIp(){
		if(getContext() != null) {
			return MHLocalUtil.getLocalIpAddress(getContext());
		} else{
			return null;
		}
	}
	
	@JavascriptInterface
	public String getMac(){
		if(getContext() != null) {
			return MHLocalUtil.getLocalMacAddress(getContext());
		}else{
			return null;
		}
	}
	
	@JavascriptInterface
	public String getOsVersion(){
		return MHLocalUtil.getOsVersion();
	}
	
	@JavascriptInterface
	public String getVersionName(){
		if(getContext() != null) {
			return MHLocalUtil.getVersionName(getContext());
		} else{
			return null;
		}
	}
	
	@JavascriptInterface
	public String getUserName(){
		if(getContext() != null) {
			return MHDatabase.getSimpleString(getContext(), MHDatabase.MH_FILE, MHDatabase.MH_LOGIN_USERNAME);
		}else{
			return null;
		}
	}
	@JavascriptInterface
	public String getPassword(){
		if(getContext() != null) {
			return MHDatabase.getSimpleString(getContext(), MHDatabase.MH_FILE, MHDatabase.MH_LOGIN_PASSWORD);
		}else{
			return null;
		}
	}
	
	@JavascriptInterface
	public String getUserId(){
		if(getContext() != null) {
			return MHResConfiguration.getCurrentMHUserId(getContext());
		} else{
			return null;
		}
	}
	
	@JavascriptInterface
	public String getTimesStamp(){
		if(getContext() != null) {
			return MHResConfiguration.getSDKLoginTimestamp(getContext());
		} else{
			return null;
		}
	}
	
	@JavascriptInterface
	public String getSign(){
		if(getContext() != null) {
			return MHResConfiguration.getSDKLoginSign(getContext());
		}else{
			return null;
		}
	}
	
	@JavascriptInterface
	public String getRoleId(){
		return "";
	}
	
	@JavascriptInterface
	public String getRoleName(){
		return "";
	}
	
	@JavascriptInterface
	public String getServerCode(){
		return "";
	}
	
	@JavascriptInterface
	public String getLanguage(){
		if(getContext() != null) {
			return MHResConfiguration.getSDKLanguage(getContext());
		}else{
			return null;
		}
	}
	
	@JavascriptInterface
	public String getLoginType(){
		return "";
	}
	
	@JavascriptInterface
	public String getGameCode(){
		if(getContext() != null) {
			return MHResConfiguration.getGameCode(getContext());
		} else{
			return null;
		}
	}
	
	@JavascriptInterface
	public void finishActivity(){
		Context context = getContext();
		if (context != null && context instanceof Activity) {
			MHLogUtil.logD("mh", "activity finish");
			((Activity) context).finish();
		}
	}
	
	@JavascriptInterface
	public void close(){
		finishActivity();
	}
	
//	gameInfo：{"userid":"1","serverCode":"1","roleLevel":"1","gameCode":"sehzw","roleId":"MH_1"}
//	gameLoginInfo：{"userid":"1","sign":"CB7AEE4499008034774E75586C6F24BD","timestamp":"1428048053685","partner":"mh","loginType":"mh"}
//	deviceInfo：{"systemVersion":"4.3.1","mac":"d4:6e:5c:41:d1:2f","deviceType":"HUAWEI G700-T00","imei":"860457020317674","ip":"172.16.80.159"}

	@JavascriptInterface
	public String getDeviceInfo(){
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("systemVersion", MHLocalUtil.getOsVersion());
			jsonObject.put("mac", MHLocalUtil.getLocalMacAddress(getContext()));
			jsonObject.put("deviceType", MHLocalUtil.getDeviceType());
			jsonObject.put("imei", MHLocalUtil.getLocalImeiAddress(getContext()));
			jsonObject.put("ip", MHLocalUtil.getLocalIpAddress(getContext()));
			jsonObject.put("androidid", MHLocalUtil.getLocalAndroidId(getContext()));
			return jsonObject.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	@JavascriptInterface
	public void setSDKMsg(String msg){
		final String m = msg;
		if (mhWebView != null) {
			mhWebView.getHandler().post(new Runnable() {
				
				@Override
				public void run() {
					mhWebView.jsCallBack(m);
				}
			});
			
		}
	}

	/**
	 * 获取缓存的上下文
	 * @return 上下文
	 */
	public Context getContext(){
		return this.mContext == null ? null : this.mContext.get();
	}
	
}
