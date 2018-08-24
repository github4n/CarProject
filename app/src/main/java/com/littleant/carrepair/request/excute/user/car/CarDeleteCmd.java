package com.littleant.carrepair.request.excute.user.car;

import android.content.Context;

import com.littleant.carrepair.request.constant.InterfaceConstant;
import com.littleant.carrepair.request.constant.ParamsConstant;
import com.littleant.carrepair.request.excute.BaseRequestCmd;
import com.littleant.carrepair.request.excute.user.user.BaseUserCmd;
import com.mh.core.tools.MHLogUtil;

public class CarDeleteCmd extends BaseRequestCmd {

    protected CarDeleteCmd(Context context, String id) {
        super(context);
        params.put(ParamsConstant.ID, id);
        MHLogUtil.logI(getClass().getSimpleName() + this.params.toString());
    }

    @Override
    protected String getInterfaceName() {
        return InterfaceConstant.USER_CAR_DELETE;
    }

    @Override
    protected RequestMethod getRequestMethod() {
        return RequestMethod.GET;
    }
}
