package com.littleant.carrepair.request.excute;

import android.content.Context;

import com.littleant.carrepair.request.constant.InterfaceConstant;
import com.littleant.carrepair.request.constant.ParamsConstant;
import com.mh.core.tools.MHLogUtil;

public class RegisterCmd extends BaseRequestCmd {

    public RegisterCmd(Context context, String phone, String password) {
        super(context);
        params.put(ParamsConstant.PHONE, phone);
        params.put(ParamsConstant.PASSWORD, password);
        MHLogUtil.logI(getClass().getSimpleName() + this.params.toString());
    }

    @Override
    protected String getInterfaceName() {
        return InterfaceConstant.REGISTER;
    }
}
