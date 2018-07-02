package com.littleant.carrepair.activies;

import android.os.Bundle;

import com.littleant.carrepair.R;

/**
 * 充值记录
 */
public class RemainingSumActivity extends BaseActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_remaining_sum;
    }

    @Override
    protected int getTitleId() {
        return R.string.text_remaining_sum;
    }

    @Override
    protected int getOptionStringId() {
        return R.string.text_recharge_record;
    }
}
