package com.littleant.carrepair.request.excute;

import android.content.Context;

import com.littleant.carrepair.R;
import com.littleant.carrepair.request.utils.RequestHelper;
import com.mh.core.task.command.abstracts.MHCommand;
import com.mh.core.tools.MHLogUtil;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseRequestCmd extends MHCommand {
    protected String result;
    protected String requestUrl;
    private Context mContext;
    Map<String, String> params = new HashMap<>();

    protected BaseRequestCmd(Context context){
        this.mContext = context;
    }

    @Override
    public String getResponse() {
        return result;
    }

    @Override
    public void execute() throws Exception {
        if ((this.params != null) && (!(this.params.isEmpty())))
        {
            requestUrl = mContext.getResources().getString(R.string.main_domain_url);
            this.requestUrl += getInterfaceName();
            MHLogUtil.logI("requesturl:" + this.requestUrl);
            this.result = RequestHelper.sendRequest(this.params, this.requestUrl);
        }
    }

    protected abstract String getInterfaceName();
}
