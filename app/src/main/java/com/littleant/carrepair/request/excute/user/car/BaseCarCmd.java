package com.littleant.carrepair.request.excute.user.car;

import android.content.Context;

import com.littleant.carrepair.request.constant.InterfaceConstant;
import com.littleant.carrepair.request.excute.BaseRequestCmd;

public abstract class BaseCarCmd extends BaseRequestCmd {
    protected BaseCarCmd(Context context) {
        super(context);
    }

    @Override
    protected String getInterfaceName() {
        return InterfaceConstant.USER_CAR;
    }
}
