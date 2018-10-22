package com.littleant.carrepair.request.excute.maintain.maintain;

import android.content.Context;

import com.littleant.carrepair.request.constant.InterfaceConstant;
import com.littleant.carrepair.request.constant.ParamsConstant;
import com.littleant.carrepair.request.excute.BaseRequestCmd;
import com.mh.core.tools.MHLogUtil;

public class MaintainMethodCmd extends BaseRequestCmd {
    /**
     *
     * @param context
     * @param id
     * @param type 只有付款或评论
     * @param score
     */
    public MaintainMethodCmd(Context context, int id, ParamsConstant.MethodStatus type, int score, ParamsConstant.PayChannel payChannel, String itemList) {
        super(context);
        params.put(ParamsConstant.ID, id + "");
        params.put(ParamsConstant.METHOD, type.getDes());
        if(type == ParamsConstant.MethodStatus.COMMENT) {
            params.put(ParamsConstant.SCORE, score + "");
        }
        if(type == ParamsConstant.MethodStatus.PAY) {
            params.put(ParamsConstant.ORDER_METHOD, payChannel.getDes());
        }
        if(type == ParamsConstant.MethodStatus.ITEM_LIST) {
            params.put(ParamsConstant.MethodStatus.ITEM_LIST.getDes(), itemList);
        }
        MHLogUtil.logI(getClass().getSimpleName() + this.params.toString());
    }

    @Override
    protected String getInterfaceName() {
        return InterfaceConstant.MAINTAIN_MAINTAIN_METHOD;
    }

    @Override
    protected RequestMethod getRequestMethod() {
        return RequestMethod.POST;
    }
}
