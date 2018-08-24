package com.littleant.carrepair.request.excute.login;

import android.content.Context;

import com.littleant.carrepair.request.constant.InterfaceConstant;
import com.littleant.carrepair.request.constant.ParamsConstant;
import com.littleant.carrepair.request.excute.BaseRequestCmd;
import com.mh.core.tools.MHLogUtil;

public class RefreshCmd extends BaseRequestCmd {

    public RefreshCmd(Context context) {
        super(context);
        MHLogUtil.logI(getClass().getSimpleName() + this.params.toString());
    }

    @Override
    protected String getInterfaceName() {
        return InterfaceConstant.LOGIN_REFRESH;
    }

    @Override
    protected RequestMethod getRequestMethod() {
        return RequestMethod.GET;
    }
}
