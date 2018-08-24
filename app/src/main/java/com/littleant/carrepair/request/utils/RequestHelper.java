package com.littleant.carrepair.request.utils;

import com.mh.core.http.HttpRequest;

import java.util.Map;

public class RequestHelper {

    public static String postRequest(Map<String, String> params, String url) {
        if (params == null || params.isEmpty()) {
            return null;
        }
        return HttpRequest.post(url, params);
    }

    public static String getRequest(Map<String, String> params, String url) {
        if (params == null || params.isEmpty()) {
            return null;
        }
        return HttpRequest.get(url, params);
    }
}
