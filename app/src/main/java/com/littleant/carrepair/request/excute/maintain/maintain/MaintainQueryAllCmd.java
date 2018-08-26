package com.littleant.carrepair.request.excute.maintain.maintain;

import android.content.Context;

import com.littleant.carrepair.request.constant.ParamsConstant;
import com.littleant.carrepair.request.excute.maintain.upkeep.BaseUpkeepCmd;
import com.mh.core.tools.MHLogUtil;

public class MaintainQueryAllCmd extends BaseMaintainCmd {
    protected MaintainQueryAllCmd(Context context, int finish) {
        super(context);
        params.put(ParamsConstant.FINISH, finish + "");
        MHLogUtil.logI(getClass().getSimpleName() + this.params.toString());
    }

    @Override
    protected RequestMethod getRequestMethod() {
        return RequestMethod.GET;
    }
}
