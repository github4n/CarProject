package com.littleant.carrepair.request.excute.maintain.upkeep;

import android.content.Context;

import com.littleant.carrepair.request.constant.InterfaceConstant;
import com.littleant.carrepair.request.constant.ParamsConstant;
import com.littleant.carrepair.request.excute.BaseRequestCmd;
import com.mh.core.tools.MHLogUtil;

public class UpkeepCreateCmd extends BaseUpkeepCmd {
    public UpkeepCreateCmd(Context context, int garage_id, int car_id, String name, String phone
                           , String longitude, String latitude, String address,
                           String number, String oil_id_list,String oil_amount_list) {
        super(context);
        params.put(ParamsConstant.GARAGE_ID, garage_id + "");
        params.put(ParamsConstant.CAR_ID, car_id + "");
        params.put(ParamsConstant.NAME, name);
        params.put(ParamsConstant.PHONE, phone);
        //params.put(ParamsConstant.SUBSCRIBE_TIME, subscribe_time);
        params.put(ParamsConstant.LONGITUDE, longitude);
        params.put(ParamsConstant.LATITUDE, latitude);
        params.put(ParamsConstant.ADDRESS, address);
        params.put(ParamsConstant.NUMBER, number);
        params.put(ParamsConstant.OIL_ID_LIST, oil_id_list);
        params.put(ParamsConstant.OIL_AMOUNT, oil_amount_list);

        MHLogUtil.logI(getClass().getSimpleName() + this.params.toString());
    }

    @Override
    protected RequestMethod getRequestMethod() {
        return RequestMethod.POST;
    }
}
