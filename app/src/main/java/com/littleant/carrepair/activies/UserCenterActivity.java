package com.littleant.carrepair.activies;

import android.os.Bundle;

import com.littleant.carrepair.R;

/**
 * 个人中心
 */
public class UserCenterActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_center;
    }

    @Override
    protected int getTitleId() {
        return R.string.text_user_center;
    }
}
