package com.littleant.carrepair.request.excute.survey.survey;

import android.content.Context;

import com.littleant.carrepair.request.constant.InterfaceConstant;
import com.littleant.carrepair.request.excute.BaseRequestCmd;

/**
 * 文件描述:
 * 作者:莫进生
 * 创建时间:2018/11/24 0024
 * 版本号:1
 */


public class SurveyPphoneCmd extends BaseRequestCmd {
    public SurveyPphoneCmd(Context context) {
        super(context);
    }

    @Override
    protected String getInterfaceName() {
        return InterfaceConstant.SURVEY_SURVEY_SURVEY_PHONE;
    }

    @Override
    protected RequestMethod getRequestMethod() {
        return RequestMethod.GET;
    }
}
