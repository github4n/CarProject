package com.littleant.carrepair.request.excute.aftersale;

import android.content.Context;

import com.littleant.carrepair.request.constant.InterfaceConstant;
import com.littleant.carrepair.request.constant.ParamsConstant;
import com.littleant.carrepair.request.excute.BaseRequestCmd;
import com.mh.core.tools.MHLogUtil;

/**
 * 文件描述:
 * 作者:莫进生
 * 创建时间:2018/10/30 0030
 * 版本号:1
 */


public class AftersaleAllCmd extends BaseRequestCmd {
    public AftersaleAllCmd(Context context) {
        super(context);
        params.put(ParamsConstant.STATE,ParamsConstant.MAINTAIN_LIST_STATUS_ALL+"");
        MHLogUtil.logI(getClass().getSimpleName() + this.params.toString());

    }

    @Override
    protected String getInterfaceName() {
        return InterfaceConstant.AFTER_SAVE_ALL;
    }

    @Override
    protected RequestMethod getRequestMethod() {
        return RequestMethod.GET;
    }
}
