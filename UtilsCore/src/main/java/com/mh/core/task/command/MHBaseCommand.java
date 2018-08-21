package com.mh.core.task.command;

import java.io.Serializable;
import java.util.Map;

import com.mh.core.http.HttpRequest;
import com.mh.core.http.HttpResponse;
import com.mh.core.task.MHCommandCallBack;

import android.text.TextUtils;

/**
 * Class Description：
 * @author Joe
 * @date 2013-4-16
 * @version 1.0
 */
public class MHBaseCommand implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String commandId;
	private String commandMsg;
	private MHCommandCallBack callback;
	private boolean showProgress = true;
	
	public MHBaseCommand(){
		this.commandId = null;		
		this.callback = null;
		this.commandMsg = null;
	}

	public String getCommandId() {
		return commandId;
	}

	public void setCommandId(String commandId) {
		this.commandId = commandId;
	}

	public String getCommandMsg() {
		return commandMsg;
	}

	public void setCommandMsg(String commandMsg) {
		this.commandMsg = commandMsg;
	}

	public MHCommandCallBack getCallback() {
		return callback;
	}
	public void setCallback(MHCommandCallBack callback) {
		this.callback = callback;
	}

	public boolean isShowProgress() {
		return showProgress;
	}

	public void setShowProgress(boolean showProgress) {
		this.showProgress = showProgress;
	}
	
	
	String requestCompleteUrl;
	
	public String getRequestCompleteUrl() {
		return requestCompleteUrl;
	}
	
	
	
	/**
	 * @param requestCompleteUrl the requestCompleteUrl to set
	 */
	public void setRequestCompleteUrl(String requestCompleteUrl) {
		this.requestCompleteUrl = requestCompleteUrl;
	}


	HttpResponse httpResponse;
	
	/**
	 * @return the httpResponse
	 */
	public HttpResponse getHttpResponse() {
		return httpResponse;
	}

	/**
	 * <p>Description: </p>
	 * @param requestParams 请求参数	
	 * @param requestUrls  请求连接
	 * @return
	 * @date 2016年8月15日
	 */
	protected HttpResponse doRequest_Post(Map<String, String> requestParams,String... requestUrls){
		
		for (String url : requestUrls) {
			
			if (!TextUtils.isEmpty(url)) {
				HttpRequest httpRequest = new HttpRequest();
				httpResponse = httpRequest.postReuqest(url, requestParams);
				String mhResponse = httpResponse.getResult();
				if (!TextUtils.isEmpty(mhResponse)) {
					requestCompleteUrl = httpResponse.getRequestCompleteUrl();
					return httpResponse;
				}
			}
			
		}
		return null;
		
	}
}
