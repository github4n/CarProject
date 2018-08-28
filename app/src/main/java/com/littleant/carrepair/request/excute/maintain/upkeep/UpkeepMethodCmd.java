package com.littleant.carrepair.request.excute.maintain.upkeep;

import android.content.Context;

import com.littleant.carrepair.request.constant.InterfaceConstant;
import com.littleant.carrepair.request.constant.ParamsConstant;
import com.littleant.carrepair.request.excute.BaseRequestCmd;
import com.mh.core.tools.MHLogUtil;

public class UpkeepMethodCmd extends BaseRequestCmd {
    /**
     *
     * @param context
     * @param id
     * @param type 只有付款或评论
     * @param score
     */
    public UpkeepMethodCmd(Context context, String id, ParamsConstant.MethodStatus type, int score) {
        super(context);
        params.put(ParamsConstant.ID, id);
        params.put(ParamsConstant.METHOD, type.getDes());
        if(type == ParamsConstant.MethodStatus.COMMENT) {
            params.put(ParamsConstant.ORDER_CAR_LIST, score + "");
        }
        MHLogUtil.logI(getClass().getSimpleName() + this.params.toString());
    }

    @Override
    protected String getInterfaceName() {
        return InterfaceConstant.MAINTAIN_UPKEEP_METHOD;
    }

    @Override
    protected RequestMethod getRequestMethod() {
        return RequestMethod.POST;
    }
}
