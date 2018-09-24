package com.littleant.carrepair.request.excute.user.car;

import android.content.Context;
import android.graphics.Bitmap;

import com.littleant.carrepair.request.constant.ParamsConstant;
import com.littleant.carrepair.request.excute.user.user.BaseUserCmd;
import com.littleant.carrepair.request.utils.DataHelper;
import com.littleant.carrepair.utils.ProjectUtil;
import com.mh.core.tools.MHLogUtil;

public class CarAddCmd extends BaseCarCmd {

    public CarAddCmd(Context context, String code, int car_style, String engine, String city_code, String classsno, String hpzl, boolean isDefault, Bitmap bitmap) {
        super(context);
        params.put(ParamsConstant.CODE, code);
        params.put(ParamsConstant.CAR_STYLE, car_style + "");
        params.put(ParamsConstant.ENGINE, engine);
        params.put(ParamsConstant.IS_DEFAULT, isDefault + "");
        params.put(ParamsConstant.CITY_CODE, city_code);
        params.put(ParamsConstant.CLASSSNO, classsno);
        params.put(ParamsConstant.HPZL, hpzl);
        params.put(ParamsConstant.PIC, DataHelper.bitmap2StrByBase64(bitmap));
        MHLogUtil.logI(getClass().getSimpleName() + this.params.toString());
    }

    @Override
    protected RequestMethod getRequestMethod() {
        return RequestMethod.POST;
    }
}
