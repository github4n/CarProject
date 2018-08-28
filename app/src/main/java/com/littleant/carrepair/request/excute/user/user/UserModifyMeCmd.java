package com.littleant.carrepair.request.excute.user.user;

import android.content.Context;

import com.littleant.carrepair.request.constant.ParamsConstant;
import com.mh.core.tools.MHLogUtil;

public class UserModifyMeCmd extends BaseUserCmd {

    public UserModifyMeCmd(Context context, String phone, String name, String picUrl) {
        super(context);
        params.put(ParamsConstant.ID, user_id);
        params.put(ParamsConstant.PHONE, phone);
        params.put(ParamsConstant.NAME, name);
        // TODO: 2018/8/24 处理图片转换
        params.put(ParamsConstant.PIC, picUrl);
        MHLogUtil.logI(getClass().getSimpleName() + this.params.toString());
    }

    @Override
    protected RequestMethod getRequestMethod() {
        return RequestMethod.POST;
    }
}
