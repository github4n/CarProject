package com.littleant.carrepair.activies.pay;

import android.os.Bundle;
import android.view.View;

import com.littleant.carrepair.R;
import com.littleant.carrepair.activies.BaseActivity;

public class PaymentActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_payment;
    }

    @Override
    protected int getTitleId() {
        return R.string.text_payment;
    }

    @Override
    public void onClick(View v) {

    }
}
