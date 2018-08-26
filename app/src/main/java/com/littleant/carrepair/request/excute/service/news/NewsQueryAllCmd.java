package com.littleant.carrepair.request.excute.service.news;

import android.content.Context;

import com.littleant.carrepair.request.constant.InterfaceConstant;
import com.littleant.carrepair.request.constant.ParamsConstant;
import com.littleant.carrepair.request.excute.BaseRequestCmd;
import com.mh.core.tools.MHLogUtil;

public class NewsQueryAllCmd extends BaseRequestCmd {
    protected NewsQueryAllCmd(Context context, String news_catalog_id) {
        super(context);
        params.put(ParamsConstant.NEWS_CATALOG_ID, news_catalog_id);
        MHLogUtil.logI(getClass().getSimpleName() + this.params.toString());
    }

    @Override
    protected String getInterfaceName() {
        return InterfaceConstant.SERVICE_NEWS;
    }

    @Override
    protected RequestMethod getRequestMethod() {
        return RequestMethod.GET;
    }
}
