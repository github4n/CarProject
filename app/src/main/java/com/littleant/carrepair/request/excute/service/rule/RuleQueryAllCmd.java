package com.littleant.carrepair.request.excute.service.rule;

import android.content.Context;

import com.littleant.carrepair.request.constant.InterfaceConstant;
import com.littleant.carrepair.request.excute.BaseRequestCmd;
import com.littleant.carrepair.request.excute.service.ordercar.BaseOrderCarCmd;
import com.mh.core.tools.MHLogUtil;

public class RuleQueryAllCmd extends BaseRequestCmd {
    protected RuleQueryAllCmd(Context context) {
        super(context);
        MHLogUtil.logI(getClass().getSimpleName() + this.params.toString());
    }

    @Override
    protected String getInterfaceName() {
        return InterfaceConstant.SERVICE_RULES;
    }

    @Override
    protected RequestMethod getRequestMethod() {
        return RequestMethod.GET;
    }
}
