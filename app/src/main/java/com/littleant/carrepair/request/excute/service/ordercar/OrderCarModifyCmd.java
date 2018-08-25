package com.littleant.carrepair.request.excute.service.ordercar;

import android.content.Context;

import com.littleant.carrepair.request.constant.ParamsConstant;
import com.littleant.carrepair.request.excute.user.address.BaseAddressCmd;
import com.mh.core.tools.MHLogUtil;

public class OrderCarModifyCmd extends BaseOrderCarCmd {

    protected OrderCarModifyCmd(Context context, String id, int amount) {
        super(context);
        params.put(ParamsConstant.ID, id);
        params.put(ParamsConstant.AMOUNT, amount + "");
        MHLogUtil.logI(getClass().getSimpleName() + this.params.toString());
    }

    @Override
    protected RequestMethod getRequestMethod() {
        return RequestMethod.POST;
    }
}
