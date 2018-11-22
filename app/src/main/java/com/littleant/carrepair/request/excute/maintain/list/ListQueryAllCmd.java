package com.littleant.carrepair.request.excute.maintain.list;

import android.content.Context;

import com.littleant.carrepair.request.constant.InterfaceConstant;
import com.littleant.carrepair.request.constant.ParamsConstant;
import com.littleant.carrepair.request.excute.BaseRequestCmd;
import com.mh.core.tools.MHLogUtil;

public class ListQueryAllCmd extends BaseRequestCmd {
    public ListQueryAllCmd(Context context, int state, ParamsConstant.CommentStatus commentStatus) {
        super(context);
        params.put(ParamsConstant.STATE, state + "");
        switch (commentStatus) {
            case NONE:
                break;

            case COMMENT:
                params.put(ParamsConstant.IS_COMMENT, "True");
                break;

            case NOT_COMMENT:
                params.put(ParamsConstant.IS_COMMENT, "False");
                break;
        }
        MHLogUtil.logI(getClass().getSimpleName() + this.params.toString());
    }

    @Override
    protected String getInterfaceName() {
        return InterfaceConstant.MAINTAIN_LIST;
    }

    @Override
    protected RequestMethod getRequestMethod() {
        return RequestMethod.GET;
    }
}
