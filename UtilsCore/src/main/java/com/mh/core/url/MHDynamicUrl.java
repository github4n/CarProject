package com.mh.core.url;

import android.content.Context;
import android.text.TextUtils;

import com.mh.core.beans.GameNoticeConfigBean;
import com.mh.core.beans.InviteConfigBean;
import com.mh.core.beans.UrlBean;
import com.mh.core.beans.UrlFileContent;
import com.mh.core.cipher.MHCipher;
import com.mh.core.constant.InviteType;
import com.mh.core.db.MHDatabase;
import com.mh.core.task.MHCommandCallBack;
import com.mh.core.task.MHCommandExecute;
import com.mh.core.task.command.impl.MHDynamicUrlCmd;
import com.mh.core.task.command.impl.MHReadFileConfigCmd;
import com.mh.core.tools.MHFileUtil;
import com.mh.core.tools.MHLogUtil;
import com.mh.core.tools.MHStringUtil;
import com.mh.work.SwitchHelper;

import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class MHDynamicUrl {

	private static UrlBean urlBean = null;
	private static GameNoticeConfigBean gameNoticeConfigBean = null;
	private static InviteConfigBean inviteConfigBean = null;

	/**
	 * 
	 * @param callBack
	 * @param context
	 * @param gameCode
	 * @param cdnUrl
	 * @param serverUrl
	 */
	public static void initGameNoticeConfig(final MHCommandCallBack callBack, final Context context, String gameCode, String cdnUrl,
			String serverUrl) {

		SwitchHelper.oldGameNoticeCfg(callBack, context, gameCode, cdnUrl, serverUrl);
	}

	/**
	 * 
	 * @param callBack
	 * @param context
	 * @param gameCode
	 * @param cdnUrl
	 * @param serverUrl
	 * @param inviteType
	 *            Fb 、Kakao…..区分大写小；比如com.mh.core.constant.InviteType.Kakao
	 */
	public static void initInviteConfig(final MHCommandCallBack callBack, final Context context, String gameCode, String cdnUrl, String serverUrl,
			String inviteType) {
		
		SwitchHelper.oldInviteConfig(callBack, context, gameCode, cdnUrl, serverUrl, inviteType);
	}

	/**
	 * 
	 * @param callBack
	 * @param context
	 * @param gameCode
	 * @param cdnUrl
	 * @param serverUrl
	 */
	public static void initFbInviteConfig(final MHCommandCallBack callBack, final Context context, String gameCode, String cdnUrl,
			String serverUrl) {

		SwitchHelper.oldInviteConfig(callBack, context, gameCode, cdnUrl, serverUrl, InviteType.FB);
	}


	public synchronized static GameNoticeConfigBean getGameNoticeConfigBean(Context context) {
		try {
			FileInputStream fis = new FileInputStream(context.getFilesDir().getAbsolutePath() + File.separator + "mh" + File.separator + "platform"
					+ File.separator + "gameNoticeConfig.cf");
			ObjectInputStream ois = new ObjectInputStream(fis);
			gameNoticeConfigBean = (GameNoticeConfigBean) ois.readObject();
			ois.close();
			fis.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return gameNoticeConfigBean;
	}

	public static String getGameNoticeConfigByKey(Context context,String key,String def){
		try {
			String result = "";
			FileInputStream fis = new FileInputStream(context.getFilesDir().getAbsolutePath() + File.separator + "mh" + File.separator + "platform" + File.separator + "gameNoticeConfig.cf");
			ObjectInputStream ois = new ObjectInputStream(fis); 
			GameNoticeConfigBean g = (GameNoticeConfigBean) ois.readObject(); 
			ois.close();
			fis.close();
			if (g != null) {
				result = g.getRawRespone();
			}
			
			if (TextUtils.isEmpty(result)) {
				return def;
			}
			JSONObject resultJsonObject = new JSONObject(result);
			return resultJsonObject.optString(key, def);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return def;
	}
	
	
	public synchronized static InviteConfigBean getInviteConfigBean(Context context) {
		
		try {
			FileInputStream fis = new FileInputStream(context.getFilesDir().getAbsolutePath() + File.separator + "mh" + File.separator
					+ "platform" + File.separator + "inviteConfig.cf");
			ObjectInputStream ois = new ObjectInputStream(fis);
			inviteConfigBean = (InviteConfigBean) ois.readObject();
			ois.close();
			fis.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return inviteConfigBean;
	}

	public static String getInviteConfigByKey(Context context,String key,String def){
		try {
			String result = "";
			
			FileInputStream fis = new FileInputStream(context.getFilesDir().getAbsolutePath() + File.separator + "mh" + File.separator
					+ "platform" + File.separator + "inviteConfig.cf");
			
			ObjectInputStream ois = new ObjectInputStream(fis);
			InviteConfigBean i = (InviteConfigBean) ois.readObject();
			ois.close();
			fis.close();
			
			if (i != null) {
				result = i.getRawRespone();
			}
		
			
			if (TextUtils.isEmpty(result)) {
				return def;
			}
			JSONObject resultJsonObject = new JSONObject(result);
			return resultJsonObject.optString(key, def);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return def;
	}
	
	
	public static void initDynamicUrl(Context context, String gameCode, String cdnUrl, String serverUrl, MHCommandCallBack callBack) {
		cdnUrl = MHStringUtil.checkUrl(cdnUrl);
		serverUrl = MHStringUtil.checkUrl(serverUrl);
		String versionFileUrl_cdn = cdnUrl + gameCode + "/google/mhVersionCode.txt";
		String contentFileUrl_cdn = cdnUrl + gameCode + "/google/mhDomainInventory.txt";
		String versionFileUrl_server = serverUrl + gameCode + "/google/mhVersionCode.txt";
		String contentFileUrl_server = serverUrl + gameCode + "/google/mhDomainInventory.txt";
		initDynamicUrl(context, versionFileUrl_cdn, versionFileUrl_server, contentFileUrl_cdn, contentFileUrl_server, callBack);
	}

	public static void initDynamicUrl(Context context, String versionFileUrl_cdn, String versionFileUrl_server, String contentFileUrl_cdn,
		String contentFileUrl_server, MHCommandCallBack callBack) {
		MHDynamicUrlCmd dynamicUrlCmd = new MHDynamicUrlCmd(context);
		dynamicUrlCmd.setShowProgress(false);
		dynamicUrlCmd.setVersionCodeFileUrl(versionFileUrl_cdn);
		dynamicUrlCmd.setVersionCodeFileUrl_Low(versionFileUrl_server);
		dynamicUrlCmd.setContentFileUrl(contentFileUrl_cdn);
		dynamicUrlCmd.setContentFileUrl_Low(contentFileUrl_server);
		dynamicUrlCmd.setCallback(callBack);
		MHCommandExecute.getInstance().asynExecute(context, dynamicUrlCmd);
	}

	public static void getMHFileConfigContent(Context context, String fileUrl, MHCommandCallBack callBack) {
		fileUrl = getDynamicUrl(context, "mhLoginSwitchURL", fileUrl);
		if (TextUtils.isEmpty(fileUrl)) {
			return;
		}
		MHReadFileConfigCmd readFileUrlCmd = new MHReadFileConfigCmd(context);
		readFileUrlCmd.setShowProgress(false);
		readFileUrlCmd.setFileUrl(fileUrl);
		readFileUrlCmd.setCallback(callBack);
		MHCommandExecute.getInstance().asynExecute(context, readFileUrlCmd);
	}

	public static void initPlatformDynamicUrl(Context context, String versionFileUrl_cdn, String versionFileUrl_server, String contentFileUrl_cdn,
			String contentFileUrl_server, MHCommandCallBack callBack) {
		MHDynamicUrlCmd dynamicUrlCmd = new MHDynamicUrlCmd(context);
		dynamicUrlCmd.setShowProgress(false);
		dynamicUrlCmd.setVersionCodeFileUrl(versionFileUrl_cdn);
		dynamicUrlCmd.setVersionCodeFileUrl_Low(versionFileUrl_server);
		dynamicUrlCmd.setContentFileUrl(contentFileUrl_cdn);
		dynamicUrlCmd.setContentFileUrl_Low(contentFileUrl_server);
		dynamicUrlCmd.setPlatform(true);// 设置平台
		dynamicUrlCmd.setCallback(callBack);
		String localContentFileDir = context.getFilesDir().getAbsolutePath() + File.separator + "mh" + File.separator + "platform" + File.separator;
		dynamicUrlCmd.setLocalContentFileDir(localContentFileDir);
		MHCommandExecute.getInstance().asynExecute(context, dynamicUrlCmd);
	}

	public synchronized static UrlBean getUrlBean(Context context) {
		if (null != urlBean && !urlBean.isEmpty()) {
			return urlBean;
		}
		if(context != null) {
			String localContentFileDir = context.getFilesDir().getAbsolutePath() + File.separator + "mh" + File.separator;
			UrlFileContent urlFileContent = readUrlFileContent(context, localContentFileDir + "mhDomainInventory.txt");
			if (null != urlFileContent && urlFileContent.getUrlContent() != null) {
				return MHDynamicUrlCmd.parseUrlContent(context, urlFileContent.getUrlContent(), false);
			}
		}
		return null;
	}

	/**
	 * <p>
	 * Title: getDynamicUrls
	 * </p>
	 * <p>
	 * Description: 根据json键获取相应的域名值
	 * </p>
	 * 
	 * @param context
	 * @param urlKey
	 *            url键的数组
	 * @return url对应键的值
	 */
	public synchronized static String[] getDynamicUrls(Context context, String... urlKey) {
		if(context != null) {
			String localContentFileDir = context.getFilesDir().getAbsolutePath() + File.separator + "mh" + File.separator;
			UrlFileContent urlFileContent = readUrlFileContent(context, localContentFileDir + "mhDomainInventory.txt");
			if (null != urlFileContent && urlFileContent.getUrlContent() != null) {
				String urlContent = urlFileContent.getUrlContent();
				if (!TextUtils.isEmpty(urlContent)) {
					try {
						JSONObject jsonObject = new JSONObject(urlContent);
						String[] urlValues = new String[urlKey.length];
						for (int i = 0; i < urlKey.length; i++) {
							urlValues[i] = jsonObject.optString(urlKey[i], "");
						}
						return urlValues;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return null;
	}

	public synchronized static String[] getDynamicUrls(Context context, String[] urlKey, String[] defaultValues) {
		if (urlKey.length != defaultValues.length) {
			throw new RuntimeException("urlKey与defaultValues长度不一致");
		}
		if(context != null) {
			String localContentFileDir = context.getFilesDir().getAbsolutePath() + File.separator + "mh" + File.separator;
			UrlFileContent urlFileContent = readUrlFileContent(context, localContentFileDir + "mhDomainInventory.txt");
			if (null != urlFileContent && urlFileContent.getUrlContent() != null) {
				String urlContent = urlFileContent.getUrlContent();
				if (!TextUtils.isEmpty(urlContent)) {
					try {
						JSONObject jsonObject = new JSONObject(urlContent);
						String[] urlValues = new String[urlKey.length];
						for (int i = 0; i < urlKey.length; i++) {
							urlValues[i] = jsonObject.optString(urlKey[i], defaultValues[i]);
						}
						return urlValues;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return defaultValues;
	}

	public synchronized static String[] getPlatformDynamicUrls(Context context, String[] urlKey, String[] defaultValues) {
		if (urlKey.length != defaultValues.length) {
			throw new RuntimeException("urlKey与defaultValues长度不一致");
		}
		if(context != null) {
			String localContentFileDir = context.getFilesDir().getAbsolutePath() + File.separator + "mh" + File.separator + "platform" + File.separator;
			// String localContentFileDir = context.getFilesDir().getAbsolutePath()
			// + File.separator + "mh" + File.separator;
			UrlFileContent urlFileContent = readUrlFileContent(context, localContentFileDir + "mhDomainInventory.txt");
			if (null != urlFileContent && urlFileContent.getUrlContent() != null) {
				String urlContent = urlFileContent.getUrlContent();
				if (!TextUtils.isEmpty(urlContent)) {
					try {
						JSONObject jsonObject = new JSONObject(urlContent);
						String[] urlValues = new String[urlKey.length];
						for (int i = 0; i < urlKey.length; i++) {
							urlValues[i] = jsonObject.optString(urlKey[i], defaultValues[i]);
						}
						return urlValues;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return defaultValues;
	}

	/**
	 * <p>
	 * Title: getDynamicUrls
	 * </p>
	 * <p>
	 * Description: 根据json键获取相应的域名值
	 * </p>
	 * 
	 * @param context
	 * @param urlKey
	 *            url键
	 * @return url对应键的值
	 */
	public synchronized static String getDynamicUrl(Context context, String urlKey) {
		if(context != null) {
			String localContentFileDir = context.getFilesDir().getAbsolutePath() + File.separator + "mh" + File.separator;
			UrlFileContent urlFileContent = readUrlFileContent(context, localContentFileDir + "mhDomainInventory.txt");
			if (null != urlFileContent && urlFileContent.getUrlContent() != null) {
				String urlContent = urlFileContent.getUrlContent();
				if (!TextUtils.isEmpty(urlContent)) {
					try {
						JSONObject jsonObject = new JSONObject(urlContent);
						String urlValue = jsonObject.optString(urlKey, "");
						if (urlValue != null && !"".equals(urlValue.trim()) && !"null".equalsIgnoreCase(urlValue.trim())) {
							return urlValue;
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return null;
	}

	/**
	 * <p>
	 * Title: getDynamicUrls
	 * </p>
	 * <p>
	 * Description: 根据json键获取相应的域名值
	 * </p>
	 * 
	 * @param context
	 * @param urlKey
	 *            url键
	 * @param defaultValue
	 *            defaultValue默认值
	 * @return url对应键的值
	 */
	public synchronized static String getDynamicUrl(Context context, String urlKey, String defaultValue) {
		if(context != null) {
			String localContentFileDir = context.getFilesDir().getAbsolutePath() + File.separator + "mh" + File.separator;
			UrlFileContent urlFileContent = readUrlFileContent(context, localContentFileDir + "mhDomainInventory.txt");
			if (null != urlFileContent && urlFileContent.getUrlContent() != null) {
				String urlContent = urlFileContent.getUrlContent();
				if (!TextUtils.isEmpty(urlContent)) {
					try {
						JSONObject jsonObject = new JSONObject(urlContent);
						String urlValue = jsonObject.optString(urlKey, defaultValue);
						if (urlValue != null && !"".equals(urlValue.trim()) && !"null".equalsIgnoreCase(urlValue.trim())) {
							return urlValue;
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return defaultValue;
	}

	public static String getDynamicContent(Context context) {
		String urlContent = null;
		if(context != null) {
			String localContentFileDir = context.getFilesDir().getAbsolutePath() + File.separator + "mh" + File.separator;
			UrlFileContent urlFileContent = readUrlFileContent(context, localContentFileDir + "mhDomainInventory.txt");
			urlContent = urlFileContent.getUrlContent();
		}
		return urlContent;
	}

	/**
	 * <p>
	 * Title: readUrlFileContent
	 * </p>
	 * <p>
	 * Description: 读取本地域名内容文件的内容
	 * </p>
	 * 
	 * @param context
	 * @param filePath
	 *            本地域名内容的路径
	 * @return
	 */
	private static UrlFileContent readUrlFileContent(Context context, String filePath) {
		String localVersionCode = "";
		String localVersionContentMd5 = "";
		String localPlaintext = "";
		String localContent = "";
		UrlFileContent urlFileContent = new UrlFileContent();
		try {
			localContent = MHFileUtil.readFile(context, filePath);
			if (!TextUtils.isEmpty(localContent)) {
				localVersionContentMd5 = MHStringUtil.toMd5(localContent, false);
				localPlaintext = MHCipher.decrypt3DES(localContent, MHDynamicUrlCmd.PASSWORD);
				// MHLogUtil.logD("local content:" + localPlaintext);
				if (!TextUtils.isEmpty(localPlaintext)) {
					JSONObject contentJsonObject = new JSONObject(localPlaintext);
					localVersionCode = contentJsonObject.optString("mhVersionCode", "");
					MHLogUtil.logD("local VersionCode:" + localVersionCode);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		urlFileContent.setUrlContent(localPlaintext);
		urlFileContent.setVersionCode(localVersionCode);
		urlFileContent.setVersionContentMd5(localVersionContentMd5);
		return urlFileContent;
	}


	public static String getDynamicPlatformVersionContent(Context context) {
		return MHDatabase.getSimpleString(context, MHDatabase.MH_FILE, MHDatabase.MH_APP_PLATFORM_DYNAMIC_VERSION_CONTENT);
	}

	public static String getDynamicGameVersionContent(Context context) {
		return MHDatabase.getSimpleString(context, MHDatabase.MH_FILE, MHDatabase.MH_GAME_DYNAMIC_VERSION_CONTENT);
	}

}
