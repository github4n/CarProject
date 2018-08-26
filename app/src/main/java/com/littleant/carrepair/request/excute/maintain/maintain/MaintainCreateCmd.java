package com.littleant.carrepair.request.excute.maintain.maintain;

import android.content.Context;

import com.littleant.carrepair.request.constant.ParamsConstant;
import com.littleant.carrepair.request.excute.maintain.upkeep.BaseUpkeepCmd;
import com.mh.core.tools.MHLogUtil;

public class MaintainCreateCmd extends BaseMaintainCmd {
    protected MaintainCreateCmd(Context context, int garage_id, int car_id, String name, String phone,
                                String subscribe_time, String longitude, String latitude, String address,
                                String content, int number, String[] picUrls) {
        super(context);
        params.put(ParamsConstant.GARAGE_ID, garage_id + "");
        params.put(ParamsConstant.CAR_ID, car_id + "");
        params.put(ParamsConstant.NAME, name);
        params.put(ParamsConstant.PHONE, phone);
        params.put(ParamsConstant.SUBSCRIBE_TIME, subscribe_time);
        params.put(ParamsConstant.LONGITUDE, longitude);
        params.put(ParamsConstant.LATITUDE, latitude);
        params.put(ParamsConstant.ADDRESS, address);
        params.put(ParamsConstant.NUMBER, number + "");
        params.put(ParamsConstant.CONTENT, content);
        if(picUrls != null && picUrls.length > 0) {
            int size = picUrls.length;
        }
        MHLogUtil.logI(getClass().getSimpleName() + this.params.toString());
    }

    @Override
    protected RequestMethod getRequestMethod() {
        return RequestMethod.POST;
    }
}
