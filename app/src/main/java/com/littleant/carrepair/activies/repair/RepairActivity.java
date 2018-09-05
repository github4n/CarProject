package com.littleant.carrepair.activies.repair;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.littleant.carrepair.R;
import com.littleant.carrepair.activies.BaseActivity;

/**
 * 维修保养
 */
public class RepairActivity extends BaseActivity implements View.OnClickListener {

    private Button r_btn_confrm;
    private TextView r_tv_location_display, r_tv_time_display;
    private EditText r_et_description;
    private ImageView r_btn_add_pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void init() {
        super.init();
        r_btn_confrm = findViewById(R.id.r_btn_confrm);
        r_btn_confrm.setOnClickListener(this);

        r_tv_location_display = findViewById(R.id.r_tv_location_display);
        r_tv_location_display.setOnClickListener(this);

        r_tv_time_display = findViewById(R.id.r_tv_time_display);
        r_tv_time_display.setOnClickListener(this);

        r_btn_add_pic = findViewById(R.id.r_btn_add_pic);
        r_btn_add_pic.setOnClickListener(this);

        r_et_description = findViewById(R.id.r_et_description);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_repair;
    }

    @Override
    protected int getTitleId() {
        return R.string.text_repair;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.r_btn_confrm:
                Intent intent = new Intent(mContext, RepairRecordActivity.class);
                RepairActivity.this.startActivity(intent);
                break;

            case R.id.r_tv_location_display:

                break;

            case R.id.r_tv_time_display:

                break;
        }
    }
}
