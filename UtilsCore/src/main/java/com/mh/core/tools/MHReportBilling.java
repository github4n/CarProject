package com.mh.core.tools;

import android.content.Context;
import android.os.Looper;

import com.mh.core.http.HttpRequest;
import com.mh.core.res.MHResConfiguration;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * band等上报储值结果到mh服务器端
 * 
 */
public class MHReportBilling {
	
	public static void mhBandReport(final Context ctx,final String mhOrderId,final String bandUserKey,final String markType,
			final String osType,final String fromType,final String param1,final String param2){
		MHLogUtil.logI("mhLog", "开始上报储值结果2");
		Runnable run =new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				MHLogUtil.logI("mhLog", "开始上报储值结果3");
				String address1=MHResConfiguration.getMHPayPreferredUrl(ctx);
				
				String url=address1+"band_inform.shtml";
				try{
					MHLogUtil.logI("mhLog","储值上报Looper验证"+ Looper.getMainLooper()+"_"+Looper.myLooper());
				}catch(Exception e){
					
				}
				try{
					/*List<NameValuePair> walletParamsPay=new ArrayList<NameValuePair>();
					
					walletParamsPay.add(new BasicNameValuePair("mhOrderId", mhOrderId));
					walletParamsPay.add(new BasicNameValuePair("user_key", bandUserKey));
					walletParamsPay.add(new BasicNameValuePair("market_type", markType));
					walletParamsPay.add(new BasicNameValuePair("os_type", osType));
					walletParamsPay.add(new BasicNameValuePair("from_to", fromType));
					if(param1!=null)
						walletParamsPay.add(new BasicNameValuePair("param1",param1));
					if(param2!=null)
						walletParamsPay.add(new BasicNameValuePair("param2",param2));
					MHLogUtil.logI("mhLog", url+"?mhOrderId="+mhOrderId+"&user_key="+bandUserKey+"&market_type="
					+markType+"&os_type="+osType+"&from_to="+fromType+"&param1="+param1+"&param2="+param2);*/
					
					Map<String, String> dataMap = new HashMap<String, String>();
					dataMap.put("mhOrderId", mhOrderId);
					dataMap.put("user_key", bandUserKey);
					dataMap.put("market_type", markType);
					dataMap.put("os_type", osType);
					dataMap.put("from_to", fromType);
					if(param1!=null)
					dataMap.put("param1", param1);
					if(param2!=null)
					dataMap.put("param2", param2);
					
					HttpRequest.post(url, dataMap);
					//MHHttpUtil.mhPostRequest(url, walletParamsPay);
				}catch(Exception e){
					e.printStackTrace();
				}
			}};
		addThread(run);
		
	}
	
	final static ExecutorService pool = Executors. newSingleThreadExecutor();
	public static synchronized void addThread(Runnable runnable){
		pool.execute(runnable);
	}
	
}
