package com.littleant.carrepair.activies;

import android.os.Bundle;

import com.littleant.carrepair.R;

public class ServiceMainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_service_main;
    }

    @Override
    protected int getTitleId() {
        return R.string.text_service;
    }

    @Override
    protected boolean showBackButton() {
        return false;
    }
}
