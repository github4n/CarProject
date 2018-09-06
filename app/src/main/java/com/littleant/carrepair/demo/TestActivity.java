package com.littleant.carrepair.demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.littleant.carrepair.R;
import com.littleant.carrepair.activies.address.AddAddressActivity;
import com.littleant.carrepair.activies.car.AddCarActivity;
import com.littleant.carrepair.activies.order.AllOrderActivity;
import com.littleant.carrepair.activies.annualcheck.AnnualCheckActivity;
import com.littleant.carrepair.activies.annualcheck.AnnualCheckFillInfoActivity;
import com.littleant.carrepair.activies.annualcheck.CheckReturnCarActivity;
import com.littleant.carrepair.activies.info.InformationActivity;
import com.littleant.carrepair.activies.login.LoginActivity;
import com.littleant.carrepair.activies.main.MainActivity;
import com.littleant.carrepair.activies.map.MapActivity;
import com.littleant.carrepair.activies.address.MyAddressActivity;
import com.littleant.carrepair.activies.car.MyCarActivity;
import com.littleant.carrepair.activies.shopping.ShoppingOrderDetailActivity;
import com.littleant.carrepair.activies.order.OrderPageActivity;
import com.littleant.carrepair.activies.annualcheck.PickCarActivity;
import com.littleant.carrepair.activies.repair.RepairActivity;
import com.littleant.carrepair.activies.repair.RepairRecordActivity;
import com.littleant.carrepair.activies.repair.RepairStationActivity;
import com.littleant.carrepair.activies.shopping.ShoppingActivity;
import com.littleant.carrepair.activies.shopping.ShoppingCarActivity;
import com.littleant.carrepair.activies.annualcheck.StartCheckActivity;
import com.mh.core.tools.MHLogUtil;

public class TestActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

//        MHLogUtil.enableDebug(true);
//        MHLogUtil.enableInfo(true);
        MHLogUtil.enableLogLevel(Log.VERBOSE);

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
        findViewById(R.id.info).setOnClickListener(this);
        findViewById(R.id.service).setOnClickListener(this);
        findViewById(R.id.annualChack).setOnClickListener(this);
        findViewById(R.id.fillinfo).setOnClickListener(this);
        findViewById(R.id.pickcar).setOnClickListener(this);
        findViewById(R.id.startcheck).setOnClickListener(this);
        findViewById(R.id.returncar).setOnClickListener(this);
        findViewById(R.id.map).setOnClickListener(this);
        findViewById(R.id.date).setOnClickListener(this);

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
                c = ShoppingOrderDetailActivity.class;
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

            case R.id.info:
                c = InformationActivity.class;
                break;

            case R.id.service:
                c = ServiceMainActivity.class;
                break;

            case R.id.annualChack:
                c = AnnualCheckActivity.class;
                break;

            case R.id.fillinfo:
                c = AnnualCheckFillInfoActivity.class;
                break;

            case R.id.pickcar:
                c = PickCarActivity.class;
                break;

            case R.id.startcheck:
                c = StartCheckActivity.class;
                break;

            case R.id.returncar:
                c = CheckReturnCarActivity.class;
                break;

            case R.id.map:
                c = MapActivity.class;
                break;

            case R.id.date:
                c = DateActivity.class;
                break;
        }
        Intent i = new Intent(this, c);
        this.startActivity(i);
    }
}
