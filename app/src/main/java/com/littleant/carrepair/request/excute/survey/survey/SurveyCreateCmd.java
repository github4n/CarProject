package com.littleant.carrepair.request.excute.survey.survey;

import android.content.Context;

import com.littleant.carrepair.request.constant.InterfaceConstant;
import com.littleant.carrepair.request.constant.ParamsConstant;
import com.littleant.carrepair.request.excute.BaseRequestCmd;
import com.mh.core.tools.MHLogUtil;

public class SurveyCreateCmd extends BaseRequestCmd {
    public SurveyCreateCmd(Context context, String name, String phone, String car_name, String car_brand, String car_code,
                              String car_type, int surveystation_id, String order_longitude, String order_latitude,
                              String order_address, String subscribe_time, String is_self, int combo_id, String comboitem_list) {
        super(context);
        params.put(ParamsConstant.NAME, name);
        params.put(ParamsConstant.PHONE, phone);
        params.put(ParamsConstant.CAR_NAME, car_name);
        params.put(ParamsConstant.CAR_BRAND, car_brand);
        params.put(ParamsConstant.CAR_CODE, car_code);
        params.put(ParamsConstant.CAR_TYPE, car_type);
        params.put(ParamsConstant.SURVEYSTATION_ID, surveystation_id + "");
        params.put(ParamsConstant.ORDER_LONGITUDE, order_longitude);
        params.put(ParamsConstant.ORDER_LATITUDE, order_latitude);
        params.put(ParamsConstant.ORDER_ADDRESS, order_address);
        params.put(ParamsConstant.SUBSCRIBE_TIME, subscribe_time);
        params.put(ParamsConstant.IS_SELF, is_self);
        params.put(ParamsConstant.COMBO_ID, combo_id + "");
        params.put(ParamsConstant.COMBOITEM_LIST, comboitem_list);
        MHLogUtil.logI(getClass().getSimpleName() + this.params.toString());
    }

    @Override
    protected String getInterfaceName() {
        return InterfaceConstant.SURVEY_SURVEY;
    }

    @Override
    protected RequestMethod getRequestMethod() {
        return RequestMethod.POST;
    }
}
