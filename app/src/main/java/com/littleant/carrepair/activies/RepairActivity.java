package com.littleant.carrepair.activies;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.littleant.carrepair.R;

/**
 * 维修保养
 */
public class RepairActivity extends BaseActivity {

    private Button r_btn_confrm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        r_btn_confrm = findViewById(R.id.r_btn_confrm);
        r_btn_confrm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RepairActivity.this, RepairRecordActivity.class);
                RepairActivity.this.startActivity(intent);
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_repair;
    }

    @Override
    protected int getTitleId() {
        return R.string.text_repair;
    }
}
