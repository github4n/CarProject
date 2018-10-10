package com.littleant.carrepair.request.excute.survey.survey;

import android.content.Context;
import android.text.TextUtils;

import com.littleant.carrepair.request.constant.InterfaceConstant;
import com.littleant.carrepair.request.constant.ParamsConstant;
import com.littleant.carrepair.request.excute.BaseRequestCmd;
import com.mh.core.tools.MHLogUtil;

public class SurveyInfoCmd extends BaseRequestCmd {
    public SurveyInfoCmd(Context context) {
        super(context);
        MHLogUtil.logI(getClass().getSimpleName() + this.params.toString());
    }

    @Override
    protected String getInterfaceName() {
        return InterfaceConstant.SURVEY_SURVEY_INFO;
    }

    @Override
    protected RequestMethod getRequestMethod() {
        return RequestMethod.GET;
    }
}
