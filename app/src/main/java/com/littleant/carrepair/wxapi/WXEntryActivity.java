package com.littleant.carrepair.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.littleant.carrepair.R;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;


public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final int TIMELINE_SUPPORTED_VERSION = 0x21020001;

    private static final String WECHAT_APPID = "wx43bdade617c80760";
    public static final String PAY_PARAMS = "pay_params";

    private IWXAPI api;
    private String params;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            params = extras.getString(PAY_PARAMS);
        } else {
            finish();
        }

        api = WXAPIFactory.createWXAPI(this, null);
        api.registerApp(WECHAT_APPID);
        startPay(params);
        try {
            boolean b = api.handleIntent(getIntent(), this);
            if (!b) {
                finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        int result = 0;
        Log.i("onResp", "pay code : " + resp.errCode);
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                setResult(RESULT_OK);
                finish();
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                result = R.string.errcode_cancel;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                result = R.string.errcode_deny;
            case BaseResp.ErrCode.ERR_UNSUPPORT:
                result = R.string.errcode_unsupported;
            default:
                result = R.string.errcode_unknown;
        }
        Toast.makeText(this, result, Toast.LENGTH_LONG).show();
        finish();
    }
}