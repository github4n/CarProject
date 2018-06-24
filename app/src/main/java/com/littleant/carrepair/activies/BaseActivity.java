package com.littleant.carrepair.activies;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.littleant.carrepair.R;

public abstract class BaseActivity extends AppCompatActivity {
    private TextView title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        title = findViewById(R.id.header_title);
        title.setText(getTitleId());
    }

    protected abstract int getLayoutId();
    protected abstract int getTitleId();
}
