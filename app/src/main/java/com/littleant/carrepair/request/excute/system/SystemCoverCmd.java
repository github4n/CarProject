package com.littleant.carrepair.request.excute.system;

import android.content.Context;

import com.littleant.carrepair.request.constant.InterfaceConstant;
import com.littleant.carrepair.request.constant.ParamsConstant;
import com.littleant.carrepair.request.excute.BaseRequestCmd;

public class SystemCoverCmd extends BaseRequestCmd {
    public SystemCoverCmd(Context context) {
        super(context);
        params.put(ParamsConstant.SYSTEM, ParamsConstant.SYSTEM_ANDROID);
        params.put(ParamsConstant.APP_TYPE, "user");
    }

    @Override
    protected String getInterfaceName() {
        return InterfaceConstant.SYSTEM_COVER;
    }

    @Override
    protected RequestMethod getRequestMethod() {
        return RequestMethod.GET;
    }
}
