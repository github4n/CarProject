package com.littleant.carrepair.request.excute.survey.survey;

import android.content.Context;

import com.littleant.carrepair.request.constant.InterfaceConstant;
import com.littleant.carrepair.request.constant.ParamsConstant;
import com.littleant.carrepair.request.excute.BaseRequestCmd;
import com.mh.core.tools.MHLogUtil;

public class SurveyBehalfMethodCmd extends BaseRequestCmd {
    public SurveyBehalfMethodCmd(Context context, String id, ParamsConstant.SurveyMethodType method, String longitude, String latitude,
                                 int surveystation_id, int combo_id, String comboitem_list) {
        super(context);
        params.put(ParamsConstant.ID, id);
        params.put(ParamsConstant.METHOD, method.getDes());
        params.put(ParamsConstant.LONGITUDE, longitude);
        params.put(ParamsConstant.LATITUDE, latitude);
        params.put(ParamsConstant.SURVEYSTATION_ID, surveystation_id + "");
        params.put(ParamsConstant.COMBO_ID, combo_id + "");
        params.put(ParamsConstant.COMBOITEM_LIST, comboitem_list);
        MHLogUtil.logI(getClass().getSimpleName() + this.params.toString());
    }

    @Override
    protected String getInterfaceName() {
        return InterfaceConstant.SURVEY_SURVEY_BEHALFMETHOD;
    }

    @Override
    protected RequestMethod getRequestMethod() {
        return RequestMethod.POST;
    }
}
