package com.littleant.carrepair.activies;

import android.os.Bundle;

import com.littleant.carrepair.R;

/**
 * 送车服务
 */
public class SendCarServiceActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_send_car_service;
    }

    @Override
    protected int getTitleId() {
        return R.string.text_send_car_service;
    }
}