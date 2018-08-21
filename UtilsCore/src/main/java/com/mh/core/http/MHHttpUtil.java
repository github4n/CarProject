/*package com.mh.core.http;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.text.TextUtils;
import android.util.Log;

import com.mh.core.beans.MHGetParamBean;
import com.mh.core.tools.MHJSONUtil;
import com.mh.core.tools.MHLogUtil;
import com.mh.core.tools.MHStringUtil;

*//**
* <p>Title: MHHttpUtil</p>
* <p>Description: 网络连接请求工具类</p>
* <p>Company: EFun</p> 
* @author GanYuanrong
* @date 2014年12月9日
*//*
public class MHHttpUtil {

	private static HttpPost getHttpPost(String url) {
		return new HttpPost(url);
	}
	
	private static HttpResponse getHttpResponse(HttpPost request)
			throws ClientProtocolException, IOException {
		HttpClient httpClient = new DefaultHttpClient();
		httpClient.getParams().setParameter(
				CoreConnectionPNames.CONNECTION_TIMEOUT, 30000);
		httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
				30000);
		return httpClient.execute(request);

	}
	
	private static HttpResponse getHttpResponse(HttpPost request, int timeOut)
			throws ClientProtocolException, IOException {
		HttpClient httpClient = new DefaultHttpClient();
		httpClient.getParams().setParameter(
				CoreConnectionPNames.CONNECTION_TIMEOUT, timeOut);
		httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
				30000);
		return httpClient.execute(request);
		
	}
	
	public static String mhPostRequest(String url, Map<String, String> m) {
		return HttpRequest.post(url, m);
	}
	
	
	@Deprecated
	public static String mhPostRequest(String url, List<NameValuePair> params) {
		MHLogUtil.logUrl(url, params);
		String reqResult= null;
		HttpPost request = getHttpPost(url);
		try {
			HttpEntity entity = new UrlEncodedFormEntity(params, HTTP.UTF_8);
			request.setEntity(entity);
			HttpResponse response = getHttpResponse(request);
			if(null == response){
				return MHJSONUtil.mhTransformToJSONStr(reqResult);
			}
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				reqResult = EntityUtils.toString(response.getEntity(), "UTF-8");
				return MHJSONUtil.mhTransformToJSONStr(reqResult);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return MHJSONUtil.mhTransformToJSONStr(reqResult);
	}
	
	
	public static String mhExecutePostRequest(String url, Map<String, String> m) {
		return HttpRequest.post(url, m);
	}
	
	*//**
	 * <p>Description: 执行POST请求</p>
	 * @author GanYuanrong
	 * @param url 请求的地址
	 * @param params 请求的参数
	 * @return
	 * @date 2014年12月9日
	 *//*
	public static String mhExecutePostRequest(String url, List<NameValuePair> params) {
		MHLogUtil.logUrl(url, params);
		String reqResult= null;
		HttpPost request = getHttpPost(url);
		try {
			HttpEntity entity = new UrlEncodedFormEntity(params, HTTP.UTF_8);
			request.setEntity(entity);
			HttpResponse response = getHttpResponse(request);
			if(null == response){
				return null;
			}
			MHLogUtil.logD("mh", "tatusCode:" + response.getStatusLine().getStatusCode());
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				reqResult = EntityUtils.toString(response.getEntity(), "UTF-8");
				if(MHStringUtil.isEmpty(reqResult)){
					return null;
				}
				return reqResult;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return reqResult;
	}
	
	
	*//**
	 * <p>Description: get请求方式</p>
	 * @author GanYuanrong
	 * @param uri get请求url
	 * @return
	 * @date 2014年12月9日
	 *//*
	private static HttpGet getHttpGet(String uri){
		return new HttpGet(uri);
	}
	
	*//**
	 * 获取HttpResponse
	 * @return HttpRespone
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 *//*
	private static HttpResponse getHttpResponse(HttpGet httpGet){
		HttpClient client = new DefaultHttpClient();
		client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);
		client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 10000);
		try {
			return client.execute(httpGet);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	*//**
	 * get请求
	 * @param path GET请求的地址
	 * @param params GetParamBean集合
	 * @return 返回请求内容
	 *//*
	public static String mhGetRequest(String path){
		String reqResult = null;
		HttpGet request = getHttpGet(path);
		try {
			HttpResponse response = getHttpResponse(request);
			if(response==null){
				return MHJSONUtil.mhTransformToJSONStr(reqResult);
			}
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				reqResult = EntityUtils.toString(response.getEntity(), "UTF-8");
				return MHJSONUtil.mhTransformToJSONStr(reqResult);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return MHJSONUtil.mhTransformToJSONStr(reqResult);
	}
	*//**
	 * get请求
	 * @param path GET请求的地址
	 * @param params GetParamBean集合
	 * @throws UnsupportedEncodingException
	 * @return 返回请求内容
	 *//*
	public static String requireByGet(String path,ArrayList<MHGetParamBean> params){
		String reqResult = null;
		HttpGet request = null;
		if(params==null){
			request = getHttpGet(path);
		}else{
			String uri_path = "?";
			for (MHGetParamBean bean:params) {
				uri_path+=bean.getParamCombine()+"&";
			}
			//去掉最后一个  "&"
			uri_path = uri_path.substring(0, uri_path.length()-1);
			request = getHttpGet((path+uri_path));
		}
		try {
			HttpResponse response = getHttpResponse(request);
			if(response==null){
				return MHJSONUtil.mhTransformToJSONStr(reqResult);
			}
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				reqResult = EntityUtils.toString(response.getEntity(), "UTF-8");
				return MHJSONUtil.mhTransformToJSONStr(reqResult);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return MHJSONUtil.mhTransformToJSONStr(reqResult);
	}
	
	public static String excuteDoubleUrlPostRequest(String first_url, String second_url, List<NameValuePair> requestParams){
		String mhResponse = "";
		if (MHStringUtil.isNotEmpty(first_url)) {
			MHLogUtil.logD("first_url:" + first_url);
			mhResponse = MHHttpUtil.mhExecutePostRequest(first_url, requestParams);
			MHLogUtil.logD("first_url response: " + mhResponse);
		}
		if (MHStringUtil.isEmpty(mhResponse) && MHStringUtil.isNotEmpty(second_url)) {
			MHLogUtil.logD("second_url Url: " + second_url);
			mhResponse = MHHttpUtil.mhExecutePostRequest(second_url, requestParams);
			MHLogUtil.logD("second_url response: " + mhResponse);
		}
		return mhResponse;
	}
	
	public static String excuteDoubleUrlPostRequest(String first_url, String second_url, String urlMethod, List<NameValuePair> requestParams){
		first_url = MHStringUtil.appenUrl(first_url,urlMethod);
		second_url = MHStringUtil.appenUrl(second_url,urlMethod);
		return excuteDoubleUrlPostRequest(first_url, second_url, requestParams);
	}
	
	*//**
	 * <p>Description: 双域名POST请求</p>
	 * @param first_url 域名1
	 * @param second_url 域名2
	 * @param urlMethod 接口方法名
	 * @param map post请求参数
	 * @return
	 * @date 2014年12月5日
	 *//*
	public static String excuteDoubleUrlPostRequest(String first_url, String second_url, String urlMethod, Map<String, String> map) {
		
		List<NameValuePair> valuePairs = new ArrayList<NameValuePair>();

		if (null != map && !map.isEmpty()) {
			for (Entry<String, String> entity : map.entrySet()) {
				if (!TextUtils.isEmpty(entity.getKey())) {
					valuePairs.add(new BasicNameValuePair(entity.getKey(), entity.getValue()));
				}
			}
		}
		return excuteDoubleUrlPostRequest(first_url, second_url, urlMethod, valuePairs);
	}

}
*/