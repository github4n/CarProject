package com.littleant.carrepair.activies.annualcheck;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.amap.searchdemo.SelectPlaceActivity;
import com.littleant.carrepair.R;
import com.littleant.carrepair.activies.BaseActivity;
import com.littleant.carrepair.activies.datetime.DateActivity;
import com.littleant.carrepair.activies.pay.PaymentActivity;
import com.littleant.carrepair.activies.repair.RepairActivity;
import com.littleant.carrepair.request.bean.SurveyStationInfo;
import com.littleant.carrepair.request.utils.DataHelper;

import java.util.List;

public class AnnualCheckFillInfoActivity extends BaseFillInfoActivity implements BaseFillInfoActivity.RequestStationListener {

    private ConstraintLayout acf_package_layout;
    private RadioButton acf_btn_package_a, acf_btn_package_b;
    private TextView acf_package_detail, acf_confirm_pay, acf_et_car_type, acf_et_pick_station, acf_tv_date1;
    private TextView acf_et_pick_location;
    private static final int REQUEST_CODE_SELECT_PLACE = 11;//定义请求码常量
    private double selectLat, selectLon;
    private String selectAddress;
//    private String[] carType = new String[]{"汽车1", "汽车2"};
//    private String[] stations = new String[]{"站点1", "站点2", "站点3"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void init() {
        super.init();
        //套餐A
        acf_btn_package_a = findViewById(R.id.acf_btn_package_a);
        //套餐B
        acf_btn_package_b = findViewById(R.id.acf_btn_package_b);
        //套餐A明细布局
        acf_package_detail = findViewById(R.id.acf_package_detail);
        //套餐B明细布局
        acf_package_layout = findViewById(R.id.acf_package_layout);

        acf_confirm_pay = findViewById(R.id.acf_confirm_pay);
        acf_confirm_pay.setOnClickListener(this);

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

        acf_et_car_type = findViewById(R.id.acf_et_car_type);
        acf_et_car_type.setOnClickListener(this);

        acf_et_pick_station = findViewById(R.id.acf_et_pick_station);
        acf_et_pick_station.setOnClickListener(this);

        acf_tv_date1 = findViewById(R.id.acf_tv_date1);
        acf_tv_date1.setOnClickListener(this);

        acf_et_pick_location = findViewById(R.id.acf_et_pick_location);
        acf_et_pick_location.setOnClickListener(this);
    }

    private Dialog setDialog(Activity activity, View contentView) {
        final Dialog d = new Dialog(activity, R.style.MyTransparentDialog);
        d.setContentView(contentView);
        DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();
        int dialogWidth = dm.widthPixels;
        int dialogHeight = (int) (dm.heightPixels * 0.4);

        Window window = d.getWindow();
        window.setGravity(Gravity.BOTTOM);
//                window.getDecorView().setPadding(0, 0, 0, 0);
        //设置显示动画
//                window.setWindowAnimations(R.style.main_menu_animstyle);
        //设置显示位置
        WindowManager.LayoutParams p = window.getAttributes(); //获取对话框当前的参数值
        p.width = WindowManager.LayoutParams.MATCH_PARENT;
        p.height = dialogHeight;
        window.setAttributes(p);

        d.setCanceledOnTouchOutside(false);
        return d;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_annual_check_fill_info;
    }

    @Override
    protected int getTitleId() {
        return R.string.text_fill_info;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.acf_confirm_pay:
                Intent intent = new Intent(AnnualCheckFillInfoActivity.this, PaymentActivity.class);
                AnnualCheckFillInfoActivity.this.startActivity(intent);
                break;

            case R.id.acf_et_car_type:
                showList(carType, null, acf_et_car_type);
                break;

            case R.id.acf_et_pick_station:
                if(stations == null) {
                    requestStation(this);
                } else {
                    showList(carType, stations, acf_et_pick_station);
                }
                break;

            case R.id.acf_et_pick_location:
                Intent intent1 = new Intent(mContext, SelectPlaceActivity.class);
                startActivityForResult(intent1, REQUEST_CODE_SELECT_PLACE);
                break;

            case R.id.acf_tv_date1:
                DataHelper.pickDateAndTime(this, new DataHelper.PickDateListener() {
                    @Override
                    public void onDatePick(String dateAndTime) {
                        acf_tv_date1.setText(dateAndTime);
                    }
                });
                break;
        }
    }

    @Override
    public void onRequestComplete(List<SurveyStationInfo> stations) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_SELECT_PLACE && resultCode == Activity.RESULT_OK) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                selectAddress = extras.getString(SelectPlaceActivity.SELECT_PLACE_ADDRESS, "");
                selectLat = extras.getDouble(SelectPlaceActivity.SELECT_PLACE_LAT);
                selectLon = extras.getDouble(SelectPlaceActivity.SELECT_PLACE_LON);
                acf_et_pick_location.setText(selectAddress);
            }
        }
    }
}
