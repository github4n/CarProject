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


}
