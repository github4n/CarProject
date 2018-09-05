package com.littleant.carrepair.activies.order;

import android.os.Bundle;

import com.littleant.carrepair.R;
import com.littleant.carrepair.activies.BaseActivity;

/**
 * 订单详情
 */
public class OrderDetailActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_detail;
    }

    @Override
    protected int getTitleId() {
        return R.string.text_order_detail;
    }
}
