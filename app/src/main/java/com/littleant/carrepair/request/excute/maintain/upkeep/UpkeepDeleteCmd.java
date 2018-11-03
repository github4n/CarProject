package com.littleant.carrepair.request.excute.maintain.upkeep;

import android.content.Context;

import com.littleant.carrepair.request.constant.InterfaceConstant;
import com.littleant.carrepair.request.constant.ParamsConstant;
import com.littleant.carrepair.request.excute.BaseRequestCmd;
import com.mh.core.tools.MHLogUtil;

public class UpkeepDeleteCmd extends BaseRequestCmd {

    public UpkeepDeleteCmd(Context context, int id) {
        super(context);
        params.put(ParamsConstant.ID, id + "");
        MHLogUtil.logI(getClass().getSimpleName() + this.params.toString());
    }

    @Override
    protected String getInterfaceName() {
        return InterfaceConstant.MAINTAIN_UPKEEP_DELETE;
    }

    @Override
    protected RequestMethod getRequestMethod() {
        return RequestMethod.GET;
    }
}
