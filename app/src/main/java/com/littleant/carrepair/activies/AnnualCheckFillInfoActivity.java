package com.littleant.carrepair.activies;

import android.os.Bundle;

import com.littleant.carrepair.R;

public class AnnualCheckFillInfoActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_annual_check_fill_info;
    }

    @Override
    protected int getTitleId() {
        return R.string.text_fill_info;
    }
}
