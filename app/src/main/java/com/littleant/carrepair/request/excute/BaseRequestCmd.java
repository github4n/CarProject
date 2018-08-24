package com.littleant.carrepair.request.excute;

import android.content.Context;
import android.text.TextUtils;

import com.littleant.carrepair.R;
import com.littleant.carrepair.request.constant.InterfaceConstant;
import com.littleant.carrepair.request.constant.ParamsConstant;
import com.littleant.carrepair.request.utils.DataHelper;
import com.littleant.carrepair.request.utils.RequestHelper;
import com.mh.core.db.MHDatabase;
import com.mh.core.task.command.abstracts.MHCommand;
import com.mh.core.tools.MHLogUtil;
import com.mh.core.tools.MHStringUtil;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseRequestCmd extends MHCommand {
    protected String result;
    protected String requestUrl;
    private Context mContext;
    protected Map<String, String> params = new HashMap<>();
    protected String user_id, timestamp, token, sign;

    protected BaseRequestCmd(Context context){
        this.mContext = context;
        user_id = DataHelper.getUserId(context);
        timestamp = System.currentTimeMillis() + "";
        token = DataHelper.getToken(context);
        sign = TextUtils.isEmpty(token) ? "" : MHStringUtil.toMd5(token + timestamp, false);

        params.put(ParamsConstant.USER_ID, user_id);
        params.put(ParamsConstant.TIMESTAMP, timestamp);
        params.put(ParamsConstant.SIGN, sign);
        params.put(ParamsConstant.VERSION, ParamsConstant.API_VERSION);
        params.put(ParamsConstant.SYSTEM, ParamsConstant.SYSTEM_ANDROID);
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
            switch (getRequestMethod()) {
                case GET:
                    this.result = RequestHelper.getRequest(this.params, this.requestUrl);
                    break;

                case POST:
                    this.result = RequestHelper.postRequest(this.params, this.requestUrl);
                    break;
            }
        }
    }

    protected abstract String getInterfaceName();
    protected abstract RequestMethod getRequestMethod();

    protected enum RequestMethod {
        GET,
        POST
    }
}
