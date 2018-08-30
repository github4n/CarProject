package com.littleant.carrepair.request.excute.user.car;

import android.content.Context;
import android.graphics.Bitmap;

import com.littleant.carrepair.request.constant.ParamsConstant;
import com.littleant.carrepair.request.excute.user.user.BaseUserCmd;
import com.littleant.carrepair.request.utils.DataHelper;
import com.littleant.carrepair.utils.ProjectUtil;
import com.mh.core.tools.MHLogUtil;

public class CarAddCmd extends BaseCarCmd {

    public CarAddCmd(Context context, String brand, String code, String engine, String buyTime, String mile, boolean isDefault, Bitmap bitmap) {
        super(context);
        params.put(ParamsConstant.BRAND, brand);
        params.put(ParamsConstant.CODE, code);
        params.put(ParamsConstant.ENGINE, engine);
        params.put(ParamsConstant.BUY_TIME, buyTime);
        params.put(ParamsConstant.MILEAGE, mile);
        params.put(ParamsConstant.IS_DEFAULT, isDefault + "");
        // TODO: 2018/8/24 处理图片转换
        params.put(ParamsConstant.PIC, DataHelper.bitmap2StrByBase64(bitmap));
        MHLogUtil.logI(getClass().getSimpleName() + this.params.toString());
    }

    @Override
    protected RequestMethod getRequestMethod() {
        return RequestMethod.POST;
    }
}
