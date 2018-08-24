package com.littleant.carrepair.request.excute.user.user;

import android.content.Context;

import com.littleant.carrepair.request.constant.InterfaceConstant;
import com.littleant.carrepair.request.excute.BaseRequestCmd;

public abstract class BaseUserCmd extends BaseRequestCmd {
    protected BaseUserCmd(Context context) {
        super(context);
    }

    @Override
    protected String getInterfaceName() {
        return InterfaceConstant.USER_USER;
    }
}
