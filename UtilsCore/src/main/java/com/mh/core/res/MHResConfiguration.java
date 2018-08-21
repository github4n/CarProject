package com.mh.core.res;

import android.content.Context;
import android.text.TextUtils;

import com.mh.core.cipher.MHCipher;
import com.mh.core.db.MHDatabase;
import com.mh.core.tools.MHLocalUtil;
import com.mh.core.tools.MHLogUtil;
import com.mh.core.tools.MHResourceUtil;

public class MHResConfiguration {
	//此处静态变量原因如下：
	//由于需要在http请求中拼接此参数，但由于HttpRequest类接口中无上下文传进来，无法取得保存的这些数据，并且又需要对外接口保持防止大改动，故在一些特殊方法中获取一下值保存在这里的静态变量
	private static String region = "";
	private static String loginSign = "";
	private static String loginTimestamp = "";
	public static Context mContext;
	
	public static void clearLoginMsg(Context context) {
		region = "";
		loginSign = "";
		loginTimestamp = "";
		mContext = context.getApplicationContext();
		MHDatabase.saveSimpleInfo(context, MHDatabase.MH_FILE, MHDatabase.MH_LOGIN_SIGN, "");// 广告启动（每次启动游戏）清除掉sign
		MHDatabase.saveSimpleInfo(context, MHDatabase.MH_FILE, MHDatabase.MH_LOGIN_TIMESTAMP, "");
		MHDatabase.saveSimpleInfo(context, MHDatabase.MH_FILE, MHDatabase.MH_LOGIN_SERVER_RETURN_DATA, "");
		MHDatabase.saveSimpleInfo(context, MHDatabase.MH_FILE, MHDatabase.MH_LOGIN_USER_ID, "");
	}
	
	public static String getSDKLoginReturnData() {
		if (mContext == null) {
			return "";
		}
		return getSDKLoginReturnData(mContext);
	}
	
	public static String getSDKLoginReturnData(Context context) {
		if (context == null) {
			return "";
		}
		return MHDatabase.getSimpleString(context, MHDatabase.MH_FILE, MHDatabase.MH_LOGIN_SERVER_RETURN_DATA);
	}
	
	/**
	 * @return the loginSign  用来http拼接
	 */
	public static String getLoginSign() {
		if (TextUtils.isEmpty(loginSign) && mContext != null) {
			return getSDKLoginSign(mContext);
		}
		return loginSign;
	}
	
	public static String getLoginTimestamp() {
		if (TextUtils.isEmpty(loginTimestamp) && mContext != null) {
			return getSDKLoginTimestamp(mContext);
		}
		return loginTimestamp;
	}

	/**
	 * @param loginSign the loginSign to set
	 */
	public static void setLoginSign(Context context,String loginSign) {
		MHDatabase.saveSimpleInfo(context, MHDatabase.MH_FILE, MHDatabase.MH_LOGIN_SIGN, loginSign);
		MHResConfiguration.loginSign = loginSign;
	}

	public static void setRegion(Context context,String region){
		MHResConfiguration.region = region;
		MHDatabase.saveSimpleInfo(context, MHDatabase.MH_FILE, MHDatabase.MH_GAME_REGION, region);
	}
	
	public static String getRegion(Context context){
		if (TextUtils.isEmpty(region)) {
			region = MHDatabase.getSimpleString(context, MHDatabase.MH_FILE, MHDatabase.MH_GAME_REGION);
		}
		return region;
	}
	
	
	//===========================================参数配置start===============================================	
	//===========================================每個遊戲每個渠道都可能不一樣=======================================
	//===========================================参数配置start================================================

	
	/**
	 * 获取gameCode
	 * 
	 * @param context
	 * @return
	 */
	public static String getGameCode(Context context) {
		return mhGetString(context, "mhGameCode");
	}
	
	public static String getAppPlatform(Context context) {
		return mhGetString(context, "mhAppPlatform");
	}

	/**
	 * 获取秘钥
	 * 
	 * @param context
	 * @return
	 */
	public static String getAppKey(Context context) {
		return mhGetString(context, "mhAppKey");
	}

	/**
	 * 获取gameShortName
	 * 
	 * @param context
	 * @return
	 */
	public static String getGameShortName(Context context) {
		return mhGetString(context, "mhGameShortName");
	}

	public static String getApplicationId(Context context) {
		return mhGetString(context, "mhFBApplicationId");
	}

	/**
	 * 获取前缀
	 * 
	 * @param context
	 * @return
	 */
	public static String getPrefixName(Context context) {
		return mhGetString(context, "mhPrefixName");
	}

	/**
	 * 获取屏幕方向
	 * 
	 * @param context
	 * @return
	 */
	public static String getScreenOrientation(Context context) {
		return mhGetString(context, "mhScreenOrientation");
	}

	/**
	 * 获取语言种类
	 * 
	 * @param context
	 * @return
	 */
	@Deprecated
	public static String getLanguage(Context context) {
		//return mhGetString(context, "mhLanguage");
		return getSDKLanguage(context);
	}
	
	public static String getSDKLanguage(Context context) {
		String language = MHDatabase.getSimpleString(context, MHDatabase.MH_FILE, MHDatabase.MH_SDK_LANGUAGE);
		if (TextUtils.isEmpty(language)) {
			language =  mhGetString(context, "mhLanguage");
		}
		return language;
	}
	
	public static String getSDKLanguageLower(Context context){
		return getSDKLanguage(context).toLowerCase();
	}
	
	public static String getSDKLoginSign(Context context){
		loginSign = MHDatabase.getSimpleString(context, MHDatabase.MH_FILE, MHDatabase.MH_LOGIN_SIGN);
		return loginSign;
	}
	
	public static String getSDKLoginTimestamp(Context context){
		loginTimestamp = MHDatabase.getSimpleString(context, MHDatabase.MH_FILE, MHDatabase.MH_LOGIN_TIMESTAMP);
		return loginTimestamp;
	}
	
	public static String getCurrentMHUserId(Context context){
		return MHDatabase.getSimpleString(context, MHDatabase.MH_FILE, MHDatabase.MH_LOGIN_USER_ID);
	}
	
	public static String getMHAppPlatform(Context context) {
		return mhGetString(context, "mhAppPlatform");
	}
	
	//===========================================参数配置end===============================================	
	//===========================================参数配置end===============================================	
	//===========================================参数配置end===============================================	
	
	
	
	
	
	
	
	//===========================================域名获取start===============================================	
	//=========================================== 根据地区改变，同一地区的游戏不变===================================
	//===========================================域名获取start===============================================	

	/**
	 * 获取登录域名地址
	 * 
	 * @param context
	 * @return
	 */
	public static String getLoginPreferredUrl(Context context) {
		return mhGetConfigUrl(context, "mhLoginPreferredUrl");
	}

	public static String getLoginSpareUrl(Context context) {
		return mhGetConfigUrl(context, "mhLoginSpareUrl");
	}

	/**
	 * 获取活动工程域名地址
	 * @param cxt 上下文
	 * @return 活动工程域名地址
	 */
	public static String getActivityPreferredUrl(Context cxt){
		return mhGetConfigUrl(cxt, "mhActivityPreferredUrl");
	}

	/**
	 * 获取活动工程备用域名地址
	 * @param cxt 上下文
	 * @return 活动工程备用域名地址
	 */
	public static String getActivitySpareUrl(Context cxt){
		return mhGetConfigUrl(cxt, "mhActivitySpareUrl");
	}

	/**
	 * 获取三方登录域名地址
	 * 
	 * @param context
	 * @return
	 */
	public static String getPlatformLoginPreferredUrl(Context context) {
		return mhGetConfigUrl(context, "mhPlatformLoginPreferredUrl");
	}

	public static String getPlatformLoginSpareUrl(Context context) {
		return mhGetConfigUrl(context, "mhPlatformLoginSpareUrl");
	}
	/**
	 * <p>Description: 获取储值域名</p>
	 * @param context
	 * @return
	 * @date 2015年2月5日
	 */
	public static String getMHPayPreferredUrl(Context context) {
		return mhGetConfigUrl(context, "mhPayPreferredUrl");
	}
	public static String getMHPaySpareUrl(Context context) {
		return mhGetConfigUrl(context, "mhPaySpareUrl");
	}

	/**
	 * <p>Description: 获取广告域名</p>
	 * @param context
	 * @return
	 * @date 2015年2月5日
	 */
	public static String getAdsPreferredUrl(Context context) {
		return mhGetConfigUrl(context, "mhAdsPreferredUrl");
	}
	
	public static String getAdsSpareUrl(Context context) {
		return mhGetConfigUrl(context, "mhAdsSpareUrl");
	}
	
	/**
	 * <p>Description: 获取game工程域名</p>
	 * @param context
	 * @return
	 * @date 2015年2月5日
	 */
	public static String getGamePreferredUrl(Context context) {
		return mhGetConfigUrl(context, "mhGamePreferredDomainUrl");
	}
	
	public static String getGameSpareUrl(Context context) {
		return mhGetConfigUrl(context, "mhGameSpareDomainUrl");
	}
	
	/**
	 * <p>Description: 获取动态域名工程域名</p>
	 * @param context
	 * @return
	 * @date 2015年2月5日
	 */
	public static String getDynamicPreferredUrl(Context context) {
		return mhGetConfigUrl(context, "mhDynamicPreUrl");
	}
	
	public static String getDynamicSpareUrl(Context context) {
		return mhGetConfigUrl(context, "mhDynamicSpaUrl");
	}
	
	/**
	 * <p>Description: 获取FB工程域名</p>
	 * @param context
	 * @return
	 * @date 2015年2月5日
	 */
	public static String getFBPreferredUrl(Context context) {
		return mhGetConfigUrl(context, "mhFbPreferredUrl");
	}
	
	public static String getFBSpareUrl(Context context) {
		return mhGetConfigUrl(context, "mhFbSpareUrl");
	}
	/**
	 * <p>Description: 获取FB工程域名</p>
	 * @param context
	 * @return
	 * @date 2015年2月5日
	 */
	public static String getQuestionPreferredUrl(Context context) {
		return mhGetConfigUrl(context, "mhQuestionPreUrl");
	}
	
	public static String getQuestionSpareUrl(Context context) {
		return mhGetConfigUrl(context, "mhQuestionSpaUrl");
	}
	
	
	public static String getPushServerPreferredUrl(Context context) {
		return mhGetConfigUrl(context, "mhPushPreferredUrl");
	}
	
	public static String getPushServerSpareUrl(Context context) {
		return mhGetConfigUrl(context, "mhPushSpareUrl");
	}
	
/*	public static String getMHLuaSwitchUrl(Context context) {
		return mhGetConfigUrl(context, "mhLuaSwitchUrl");
	}*/
	
	
//===========================================域名获取end===============================================	
//===========================================域名获取end===============================================	
//===========================================域名获取end===============================================	
	
	/**
	 * 获取google play 服务错误提示
	 * 
	 * @param context
	 * @return
	 */
	public static String getGoogleServiceError(Context context) {
		return mhGetString(context, "mhGoogleServerError");
	}

	/**
	 * 获取google play 购买错误提示
	 * 
	 * @param context
	 * @return
	 */
	public static String getGoogleBuyFailError(Context context) {
		return mhGetString(context, "mhGoogleBuyFailError");
	}

	/**
	 * 获取google play 地区错误提示
	 * 
	 * @param context
	 * @return
	 */
	public static String getGoogleStoreError(Context context) {
		return mhGetString(context, "mhGoogleStoreError");
	}

	public static String getGoogleAnalyticsTrackingId(Context context) {
		return mhGetString(context, "mhGoogleAnalyticsTrackingId");
	}
	
	public static String getS2SListenerName(Context context) {
		return mhGetString(context, "mhS2SListenerName");
	}
	
	public static String getGAListenerName(Context context) {
		return mhGetString(context, "mhGAListenerName");
	}
	
	public static String getMHLoginListener(Context context) {
		return mhGetString(context, "mhLoginListenerName");
	}
	
	
	//<string name="reg_jp_mhAdsPreferredUrl">http://ad.mhjp.com/</string>
	public static String mhGetConfigUrl(Context context,String xmlSchemaName){
		mContext = context.getApplicationContext();
		String region = getRegion(context);
		String url = "";
		if (!TextUtils.isEmpty(region)) {
			String xmlSchemaNameTmp = region.toLowerCase() + "_" + xmlSchemaName;
			url = mhGetString(context, xmlSchemaNameTmp);
		}
		
		if (TextUtils.isEmpty(url)) {
			url = mhGetString(context, xmlSchemaName);
		}
		
		if (TextUtils.isEmpty(url)) {
			return "";
		}
		if (url.contains(".com") || url.contains("http") || url.contains("//")) {
			return url;
		}
		String tempUrl = MHCipher.decryptMHDES(url);//解密域名
		if(TextUtils.isEmpty(tempUrl)){
			return url;
		}
		return tempUrl;
	}
	
	private static String mhGetString(Context context,String xmlSchemaName){
		mContext = context.getApplicationContext();
		getSDKLoginSign(context);//初始化一下sign值，为请求网络使用
		getSDKLoginTimestamp(context);
		MHLocalUtil.getMHUUid(context);
		String xmlSchemaContent = "";
		try {
			xmlSchemaContent = context.getResources().getString(MHResourceUtil.findStringIdByName(context, xmlSchemaName));
		} catch (Exception e) {
			MHLogUtil.logW("mh", "String not find:" + xmlSchemaName);
			e.printStackTrace();
			return "";
		}
		return xmlSchemaContent;
	}

}
