package com.littleant.carrepair.activies.order;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.littleant.carrepair.R;
import com.littleant.carrepair.activies.BaseActivity;
import com.littleant.carrepair.request.bean.maintain.MaintainOrderListBean;

import static com.littleant.carrepair.activies.order.MyOrderActivity.ORDER_INFO;

public class ServiceOrderDetailActivity extends BaseActivity {

    public static final String TYPE_UPKEEP = "upkeep";
    public static final String PAY_MAINTAIN = "maintain";

    private MaintainOrderListBean.OrderInfo orderInfo;
    private TextView asod_tv_title, asod_tv_state;
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

    private void requestMaintainOrderDetail() {

    }

    @Override
    protected void init() {
        super.init();
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

    }
}
