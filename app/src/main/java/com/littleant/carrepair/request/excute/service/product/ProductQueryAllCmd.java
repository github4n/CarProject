package com.littleant.carrepair.request.excute.service.product;

import android.content.Context;

import com.littleant.carrepair.request.constant.InterfaceConstant;
import com.littleant.carrepair.request.constant.ParamsConstant;
import com.littleant.carrepair.request.excute.BaseRequestCmd;
import com.mh.core.tools.MHLogUtil;

public class ProductQueryAllCmd extends BaseRequestCmd {
    protected ProductQueryAllCmd(Context context, int catalog_id, int price_order, int sale_order) {
        super(context);
        params.put(ParamsConstant.CATALOG_ID, catalog_id + "");
        params.put(ParamsConstant.PRICE_ORDER, price_order + "");
        params.put(ParamsConstant.SALE_ORDER, sale_order + "");
        MHLogUtil.logI(getClass().getSimpleName() + this.params.toString());
    }

    @Override
    protected String getInterfaceName() {
        return InterfaceConstant.SERVICE_PRODUCT;
    }

    @Override
    protected RequestMethod getRequestMethod() {
        return RequestMethod.GET;
    }
}
