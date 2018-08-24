package com.littleant.carrepair.request.excute.login;

import android.content.Context;

import com.littleant.carrepair.request.constant.InterfaceConstant;
import com.littleant.carrepair.request.constant.ParamsConstant;
import com.littleant.carrepair.request.excute.BaseRequestCmd;
import com.mh.core.tools.MHLogUtil;

public class ResetCmd extends BaseRequestCmd {

    public ResetCmd(Context context, String phone, String password, String message) {
        super(context);
        params.put(ParamsConstant.PHONE, phone);
        params.put(ParamsConstant.PASSWORD, password);
        params.put(ParamsConstant.MESSAGE, message);
        MHLogUtil.logI(getClass().getSimpleName() + this.params.toString());
    }

    @Override
    protected String getInterfaceName() {
        return InterfaceConstant.LOGIN_RESET;
    }

    @Override
    protected RequestMethod getRequestMethod() {
        return RequestMethod.POST;
    }
}
