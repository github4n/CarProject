package com.littleant.carrepair.activies.pay;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.littleant.carrepair.R;
import com.littleant.carrepair.activies.BaseActivity;

public class PaymentActivity extends BaseActivity {
    private RadioButton ap_radioButton, ap_radioButton2;
    private TextView ap_rest_time, ap_tv_money, ap_orderid, ap_pay;
    public static final String ORDER_ID = "order_id";
    public static final String MONEY = "money";
    private String orderId;
    private float money;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void init() {
        super.init();

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            orderId = extras.getString(ORDER_ID);
            money = extras.getFloat(MONEY);
        }

        ap_rest_time = findViewById(R.id.ap_rest_time);
        ap_tv_money = findViewById(R.id.ap_tv_money);
        ap_orderid = findViewById(R.id.ap_orderid);
        ap_pay = findViewById(R.id.ap_pay);
        ap_pay.setOnClickListener(this);

        ap_radioButton = findViewById(R.id.ap_radioButton);
        ap_radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    ap_radioButton2.setChecked(false);
                }
            }
        });

        ap_radioButton2 = findViewById(R.id.ap_radioButton2);
        ap_radioButton2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    ap_radioButton.setChecked(false);
                }
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_payment;
    }

    @Override
    protected int getTitleId() {
        return R.string.text_payment;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ap_pay:
                if(ap_radioButton.isChecked()) {
                    wePay();
                } else if(ap_radioButton2.isChecked()) {
                    aliPay();
                }
                break;
        }
    }

    private void aliPay() {

    }

    private void wePay() {

    }
}
