package com.littleant.carrepair.activies.order;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.littleant.carrepair.R;
import com.littleant.carrepair.activies.BaseActivity;
import com.littleant.carrepair.request.bean.BaseResponseBean;
import com.littleant.carrepair.request.bean.login.LoginBean;
import com.littleant.carrepair.request.bean.maintain.MaintainOrderDetailBean;
import com.littleant.carrepair.request.bean.maintain.MaintainOrderListBean;
import com.littleant.carrepair.request.constant.ParamsConstant;
import com.littleant.carrepair.request.excute.maintain.maintain.MaintainQueryOneCmd;
import com.littleant.carrepair.utils.ProjectUtil;
import com.mh.core.task.MHCommandCallBack;
import com.mh.core.task.MHCommandExecute;
import com.mh.core.task.command.abstracts.MHCommand;
import com.mh.core.tools.MHToast;

import static com.littleant.carrepair.activies.order.MyOrderActivity.ORDER_INFO;

public class ServiceOrderDetailActivity extends BaseActivity {

    public static final String TYPE_UPKEEP = "upkeep";
    public static final String PAY_MAINTAIN = "maintain";
    private MaintainOrderDetailBean.MaintainOrderDetail data;

    private MaintainOrderListBean.OrderInfo orderInfo;
    private TextView asod_tv_title, asod_tv_state, asod_tv_modify;
    //维修修改的项目部分,维修照片部分
    private LinearLayout asod_ll_detail, asod_ll_pic;
    //支付和删除订单部分
    private View asod_cl_buttons;
    //删除订单和支付按钮
    private TextView asod_btn_delete, asod_btn_pay;
    //订单类型、交易单号、订单单号
    private TextView asod_order_type, asod_exchange_id, asod_order_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            orderInfo = (MaintainOrderListBean.OrderInfo) extras.getSerializable(ORDER_INFO);
            if(orderInfo == null) {
                finish();
                return;
            } else {
                if(TYPE_UPKEEP.equals(orderInfo.getType())) {

                } else if(PAY_MAINTAIN.equals(orderInfo.getType())) {
                    requestMaintainOrderDetail();
                }
            }

        }
    }

    @Override
    protected void init() {
        super.init();
        asod_tv_title = findViewById(R.id.asod_tv_title);
        asod_tv_state = findViewById(R.id.asod_tv_state);
        asod_ll_detail = findViewById(R.id.asod_ll_detail);
        asod_ll_pic = findViewById(R.id.asod_ll_pic);
        asod_cl_buttons = findViewById(R.id.asod_cl_buttons);
        asod_order_type = findViewById(R.id.asod_order_type);
        asod_exchange_id = findViewById(R.id.asod_exchange_id);
        asod_order_id = findViewById(R.id.asod_order_id);

        asod_btn_delete = findViewById(R.id.asod_btn_delete);
        asod_btn_delete.setOnClickListener(this);

        asod_btn_pay = findViewById(R.id.asod_btn_pay);
        asod_btn_pay.setOnClickListener(this);

        asod_tv_modify = findViewById(R.id.asod_tv_modify);
        asod_tv_modify.setOnClickListener(this);
    }

    private void requestMaintainOrderDetail() {
        MaintainQueryOneCmd queryOneCmd = new MaintainQueryOneCmd(mContext, orderInfo.getId());
        queryOneCmd.setCallback(new MHCommandCallBack() {
            @Override
            public void cmdCallBack(MHCommand command) {
                if (command != null) {
                    Log.i("response", command.getResponse());
                    BaseResponseBean responseBean = ProjectUtil.getBaseResponseBean(command.getResponse(), BaseResponseBean.class);
                    if(responseBean != null && ParamsConstant.REAPONSE_CODE_SUCCESS == responseBean.getCode()) {
                        MaintainOrderDetailBean orderDetailBean = ProjectUtil.getBaseResponseBean(command.getResponse(), MaintainOrderDetailBean.class);
                        if(orderDetailBean != null) {
                            data = orderDetailBean.getData();
                            showInfo(data);
                        }
                    } else if(responseBean != null && !TextUtils.isEmpty(responseBean.getMsg())) {
                        MHToast.showS(mContext, responseBean.getMsg());
                    }
                } else {
                    MHToast.showS(mContext, R.string.request_fail);
                }
            }
        });
        MHCommandExecute.getInstance().asynExecute(mContext, queryOneCmd);
    }

    private void showInfo(MaintainOrderDetailBean.MaintainOrderDetail data) {
        asod_tv_title.setText(data.getName());
        switch (orderInfo.getState()) {
            case 0: //等待接单
                asod_tv_state.setText("等待接单");
                asod_tv_state.setTextColor(getResources().getColor(R.color.color_not_pay));
                break;

            case 1:
                asod_tv_state.setText("未支付");
                asod_tv_state.setTextColor(getResources().getColor(R.color.color_not_pay));
                break;

            case 2:
                asod_tv_state.setText("等待服务");
                asod_tv_state.setTextColor(getResources().getColor(R.color.color_wait_service));
                break;

            case 3:
                asod_tv_state.setText("服务中");
                asod_tv_state.setTextColor(getResources().getColor(R.color.color_service_ing));
                break;

            case 4:
                asod_tv_state.setText("服务完成");
                asod_tv_state.setTextColor(getResources().getColor(R.color.color_service_done));
                break;
        }
        asod_ll_detail = findViewById(R.id.asod_ll_detail); //显示维修项目
        asod_ll_pic = findViewById(R.id.asod_ll_pic);
        asod_cl_buttons = findViewById(R.id.asod_cl_buttons);
        asod_order_type.setText(data.getOrder_type());
        asod_exchange_id.setText(data.getDeal_id());
        asod_order_id.setText(data.getOrder_id());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_service_order_detail;
    }

    @Override
    protected int getTitleId() {
        return R.string.text_order_detail;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.asod_btn_delete:

                break;

            case R.id.asod_btn_pay:

                break;

            case R.id.asod_tv_modify:

                break;
        }
    }
}
