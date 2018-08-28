package com.littleant.carrepair.request.excute.user.car;

import android.content.Context;

import com.littleant.carrepair.request.constant.ParamsConstant;
import com.littleant.carrepair.request.excute.user.user.BaseUserCmd;
import com.mh.core.tools.MHLogUtil;

public class CarQueryAllCmd extends BaseCarCmd {

    public CarQueryAllCmd(Context context, ParamsConstant.QueryType type) {
        super(context);
        switch (type) {
            case NONE:
                break;
            case DEFAULT:
                params.put(ParamsConstant.IS_DEFAULT, "true");
                break;
            case NORMAL:
                params.put(ParamsConstant.IS_DEFAULT, "false");
                break;
        }
        MHLogUtil.logI(getClass().getSimpleName() + this.params.toString());
    }

    @Override
    protected RequestMethod getRequestMethod() {
        return RequestMethod.GET;
    }
}
