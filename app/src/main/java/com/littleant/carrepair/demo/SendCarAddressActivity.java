package com.littleant.carrepair.demo;

import android.os.Bundle;

import com.littleant.carrepair.R;
import com.littleant.carrepair.activies.BaseActivity;

/**
 * 送车地址
 */
public class SendCarAddressActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_send_car_address;
    }

    @Override
    protected int getTitleId() {
        return R.string.text_send_car_address;
    }
}
