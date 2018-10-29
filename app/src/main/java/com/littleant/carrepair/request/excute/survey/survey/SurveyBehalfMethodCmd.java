package com.littleant.carrepair.request.excute.survey.survey;

import android.content.Context;
import android.text.TextUtils;
import android.view.TextureView;

import com.littleant.carrepair.request.constant.InterfaceConstant;
import com.littleant.carrepair.request.constant.ParamsConstant;
import com.littleant.carrepair.request.excute.BaseRequestCmd;
import com.mh.core.tools.MHLogUtil;

public class SurveyBehalfMethodCmd extends BaseRequestCmd {
    public SurveyBehalfMethodCmd(Context context, int id, ParamsConstant.SurveyMethodType method, String longitude, String latitude,
                                 int surveystation_id, int combo_id, String comboitem_list, ParamsConstant.PayChannel payChannel, int score) {
        super(context);
        params.put(ParamsConstant.ID, id + "");
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
        if(method == ParamsConstant.SurveyMethodType.PAY && payChannel != null) {
            params.put(ParamsConstant.ORDER_METHOD, payChannel.getDes());
        }
        if(method == ParamsConstant.SurveyMethodType.COMMENT) {
            params.put(ParamsConstant.SCORE, score + "");
        }
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
