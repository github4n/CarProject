package com.littleant.carrepair.request.excute.service.catalog;

import android.content.Context;

import com.littleant.carrepair.request.constant.InterfaceConstant;
import com.littleant.carrepair.request.constant.ParamsConstant;
import com.littleant.carrepair.request.excute.BaseRequestCmd;
import com.mh.core.tools.MHLogUtil;

public class CatalogQueryAllCmd extends BaseRequestCmd {
    public CatalogQueryAllCmd(Context context, int type, int p_id) {
        super(context);
        params.put(ParamsConstant.TYPE, type + "");
        params.put(ParamsConstant.P_ID, p_id + "");
        MHLogUtil.logI(getClass().getSimpleName() + this.params.toString());
    }

    @Override
    protected String getInterfaceName() {
        return InterfaceConstant.SERVICE_CATALOG;
    }

    @Override
    protected RequestMethod getRequestMethod() {
        return RequestMethod.GET;
    }
}
