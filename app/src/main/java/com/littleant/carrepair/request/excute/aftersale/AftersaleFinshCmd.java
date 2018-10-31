package com.littleant.carrepair.request.excute.aftersale;

import android.content.Context;

import com.littleant.carrepair.request.constant.InterfaceConstant;
import com.littleant.carrepair.request.constant.ParamsConstant;
import com.littleant.carrepair.request.excute.BaseRequestCmd;
import com.mh.core.tools.MHLogUtil;

/**
 * 文件描述:
 * 作者:莫进生
 * 创建时间:2018/10/31 0031
 * 版本号:1
 */


public class AftersaleFinshCmd extends BaseRequestCmd {

    public AftersaleFinshCmd(Context context,String id) {
        super(context);
        params.put(ParamsConstant.AFTERSALE_ID,id);
        params.put(ParamsConstant.AFTERSALE_METHOD,ParamsConstant.AFTERSALE_FINISH);
        MHLogUtil.logI(getClass().getSimpleName() + this.params.toString());

    }

    @Override
    protected String getInterfaceName() {
        return InterfaceConstant.AFTER_SAVE_FINSH;
    }

    @Override
    protected RequestMethod getRequestMethod() {
        return RequestMethod.POST;
    }
}
