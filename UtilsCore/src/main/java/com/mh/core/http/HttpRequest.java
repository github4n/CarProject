package com.mh.core.http;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import org.json.JSONObject;

import com.mh.core.res.MHResConfiguration;
import com.mh.core.tools.MHLocalUtil;
import com.mh.core.tools.MHStringUtil;

import android.content.Context;
import android.text.TextUtils;

public class HttpRequest {
	
	public HttpRequest() {
		// TODO Auto-generated constructor stub
	}
	

	public static String get(String urlStr,Map<String, String> dataMap) {
		return getWithAccessToken(urlStr, dataMap, true);
	}
	
	private static String getWithAccessToken(String urlStr,Map<String, String> dataMap,boolean accessToken) {
		if (TextUtils.isEmpty(urlStr)) {
			return "";
		}
		HttpRequestCore requestCore = new HttpRequestCore();
		if (dataMap != null) {
			dataMap = putCommonParams(dataMap);
		}
		HttpResponse hr = requestCore.excuteGetRequest(urlStr, dataMap);
		return hr.getResult();
	}
	
	public static String get(String urlStr) {
		return get(urlStr, null);
	}
	
	public static String get(String urlStr,boolean accessToken) {
		return getWithAccessToken(urlStr, null, accessToken);
	}
	
	public static String getIn2Url(String preUrl,String sprUrl) {
		return getIn2Url(preUrl, sprUrl,true);
	}
	
	public static String getIn2Url(String preUrl,String sprUrl,boolean accessToken) {
		String preResult = get(preUrl,accessToken);
		if (TextUtils.isEmpty(preResult)) {
			return get(sprUrl,accessToken);
		}
		return preResult;
	}
	
	public static String getVerticallineAccessToken(Context context, String urlStr) {
		if (!TextUtils.isEmpty(urlStr) && urlStr.contains("|") && !TextUtils.isEmpty(MHResConfiguration.getSDKLoginReturnData(context))) {
			urlStr = urlStr + "|" + MHResConfiguration.getSDKLoginReturnData(context);
			HttpRequestCore requestCore = new HttpRequestCore();
			return requestCore.excuteGetRequest(urlStr).getResult();
		}
		return "";
	}
	
	public HttpResponse getReuqest(String urlStr) {
		HttpRequestCore requestCore = new HttpRequestCore();
		return requestCore.excuteGetRequest(urlStr);
	}
	
	public HttpResponse getReuqestIn2Url(String preUrl,String sprUrl) {
		HttpResponse s = getReuqest(preUrl);
		if (s != null && TextUtils.isEmpty(s.getResult())) {
			return getReuqest(preUrl);
		}
		return s;
	}
	
	
	/**
	 * <p>Description: 发送post请求</p>
	 * @param urlStr 请求地址
	 * @return  请求的结果
	 * @date 2015年10月9日
	 */
	public static String post(String urlStr,Map<String, String> dataMap) {
//		dataMap = putCommonParams(dataMap);
		HttpRequestCore requestCore = new HttpRequestCore();
		HttpResponse hr = requestCore.excutePostRequest(urlStr, dataMap);
		if (hr != null) {
			return hr.getResult();
		}else{
			return "";
		}
	}
	
	public  HttpResponse postReuqest(String urlStr,Map<String, String> dataMap) {
		dataMap = putCommonParams(dataMap);
		HttpRequestCore requestCore = new HttpRequestCore();
		return requestCore.excutePostRequest(urlStr, dataMap);
	}
	
	
	public static String post(String urlStr,byte[] dataByte) {
		HttpRequestCore requestCore = new HttpRequestCore();
		return requestCore.postByteData(urlStr, dataByte);
	}
	
	
	public static String postIn2Url(String preUrl,String sprUrl,Map<String, String> dataMap) {
		String result = post(preUrl, dataMap);
		if (TextUtils.isEmpty(result)) {
			result = post(sprUrl, dataMap);
		}
		return result;
	}
	
	public static String postIn2Url(String preUrl,String sprUrl,String mInterfaceName,Map<String, String> dataMap) {
		preUrl = MHStringUtil.checkUrl(preUrl) + mInterfaceName;
		String result = post(preUrl, dataMap);
		
		if (TextUtils.isEmpty(result)) {//备用域名请求
			sprUrl = MHStringUtil.checkUrl(sprUrl) + mInterfaceName;
			result = post(sprUrl, dataMap);
		}
		return result;
	}
	
	
	public static String postJsonObject(String urlStr,JSONObject jsonObject) {
		HttpRequestCore requestCore = new HttpRequestCore();
		requestCore.setSendData(jsonObject.toString());
		requestCore.setRequestUrl(urlStr);
		requestCore.setContentType("application/json");
		return requestCore.doPost().getResult();
	}

	public static String postJsonString(String urlStr,String json) {
		HttpRequestCore requestCore = new HttpRequestCore();
		requestCore.setSendData(json);
		requestCore.setRequestUrl(urlStr);
		requestCore.setContentType("application/json;charset=utf-8");
		return requestCore.doPost().getResult();
	}
	
	public static boolean downLoadUrlFile(String downLoadFileUrl,String savePath) {
		HttpRequestCore requestCore = new HttpRequestCore();
		return requestCore.downLoadUrlFile(downLoadFileUrl, savePath);
	}
	
	
	private static Map<String, String> putCommonParams(Map<String, String> dataMap){
		if (dataMap == null) { 
			return null;
		}
		
		/*if (!dataMap.containsKey("sign")) {
			dataMap.put("sign", MHResConfiguration.getLoginSign());
		}
		if (!dataMap.containsKey("loginTimestamp")) {
			dataMap.put("loginTimestamp", MHResConfiguration.getLoginTimestamp());
		}*/
		
		if (!dataMap.containsKey("os_language")) {
			dataMap.put("os_language", MHLocalUtil.getOsLanguage());
		}
		
		if (!dataMap.containsKey("eid")) {
			dataMap.put("eid", MHLocalUtil.mh_uuid);
		}
		
		if (!TextUtils.isEmpty(MHResConfiguration.getSDKLoginReturnData())) {
			dataMap.put("accessToken", MHResConfiguration.getSDKLoginReturnData());
		}
		return dataMap;
	}

	/**
	 * 上传文件
	 * @param params
	 *            传递的普通参数
	 * @param uploadFile
	 *            需要上传的文件名
	 * @param newFileName
	 *            上传的文件名称，不填写将为uploadFile的名称
	 * @param urlStr
	 *            上传的服务器的路径
	 */
	public static String uploadFile(Map<String, String> params, File uploadFile, String newFileName, String urlStr){
		return HttpFileUploadRequest.uploadFile(params, uploadFile, newFileName, urlStr);
	}
}
