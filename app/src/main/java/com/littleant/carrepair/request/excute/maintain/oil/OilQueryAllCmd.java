package com.littleant.carrepair.request.excute.maintain.oil;

import android.content.Context;

import com.littleant.carrepair.request.constant.InterfaceConstant;
import com.littleant.carrepair.request.constant.ParamsConstant;
import com.littleant.carrepair.request.excute.BaseRequestCmd;
import com.mh.core.tools.MHLogUtil;

public class OilQueryAllCmd extends BaseRequestCmd {
    public OilQueryAllCmd(Context context, int garage_id) {
        super(context);
        params.put(ParamsConstant.GARAGE_ID, garage_id + "");
        MHLogUtil.logI(getClass().getSimpleName() + this.params.toString());
    }

    @Override
    protected String getInterfaceName() {
        return InterfaceConstant.MAINTAIN_OIL;
    }

    @Override
    protected RequestMethod getRequestMethod() {
        return RequestMethod.GET;
    }
}
