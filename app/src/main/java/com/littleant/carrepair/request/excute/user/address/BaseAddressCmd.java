package com.littleant.carrepair.request.excute.user.address;

import android.content.Context;

import com.littleant.carrepair.request.constant.InterfaceConstant;
import com.littleant.carrepair.request.excute.BaseRequestCmd;

public abstract class BaseAddressCmd extends BaseRequestCmd {
    protected BaseAddressCmd(Context context) {
        super(context);
    }

    @Override
    protected String getInterfaceName() {
        return InterfaceConstant.USER_ADDRESS;
    }
}
