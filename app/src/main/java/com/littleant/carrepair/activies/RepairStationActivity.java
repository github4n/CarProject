package com.littleant.carrepair.activies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.littleant.carrepair.R;

/**
 * 维修点
 */
public class RepairStationActivity extends AppCompatActivity {

    private ImageView rs_iv_back, rs_iv_like;
    private TextView rs_btn_navi, rs_btn_contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repair_station);

        rs_iv_back = findViewById(R.id.rs_iv_back);
        rs_iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RepairStationActivity.this.finish();
            }
        });

        rs_iv_like = findViewById(R.id.rs_iv_like);
        rs_iv_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        rs_btn_navi = findViewById(R.id.rs_btn_navi);
        rs_btn_navi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        rs_btn_contact = findViewById(R.id.rs_btn_contact);
        rs_btn_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }

}
