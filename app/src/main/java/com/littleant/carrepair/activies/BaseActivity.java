package com.littleant.carrepair.activies;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.littleant.carrepair.R;
import com.littleant.carrepair.utils.ProjectUtil;

/**
 * 基类Activity
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    protected TextView mTitle, mOptionContent, mOptionText;
    protected ImageView backButton;
    protected Context mContext;
//    protected View lh_header;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        mContext = this;

//        lh_header = findViewById(R.id.lh_header3);

        mTitle = findViewById(R.id.header_title);

        mOptionText = findViewById(R.id.header_option_text);

        mOptionContent = findViewById(R.id.header_option_content);

        backButton = findViewById(R.id.title_back);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BaseActivity.this.finish();
            }
        });

        init();

//        if(getBackgroundColor() != 0) {
//            lh_header.setBackgroundColor(getBackgroundColor());
//        }

        if(getTitleId() != 0) {
            mTitle.setText(getTitleId());
        }
        if(getOptionStringId() != 0) {
            mOptionText.setText(getOptionStringId());
        }
        if(getOptionBackgroundId() != 0) {
            mOptionContent.setBackgroundResource(getOptionBackgroundId());
            mOptionContent.setOnClickListener(this);
        }
        if(!showBackButton()) {
            backButton.setVisibility(View.INVISIBLE);
        }
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

    protected int getBackgroundColor() { return 0; }

    protected boolean validateParams(String... params) {
        for(String param : params) {
            if (TextUtils.isEmpty(param)) {
                return false;
            }
        }
        return true;
    }
}
