package com.littleant.carrepair.request.excute.maintain.list;

import android.content.Context;

import com.littleant.carrepair.request.constant.InterfaceConstant;
import com.littleant.carrepair.request.constant.ParamsConstant;
import com.littleant.carrepair.request.excute.BaseRequestCmd;
import com.mh.core.tools.MHLogUtil;

public class ListQueryAllCmd extends BaseRequestCmd {
    public ListQueryAllCmd(Context context, int state, String is_comment) {
        super(context);
        params.put(ParamsConstant.STATE, state + "");
        params.put(ParamsConstant.IS_COMMENT, is_comment);
        MHLogUtil.logI(getClass().getSimpleName() + this.params.toString());
    }

    @Override
    protected String getInterfaceName() {
        return InterfaceConstant.MAINTAIN_LIST;
    }

    @Override
    protected RequestMethod getRequestMethod() {
        return RequestMethod.GET;
    }
}
