package com.littleant.carrepair.request.utils;

import com.mh.core.http.HttpRequest;

import java.util.Map;

public class RequestHelper {

    public static String sendRequest(Map<String, String> params, String preUrl) {
        if (params == null || params.isEmpty()) {
            return null;
        }
        return HttpRequest.post(preUrl, params);
    }

}
