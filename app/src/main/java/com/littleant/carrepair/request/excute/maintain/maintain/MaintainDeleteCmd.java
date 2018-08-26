package com.littleant.carrepair.request.excute.maintain.maintain;

import android.content.Context;

import com.littleant.carrepair.request.constant.InterfaceConstant;
import com.littleant.carrepair.request.constant.ParamsConstant;
import com.littleant.carrepair.request.excute.BaseRequestCmd;
import com.littleant.carrepair.request.excute.maintain.upkeep.BaseUpkeepCmd;
import com.mh.core.tools.MHLogUtil;

public class MaintainDeleteCmd extends BaseRequestCmd {

    protected MaintainDeleteCmd(Context context, String id) {
        super(context);
        params.put(ParamsConstant.ID, id);
        MHLogUtil.logI(getClass().getSimpleName() + this.params.toString());
    }

    @Override
    protected String getInterfaceName() {
        return InterfaceConstant.MAINTAIN_MAINTAIN_DELETE;
    }

    @Override
    protected RequestMethod getRequestMethod() {
        return RequestMethod.GET;
    }
}
