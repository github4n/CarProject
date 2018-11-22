package com.littleant.carrepair.request.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Poi;
import com.amap.api.navi.AmapNaviPage;
import com.amap.api.navi.AmapNaviParams;
import com.amap.api.navi.AmapNaviType;
import com.amap.api.navi.AmapRouteActivity;
import com.amap.api.navi.INaviInfoCallback;
import com.littleant.carrepair.R;
import com.littleant.carrepair.activies.datetime.DateActivity;
import com.littleant.carrepair.activies.datetime.TimeActivity;
import com.littleant.carrepair.request.constant.ParamsConstant;
import com.mh.core.cipher.MHCipher;
import com.mh.core.db.MHDatabase;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;

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
        bit.recycle();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    //打电话
    public static void callPhone(Activity activity, String phoneNum) {
        if (TextUtils.isEmpty(phoneNum)) {
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

    //保存、获取纬度
    public static void saveMyLatitude(Context context, Double myLatitude) {
        MHDatabase.saveSimpleInfo(context, MHDatabase.MH_FILE, ParamsConstant.LATITUDE, myLatitude);
    }

    public static String getMyLatitude(Context context) {
        return MHDatabase.getSimpleString(context, MHDatabase.MH_FILE, ParamsConstant.LATITUDE);
    }

    //保存、获取经度
    public static void saveMyLongitude(Context context, Double myLongitude) {
        MHDatabase.saveSimpleInfo(context, MHDatabase.MH_FILE, ParamsConstant.LONGITUDE, myLongitude);
    }

    public static String getMyLongitude(Context context) {
        return MHDatabase.getSimpleString(context, MHDatabase.MH_FILE, ParamsConstant.LONGITUDE);
    }
    //导航功能
    public static void prepareNavi(Context context, LatLng startLocation, LatLng endLocation, INaviInfoCallback iNaviInfoCallback) {
        Poi start = new Poi("", startLocation, "");
        Poi end = new Poi("", endLocation, "");
        AmapNaviPage.getInstance().showRouteActivity(context, new AmapNaviParams(start, null, end, AmapNaviType.DRIVER), iNaviInfoCallback);
    }

    public static Bitmap[] parseUriList2BitmapArray(Context context, List<Uri> uris) {
        if (uris == null || uris.size() < 1) {
            return null;
        }
        int size = uris.size();
        Bitmap[] bitmaps = new Bitmap[size];
        for (int i = 0; i < size; i++) {
            try {
                bitmaps[i] = getThumbnail(context, uris.get(i), 500);
//                bitmaps[i] = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uris.get(i));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bitmaps;
    }

    private static Bitmap getThumbnail(Context context, Uri uri,int size) throws FileNotFoundException, IOException{
        InputStream input = context.getContentResolver().openInputStream(uri);
        BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
        onlyBoundsOptions.inJustDecodeBounds = true;
        onlyBoundsOptions.inDither=true;//optional
        onlyBoundsOptions.inPreferredConfig=Bitmap.Config.ARGB_8888;//optional
        BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
        input.close();
        if ((onlyBoundsOptions.outWidth == -1) || (onlyBoundsOptions.outHeight == -1)) {
            return null;
        }
        int originalSize = (onlyBoundsOptions.outHeight > onlyBoundsOptions.outWidth) ? onlyBoundsOptions.outHeight : onlyBoundsOptions.outWidth;
        double ratio = (originalSize > size) ? (originalSize / size) : 1.0;
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inSampleSize = getPowerOfTwoForSampleRatio(ratio);
        bitmapOptions.inDither=true;//optional
        bitmapOptions.inPreferredConfig=Bitmap.Config.ARGB_8888;//optional
        input = context.getContentResolver().openInputStream(uri);
        Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
        input.close();
        return bitmap;
    }
    private static int getPowerOfTwoForSampleRatio(double ratio){
        int k = Integer.highestOneBit((int)Math.floor(ratio));
        if(k==0) return 1;
        else return k;
    }

    public interface PickDateListener {
        void onDatePick(String dateAndTime);
    }

    //选择日期和时间
    public static void pickDateAndTime(final Activity activity, final PickDateListener listener) {
        DateActivity dateActivity = new DateActivity();
        dateActivity.setCallback(new DateActivity.SelectDateCallback() {
            @Override
            public void onSelectDate(int year, int month, int day) {
                Log.i("aac_tv_time", "year -- " + year);
                Log.i("aac_tv_time", "month -- " + month);
                Log.i("aac_tv_time", "day -- " + day);
                //格式示例2018-03-20
                final String date = DataHelper.parseDate(year, month, day);
                TimeActivity timeActivity = new TimeActivity();
                timeActivity.setCallback(new TimeActivity.SelectTimeCallback() {
                    @Override
                    public void onSelectTime(int hourOfDay, int minute) {
                        String time = DataHelper.parseTime(hourOfDay, minute);
                        if (listener != null) {
                            listener.onDatePick(date + " " + time);
                        }
                    }
                });
                timeActivity.show(activity.getFragmentManager(), TimeActivity.class.getSimpleName());
            }
        });
        dateActivity.show(activity.getFragmentManager(), DateActivity.class.getSimpleName());
    }

    //选择日期
    public static void pickDate(final Activity activity, final PickDateListener listener) {
        DateActivity dateActivity = new DateActivity();
        dateActivity.setCallback(new DateActivity.SelectDateCallback() {
            @Override
            public void onSelectDate(int year, int month, int day) {
                Log.i("aac_tv_time", "year -- " + year);
                Log.i("aac_tv_time", "month -- " + month);
                Log.i("aac_tv_time", "day -- " + day);
                //格式示例2018-03-20
                String date = DataHelper.parseDate(year, month, day);
                if (listener != null) {
                    listener.onDatePick(date);
                }
            }
        });
        dateActivity.show(activity.getFragmentManager(), DateActivity.class.getSimpleName());
    }

    public static String displayPrice(Context context, float price) {
        return String.format(context.getResources().getString(R.string.text_price_prefix), transformBig(price));
    }

    private static String transformBig(float d) {
        BigDecimal d1 = new BigDecimal(Float.toString(d));
        BigDecimal d2 = new BigDecimal(Integer.toString(1));
        // 四舍五入,保留2位小数
        return d1.divide(d2,2,BigDecimal.ROUND_HALF_UP).toString();
    }

    public static float getDisplayPrice(Context context, String display) {
        if (TextUtils.isEmpty(display)) {
            return 0;
        }
        if (display.startsWith("￥")) {
            return Float.parseFloat(display.split("￥")[1]);
        }
        return Float.parseFloat(display);
    }

    private static final String CONTRACT_NAME = "CONTRACT_NAME";
    private static final String CONTRACT_PHONE = "CONTRACT_PHONE";
    private static final String CONTRACT_CITY = "CONTRACT_CITY";


    public static void saveContractName(Context context, String name) {
        MHDatabase.saveSimpleInfo(context, MHDatabase.MH_FILE, CONTRACT_NAME, name);
    }

    public static String getContractName(Context context) {
        return MHDatabase.getSimpleString(context, MHDatabase.MH_FILE, CONTRACT_NAME);
    }

    public static void saveContractPhone(Context context, String phone) {
        MHDatabase.saveSimpleInfo(context, MHDatabase.MH_FILE, CONTRACT_PHONE, phone);
    }

    public static String getContractPhone(Context context) {
        return MHDatabase.getSimpleString(context, MHDatabase.MH_FILE, CONTRACT_PHONE);
    }
    //保存城市
    public static void saveContractCity(Context context, String city) {
        MHDatabase.saveSimpleInfo(context, MHDatabase.MH_FILE, CONTRACT_CITY, city);
    }
    //获取城市
    public static String getContractCity(Context context) {
        return MHDatabase.getSimpleString(context, MHDatabase.MH_FILE, CONTRACT_CITY);
    }

    //维修项是否已确认
    public static void saveRepairConfirm(Context context, int id) {
        MHDatabase.saveSimpleInfo(context, MHDatabase.MH_FILE, id + "", true);
    }

    public static boolean getRepairConfirm(Context context, int id) {
        return MHDatabase.getSimpleBoolean(context, MHDatabase.MH_FILE, id + "");
    }

    //游客登录
    private static final String GUEST_LOGIN = "GUEST_LOGIN";

    public static void saveGuestLogin(Context context, boolean isGuest) {
        MHDatabase.saveSimpleInfo(context, MHDatabase.MH_FILE, GUEST_LOGIN, isGuest);
    }

    public static boolean getGuestLogin(Context context) {
        return MHDatabase.getSimpleBoolean(context, MHDatabase.MH_FILE, GUEST_LOGIN);
    }

    //记录我的当前位置
    private static final String MY_LOCATION_LAT = "MY_LOCATION_LAT";
    private static final String MY_LOCATION_LON = "MY_LOCATION_LON";

    public static void saveMyLocation(Context context, double lat, double lon) {
        MHDatabase.saveSimpleInfo(context, MHDatabase.MH_FILE, MY_LOCATION_LAT, lat);
        MHDatabase.saveSimpleInfo(context, MHDatabase.MH_FILE, MY_LOCATION_LON, lon);
    }

    public static double[] getMyLocation(Context context) {
        return new double[]{MHDatabase.getSimpleDouble(context, MHDatabase.MH_FILE, MY_LOCATION_LAT), MHDatabase.getSimpleDouble(context, MHDatabase.MH_FILE, MY_LOCATION_LON)};
    }

}
