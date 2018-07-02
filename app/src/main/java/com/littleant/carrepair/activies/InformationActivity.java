package com.littleant.carrepair.activies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.littleant.carrepair.R;

public class InformationActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_information;
    }

    @Override
    protected int getTitleId() {
        return R.string.text_info_title;
    }
}
