package com.littleant.carrepair.activies.annualcheck;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.amap.searchdemo.SelectPlaceActivity;
import com.littleant.carrepair.R;
import com.littleant.carrepair.activies.BaseActivity;
import com.littleant.carrepair.activies.datetime.DateActivity;
import com.littleant.carrepair.activies.pay.PaymentActivity;
import com.littleant.carrepair.activies.repair.RepairActivity;
import com.littleant.carrepair.request.bean.BaseResponseBean;
import com.littleant.carrepair.request.bean.ComboBean;
import com.littleant.carrepair.request.bean.ComboItemSet;
import com.littleant.carrepair.request.bean.ComboListBean;
import com.littleant.carrepair.request.bean.SurveyStationInfo;
import com.littleant.carrepair.request.bean.SurveyStationListBean;
import com.littleant.carrepair.request.constant.ParamsConstant;
import com.littleant.carrepair.request.excute.survey.combo.ComboQueryAllCmd;
import com.littleant.carrepair.request.utils.DataHelper;
import com.littleant.carrepair.utils.ProjectUtil;
import com.mh.core.task.MHCommandCallBack;
import com.mh.core.task.MHCommandExecute;
import com.mh.core.task.command.abstracts.MHCommand;
import com.mh.core.tools.MHToast;

import java.util.List;

public class AnnualCheckFillInfoActivity extends BaseFillInfoActivity implements BaseFillInfoActivity.RequestStationListener {

    private ConstraintLayout acf_package_layout;
    //两个套餐
    private RadioButton acf_btn_package_a, acf_btn_package_b;
    //点击跳转或选择的参数
    private TextView acf_package_detail, acf_confirm_pay, acf_et_car_type, acf_et_pick_station, acf_tv_date1, acf_tv_package_detail;
    private TextView acf_et_pick_location;
    private static final int REQUEST_CODE_SELECT_PLACE = 11;//定义请求码常量
    //交车坐标
    private double selectLat, selectLon;
    //交车地址
    private String selectAddress;
    //输入的参数
    private EditText acf_et_contact_name, acf_et_contact_phone, acf_et_driver_name, acf_et_driver_brand,
            acf_et_driver_plate;
    //套餐子选项
    private CheckBox acf_rb_light, acf_rb_gas, acf_rb_sight;
    //套餐子选项价格
    private TextView acf_tv_price1, acf_tv_price2, acf_tv_price3;
    //四个价钱
    private TextView acf_et_fee_base, acf_et_fee_package, acf_et_fee_check, acf_et_fee_total;
    private List<ComboBean> comboList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lacf_tv_fill.setChecked(true);

        requestCombo();

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
        //详细说明
        acf_tv_package_detail = findViewById(R.id.acf_tv_package_detail);


        acf_tv_price1 = findViewById(R.id.acf_tv_price1);
        acf_tv_price2 = findViewById(R.id.acf_tv_price2);
        acf_tv_price3 = findViewById(R.id.acf_tv_price3);

        //输入框
        acf_et_contact_name = findViewById(R.id.acf_et_contact_name);
        acf_et_contact_phone = findViewById(R.id.acf_et_contact_phone);
        acf_et_driver_name = findViewById(R.id.acf_et_driver_name);
        acf_et_driver_brand = findViewById(R.id.acf_et_driver_brand);
        acf_et_driver_plate = findViewById(R.id.acf_et_driver_plate);

        //checkbox
        acf_rb_light = findViewById(R.id.acf_rb_light);
        acf_rb_light.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                int price = Integer.parseInt(acf_et_fee_package.getText().toString().split("￥")[1]);
                if(b) {
                    price += 200;
                } else {
                    price -= 200;
                }
                acf_et_fee_package.setText("￥" + price);
                int totlal = 200 + price;
                acf_et_fee_total.setText("￥" + totlal);
            }
        });
        acf_rb_gas = findViewById(R.id.acf_rb_gas);
        acf_rb_gas.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                int price = Integer.parseInt(acf_et_fee_package.getText().toString().split("￥")[1]);
                if(b) {
                    price += 200;
                } else {
                    price -= 200;
                }
                acf_et_fee_package.setText("￥" + price);
                int totlal = 200 + price;
                acf_et_fee_total.setText("￥" + totlal);
            }
        });
        acf_rb_sight = findViewById(R.id.acf_rb_sight);
        acf_rb_sight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                int price = Integer.parseInt(acf_et_fee_package.getText().toString().split("￥")[1]);
                if(b) {
                    price += 200;
                } else {
                    price -= 200;
                }
                acf_et_fee_package.setText("￥" + price);
                int totlal = 200 + price;
                acf_et_fee_total.setText("￥" + totlal);
            }
        });

        //价钱
        acf_et_fee_base = findViewById(R.id.acf_et_fee_base);
        acf_et_fee_package = findViewById(R.id.acf_et_fee_package);
        acf_et_fee_check = findViewById(R.id.acf_et_fee_check);
        acf_et_fee_total = findViewById(R.id.acf_et_fee_total);

        acf_confirm_pay = findViewById(R.id.acf_confirm_pay);
        acf_confirm_pay.setOnClickListener(this);

        acf_btn_package_a.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    acf_tv_package_detail.setVisibility(View.VISIBLE);
                    acf_package_layout.setVisibility(View.GONE);
                    acf_package_detail.setVisibility(View.VISIBLE);

                    //价钱
                    acf_et_fee_base.setText("￥200");
                    acf_et_fee_package.setText("￥0");
                    acf_et_fee_check.setText("￥0");
                    acf_et_fee_total.setText("￥200");

                }
            }
        });

        acf_btn_package_b.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    acf_tv_package_detail.setVisibility(View.VISIBLE);
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
                String contactName = acf_et_contact_name.getText().toString();
                String contactPhone = acf_et_contact_phone.getText().toString();
                String driverName = acf_et_driver_name.getText().toString();
                String brand = acf_et_driver_brand.getText().toString();
                String plate = acf_et_driver_plate.getText().toString();
                String type = acf_et_car_type.getText().toString();
                String station = acf_et_pick_station.getText().toString();
                String date = acf_tv_date1.getText().toString();
                if(!validateParams(contactName, contactPhone, driverName, brand, plate, type, station, date, selectAddress)) {
                    MHToast.showS(mContext, R.string.need_finish_info);
                    return;
                }
                if(selectLat == 0 || selectLon == 0) {
                    return;
                }

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
                    showList(null, stations, acf_et_pick_station);
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
        showList(null, stations, acf_et_pick_station);
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

    private void requestCombo() {
        ComboQueryAllCmd comboQueryAllCmd = new ComboQueryAllCmd(mContext);
        comboQueryAllCmd.setCallback(new MHCommandCallBack() {
            @Override
            public void cmdCallBack(MHCommand command) {
                if (command != null) {
                    Log.i("response", command.getResponse());
                    BaseResponseBean responseBean = ProjectUtil.getBaseResponseBean(command.getResponse());
                    if(responseBean != null && ParamsConstant.REAPONSE_CODE_SUCCESS == responseBean.getCode()) {
                        ComboListBean listBean = ProjectUtil.getBaseResponseBean(command.getResponse(), ComboListBean.class);
                        comboList = listBean.getData();
                        if(comboList != null) {
                            showCombo(comboList);
                        }
                    } else if(responseBean != null && !TextUtils.isEmpty(responseBean.getMsg())) {
                        MHToast.showS(mContext, responseBean.getMsg());
                    }
                } else {
                    MHToast.showS(mContext, R.string.request_fail);
                }
            }
        });
        MHCommandExecute.getInstance().asynExecute(mContext, comboQueryAllCmd);
    }

    private void showCombo(List<ComboBean> combos) {
        for(ComboBean comboBean : combos) {
            int id = comboBean.getId();
            if(id == 1) {
                acf_btn_package_a.setText(comboBean.getName());
                acf_package_detail.setText(comboBean.getDetail());
            } else if (id == 2) {
                acf_btn_package_b.setText(comboBean.getName());
                List<ComboItemSet> comboitem_set = comboBean.getComboitem_set();
                int itemSize = comboitem_set.size();
                acf_rb_light.setText(comboitem_set.get(0).getName());
                acf_tv_price1.setText(DataHelper.displayPrice(this, comboitem_set.get(0).getPrice()));

                acf_rb_gas.setText(comboitem_set.get(1).getName());
                acf_tv_price2.setText(DataHelper.displayPrice(this, comboitem_set.get(1).getPrice()));

                acf_rb_sight.setText(comboitem_set.get(2).getName());
                acf_tv_price3.setText(DataHelper.displayPrice(this, comboitem_set.get(2).getPrice()));

            }
        }
    }
}
