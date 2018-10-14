package com.littleant.carrepair.pay.wechat;

import android.content.Context;
import android.text.TextUtils;

import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;

public class WechatPay {
    private Context mContext;
    private IWXAPI msgApi;
    private static final String WECHAT_APPID = "wx43bdade617c80760";


    public WechatPay(Context mContext) {
        this.mContext = mContext;
        msgApi = WXAPIFactory.createWXAPI(mContext, null);
        // 将该app注册到微信
        msgApi.registerApp(WECHAT_APPID);
    }

    /*
    {\"appid\": \"wx43bdade617c80760\",
     \"partnerid\": \"1514215301\",
      \"prepayid\": \"wx14153418098392bef076efdc1652701153\",
       \"package\": \"Sign=WXPay\",
        \"noncestr\": \"oxWL8DUzQy7QmhSBPQvKYA0GG5eSTpPJ\",
         \"timestamp\": \"1539502458\",
          \"sign\": \"220672BF74920EBF51C6D500A5EAF152\"}
     */
    public void startPay(String json) {
        if(TextUtils.isEmpty(json)) {
            return;
        }
        try {
            JSONObject info = new JSONObject(json);
            PayReq request = new PayReq();
            request.appId = info.optString("appid");
            request.partnerId = info.optString("partnerid");
            request.prepayId= info.optString("prepayid");
            request.packageValue = info.optString("package");
            request.nonceStr= info.optString("noncestr");
            request.timeStamp= info.optString("timestamp");
            request.sign= info.optString("sign");
            msgApi.sendReq(request);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
