package com.littleant.carrepair.request.excute.user.user;

import android.content.Context;
import android.graphics.Bitmap;

import com.littleant.carrepair.request.constant.ParamsConstant;
import com.littleant.carrepair.request.utils.DataHelper;
import com.mh.core.tools.MHLogUtil;

public class UserModifyMeCmd extends BaseUserCmd {

    public UserModifyMeCmd(Context context, String phone, String name, Bitmap bitmap) {
        super(context);
        params.put(ParamsConstant.ID, user_id);
        params.put(ParamsConstant.PHONE, phone);
        params.put(ParamsConstant.NAME, name);
        String picBase64 = DataHelper.bitmap2StrByBase64(bitmap);
        // TODO: 2018/8/24 处理图片转换
        params.put(ParamsConstant.PIC, picBase64);
        MHLogUtil.logI(getClass().getSimpleName() + this.params.toString());
    }

    @Override
    protected RequestMethod getRequestMethod() {
        return RequestMethod.POST;
    }
}
