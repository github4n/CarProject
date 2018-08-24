package com.littleant.carrepair.request.excute.user.car;

import android.content.Context;

import com.littleant.carrepair.request.constant.ParamsConstant;
import com.littleant.carrepair.request.excute.user.user.BaseUserCmd;
import com.mh.core.tools.MHLogUtil;

public class CarAddCmd extends BaseUserCmd {

    protected CarAddCmd(Context context, String brand, String code, String engine, String buyTime, String mile, boolean isDefault, String picUrl) {
        super(context);
        params.put(ParamsConstant.BRAND, brand);
        params.put(ParamsConstant.CODE, code);
        params.put(ParamsConstant.ENGINE, engine);
        params.put(ParamsConstant.BUY_TIME, buyTime);
        params.put(ParamsConstant.MILEAGE, mile);
        params.put(ParamsConstant.IS_DEFAULT, isDefault + "");
        // TODO: 2018/8/24 处理图片转换
        params.put(ParamsConstant.PIC, picUrl);
        MHLogUtil.logI(getClass().getSimpleName() + this.params.toString());
    }

    @Override
    protected RequestMethod getRequestMethod() {
        return RequestMethod.POST;
    }
}
