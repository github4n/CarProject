package com.littleant.carrepair.request.excute.service.order;

import android.content.Context;

import com.littleant.carrepair.request.constant.InterfaceConstant;
import com.littleant.carrepair.request.constant.ParamsConstant;
import com.littleant.carrepair.request.excute.BaseRequestCmd;
import com.mh.core.tools.MHLogUtil;

public class OrderCreateCmd extends BaseRequestCmd {
    public OrderCreateCmd(Context context, int address_id, String order_car_list) {
        super(context);
        params.put(ParamsConstant.ADDRESS_ID, address_id + "");
        params.put(ParamsConstant.ORDER_CAR_LIST, order_car_list + "");
        MHLogUtil.logI(getClass().getSimpleName() + this.params.toString());
    }

    @Override
    protected String getInterfaceName() {
        return InterfaceConstant.SERVICE_ORDER;
    }

    @Override
    protected RequestMethod getRequestMethod() {
        return RequestMethod.POST;
    }
}
