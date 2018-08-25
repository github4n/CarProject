package com.littleant.carrepair.request.excute.user.address;

import android.content.Context;

import com.littleant.carrepair.request.constant.ParamsConstant;
import com.littleant.carrepair.request.excute.user.car.BaseCarCmd;
import com.mh.core.tools.MHLogUtil;

public class AddressQueryOneCmd extends BaseAddressCmd {

    protected AddressQueryOneCmd(Context context, String id) {
        super(context);
        params.put(ParamsConstant.ID, id);
        MHLogUtil.logI(getClass().getSimpleName() + this.params.toString());
    }

    @Override
    protected RequestMethod getRequestMethod() {
        return RequestMethod.GET;
    }
}
