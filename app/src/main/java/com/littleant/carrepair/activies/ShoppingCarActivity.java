package com.littleant.carrepair.activies;

import android.os.Bundle;

import com.littleant.carrepair.R;

public class ShoppingCarActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shopping_car;
    }

    @Override
    protected int getTitleId() {
        return R.string.text_shopping_car;
    }
}
