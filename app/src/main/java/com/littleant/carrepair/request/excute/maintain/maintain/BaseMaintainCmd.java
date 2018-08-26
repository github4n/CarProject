package com.littleant.carrepair.request.excute.maintain.maintain;

import android.content.Context;

import com.littleant.carrepair.request.constant.InterfaceConstant;
import com.littleant.carrepair.request.excute.BaseRequestCmd;

public abstract class BaseMaintainCmd extends BaseRequestCmd {
    protected BaseMaintainCmd(Context context) {
        super(context);
    }

    @Override
    protected String getInterfaceName() {
        return InterfaceConstant.MAINTAIN_MAINTAIN;
    }
}
