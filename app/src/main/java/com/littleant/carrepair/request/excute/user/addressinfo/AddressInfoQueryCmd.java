package com.littleant.carrepair.request.excute.user.addressinfo;

import android.content.Context;

import com.littleant.carrepair.request.constant.InterfaceConstant;
import com.littleant.carrepair.request.excute.BaseRequestCmd;

public class AddressInfoQueryCmd extends BaseRequestCmd {
    protected AddressInfoQueryCmd(Context context) {
        super(context);
    }

    @Override
    protected String getInterfaceName() {
        return InterfaceConstant.USER_ADDRESSINFO;
    }

    @Override
    protected RequestMethod getRequestMethod() {
        return RequestMethod.GET;
    }
}
