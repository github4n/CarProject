package com.littleant.carrepair.activies.order;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.littleant.carrepair.R;
import com.littleant.carrepair.activies.BaseActivity;
import com.littleant.carrepair.request.bean.maintain.MaintainOrderListBean;

import static com.littleant.carrepair.activies.order.MyOrderActivity.ORDER_INFO;

public class ServiceOrderDetailActivity extends BaseActivity {

    private MaintainOrderListBean.OrderInfo orderInfo;
    private TextView asod_tv_title, asod_tv_state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            orderInfo = (MaintainOrderListBean.OrderInfo) extras.getSerializable(ORDER_INFO);
            if(orderInfo == null) {
                finish();
                return;
            }

        }
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
