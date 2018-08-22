package com.littleant.carrepair.request.impl;

import android.text.TextUtils;

import com.littleant.carrepair.request.IRequest;
import com.littleant.carrepair.request.bean.ListenerParameters;
import com.mh.core.exception.MHException;
import com.mh.core.http.HttpRequest;
import com.mh.core.http.HttpResponse;
import com.mh.core.tools.MHLogUtil;
import com.mh.core.tools.MHStringUtil;

import java.util.Map;

public class MHBaseRequestOperation implements IRequest {

    @Override
    public String mhRequestServer() throws MHException {
        return null;
    }

    protected ListenerParameters parameters = null;
    protected String preferredUrl = null;

    String requestCompleteUrl;

    public String getRequestCompleteUrl() {
        return requestCompleteUrl;
    }

    protected String checkUrl(String url){

        if (TextUtils.isEmpty(url)){
            return null;
        }
        if (!url.endsWith("/")) {
            url = url + "/";
        }
        return url;
    }

    protected String emptyReturn() {
        MHLogUtil.logI("Method efunLogin params userName or userPwd is null");
        return "{\"message\":\"傳遞參數異常\",\"code\":\"params_error\"}";
    }

    public String doRequest(String efunDomainSite,Map<String, String> requestParams){
        String efunResponse = "";
        if (MHStringUtil.isNotEmpty(preferredUrl)) {
            preferredUrl = preferredUrl + efunDomainSite;
            MHLogUtil.logD("preferredUrl:" + preferredUrl);
//			efunResponse = HttpRequest.post(preferredUrl, requestParams);
            HttpRequest httpRequest = new HttpRequest();
            HttpResponse hr = httpRequest.postReuqest(preferredUrl, requestParams);
            efunResponse = hr.getResult();
            requestCompleteUrl = hr.getRequestCompleteUrl();
            MHLogUtil.logD("preferredUrl response: " + efunResponse);
        }
        return efunResponse;
    }

    /**
     * @return the parameters
     */
    public ListenerParameters getParameters() {
        return parameters;
    }

    /**
     * @param parameters the parameters to set
     */
    public void setParameters(ListenerParameters parameters) {
        this.parameters = parameters;
    }

    public String getPreferredUrl() {
        return preferredUrl;
    }

    public void setPreferredUrl(String preferredUrl) {
        preferredUrl = checkUrl(preferredUrl);
        this.preferredUrl = preferredUrl;
    }
}
