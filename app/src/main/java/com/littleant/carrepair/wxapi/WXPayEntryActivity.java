package com.littleant.carrepair.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;


public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final int TIMELINE_SUPPORTED_VERSION = 0x21020001;

    private static final String WECHAT_APPID = "wx43bdade617c80760";
    public static final String PAY_PARAMS = "pay_params";

    private IWXAPI api;
    private String params;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, null);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            params = extras.getString(PAY_PARAMS);
        } else {
            finish();
        }

        api.registerApp(WECHAT_APPID);
        startPay(params);
    }

    public void startPay(String json) {
        if (TextUtils.isEmpty(json)) {
            return;
        }
        try {
            JSONObject info = new JSONObject(json);
            PayReq request = new PayReq();
            request.appId = info.optString("appid");
            request.partnerId = info.optString("partnerid");
            request.prepayId = info.optString("prepayid");
            request.packageValue = info.optString("package");
            request.nonceStr = info.optString("noncestr");
            request.timeStamp = info.optString("timestamp");
            request.sign = info.optString("sign");
            api.sendReq(request);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
        Log.i("onResp", "pay code : " + resp.errCode);
        if (resp.errCode == BaseResp.ErrCode.ERR_OK) {
            WXPayEntryActivity.this.setResult(RESULT_OK);
        }
        WXPayEntryActivity.this.finish();
    }
}