package com.littleant.carrepair.request.excute.service.order;

import android.content.Context;

import com.littleant.carrepair.request.constant.InterfaceConstant;
import com.littleant.carrepair.request.constant.ParamsConstant;
import com.littleant.carrepair.request.excute.BaseRequestCmd;
import com.mh.core.tools.MHLogUtil;

public class OrderQueryAllCmd extends BaseRequestCmd {
    public OrderQueryAllCmd(Context context, int status, ParamsConstant.CommentStatus commentStatus) {
        super(context);
        params.put(ParamsConstant.STATUS, status + "");
        switch (commentStatus) {
            case NONE:
                break;

            case COMMENT:
                params.put(ParamsConstant.IS_COMMENT, "true");
                break;

            case NOT_COMMENT:
                params.put(ParamsConstant.IS_COMMENT, "false");
                break;
        }
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
