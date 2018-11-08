package com.littleant.carrepair.request.excute.survey.survey;

import android.content.Context;

import com.littleant.carrepair.request.constant.InterfaceConstant;
import com.littleant.carrepair.request.excute.BaseRequestCmd;
import com.mh.core.tools.MHLogUtil;

public class SurveyUserCancelInfoCmd extends BaseRequestCmd {
    public SurveyUserCancelInfoCmd(Context context) {
        super(context);
        MHLogUtil.logI(getClass().getSimpleName() + this.params.toString());
    }

    @Override
    protected String getInterfaceName() {
        return InterfaceConstant.SURVEY_SURVEY_USERCANCELINFO;
    }

    @Override
    protected RequestMethod getRequestMethod() {
        return RequestMethod.GET;
    }
}
