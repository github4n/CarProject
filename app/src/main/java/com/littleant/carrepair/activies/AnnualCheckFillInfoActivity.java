package com.littleant.carrepair.activies;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.littleant.carrepair.R;

public class AnnualCheckFillInfoActivity extends BaseActivity {

    private ConstraintLayout acf_package_layout;
    private RadioButton acf_btn_package_a, acf_btn_package_b;
    private TextView acf_package_detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //套餐A
        acf_btn_package_a = findViewById(R.id.acf_btn_package_a);
        //套餐B
        acf_btn_package_b = findViewById(R.id.acf_btn_package_b);
        //套餐A明细布局
        acf_package_detail = findViewById(R.id.acf_package_detail);
        //套餐B明细布局
        acf_package_layout = findViewById(R.id.acf_package_layout);

        acf_btn_package_a.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    acf_package_layout.setVisibility(View.GONE);
                    acf_package_detail.setVisibility(View.VISIBLE);
                }
            }
        });

        acf_btn_package_b.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    acf_package_detail.setVisibility(View.GONE);
                    acf_package_layout.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_annual_check_fill_info;
    }

    @Override
    protected int getTitleId() {
        return R.string.text_fill_info;
    }
}
