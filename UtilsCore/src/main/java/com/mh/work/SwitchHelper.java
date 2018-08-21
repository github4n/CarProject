package com.mh.work;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.json.JSONException;
import org.json.JSONObject;

import com.mh.core.beans.GameNoticeConfigBean;
import com.mh.core.beans.InviteConfigBean;
import com.mh.core.task.MHCommandCallBack;
import com.mh.core.task.MHCommandExecute;
import com.mh.core.task.command.abstracts.MHCommand;
import com.mh.core.task.command.impl.MHGameNoticeConfigCmd;
import com.mh.core.task.command.impl.MHInviteConfigCmd;
import com.mh.core.tools.MHStringUtil;
import com.mh.core.url.MHDynamicUrl;

import android.content.Context;
import android.text.TextUtils;

public class SwitchHelper {

	/*public static InviteConfigBean parseInviteResult2(String result){
		
		if (TextUtils.isEmpty(result)) {
			return null;
		}
		
		InviteConfigBean inviteConfigBean = new InviteConfigBean();
		try {
			JSONObject resultJsonObject = new JSONObject(result);
			inviteConfigBean.setCode(resultJsonObject.optString("code", ""));
			inviteConfigBean.setJumpUrl(resultJsonObject.optString("jumpUrl", ""));
			inviteConfigBean.setFbLikeUrl(resultJsonObject.optString("fbLikeUrl", ""));
			inviteConfigBean.setFbShareUrl(resultJsonObject.optString("fbShareUrl", ""));//分享链接
			inviteConfigBean.setFbIconUrl(resultJsonObject.optString("fbIconUrl", ""));
			inviteConfigBean.setExplainUrl(resultJsonObject.optString("explainUrl", ""));
			inviteConfigBean.setFbShareContent(URLDecoder.decode(resultJsonObject.optString("fbShareContent", ""), "UTF-8"));//分享内容
			inviteConfigBean.setFbInviteContent(URLDecoder.decode(resultJsonObject.optString("fbInviteContent", ""), "UTF-8"));//邀请内容
			return inviteConfigBean;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static GameNoticeConfigBean parseNoticeResult2(String result){
		
		if (TextUtils.isEmpty(result)) {
			return null;
		}
		
		GameNoticeConfigBean gameNoticeConfigBean = new GameNoticeConfigBean();
		
		try {
			JSONObject resultJsonObject = new JSONObject(result);
			gameNoticeConfigBean.setCode(resultJsonObject.optString("code", ""));
			//推广墙
			gameNoticeConfigBean.setSnsUrl(resultJsonObject.optString("snsUrl", ""));
			gameNoticeConfigBean.setServiceUrl(resultJsonObject.optString("serviceUrl", ""));
			gameNoticeConfigBean.setGameUrl(resultJsonObject.optString("gameUrl", ""));
			gameNoticeConfigBean.setPayUrl(resultJsonObject.optString("payUrl", ""));
			gameNoticeConfigBean.setConsultUrl(resultJsonObject.optString("consultUrl", ""));
			gameNoticeConfigBean.setNoticeUrl(resultJsonObject.optString("notice", ""));
			gameNoticeConfigBean.setUserSwitchEnable(resultJsonObject.optString("userSwitchEnable", ""));
			return gameNoticeConfigBean;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}*/
	
	public static void oldGameNoticeCfg(final MHCommandCallBack callBack, final Context context, String gameCode,
			String cdnUrl, String serverUrl) {
		cdnUrl = MHStringUtil.checkUrl(cdnUrl);
		serverUrl = MHStringUtil.checkUrl(serverUrl);
		String gameNoticeFileCdnUrl = cdnUrl + "gameNotice/" + gameCode + "Notice.txt";
		String gameNoticeFileUrl = serverUrl + "gameNotice/" + gameCode + "Notice.txt";

		gameNoticeFileCdnUrl = MHDynamicUrl.getDynamicUrl(context, "mhFbNoticeUrl", gameNoticeFileCdnUrl);

		final MHGameNoticeConfigCmd mhGameNoticeConfigCmd = new MHGameNoticeConfigCmd();
		mhGameNoticeConfigCmd.setGameNoticeFileUrl(gameNoticeFileUrl);
		mhGameNoticeConfigCmd.setGameNoticeFileCdnUrl(gameNoticeFileCdnUrl);
		mhGameNoticeConfigCmd.setSaveFilePath(context.getFilesDir().getAbsolutePath() + File.separator + "mh" + File.separator + "platform");
		mhGameNoticeConfigCmd.setShowProgress(false);
		
		mhGameNoticeConfigCmd.setCallback(new MHCommandCallBack() {
			public void cmdCallBack(MHCommand command) {
				if (callBack != null) {
					// mhGameNoticeConfigCmd.getResult()
					callBack.cmdCallBack(mhGameNoticeConfigCmd);
				}
			}
		});
		
		MHCommandExecute.getInstance().asynExecute(context, mhGameNoticeConfigCmd);
	}
	
	public static void oldInviteConfig(MHCommandCallBack callBack, Context context, String gameCode, String cdnUrl, String serverUrl,
			String inviteType) {
		cdnUrl = MHStringUtil.checkUrl(cdnUrl);
		serverUrl = MHStringUtil.checkUrl(serverUrl);
		String inviteFileCdnUrl = cdnUrl + "Invite/" + gameCode + inviteType + "Invite.txt";
		String inviteFileUrl = serverUrl + "Invite/" + gameCode + inviteType + "Invite.txt";
		inviteFileCdnUrl = MHDynamicUrl.getDynamicUrl(context, "mhFbInviteUrl", inviteFileCdnUrl);
		initoldInviteConfig(context, inviteFileCdnUrl, inviteFileUrl, callBack);
	}
	  
	private static void initoldInviteConfig(Context context, String inviteFileCdnUrl, String inviteFileUrl, final MHCommandCallBack callBack) {
		MHInviteConfigCmd mhInviteConfigCmd = new MHInviteConfigCmd();
		mhInviteConfigCmd.setInviteFileUrl(inviteFileUrl);
		mhInviteConfigCmd.setInviteFileCdnUrl(inviteFileCdnUrl);
		mhInviteConfigCmd.setSaveFilePath(context.getFilesDir().getAbsolutePath() + File.separator + "mh" + File.separator + "platform");
		mhInviteConfigCmd.setShowProgress(false);
		mhInviteConfigCmd.setCallback(new MHCommandCallBack() {
			public void cmdCallBack(MHCommand command) {
				if (callBack != null) {
					callBack.cmdCallBack(command);
				}
			}
		});
		MHCommandExecute.getInstance().asynExecute(context, mhInviteConfigCmd);
	}
	
	public static GameNoticeConfigBean getGameNoticeConfigBean(Context context) {
		GameNoticeConfigBean gameNoticeConfigBean = null;
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

	public static InviteConfigBean getInviteConfigBean(Context context) {
		InviteConfigBean inviteConfigBean = null;
		try {
			FileInputStream fis = new FileInputStream(context.getFilesDir().getAbsolutePath() + File.separator + "mh" + File.separator + "platform"
					+ File.separator + "inviteConfig.cf");
			ObjectInputStream ois = new ObjectInputStream(fis);
			inviteConfigBean = (InviteConfigBean) ois.readObject();
			ois.close();
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return inviteConfigBean;
	}
}
