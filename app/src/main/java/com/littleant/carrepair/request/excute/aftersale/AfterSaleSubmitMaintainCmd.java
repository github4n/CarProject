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
 * 提交售后维修
 */


public class AfterSaleSubmitMaintainCmd extends  BaseRequestCmd{
    public AfterSaleSubmitMaintainCmd(Context context, String id, String content, Bitmap[] pics) {
        super(context);

        //params.put(ParamsConstant.NUMBER, number + "");
        if(pics != null && pics.length > 0) {
            int size = pics.length;
            params.put(ParamsConstant.NUMBER, size + "");
            for(int i = 0; i < size; i++) {
                String base64 = DataHelper.bitmap2StrByBase64(pics[i]);
                params.put("pic" + (i+1), base64);
            }
        } else {
            params.put(ParamsConstant.NUMBER, "0");
        }
        params.put(ParamsConstant.ID, id);
        params.put(ParamsConstant.CONTENT, content);
        MHLogUtil.logI(getClass().getSimpleName() + this.params.toString());
    }
    @Override
    protected String getInterfaceName() {
        return InterfaceConstant.AFTER_SAVE;
    }

    @Override
    protected RequestMethod getRequestMethod() {
        return RequestMethod.POST;
    }
}
