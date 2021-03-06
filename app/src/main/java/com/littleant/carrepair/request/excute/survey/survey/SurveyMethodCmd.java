package com.littleant.carrepair.request.excute.survey.survey;

import android.content.Context;
import android.text.TextUtils;

import com.littleant.carrepair.request.constant.InterfaceConstant;
import com.littleant.carrepair.request.constant.ParamsConstant;
import com.littleant.carrepair.request.excute.BaseRequestCmd;
import com.mh.core.tools.MHLogUtil;

public class SurveyMethodCmd extends BaseRequestCmd {
    public SurveyMethodCmd(Context context, String id, ParamsConstant.SurveyMethodType method, String longitude, String latitude,
                              int surveystation_id, int combo_id, String comboitem_list) {
        super(context);
        params.put(ParamsConstant.ID, id);
        params.put(ParamsConstant.METHOD, method.getDes());
        if(!TextUtils.isEmpty(latitude) && !TextUtils.isEmpty(longitude)) {
            params.put(ParamsConstant.LONGITUDE, longitude);
            params.put(ParamsConstant.LATITUDE, latitude);
        }
        if(surveystation_id >= 0) {
            params.put(ParamsConstant.SURVEYSTATION_ID, surveystation_id + "");
        }
        if(combo_id >= 0) {
            params.put(ParamsConstant.COMBO_ID, combo_id + "");
        }
        if(!TextUtils.isEmpty(comboitem_list)) {
            params.put(ParamsConstant.COMBOITEM_LIST, comboitem_list);
        }
        MHLogUtil.logI(getClass().getSimpleName() + this.params.toString());
    }

    @Override
    protected String getInterfaceName() {
        return InterfaceConstant.SURVEY_SURVEY_SELFMETHOD;
    }

    @Override
    protected RequestMethod getRequestMethod() {
        return RequestMethod.POST;
    }
}
