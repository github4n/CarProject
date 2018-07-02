package com.littleant.carrepair.dome;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.littleant.carrepair.R;
import com.littleant.carrepair.activies.AddAddressActivity;
import com.littleant.carrepair.activies.AddCarActivity;
import com.littleant.carrepair.activies.AllOrderActivity;
import com.littleant.carrepair.activies.ExchangeRecordActivity;
import com.littleant.carrepair.activies.LoginActivity;
import com.littleant.carrepair.activies.MainActivity;
import com.littleant.carrepair.activies.MyAddressActivity;
import com.littleant.carrepair.activies.MyCarActivity;
import com.littleant.carrepair.activies.OrderDetailActivity;
import com.littleant.carrepair.activies.OrderPageActivity;
import com.littleant.carrepair.activies.RemainingSumActivity;
import com.littleant.carrepair.activies.RepairActivity;
import com.littleant.carrepair.activies.RepairRecordActivity;
import com.littleant.carrepair.activies.RepairStationActivity;
import com.littleant.carrepair.activies.SendCarAddressActivity;
import com.littleant.carrepair.activies.SendCarServiceActivity;
import com.littleant.carrepair.activies.ShoppingActivity;
import com.littleant.carrepair.activies.ShoppingCarActivity;
import com.littleant.carrepair.activies.UserCenterActivity;

public class TestActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        findViewById(R.id.login).setOnClickListener(this);
        findViewById(R.id.main).setOnClickListener(this);
        findViewById(R.id.order_detail).setOnClickListener(this);
        findViewById(R.id.all_order).setOnClickListener(this);
        findViewById(R.id.addcar).setOnClickListener(this);
        findViewById(R.id.sendcaraddress).setOnClickListener(this);
        findViewById(R.id.addaddress).setOnClickListener(this);
        findViewById(R.id.sendcarservice).setOnClickListener(this);
        findViewById(R.id.remainingsum).setOnClickListener(this);
        findViewById(R.id.moneylist).setOnClickListener(this);
        findViewById(R.id.order_page).setOnClickListener(this);
        findViewById(R.id.user_center).setOnClickListener(this);
        findViewById(R.id.shopping_car).setOnClickListener(this);
        findViewById(R.id.shopping).setOnClickListener(this);
        findViewById(R.id.repair).setOnClickListener(this);
        findViewById(R.id.mycar).setOnClickListener(this);
        findViewById(R.id.myaddress).setOnClickListener(this);
        findViewById(R.id.repairrecord).setOnClickListener(this);
        findViewById(R.id.station).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Class c = null;
        switch (view.getId()) {
            case R.id.login:
                c = LoginActivity.class;
                break;

            case R.id.main:
                c = MainActivity.class;
                break;

            case R.id.order_detail:
                c = OrderDetailActivity.class;
                break;

            case R.id.all_order:
                c = AllOrderActivity.class;
                break;

            case R.id.addcar:
                c = AddCarActivity.class;
                break;

            case R.id.sendcaraddress:
                c = SendCarAddressActivity.class;
                break;

            case R.id.addaddress:
                c = AddAddressActivity.class;
                break;

            case R.id.sendcarservice:
                c = SendCarServiceActivity.class;
                break;

            case R.id.remainingsum:
                c = RemainingSumActivity.class;
                break;

            case R.id.moneylist:
                c = ExchangeRecordActivity.class;
                break;

            case R.id.order_page:
                c = OrderPageActivity.class;
                break;

            case R.id.user_center:
                c = UserCenterActivity.class;
                break;

            case R.id.shopping_car:
                c = ShoppingCarActivity.class;
                break;

            case R.id.shopping:
                c = ShoppingActivity.class;
                break;

            case R.id.repair:
                c = RepairActivity.class;
                break;

            case R.id.mycar:
                c = MyCarActivity.class;
                break;

            case R.id.myaddress:
                c = MyAddressActivity.class;
                break;

            case R.id.repairrecord:
                c = RepairRecordActivity.class;
                break;

            case R.id.station:
                c = RepairStationActivity.class;
                break;
        }
        Intent i = new Intent(this, c);
        this.startActivity(i);
    }
}