package com.littleant.carrepair.activies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.littleant.carrepair.R;

public class SendCarAddressActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_car_address);
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
