package com.mh.core.http;

import android.text.TextUtils;

import com.mh.core.tools.MHFileUtil;
import com.mh.core.tools.MHLogUtil;
import com.mh.core.tools.MHStringUtil;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class HttpRequestCore{
	
	private static final String TAG = "HttpRequestCore";
	private String requestUrl;
	private int connectTimeout = 30 * 1000;
	private String requestMethod = HttpReuqestMethod.POST;
	private String contentType = "application/x-www-form-urlencoded";
	private String sendData;
	private HttpResponse httpResponse = new HttpResponse();

	private byte[] postByteData;

	/**
	 * @param contentType the contentType to set
	 */
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
	/**
	 * @return the httpResponse
	 */
	public HttpResponse getHttpResponse() {
		return httpResponse;
	}

	/**
	 * @param requestUrl the requestUrl to set
	 */
	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}

	/**
	 * @param connectTimeout the connectTimeout to set
	 */
	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	/**
	 * @param requestMethod the requestMethod to set
	 */
	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}

	/**
	 * @param sendData the sendData to set
	 */
	public void setSendData(String sendData) {
		this.sendData = sendData;
	}

	
/*	public String get() {
		return doReuqestByGet().getResult();
	}
	
	public String post() {
		return doReuqestByPost().getResult();
	}*/
	
	public HttpResponse excuteGetRequest(String urlStr,Map<String, String> dataMap) {
		if (TextUtils.isEmpty(urlStr)) {
			return null;
		}

		if (dataMap == null || dataMap.isEmpty()) {
			return excuteGetRequest(urlStr);
		}
		String getData = MHStringUtil.map2strData(dataMap);
		if (TextUtils.isEmpty(getData)) {
			return excuteGetRequest(urlStr);
		}
		String getUrl = "";
		if (urlStr.endsWith("?")) {
			getUrl = urlStr + getData;
		}else{
			getUrl = urlStr + "?" + getData;
		}
		MHLogUtil.logD("http request:" + getUrl);
		return excuteGetRequest(getUrl);
		
	}
	
	public HttpResponse excuteGetRequest(String urlStr) {
		this.requestUrl = urlStr;
		doGet();
		httpResponse.setRequestCompleteUrl(urlStr);
		return httpResponse;
	}
	
	public HttpResponse excutePostRequest(String urlStr,Map<String, String> dataMap) {
		
		String postData = MHStringUtil.map2strData(dataMap);
		return excutePostRequest(urlStr, postData);
	
	}
	
	public HttpResponse excutePostRequest(String urlStr, String postData) {
		if (TextUtils.isEmpty(urlStr)) {
			return httpResponse;
		}
		this.requestUrl = urlStr;
		this.sendData = postData;
		MHLogUtil.logD("http request:" + urlStr + "?" + postData);
		doPost();
		httpResponse.setRequestCompleteUrl(urlStr + "?" + postData);
		return httpResponse;
	}
	
	
	public String postByteData(String urlStr,byte[] postByteData) {
		if (TextUtils.isEmpty(urlStr)) {
			return "";
		}
		this.requestUrl = urlStr;
		this.postByteData = postByteData;
		return doPost().getResult();
	}
	
	
	public HttpResponse doPost(){
		this.requestMethod =  HttpReuqestMethod.POST;
		return doReuqest();
	}
	
	public HttpResponse doGet(){
		this.requestMethod =  HttpReuqestMethod.GET;
		return doReuqest();
	}
	
	public HttpResponse doReuqest(){

		if (TextUtils.isEmpty(requestUrl)) {
			return httpResponse;
		}
//		checkHttpsUrl(requestUrl);
		HttpURLConnection urlConnection = null;
		String result = "";
		try {
			URL url = new URL(requestUrl);
			urlConnection = (HttpURLConnection) url.openConnection();

			if (!TextUtils.isEmpty(sendData) && HttpReuqestMethod.GET != requestMethod) {
				urlConnection.setDoOutput(true);
			}
			//urlConnection.setChunkedStreamingMode(0);
			urlConnection.setUseCaches(false);
			urlConnection.setConnectTimeout(connectTimeout);
			urlConnection.setRequestMethod(requestMethod);
			urlConnection.setRequestProperty("Charset", "UTF-8");
			urlConnection.setRequestProperty("Connection", "Keep-Alive");
			urlConnection.setRequestProperty("accept", "*/*");
			urlConnection.setRequestProperty("Content-Type",contentType);
			urlConnection.connect();
			
			if (!TextUtils.isEmpty(sendData) && HttpReuqestMethod.GET != requestMethod) {//post请求需要写文件流
				writeStream(urlConnection.getOutputStream(), sendData);
			}else if(postByteData != null && HttpReuqestMethod.GET != requestMethod){
				writeStream(urlConnection.getOutputStream(), postByteData);
			}
			
			int code = urlConnection.getResponseCode();
			httpResponse.setHttpResponseCode(code);
			String message = urlConnection.getResponseMessage();
			MHLogUtil.logD("request code:" + code + ",code message:" + message);
			if (code == HttpURLConnection.HTTP_OK) {
				result = readStream(urlConnection.getInputStream());
			}
			//MHLogUtil.logD(result);
			if (httpResponse != null) {
				httpResponse.setResult(result);
				httpResponse.setServerTime(urlConnection.getHeaderField("Date"));
				httpResponse.setHeads(urlConnection.getHeaderFields());
				MHLogUtil.logD(TAG, "request url:" + requestUrl + " --> result:" + httpResponse.getResult());
			}
			
		}catch (IOException e) {
			MHLogUtil.logD(TAG, "e:" + e.getMessage());
			e.printStackTrace();
		} finally {
			if (urlConnection != null) {
				urlConnection.disconnect();
				MHLogUtil.logD(TAG, "urlConnection.disconnect()");
			}
			urlConnection= null;
		}
		return httpResponse;
	}
	

	/**
	 * <p>Description: </p>
	 * @param outputStream
	 * @param data  格式必须为（key=value&key1=value1）
	 * @throws IOException 
	 * @throws UnsupportedEncodingException 
	 * @date 2015年10月9日
	 */
	private void writeStream(OutputStream outputStream, String data) throws UnsupportedEncodingException, IOException {
		if (outputStream == null || TextUtils.isEmpty(data)) {
			return;
		}
		writeStream(outputStream, data.getBytes("UTF-8"));
	}

	private void writeStream(OutputStream outputStream, byte[] byteData) throws UnsupportedEncodingException, IOException {
		if (outputStream == null || byteData == null) {
			return;
		}
		OutputStream os = new BufferedOutputStream(outputStream);
		os.write(byteData);
		os.flush();
		os.close();
	}
	
	private String readStream(InputStream inputStream) throws IOException {
		InputStreamReader isr = new InputStreamReader(inputStream, "UTF-8");
		StringBuilder data = new StringBuilder();
		int offset = 0;
		char[] buffer = new char[2048];
		while ((offset = isr.read(buffer, 0, buffer.length)) != -1) {
			data.append(new String(buffer, 0, offset));
			offset = 0;
		}
		isr.close();
		
		return data.toString();
	}

	public byte[] getPostByteData() {
		return postByteData;
	}

	public void setPostByteData(byte[] postByteData) {
		this.postByteData = postByteData;
	}

	
	public boolean downLoadUrlFile(String downLoadFileUrl,String savePath) {
		HttpURLConnection conn = null;
		FileOutputStream fileOutputStream = null;
		InputStream is = null;
		try {
			MHLogUtil.logD("start down load...");
//			checkHttpsUrl(downLoadFileUrl);
			MHLogUtil.logD("http request:" + downLoadFileUrl);
			URL url = new URL(downLoadFileUrl);
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5 * 1000);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
			conn.setRequestProperty("Accept-Language", "zh-CN");
			conn.setRequestProperty("Referer", downLoadFileUrl); 
			conn.setRequestProperty("Charset", "UTF-8");
			conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.connect();
			int code = conn.getResponseCode();
			MHLogUtil.logD("mh", "域名文件 http response code:" + code);
			if (200 == code) {
				File saveFile = new File(savePath);
				MHFileUtil.deleteFile(saveFile);
				if (!saveFile.exists()) {
					if(saveFile.getParentFile()!= null){
						saveFile.getParentFile().mkdirs();
					}
					saveFile.createNewFile();
				}
				
				fileOutputStream = new FileOutputStream(saveFile);
				is = conn.getInputStream();
				int offset = 0;
				byte[] buffer = new byte[2048];
				while ((offset = is.read(buffer, 0, buffer.length)) != -1) {
					fileOutputStream.write(buffer,0,offset);
					offset = 0;
				}
				fileOutputStream.flush();
				return true;
			}
		} catch (IOException e) {
			MHLogUtil.logD("mh", "exception:" + e.getMessage());
			e.printStackTrace();
		} finally {
			if (null != conn) {
				conn.disconnect();
			}
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (fileOutputStream != null) {
				try {
					fileOutputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
		return false;
	}
	
//	public static void checkHttpsUrl(String url) {
//		if (!TextUtils.isEmpty(url) && !url.startsWith("https")) {
//			MHLogUtil.logE(TAG, "sdk warming: " + url + " is not https url, please check that is it correct ??");
//		}
//	}
}
