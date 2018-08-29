package com.littleant.carrepair.activies;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.littleant.carrepair.R;

public class SettingActivity extends BaseActivity implements View.OnClickListener{

    private Button setting_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void init() {
        super.init();
        setting_logout = findViewById(R.id.setting_logout);
        setting_logout.setOnClickListener(this);

        mOptionText.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected int getTitleId() {
        return R.string.text_setting;
    }

    @Override
    protected int getOptionStringId() {
        return R.string.text_setting_save;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.setting_logout:
                SettingActivity.this.setResult(RESULT_OK);
                SettingActivity.this.finish();
                break;

            case R.id.header_option_text: //保存信息

                break;
        }
    }
}
