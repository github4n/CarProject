package com.mh.core.tools.log;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.mh.core.beans.MHLogEntity;
import com.mh.core.res.MHResConfiguration;
import com.mh.core.tools.MHLocalUtil;
import com.mh.core.tools.MHLogUtil;
import com.mh.core.tools.MHResourceUtil;
import com.mh.core.url.MHDynamicUrl;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class MHLog {

    private static MHLog instance;
    /**
     * 是否正在检查上传条件
     */
    private static volatile boolean mCheckUpload = false;
    /** 日志动态域名 */
    private String mPreferredUrl;
    /** 日志动态备用域名 */
    private String mSpareUrl;
    /** 储值日志上报开关 */
    private static final String SWITCH_MH_PAY_LOG = "mh_pay_log";
    /** 上传日志文件的首选地址 */
    public static final String MH_LOG_PREFERRED_URL = "mhLogPreferredUrl";
    /** 上传日志文件的备用地址 */
    public static final String MH_LOG_SPARE_URL = "mhLogSpareUrl";

    /** 同步锁, 为保证所有操作都按照顺序执行，所有日志操作必须要上同步锁 */
    private static final Object mLock = new Object();

    private MHLog(){

    }

    /**
     * 获取单例
     * @return MHLog
     */
    public static MHLog getInstance(){
        if(instance == null){
            instance = new MHLog();
        }
        return instance;
    }


    /**
     * 记录日志
     * @param event 事件
     * @param remark 备注
     */
    public void log(@NonNull final Context context, final String event, final String remark){
        this.log(context, event, null, remark);
    }

    /**
     * 记录日志
     * @param event 事件
     * @param infoMap 信息
     * @param remark 备注
     */
    public void log(@NonNull final Context context, final String event, final Map<String, String> infoMap, final String remark){
        synchronized (mLock) {
            if(!"y".equals(
                    MHResourceUtil.findStringByName(context, SWITCH_MH_PAY_LOG).trim().toLowerCase())){
                return;
            }
            Map<String, String> info = getInfoMap(context);
            if(infoMap != null && infoMap.size() > 0){
                info.putAll(infoMap);
            }
            MHLogEntity logEntity = new MHLogEntity(event, info, remark);
            // 文件写操作，上互斥锁
            try {
                MHLogFileUtil.getReentrantLock().lock();
                MHLogFileUtil.writeLog(context, logEntity);
            }finally {
                MHLogFileUtil.getReentrantLock().unlock();
            }
            checkUpload(context);
        }
    }

    /**
     * 记录日志
     * @param event 事件
     * @param infoMap 消息
     */
    public void log(@NonNull final Context context, final String event, final Map<String, String> infoMap){
        this.log(context, event, infoMap, null);
    }

    /**
     * 记录日志
     * @param event 事件
     */
    public void log(@NonNull final Context context, final String event){
        this.log(context, event, null, null);
    }



    /**
     * 获取必要的信息，比如版本号，gameCode，包名
     * @return  info
     */
    private Map<String, String> getInfoMap(final Context context){
        Map<String, String> info = new HashMap<>();
        PackageInfo packageInfo = MHLocalUtil.getPackageInfo(context);
        info.put("systemVersion", MHLocalUtil.getOsVersion());
        try{
            info.put("userId", MHResConfiguration.getCurrentMHUserId(context));
        } catch (Exception | Error  ex ){
            MHLogUtil.logD(ex.getMessage());
        }
        if(packageInfo != null) {
            info.put("versionCode", packageInfo.versionCode + "");
            info.put("packageName", packageInfo.packageName);
        }

        if(null != context) {
            String gameCode = MHResConfiguration.getGameCode(context);
            if (!TextUtils.isEmpty(gameCode)) {
                info.put("gameCode", gameCode);
            }
            String mhUUid = MHLocalUtil.getMHUUid(context);
            if (!TextUtils.isEmpty(mhUUid)) {
                info.put("mhUUid", mhUUid);
            }
        }
        return info;
    }

    /**
     * 获取类同步锁
     * @return 同步锁
     */
    public Object getmLock(){
        return mLock;
    }

    /**
     * 上传日志文件
     * @param cxt 上下文
     */
    public void uploadLog(@NonNull final Context cxt){
        synchronized (mLock){
            MHLogFileUtil.uploadLog(cxt, mPreferredUrl, mSpareUrl);
        }
    }

    /**
     * 检查是否符合自动上传的条件
     * @param cxt
     */
    private void checkUpload(final Context cxt){
        if(cxt == null){
            return;
        }
        checkDynamicUrl(cxt);
        if(!mCheckUpload) {
            mCheckUpload = true;
            if (MHLogFileUtil.checkIsUploading()) {
                return;
            }
            //检查文件大小
            String strLogFilePath = getLogFilePath(cxt);
            if (!TextUtils.isEmpty(strLogFilePath)) {
                File logFile = new File(strLogFilePath);
                if (logFile.exists()) {
                    if (logFile.length() > (MHLogFileUtil.MIN_UPLOAD_SIZE)) {
                        MHLogFileUtil.uploadLog(cxt, mPreferredUrl, mSpareUrl);
                    }
                }
            }
            mCheckUpload = false;
        }
    }

    /**
     * 检查并暂存动态域名地址
     * @param cxt 上下文
     */
    private void checkDynamicUrl(Context cxt){
        //如果动态域名为空，就去更新
        if(cxt != null && TextUtils.isEmpty(mPreferredUrl)){
            mPreferredUrl = MHDynamicUrl.getDynamicUrl(cxt, "mhLogPreferredUrl");
            mSpareUrl = MHDynamicUrl.getDynamicUrl(cxt, "mhLogSpareUrl");
            if(TextUtils.isEmpty(mPreferredUrl) && TextUtils.isEmpty(mSpareUrl)){
                mPreferredUrl = MHResourceUtil.findStringByName(cxt, MH_LOG_PREFERRED_URL);
                mSpareUrl= MHResourceUtil.findStringByName(cxt, MH_LOG_SPARE_URL);
            }
        }
    }

    /**
     * 获取日志文件的路径
     * @param cxt 上下文
     * @return 日志文件的路径
     */
    public String getLogFilePath(final Context cxt){
        return MHLogFileUtil.getLogFilePath(cxt);
    }
}
