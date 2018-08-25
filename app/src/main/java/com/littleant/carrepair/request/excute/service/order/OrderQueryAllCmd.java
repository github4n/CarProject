package com.littleant.carrepair.request.excute.service.order;

import android.content.Context;

import com.littleant.carrepair.request.constant.InterfaceConstant;
import com.littleant.carrepair.request.constant.ParamsConstant;
import com.littleant.carrepair.request.excute.BaseRequestCmd;
import com.mh.core.tools.MHLogUtil;

public class OrderQueryAllCmd extends BaseRequestCmd {
    protected OrderQueryAllCmd(Context context, int status, boolean is_comment) {
        super(context);
        params.put(ParamsConstant.STATUS, status + "");
        params.put(ParamsConstant.IS_COMMENT, is_comment + "");
        MHLogUtil.logI(getClass().getSimpleName() + this.params.toString());
    }

    @Override
    protected String getInterfaceName() {
        return InterfaceConstant.SERVICE_ORDER;
    }

    @Override
    protected RequestMethod getRequestMethod() {
        return RequestMethod.GET;
    }
}
