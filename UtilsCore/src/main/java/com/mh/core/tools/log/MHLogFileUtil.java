package com.mh.core.tools.log;

import android.Manifest;
import android.content.Context;
import android.text.TextUtils;

import com.mh.core.beans.MHLogEntity;
import com.mh.core.http.HttpRequest;
import com.mh.core.tools.MHFileUtil;
import com.mh.core.tools.MHLocalUtil;
import com.mh.core.tools.MHLogUtil;
import com.mh.core.tools.MHStorageUtil;
import com.mh.core.tools.MHStringUtil;
import com.mh.core.tools.PermissionUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 文件日志，记录日志信息并且上传到服务器
 * Created by MH on 2016/10/25.
 */

public class MHLogFileUtil {

    /** 保存到储存卡上的文件名 */
    private static final String FILE_NAME_LOG = "mh_log.dat";
    /** 最少512b就上传 */
    public static final long MIN_UPLOAD_SIZE = 3 * 1024L;
    /** 移动数据上传最大1Mb */
    public static final long MAX_MOBILE_UPLOAD_SIZE = 1024 * 1024L;
    /** wifi环境上传最大3Mb */
    private static final long MAX_WIFI_UPLOAD_SIZE = 3 * 1024 * 1024L;
    /** 上传日志文件的接口地址 */
    private static final String MH_LOG_UPLOAD_URL = "upload/file";
    /** 上传到服务器的日志文件的文件名 */
    private static final String FILE_NAME_UPLOAD_LOG = "android_log.txt";
    /** 是否正在上传日志文件 */
    private static volatile boolean mIsUploadingLogFile = false;
    /** 文件读写锁 */
    private static final ReentrantLock REENTRANT_LOCK = new ReentrantLock(true);

    /**
     * 是否正在上传
     * @return 是否正在上传
     */
    public static synchronized boolean checkIsUploading(){
        return mIsUploadingLogFile;
    }

    /**
     * 写Log
     * @param cxt 上下文
     * @param logEntity 日志实体
     */
    protected static void writeLog(final Context cxt, MHLogEntity logEntity){
        String logFilePath = getLogFilePath(cxt);
        if(!TextUtils.isEmpty(logFilePath)){
            MHFileUtil.writeFile(logFilePath, logEntity.toString(), true);
        }
    }

    /**
     * 获取日志文件的路径
     * @param cxt 上下文
     * @return 日志文件的路径
     */
    protected static String getLogFilePath(final Context cxt){
        String logFilePath = null;
        if(null != cxt && MHStorageUtil.isExternalStorageExist() && PermissionUtil.hasSelfPermission(cxt, Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            File cachePath = cxt.getExternalCacheDir();
            if(cachePath != null) {
                logFilePath = cachePath.getAbsolutePath() + "/" + FILE_NAME_LOG;
            }
        }
        return logFilePath;
    }

    /**
     * 上传日志文件
     * @param cxt 上下文
     */
    protected static void uploadLog(final Context cxt, String dynamicUrl, String dynamicSpareUrl){
        //没有网络时候就退出
        if(!MHLocalUtil.isNetworkAvaiable(cxt)){
            return;
        }
        boolean uploadThreadStarted = false;
        mIsUploadingLogFile = true;
        //首先检查文件大小
        String strLogFilePath = getLogFilePath(cxt);
        if(!TextUtils.isEmpty(strLogFilePath)) {
            File logFile = new File(strLogFilePath);
            if(logFile.exists() && MHUploadLogAsync.checkThreadInstance()){
                if(logFile.length() > MIN_UPLOAD_SIZE && logFile.length() < MAX_MOBILE_UPLOAD_SIZE){
                    new MHUploadLogAsync(cxt, logFile, dynamicUrl, dynamicSpareUrl).start();
                } else if(logFile.length() > MAX_MOBILE_UPLOAD_SIZE && logFile.length() < MAX_WIFI_UPLOAD_SIZE){
                    //文件略大，只有在wifi的网络环境下才可以上传
                    if(MHLocalUtil.isWifiAvailable(cxt)){
                        new MHUploadLogAsync(cxt, logFile, dynamicUrl, dynamicSpareUrl).start();
                        uploadThreadStarted = true;
                    }
                } else if(logFile.length() > MAX_WIFI_UPLOAD_SIZE){
                    //文件太大，只能删掉了
                    logFile.delete();
                }
            }
        }
        if(!uploadThreadStarted){
            mIsUploadingLogFile = false;
        }
    }

    /**
     * 内部类形式，保证不会随便被其他类调用
     */
    private static class MHUploadLogAsync extends Thread {
        private static String TAG = MHUploadLogAsync.class.getSimpleName();

        private WeakReference<Context> mContext = null;
        /** 要上传的文件 */
        private final File mLogFile;

        private static MHUploadLogAsync mInstance;
        /** 动态域名地址*/
        private String mPreferredUrl;
        /** 动态域名备用地址 */
        private String mSpareUrl;

        MHUploadLogAsync(final Context ctx, final File file, String preferredUrl, String spareUrl){
            mContext = new WeakReference<>(ctx.getApplicationContext());
            mLogFile = file;
            mInstance = this;
            mPreferredUrl = preferredUrl;
            mSpareUrl = spareUrl;
        }

        @Override
        public void run() {
            if(mContext.get() == null){
                MHLogUtil.logD(TAG, "上传失败，上下文已经被销毁！！");
                return;
            }
            MHLogUtil.logD(TAG, "开始上传文件");
            //文件上传整个流程是原子的，上互斥锁
            try {
                REENTRANT_LOCK.lock();
                if (MHStringUtil.isEmpty(mPreferredUrl) && MHStringUtil.isEmpty(mSpareUrl)) {
                    MHLogUtil.logD(TAG, "上传失败，没有找到上传地址，销毁日志文件！！");
                    mLogFile.delete();
                    mInstance = null;
                    return;
                }

                String response = doRequest(mPreferredUrl, mSpareUrl);
                if (!TextUtils.isEmpty(response)) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        int responseCode = jsonObject.getInt("code");
                        String responseMsg = jsonObject.getString("message");
                        if (responseCode == 1000) {
                            MHLogUtil.logD(TAG, "上传日志成功！！！");
                            mLogFile.delete();
                        } else {
                            MHLogUtil.logE("上传日志失败！！" + (responseMsg == null ? "" : responseMsg));
                        }
                    } catch (JSONException ex) {
                        ex.printStackTrace();
                        MHLogUtil.logE("上传日志失败！！json解析失败。。。");
                    }
                } else {
                    MHLogUtil.logE("上传日志失败！！服务端请求失败。。。");
                }
            } finally {
                REENTRANT_LOCK.unlock();
            }
            mIsUploadingLogFile = false;
            mInstance = null;
        }

        /**
         * 上传文件
         * @param preferredUrl 首选地址
         * @param sparedUrl 备用地址
         * @return 服务端返回消息
         */
        private String doRequest(String preferredUrl, String sparedUrl) {
            String response = "";
            if(MHStringUtil.isEmpty(response) && MHStringUtil.isNotEmpty(preferredUrl)) {
                MHLogUtil.logD("upload log preferredUrl:" + preferredUrl + MH_LOG_UPLOAD_URL);
                response = HttpRequest.uploadFile(null, mLogFile, FILE_NAME_UPLOAD_LOG, preferredUrl + MH_LOG_UPLOAD_URL);
                MHLogUtil.logD("upload log preferredUrl response: " + (response == null ? "null" : response));
            }
            if(MHStringUtil.isEmpty(response) && MHStringUtil.isNotEmpty(sparedUrl)) {
                MHLogUtil.logD("upload log spareUrl Url: " + sparedUrl + MH_LOG_UPLOAD_URL);
                response = HttpRequest.uploadFile(null, mLogFile, FILE_NAME_UPLOAD_LOG, sparedUrl + MH_LOG_UPLOAD_URL);
                MHLogUtil.logD("upload log spareUrl response: " + (response == null ? "null" : response));
            }
            return response;
        }

        /**
         * 检查是否已经存在线程实例
         * @return true--没有实例
         */
        static boolean checkThreadInstance(){
            return mInstance == null;
        }

    }

    /** 获取日志文件读写锁 */
    public static ReentrantLock getReentrantLock() {
        return REENTRANT_LOCK;
    }
}
