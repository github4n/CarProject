package com.littleant.carrepair.activies.annualcheck;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import com.littleant.carrepair.R;
import com.littleant.carrepair.activies.BaseActivity;

public class OwnStartCheckActivity extends BaseActivity {
    private CheckBox losf_tv_fill, losf_tv_start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        losf_tv_start = findViewById(R.id.losf_tv_start);
        losf_tv_start.setChecked(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_own_start_check;
    }

    @Override
    protected int getTitleId() {
        return R.string.text_start_annual_check;
    }

    @Override
    public void onClick(View v) {

    }
}
