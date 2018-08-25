package com.littleant.carrepair.request.excute.service.ordercar;

import android.content.Context;

import com.littleant.carrepair.request.constant.ParamsConstant;
import com.littleant.carrepair.request.excute.user.address.BaseAddressCmd;
import com.mh.core.tools.MHLogUtil;

public class OrderCarAddCmd extends BaseOrderCarCmd {

    protected OrderCarAddCmd(Context context, String product_id, int amount) {
        super(context);
        params.put(ParamsConstant.PRODUCT_ID, product_id);
        params.put(ParamsConstant.AMOUNT, amount + "");
        MHLogUtil.logI(getClass().getSimpleName() + this.params.toString());
    }

    @Override
    protected RequestMethod getRequestMethod() {
        return RequestMethod.POST;
    }
}
