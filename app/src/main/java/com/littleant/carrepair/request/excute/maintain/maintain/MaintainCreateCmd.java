package com.littleant.carrepair.request.excute.maintain.maintain;

import android.content.Context;
import android.graphics.Bitmap;

import com.littleant.carrepair.request.constant.ParamsConstant;
import com.littleant.carrepair.request.excute.maintain.upkeep.BaseUpkeepCmd;
import com.littleant.carrepair.request.utils.DataHelper;
import com.mh.core.tools.MHLogUtil;
import com.squareup.picasso.Picasso;

public class MaintainCreateCmd extends BaseMaintainCmd {
    public MaintainCreateCmd(Context context, int garage_id, int car_id, String name, String phone,
                                String subscribe_time, String longitude, String latitude, String address,
                                String content, Bitmap[] pics) {
        super(context);
        params.put(ParamsConstant.GARAGE_ID, garage_id + "");
        params.put(ParamsConstant.CAR_ID, car_id + "");
        params.put(ParamsConstant.NAME, name);
        params.put(ParamsConstant.PHONE, phone);
        params.put(ParamsConstant.SUBSCRIBE_TIME, subscribe_time);
        params.put(ParamsConstant.LONGITUDE, longitude);
        params.put(ParamsConstant.LATITUDE, latitude);
        params.put(ParamsConstant.ADDRESS, address);
        params.put(ParamsConstant.CONTENT, content);
        if(pics != null && pics.length > 0) {
            int size = pics.length;
            params.put(ParamsConstant.NUMBER, size + "");
            for(int i = 0; i < size; i++) {
                String base64 = DataHelper.bitmap2StrByBase64(pics[i]);
                params.put("pic" +  (i+1), base64);
            }
        } else {
            params.put(ParamsConstant.NUMBER, "0");
        }
        MHLogUtil.logI(getClass().getSimpleName() + this.params.toString());
    }

    @Override
    protected RequestMethod getRequestMethod() {
        return RequestMethod.POST;
    }
}
