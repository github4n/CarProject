package com.littleant.carrepair.activies;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.littleant.carrepair.R;

/**
 * 基类Activity
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected TextView mTitle, mOptionContent, mOptionText;
    protected ImageView backButton;
    protected Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        mContext = this;
        mTitle = findViewById(R.id.header_title);
        if(getTitleId() != 0) {
            mTitle.setText(getTitleId());
        }

        mOptionText = findViewById(R.id.header_option_text);
        if(getOptionStringId() != 0) {
            mOptionText.setText(getOptionStringId());
        }
        mOptionContent = findViewById(R.id.header_option_content);
        if(getOptionBackgroundId() != 0) {
            mOptionContent.setBackgroundResource(getOptionBackgroundId());
        }

        backButton = findViewById(R.id.title_back);
        if(!showBackButton()) {
            backButton.setVisibility(View.INVISIBLE);
        }
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BaseActivity.this.finish();
            }
        });

        init();
    }

    protected abstract int getLayoutId();
    protected abstract int getTitleId();
    protected void init(){}

    protected int getOptionStringId() {
        return 0;
    }
    protected int getOptionBackgroundId() {
        return 0;
    }
    protected boolean showBackButton() {
        return true;
    }
}
