package com.littleant.carrepair.activies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.littleant.carrepair.R;

public class AddCarActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);
    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected int getTitleId() {
        return R.string.text_add_car;
    }
}
