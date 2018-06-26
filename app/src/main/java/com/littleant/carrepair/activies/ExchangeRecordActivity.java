package com.littleant.carrepair.activies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.littleant.carrepair.R;

public class ExchangeRecordActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_exchange_record;
    }

    @Override
    protected int getTitleId() {
        return R.string.text_remaining_sum_list;
    }
}
