package com.mh.core.task.command.impl;

import com.mh.core.beans.InviteConfigBean;
import com.mh.core.http.HttpRequest;
import com.mh.core.http.HttpResponse;
import com.mh.core.task.command.abstracts.MHCommonCmd;
import com.mh.core.tools.MHLogUtil;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URLDecoder;
import java.util.Date;

public class MHInviteConfigCmd extends MHCommonCmd<InviteConfigBean> {
	private InviteConfigBean inviteConfigBean = null;
	private String inviteFileUrl = null;
	private String saveFilePath = null;
	private String inviteFileCdnUrl;
	private String serverTime;
	private String result; 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void setInviteFileCdnUrl(String inviteFileCdnUrl) {
		this.inviteFileCdnUrl = inviteFileCdnUrl;
	}
	public String getInviteFileUrl() {
		return inviteFileUrl;
	}

	public void setInviteFileUrl(String inviteFileUrl) {
		this.inviteFileUrl = inviteFileUrl;
	}

	public String getSaveFilePath() {
		return saveFilePath;
	}

	public void setSaveFilePath(String saveFilePath) {
		this.saveFilePath = saveFilePath;
	}

	@Override
	public InviteConfigBean getResult() {
		return inviteConfigBean;
	}
	
	public void setResult(InviteConfigBean i){
		this.inviteConfigBean = i;
	}

	@Override
	public String getResponse() {
		return result;
	}
	
	public void setRespone(String mResult){
		this.result = mResult;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute() throws Exception {
		if(inviteFileUrl==null||"".equals(inviteFileUrl.trim())){
			MHLogUtil.logE("mh", "inviteFileUrl is empty");
			return;
		}
		MHLogUtil.logI("mh", "开始下载："+inviteFileUrl);
		//result = getHttpResult(inviteFileCdnUrl, inviteFileUrl);
		
		HttpRequest httpRequest = new HttpRequest();
		HttpResponse httpResponse = httpRequest.getReuqestIn2Url(inviteFileCdnUrl, inviteFileUrl);
		result = httpResponse.getResult();
		serverTime = httpResponse.getServerTime();
		
			MHLogUtil.logI("mh", "inviteNotice result:" + result);
			if(result==null||"".equals(result.trim())){
				MHLogUtil.logI("mh", "服务器返回空");
				return;
			}
			JSONObject resultJsonObject = new JSONObject(result);
			inviteConfigBean = new InviteConfigBean();
			inviteConfigBean.setRawRespone(result);
			inviteConfigBean.setAppPlatform(resultJsonObject.optString("appPlatform",""));
			inviteConfigBean.setVersion(resultJsonObject.optString("version",""));
			inviteConfigBean.setPackageName(resultJsonObject.optString("packageName",""));
			inviteConfigBean.setStartTime(resultJsonObject.optLong("startTime", 0l));
			inviteConfigBean.setEndTime(resultJsonObject.optLong("endTime", 0l));
			inviteConfigBean.setWhiteListNames(resultJsonObject.optString("whiteListNames", ""));
			inviteConfigBean.setJumpUrl(resultJsonObject.optString("jumpUrl", ""));
			inviteConfigBean.setFbLikeUrl(resultJsonObject.optString("fbLikeUrl", ""));
			inviteConfigBean.setFbShareUrl(resultJsonObject.optString("fbShareUrl", ""));//分享链接
			inviteConfigBean.setFbIconUrl(resultJsonObject.optString("fbIconUrl", ""));
			inviteConfigBean.setExplainUrl(resultJsonObject.optString("explainUrl", ""));
			inviteConfigBean.setFbShareContent(URLDecoder.decode(resultJsonObject.optString("fbShareContent", ""), "UTF-8"));//分享内容
			inviteConfigBean.setFbInviteContent(URLDecoder.decode(resultJsonObject.optString("fbInviteContent", ""), "UTF-8"));//邀请内容
			inviteConfigBean.setActivityName(resultJsonObject.optString("activityName", ""));
			long currentTime = System.currentTimeMillis();
			try {
				currentTime = new Date(serverTime).getTime();
			} catch (Exception e) {
				e.printStackTrace();
			}
			inviteConfigBean.setCurrentTime(currentTime);
			if(saveFilePath!=null&&!"".equals(result.trim())){
				MHLogUtil.logI("mh", "inviteConfigBean saveFilePath: "+saveFilePath+ File.separator+"inviteConfig.cf");
				File inviteFile = new File(saveFilePath);
				if(!inviteFile.exists()){
					inviteFile.mkdirs();
				}	
				FileOutputStream fos = new FileOutputStream(saveFilePath + File.separator+"inviteConfig.cf");
		        ObjectOutputStream oos = new ObjectOutputStream(fos);
		        try {
		            oos.writeObject(inviteConfigBean);
		            oos.close();
		            fos.close();
		        } catch (IOException e) {   
		            System.out.println(e);   
		        }   
			}
	}
//	public String getHttpResult(String reqUrl, String reqUrl2){
//		String res = "";
//		HttpURLConnection httpUrlConnection = null;
//		try {
//			httpUrlConnection  = getHttpConnection(reqUrl);
//		}catch (Exception e) {
//			try {
//				httpUrlConnection = getHttpConnection(reqUrl2);
//			} catch (Exception e1) {
//				e1.printStackTrace();
//			}
//			e.printStackTrace();
//		}
//		try {
//			int httpCode = httpUrlConnection.getResponseCode();
//			if(httpCode!=200){
//				httpUrlConnection = getHttpConnection(reqUrl2);
//			}
//			serverTime = httpUrlConnection.getHeaderField("Date");
//			InputStream inStream = httpUrlConnection.getInputStream();
//			byte[] buffer = new byte[1024];
//			StringBuffer stringBuffer = new StringBuffer();
//			int count;
//			while ((count = inStream.read(buffer))>0) {
//				stringBuffer.append(new String(buffer, 0, count));
//			}
//			res = stringBuffer.toString();
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//		} catch (ProtocolException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return res;
//	}

//	private HttpURLConnection getHttpConnection(String reqUrl)
//			throws MalformedURLException, IOException, ProtocolException {
//		MHLogUtil.logI("mh", "当前访问地址："+reqUrl);
//		URL url = new URL(reqUrl);
//		URLConnection rulConnection = url.openConnection();
//		HttpURLConnection httpUrlConnection = (HttpURLConnection) rulConnection;
//		httpUrlConnection.setConnectTimeout(15000);
//		httpUrlConnection.setReadTimeout(15000);
//		httpUrlConnection.setDoOutput(false);
//		httpUrlConnection.setDoInput(true);
//		httpUrlConnection.setUseCaches(false);
//		httpUrlConnection.setRequestProperty("accept", "*/*");
//		httpUrlConnection.setRequestProperty("connection", "Keep-Alive");
//		// 进行编码
//		httpUrlConnection.setRequestProperty("Content-Type",
//				"application/x-www-form-urlencoded");
//		// 设定请求的方法为"POST"，默认是GET
//		httpUrlConnection.setRequestMethod("GET");
//		// 连接，从上述第2条中url.openConnection()至此的配置必须要在connect之前完成，
//		httpUrlConnection.connect();
//		return httpUrlConnection;
//	}
}
