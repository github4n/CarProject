package com.mh.core.tools;

import android.os.Environment;

import java.io.File;
import java.util.Properties;

/**
 * Created by MH on 2017/4/24.
 */

public class MHTestConfig {

    private static final String TAG = MHTestConfig.class.getSimpleName();

    /**
     * mh沙盒测试文件名
     */
    private static final String MH_TEST_SETTING_FILE_NAME = File.separator + "mhlog" +
            File.separator + "mhlog.properties";

    public static final String CONFIG_TRUE = "true";

    /**
     * 获取mh沙盒测试文件路径
     * @return mh沙盒测试文件路径
     */
    public static String getMHTestSettingFilePath(){
        String mhTestSettingFilePath = null;
        try{
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                mhTestSettingFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() +
                        MH_TEST_SETTING_FILE_NAME;
            }
        }catch (Exception e) {
            e.printStackTrace();
            mhTestSettingFilePath = null;
            MHLogUtil.logD(TAG, "获取日志配置文件路径发生错误。。。");
        }
        return mhTestSettingFilePath;
    }

    /**
     * 是否mh沙盒测试
     * @return 是否mh沙盒测试
     */
    public static boolean isMHTest() {
        try {
            String logPath = getMHTestSettingFilePath();
            if (null != logPath) {
                Properties prop = MHFileUtil.readProterties(logPath);
                if (null != prop) {
                    if (CONFIG_TRUE.equals(prop.getProperty("mhTest", ""))) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            MHLogUtil.logD(TAG, "读取沙盒测试配置发生错误。。。");
        }
        return false;
    }
}
