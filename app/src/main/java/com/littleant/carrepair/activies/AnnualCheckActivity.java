package com.littleant.carrepair.activies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.littleant.carrepair.R;

public class AnnualCheckActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_annual_check;
    }

    @Override
    protected int getTitleId() {
        return R.string.text_annual_check;
    }

    @Override
    protected boolean showBackButton() {
        return false;
    }
}
