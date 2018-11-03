package com.littleant.carrepair.request.excute.aftersale;

import android.content.Context;
import android.graphics.Bitmap;

import com.littleant.carrepair.request.constant.InterfaceConstant;
import com.littleant.carrepair.request.constant.ParamsConstant;
import com.littleant.carrepair.request.excute.BaseRequestCmd;
import com.littleant.carrepair.request.utils.DataHelper;
import com.mh.core.tools.MHLogUtil;

/**
 * 文件描述:
 * 作者:莫进生
 * 创建时间:2018/10/29 0029
 * 版本号:1
 *售后保养维修详情
 */


public class AfterSaleSubmitMaintainDetailCmd extends  BaseRequestCmd{
    public AfterSaleSubmitMaintainDetailCmd(Context context, String id) {
        super(context);
        params.put(ParamsConstant.ID, id);
        params.put(ParamsConstant.AFTERSALE_METHOD,ParamsConstant.AFTERSALE_FINISH);
        MHLogUtil.logI(getClass().getSimpleName() + this.params.toString());
    }
    @Override
    protected String getInterfaceName() {
        return InterfaceConstant.AFTER_SAVE_MAINTAIN_FINSH_DEATILT;
    }

    @Override
    protected RequestMethod getRequestMethod() {
        return RequestMethod.GET;
    }
}
