package com.littleant.carrepair.request.excute.user.address;

import android.content.Context;

import com.littleant.carrepair.request.constant.ParamsConstant;
import com.littleant.carrepair.request.excute.user.user.BaseUserCmd;
import com.mh.core.tools.MHLogUtil;

public class AddressAddCmd extends BaseAddressCmd {

    protected AddressAddCmd(Context context, String name, String phone, String node1, String node2, String node3, String address, boolean isDefault) {
        super(context);
        params.put(ParamsConstant.NAME, name);
        params.put(ParamsConstant.PHONE, phone);
        params.put(ParamsConstant.NODE1, node1);
        params.put(ParamsConstant.NODE2, node2);
        params.put(ParamsConstant.NODE3, node3);
        params.put(ParamsConstant.ADDRESS, address);
        params.put(ParamsConstant.IS_DEFAULT, isDefault + "");
        MHLogUtil.logI(getClass().getSimpleName() + this.params.toString());
    }

    @Override
    protected RequestMethod getRequestMethod() {
        return RequestMethod.POST;
    }
}
