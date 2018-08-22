package com.littleant.carrepair.utils;

import android.content.Context;
import android.text.TextUtils;

public class ProjectUtil {

    public static boolean checkPhone(Context context, String phone) {
        boolean isCorrect = true;
        if((TextUtils.isEmpty(phone) || !TextUtils.isDigitsOnly(phone) || phone.length() != 11)) {
            isCorrect = false;
        }
        return isCorrect;
    }

    public static boolean checkPassword(Context context, String password) {
        boolean isCorrect = true;
        if(!password.matches("\\w{6,20}")) {
            isCorrect = false;
        }
        return isCorrect;
    }
}
