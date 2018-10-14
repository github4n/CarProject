package com.littleant.carrepair.activies.pay;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.littleant.carrepair.R;
import com.littleant.carrepair.activies.BaseActivity;
import com.littleant.carrepair.activies.login.LoginActivity;
import com.littleant.carrepair.activies.main.MainActivity;
import com.littleant.carrepair.pay.wechat.WechatPay;
import com.littleant.carrepair.request.bean.LoginBean;
import com.littleant.carrepair.request.bean.MaintainOrderListBean;
import com.littleant.carrepair.request.bean.pay.PayInfoBean;
import com.littleant.carrepair.request.constant.ParamsConstant;
import com.littleant.carrepair.request.excute.maintain.maintain.MaintainMethodCmd;
import com.littleant.carrepair.request.excute.maintain.upkeep.UpkeepMethodCmd;
import com.littleant.carrepair.request.utils.DataHelper;
import com.littleant.carrepair.utils.ProjectUtil;
import com.littleant.carrepair.wxapi.WXEntryActivity;
import com.mh.core.task.MHCommandCallBack;
import com.mh.core.task.MHCommandExecute;
import com.mh.core.task.command.abstracts.MHCommand;
import com.mh.core.tools.MHToast;

import cn.jpush.android.api.JPushInterface;

import static com.littleant.carrepair.activies.order.MyOrderActivity.ORDER_INFO;
import static com.littleant.carrepair.wxapi.WXEntryActivity.PAY_PARAMS;

public class PaymentActivity extends BaseActivity {
    private RadioButton ap_radioButton, ap_radioButton2;
    private TextView ap_rest_time, ap_tv_money, ap_orderid, ap_pay;
    public static final String ORDER_ID = "order_id";
    public static final String MONEY = "money";
    private String orderId;
    private float money;
    public static final String PAYMENT_FROM = "from";
    private String orderType;
    private ParamsConstant.PayChannel payChannel;
    private int score;
    private int id;
    private PayInfoBean.PayInfo payInfo;
    private MaintainOrderListBean.OrderInfo orderInfo;
    private static final int REQUEST_WECHAT_PAY = 10;

    /*
    支付流程
    1.order_status查订单信息
    2.pay发起支付
    3.order_status查交易状态
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            orderType = extras.getString(PAYMENT_FROM);
            if(ParamsConstant.ORDER_UPKEEP.equals(orderType) || ParamsConstant.ORDER_MAINTAIN.equals(orderType)) {
                orderInfo = (MaintainOrderListBean.OrderInfo) extras.getSerializable(ORDER_INFO);
                if(orderInfo == null) {
                    finish();
                } else {
//                    if(ParamsConstant.ORDER_UPKEEP.equals(orderType)) {
//                        MHToast.showS(mContext, "保养");
//                        requestUpkeepMethod(ParamsConstant.MethodStatus.ORDER_STATUS);
//                    } else if(ParamsConstant.ORDER_MAINTAIN.equals(orderType)) {
//                        MHToast.showS(mContext, "维修");
//                        requestMaintainMethod(ParamsConstant.MethodStatus.ORDER_STATUS);
//                    }
                    requestMethod(ParamsConstant.MethodStatus.ORDER_STATUS);
                }
            }
        }
    }

    private MHCommand getCmd(ParamsConstant.MethodStatus methodStatus) {
        id = orderInfo.getId();
        if(ParamsConstant.ORDER_UPKEEP.equals(orderType)) {
            return new UpkeepMethodCmd(mContext, id, methodStatus, score, payChannel);
        } else if(ParamsConstant.ORDER_MAINTAIN.equals(orderType)) {
            return new MaintainMethodCmd(mContext, id, methodStatus, score, payChannel, "");
        }
        return null;
    }

    private void requestMethod(final ParamsConstant.MethodStatus methodStatus) {
        id = orderInfo.getId();
        MHCommand cmd = getCmd(methodStatus);
        cmd.setCallback(new MHCommandCallBack() {
            @Override
            public void cmdCallBack(MHCommand command) {
                if (command != null) {
                    Log.i("response", command.getResponse());
                    PayInfoBean responseBean = ProjectUtil.getBaseResponseBean(command.getResponse(), PayInfoBean.class);
                    if(responseBean != null && ParamsConstant.REAPONSE_CODE_SUCCESS == responseBean.getCode()) {
                        payInfo = responseBean.getData();
                        switch (methodStatus) {
                            case ORDER_STATUS:
                                showDetail(payInfo);
                                break;

                            case PAY:
                                startPay(payInfo.getParams());
                                break;
                        }
                    } else if(responseBean != null && !TextUtils.isEmpty(responseBean.getMsg())) {
                        MHToast.showS(mContext, responseBean.getMsg());
                    }
                } else {
                    MHToast.showS(mContext, R.string.request_fail);
                }
            }
        });
        MHCommandExecute.getInstance().asynExecute(mContext, cmd);
    }

    private void requestMaintainMethod(ParamsConstant.MethodStatus methodStatus) {
        id = orderInfo.getId();
        MaintainMethodCmd maintainMethodCmd = new MaintainMethodCmd(mContext, id, methodStatus, score, payChannel, "");
        maintainMethodCmd.setCallback(new MHCommandCallBack() {
            @Override
            public void cmdCallBack(MHCommand command) {
                if (command != null) {
                    Log.i("response", command.getResponse());
                    PayInfoBean responseBean = ProjectUtil.getBaseResponseBean(command.getResponse(), PayInfoBean.class);
                    if(responseBean != null && ParamsConstant.REAPONSE_CODE_SUCCESS == responseBean.getCode()) {
                        payInfo = responseBean.getData();
                        showDetail(payInfo);
                    } else if(responseBean != null && !TextUtils.isEmpty(responseBean.getMsg())) {
                        MHToast.showS(mContext, responseBean.getMsg());
                    }
                } else {
                    MHToast.showS(mContext, R.string.request_fail);
                }
            }
        });
        MHCommandExecute.getInstance().asynExecute(mContext, maintainMethodCmd);
    }

    private void requestUpkeepMethod(ParamsConstant.MethodStatus methodStatus) {
        id = orderInfo.getId();
        UpkeepMethodCmd upkeepMethodCmd = new UpkeepMethodCmd(mContext, id, methodStatus, score, payChannel);
        upkeepMethodCmd.setCallback(new MHCommandCallBack() {
            @Override
            public void cmdCallBack(MHCommand command) {
                if (command != null) {
                    Log.i("response", command.getResponse());
                    PayInfoBean responseBean = ProjectUtil.getBaseResponseBean(command.getResponse(), PayInfoBean.class);
                    if(responseBean != null && ParamsConstant.REAPONSE_CODE_SUCCESS == responseBean.getCode()) {
                        payInfo = responseBean.getData();
                        showDetail(payInfo);
                    } else if(responseBean != null && !TextUtils.isEmpty(responseBean.getMsg())) {
                        MHToast.showS(mContext, responseBean.getMsg());
                    }
                } else {
                    MHToast.showS(mContext, R.string.request_fail);
                }
            }
        });
        MHCommandExecute.getInstance().asynExecute(mContext, upkeepMethodCmd);
    }

    private void showDetail(PayInfoBean.PayInfo payInfo) {
        if(payInfo != null) {
            ap_tv_money.setText(DataHelper.displayPrice(mContext, payInfo.getPrice()));
            ap_orderid.setText(payInfo.getOrder_code());
        }
    }

    @Override
    protected void init() {
        super.init();

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            orderId = extras.getString(ORDER_ID);
            money = extras.getFloat(MONEY);
        }

        ap_rest_time = findViewById(R.id.ap_rest_time);
        ap_tv_money = findViewById(R.id.ap_tv_money);
        ap_orderid = findViewById(R.id.ap_orderid);
        ap_pay = findViewById(R.id.ap_pay);
        ap_pay.setOnClickListener(this);

        //微信
        ap_radioButton = findViewById(R.id.ap_radioButton);
        ap_radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    ap_radioButton2.setChecked(false);
                }
            }
        });

        //支付宝
        ap_radioButton2 = findViewById(R.id.ap_radioButton2);
        ap_radioButton2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    ap_radioButton.setChecked(false);
                }
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_payment;
    }

    @Override
    protected int getTitleId() {
        return R.string.text_payment;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ap_pay:
                if(ap_radioButton.isChecked()) {
                    payChannel = ParamsConstant.PayChannel.WECHAT;
                } else if(ap_radioButton2.isChecked()) {
                    payChannel = ParamsConstant.PayChannel.ALI;
                } else {
                    MHToast.showS(mContext, R.string.need_pay_channel);
                    return;
                }

                if(ParamsConstant.ORDER_UPKEEP.equals(orderType)) {
                    requestMethod(ParamsConstant.MethodStatus.PAY);
                } else if(ParamsConstant.ORDER_MAINTAIN.equals(orderType)) {
                    requestMethod(ParamsConstant.MethodStatus.PAY);
                }

                break;
        }
    }

    private void startPay(String params) {
        if(payChannel == ParamsConstant.PayChannel.WECHAT) {
            wePay(params);
        } else if(payChannel == ParamsConstant.PayChannel.ALI) {
            aliPay(params);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_WECHAT_PAY && resultCode == RESULT_OK) {

        }
    }

    private void aliPay(String params) {

    }

    private void wePay(String params) {
        Intent intent = new Intent(mContext, WXEntryActivity.class);
        intent.putExtra(PAY_PARAMS, params);
        startActivityForResult(intent, REQUEST_WECHAT_PAY);
    }
}
