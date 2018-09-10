package com.littleant.carrepair.request.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Base64;

import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Poi;
import com.amap.api.navi.AmapNaviPage;
import com.amap.api.navi.AmapNaviParams;
import com.amap.api.navi.AmapNaviType;
import com.amap.api.navi.AmapRouteActivity;
import com.amap.api.navi.INaviInfoCallback;
import com.littleant.carrepair.request.constant.ParamsConstant;
import com.mh.core.cipher.MHCipher;
import com.mh.core.db.MHDatabase;

import java.io.ByteArrayOutputStream;

public class DataHelper {
    //保存、获取UserId
    public static void saveUserId(Context context, int userId) {
        MHDatabase.saveSimpleInteger(context, MHDatabase.MH_FILE, ParamsConstant.USER_ID, userId);
    }

    public static int getUserId(Context context) {
        return MHDatabase.getSimpleInteger(context, MHDatabase.MH_FILE, ParamsConstant.USER_ID);
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

    //保存、获取phone
    public static void savePhone(Context context, String phone) {
        MHDatabase.saveSimpleInfo(context, MHDatabase.MH_FILE, ParamsConstant.PHONE, phone);
    }

    public static String getPhone(Context context) {
        return MHDatabase.getSimpleString(context, MHDatabase.MH_FILE, ParamsConstant.PHONE);
    }

    //保存、获取password
    public static void savePassword(Context context, String password) {
        MHDatabase.saveSimpleInfo(context, MHDatabase.MH_FILE, ParamsConstant.PASSWORD, MHCipher.encryptMHData(password));
    }

    public static String getPassword(Context context) {
        return MHCipher.decryptMHData(MHDatabase.getSimpleString(context, MHDatabase.MH_FILE, ParamsConstant.PASSWORD));
    }

    //图片转换成Base64
    public static String bitmap2StrByBase64(Bitmap bit) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bit.compress(Bitmap.CompressFormat.JPEG, 40, bos);//参数100表示不压缩
        byte[] bytes = bos.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    //打电话
    public static void callPhone(Activity activity, String phoneNum) {
        if(TextUtils.isEmpty(phoneNum)) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNum));
        activity.startActivity(intent);
    }

    //格式化请求日期
    public static String parseDate(int year, int month, int day) {
        month++;
        return year + "-" + month + "-" + day;

    }

    //格式化请求日期
    public static String parseTime(int hour, int minute) {
        return hour + ":" + minute + ":" + "00";

    }

    //导航功能
    public static void prepareNavi(Context context, LatLng startLocation, LatLng endLocation, INaviInfoCallback iNaviInfoCallback) {
        Poi start = new Poi("", startLocation, "");
        Poi end = new Poi("", endLocation, "");
        AmapNaviPage.getInstance().showRouteActivity(context, new AmapNaviParams(start, null, end, AmapNaviType.DRIVER), iNaviInfoCallback);
    }
}
