package com.littleant.carrepair.request.excute.service.ordercar;

import android.content.Context;

import com.littleant.carrepair.request.constant.InterfaceConstant;
import com.littleant.carrepair.request.excute.BaseRequestCmd;

public abstract class BaseOrderCarCmd extends BaseRequestCmd {
    protected BaseOrderCarCmd(Context context) {
        super(context);
    }

    @Override
    protected String getInterfaceName() {
        return InterfaceConstant.SERVICE_ORDERCAR;
    }
}
