package com.littleant.carrepair.request.excute.login;

import android.content.Context;

import com.littleant.carrepair.request.constant.InterfaceConstant;
import com.littleant.carrepair.request.constant.ParamsConstant;
import com.littleant.carrepair.request.excute.BaseRequestCmd;
import com.mh.core.tools.MHLogUtil;

public class MessageCmd extends BaseRequestCmd {

    public MessageCmd(Context context, String phone, ParamsConstant.MessageType type) {
        super(context);
        params.put(ParamsConstant.PHONE, phone);
        params.put(ParamsConstant.TYPE, type.getDes());
        MHLogUtil.logI(getClass().getSimpleName() + this.params.toString());
    }

    @Override
    protected String getInterfaceName() {
        return InterfaceConstant.LOGIN_MESSAGE;
    }

    @Override
    protected RequestMethod getRequestMethod() {
        return RequestMethod.POST;
    }
}
