package com.littleant.carrepair.request.excute.service.order;

import android.content.Context;

import com.littleant.carrepair.request.constant.InterfaceConstant;
import com.littleant.carrepair.request.constant.ParamsConstant;
import com.littleant.carrepair.request.excute.BaseRequestCmd;
import com.mh.core.tools.MHLogUtil;

public class OrderMethodCmd extends BaseRequestCmd {
    protected OrderMethodCmd(Context context, String id, ParamsConstant.OrderMethodType type, int score) {
        super(context);
        params.put(ParamsConstant.ID, id);
        params.put(ParamsConstant.METHOD, type.getDes());
        if(type == ParamsConstant.OrderMethodType.COMMENT) {
            params.put(ParamsConstant.ORDER_CAR_LIST, score + "");
        }
        MHLogUtil.logI(getClass().getSimpleName() + this.params.toString());
    }

    @Override
    protected String getInterfaceName() {
        return InterfaceConstant.SERVICE_ORDER_METHOD;
    }

    @Override
    protected RequestMethod getRequestMethod() {
        return RequestMethod.POST;
    }
}
