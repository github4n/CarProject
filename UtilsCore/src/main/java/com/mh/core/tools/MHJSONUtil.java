package com.mh.core.tools;

import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;

/**
 * Class Description：JSON Return
 * @author Joe
 * @date 2013-4-16
 * @version 1.0
 */
public class MHJSONUtil {
	
	/**
	 * 自定义返回字段
	 */
	private static final String MH_CODE_KEY = "mhResult";
	
	/**
	 * 请求成功
	 */
	public static final String SUCCESS = "mh_success";
	
	/**
	 * 请求失败
	 */
	public static final String FAILURE = "mh_failure";
	

	@Deprecated
	public static String mhTransformToJSONStr(String result){
		if(result==null || "".equals(result)){
			return "{\""+MH_CODE_KEY+"\":"+"\""+FAILURE+"\""+"}";
		}
		result = result.trim();
		result = result.substring(1, result.length());
		return "{\""+MH_CODE_KEY+"\":"+"\""+SUCCESS+"\""+","+result;
	}
	
	/**
	 * 判断请求是否成功
	 * @param obj JSON格式数据
	 * @return 如果请求成功返回 true,否则为 false
	 */
	@Deprecated
	public static boolean mhVerificationRequest(JSONObject obj){
		if(obj.optString(MH_CODE_KEY).equals(SUCCESS)){
			return true;
		}
		return false;
	}
	
	public static String map2jsonString(Map<String, String> map) throws JSONException{
		if (map == null || map.isEmpty()) {
			return "";
		}
		JSONObject jsonObject = new JSONObject();
		for (Map.Entry<String, String> entry : map.entrySet()) {
			if (!TextUtils.isEmpty(entry.getKey())) {
				jsonObject.put(entry.getKey(), entry.getValue());
			}
		}
		
		return jsonObject.toString();
	}
}
