package com.littleant.carrepair.request.excute.system;

import android.content.Context;

import com.littleant.carrepair.request.constant.InterfaceConstant;
import com.littleant.carrepair.request.excute.BaseRequestCmd;

public class ServiceUserAgreementCmd extends BaseRequestCmd {
    public ServiceUserAgreementCmd(Context context) {
        super(context);
    }

    @Override
    protected String getInterfaceName() {
        return InterfaceConstant.SYSTEM_USERAGREEMENT;
    }

    @Override
    protected RequestMethod getRequestMethod() {
        return RequestMethod.GET;
    }
}
