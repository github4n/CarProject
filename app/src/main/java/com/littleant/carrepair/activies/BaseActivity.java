package com.littleant.carrepair.activies;

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
    private TextView mTitle, mOptionContent;
    private ImageView backButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        mTitle = findViewById(R.id.header_title);
        mTitle.setText(getTitleId());

        mOptionContent = findViewById(R.id.header_option_content);
        if(getOptionStringId() != 0) {
            mOptionContent.setText(getOptionStringId());
        }

        backButton = findViewById(R.id.title_back);
        if(!showBackButton()) {
            backButton.setVisibility(View.INVISIBLE);
        }
    }

    protected abstract int getLayoutId();
    protected abstract int getTitleId();

    protected int getOptionStringId() {
        return 0;
    }
    protected boolean showBackButton() {
        return true;
    }
}
