package com.littleant.carrepair.request.utils;

import android.content.Context;

import com.littleant.carrepair.request.constant.ParamsConstant;
import com.mh.core.db.MHDatabase;

public class DataHelper {
    //保存、获取UserId
    public static void saveUserId(Context context, String userId) {
        MHDatabase.saveSimpleInfo(context, MHDatabase.MH_FILE, ParamsConstant.USER_ID, userId);
    }
    public static String getUserId(Context context) {
        return MHDatabase.getSimpleString(context, MHDatabase.MH_FILE, ParamsConstant.USER_ID);
    }

    //保存、获取token
    public static void saveToken(Context context, String token) {
        MHDatabase.saveSimpleInfo(context, MHDatabase.MH_FILE, ParamsConstant.TOKEN, token);
    }
    public static String getToken(Context context) {
        return MHDatabase.getSimpleString(context, MHDatabase.MH_FILE, ParamsConstant.TOKEN);
    }

    //保存、获取expire
    public static void saveExpire(Context context, String expire) {
        MHDatabase.saveSimpleInfo(context, MHDatabase.MH_FILE, ParamsConstant.EXPIRE, expire);
    }
    public static String getExpire(Context context) {
        return MHDatabase.getSimpleString(context, MHDatabase.MH_FILE, ParamsConstant.EXPIRE);
    }

}
