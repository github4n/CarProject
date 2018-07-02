package com.littleant.carrepair.activies;

import android.os.Bundle;

import com.littleant.carrepair.R;

/**
 * 添加车辆
 */
public class AddCarActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_car;
    }

    @Override
    protected int getTitleId() {
        return R.string.text_add_car;
    }
}
