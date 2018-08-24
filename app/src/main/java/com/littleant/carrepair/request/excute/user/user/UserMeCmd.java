package com.littleant.carrepair.request.excute.user.user;

import android.content.Context;

import com.littleant.carrepair.request.constant.ParamsConstant;
import com.mh.core.tools.MHLogUtil;

public class UserMeCmd extends BaseUserCmd {

    protected UserMeCmd(Context context) {
        super(context);
        params.put(ParamsConstant.ID, user_id);
        MHLogUtil.logI(getClass().getSimpleName() + this.params.toString());
    }

    @Override
    protected RequestMethod getRequestMethod() {
        return RequestMethod.GET;
    }
}
