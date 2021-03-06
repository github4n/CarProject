package com.littleant.carrepair.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.littleant.carrepair.request.bean.BaseResponseBean;
import com.mh.core.db.MHDatabase;
import com.mh.core.tools.MHLogUtil;

public class ProjectUtil {

    public static boolean checkPhone(Context context, String phone) {
        boolean isCorrect = true;
        if ((TextUtils.isEmpty(phone) || !TextUtils.isDigitsOnly(phone) || phone.length() != 11)) {
            isCorrect = false;
        }
        return isCorrect;
    }

    public static boolean checkPassword(Context context, String password) {
        boolean isCorrect = true;
        if (!password.matches("\\w{6,20}")) {
            isCorrect = false;
        }
        return isCorrect;
    }

    public static BaseResponseBean getBaseResponseBean(String result) {
        BaseResponseBean t = new Gson().fromJson(result, BaseResponseBean.class);
        if (t != null) {
            String msg = t.getMsg();
            t.setMsg(ProjectUtil.decodeUnicode(msg));
        }
        return t;
    }

    public static <T> T getBaseResponseBean(String result, Class<T> tClass) {
        T t = new Gson().fromJson(result, tClass);
        if (t != null) {
            String msg = ((BaseResponseBean) t).getMsg();
            ((BaseResponseBean) t).setMsg(ProjectUtil.decodeUnicode(msg));
        }
        return t;
    }

    public static String decodeUnicode(String theString) {
        if (TextUtils.isEmpty(theString)) {
            MHLogUtil.logW("转换的string为空");
            return null;
        }
        char aChar;
        int len = theString.length();
        StringBuffer outBuffer = new StringBuffer(len);
        for (int x = 0; x < len; ) {
            aChar = theString.charAt(x++);
            if (aChar == '\\') {
                aChar = theString.charAt(x++);
                if (aChar == 'u') {
                    // Read the xxxx
                    int value = 0;
                    for (int i = 0; i < 4; i++) {
                        aChar = theString.charAt(x++);
                        switch (aChar) {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                value = (value << 4) + aChar - '0';
                                break;
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                value = (value << 4) + 10 + aChar - 'a';
                                break;
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                value = (value << 4) + 10 + aChar - 'A';
                                break;
                            default:
                                throw new IllegalArgumentException(
                                        "Malformed   \\uxxxx   encoding.");
                        }

                    }
                    outBuffer.append((char) value);
                } else {
                    if (aChar == 't')
                        aChar = '\t';
                    else if (aChar == 'r')
                        aChar = '\r';
                    else if (aChar == 'n')
                        aChar = '\n';
                    else if (aChar == 'f')
                        aChar = '\f';
                    outBuffer.append(aChar);
                }
            } else
                outBuffer.append(aChar);
        }
        return outBuffer.toString();
    }

    private static final String TERM_READ = "term_read";

    public static void saveTermRead(Context context) {
        MHDatabase.saveSimpleInfo(context, MHDatabase.MH_FILE, TERM_READ, true);
    }

    public static boolean getTermRead(Context context) {
        return MHDatabase.getSimpleBoolean(context, MHDatabase.MH_FILE, TERM_READ);
    }

    private static final String LAUNCHED = "launched";

    public static void saveLaunched(Context context) {
        MHDatabase.saveSimpleInfo(context, MHDatabase.MH_FILE, LAUNCHED, true);
    }

    public static boolean getLaunched(Context context) {
        return MHDatabase.getSimpleBoolean(context, MHDatabase.MH_FILE, LAUNCHED);
    }

    /**
     * 得到资源文件中图片的Uri
     *
     * @param context 上下文对象
     * @param id      资源id
     * @return Uri
     */
    public static String getUriFromDrawableRes(Context context, int id) {
        Resources resources = context.getResources();
        String path = ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
                + resources.getResourcePackageName(id) + "/"
                + resources.getResourceTypeName(id) + "/"
                + resources.getResourceEntryName(id);
        return path;
//        return Uri.parse(path);
    }

    public static final String ANDROID_RESOURCE = "android.resource://";
    public static final String FOREWARD_SLASH = "/";

    public static Uri resourceIdToUri(Context context, int resourceId) {
        return Uri.parse(ANDROID_RESOURCE + context.getPackageName() + FOREWARD_SLASH + resourceId);
    }

}
