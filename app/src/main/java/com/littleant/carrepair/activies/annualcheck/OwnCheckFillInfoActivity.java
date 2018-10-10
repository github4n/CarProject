package com.littleant.carrepair.activies.annualcheck;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.littleant.carrepair.R;
import com.littleant.carrepair.activies.pay.PaymentActivity;
import com.littleant.carrepair.request.bean.BaseResponseBean;
import com.littleant.carrepair.request.bean.MyCarListBean;
import com.littleant.carrepair.request.bean.SurveyFeeBean;
import com.littleant.carrepair.request.bean.SurveyStationInfo;
import com.littleant.carrepair.request.bean.SurveyStationListBean;
import com.littleant.carrepair.request.constant.ParamsConstant;
import com.littleant.carrepair.request.excute.survey.survey.SurveyMethodCmd;
import com.littleant.carrepair.request.excute.survey.surveystation.SurveyStationQueryAllCmd;
import com.littleant.carrepair.request.utils.DataHelper;
import com.littleant.carrepair.utils.ProjectUtil;
import com.mh.core.task.MHCommandCallBack;
import com.mh.core.task.MHCommandExecute;
import com.mh.core.task.command.abstracts.MHCommand;
import com.mh.core.tools.MHToast;

import java.util.List;

public class OwnCheckFillInfoActivity extends BaseFillInfoActivity implements BaseFillInfoActivity.RequestStationListener {
    private TextView aocf_confirm_pay, aocf_et_car_type, aocf_et_pick_station, aocf_tv_date1, aocf_et_fee_total;
    private EditText aocf_et_contact_name, aocf_et_contact_phone, aocf_et_driver_name, aocf_et_driver_brand, aocf_et_driver_plate;
    private String price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        locf_tv_fill.setChecked(true);
        requestPrice();
        requestDefaultCar(new DefaultCarCallBack() {
            @Override
            public void onResponse(MyCarListBean.CarInfo carInfo) {
                aocf_et_driver_brand.setText(carInfo.getBrand_name());
                aocf_et_driver_plate.setText(carInfo.getCode());
            }
        });
    }

    private void requestPrice() {
        SurveyMethodCmd surveyMethodCmd = new SurveyMethodCmd(mContext, "", ParamsConstant.SurveyMethodType.GET,
                "", "", 0, -1, "");
        surveyMethodCmd.setCallback(new MHCommandCallBack() {
            @Override
            public void cmdCallBack(MHCommand command) {
                if(command != null) {
                    // TODO: 2018/9/12 返回500错误
                    Log.i("register response", command.getResponse());
                    SurveyFeeBean surveyFeeBean = ProjectUtil.getBaseResponseBean(command.getResponse(), SurveyFeeBean.class);
                    if(surveyFeeBean != null && ParamsConstant.REAPONSE_CODE_SUCCESS == surveyFeeBean.getCode()) {
                        price = surveyFeeBean.getData().getTotal_price() + "";
                        aocf_et_fee_total.setText("￥" + price);
                    } else if(surveyFeeBean != null && !TextUtils.isEmpty(surveyFeeBean.getMsg())) {
                        MHToast.showS(mContext, surveyFeeBean.getMsg());
                    }
                } else {
                    MHToast.showS(mContext, R.string.request_fail);
                }
            }
        });
        MHCommandExecute.getInstance().asynExecute(mContext, surveyMethodCmd);
    }

    @Override
    protected void init() {
        super.init();

        aocf_confirm_pay = findViewById(R.id.aocf_confirm_pay);
        aocf_confirm_pay.setOnClickListener(this);

        aocf_et_car_type = findViewById(R.id.aocf_et_car_type);
        aocf_et_car_type.setOnClickListener(this);

        aocf_et_pick_station = findViewById(R.id.aocf_et_pick_station);
        aocf_et_pick_station.setOnClickListener(this);

        aocf_tv_date1 = findViewById(R.id.aocf_tv_date1);
        aocf_tv_date1.setOnClickListener(this);

        aocf_et_fee_total = findViewById(R.id.aocf_et_fee_total);

        //EditText输入框
        aocf_et_contact_name = findViewById(R.id.aocf_et_contact_name);
        aocf_et_contact_phone = findViewById(R.id.aocf_et_contact_phone);
        aocf_et_driver_name = findViewById(R.id.aocf_et_driver_name);
        aocf_et_driver_brand = findViewById(R.id.aocf_et_driver_brand);
        aocf_et_driver_plate = findViewById(R.id.aocf_et_driver_plate);

        locf_tv_check_know.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_own_check_fill_info;
    }

    @Override
    protected int getTitleId() {
        return R.string.text_fill_info;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.aocf_confirm_pay:
                String contactName = aocf_et_contact_name.getText().toString();
                String contactPhone = aocf_et_contact_phone.getText().toString();
                String driverName = aocf_et_driver_name.getText().toString();
                String brand = aocf_et_driver_brand.getText().toString();
                String plate = aocf_et_driver_plate.getText().toString();
                String type = aocf_et_car_type.getText().toString();
                String station = aocf_et_pick_station.getText().toString();
                String date = aocf_tv_date1.getText().toString();
                if(!validateParams(contactName, contactPhone, driverName, brand, plate, type, station, date)) {
                    MHToast.showS(mContext, R.string.need_finish_info);
                    return;
                }
                Intent intent = new Intent(OwnCheckFillInfoActivity.this, PaymentActivity.class);
                OwnCheckFillInfoActivity.this.startActivity(intent);
                break;

            case R.id.aocf_et_car_type:
                showList(carType, null, aocf_et_car_type);
                break;

            case R.id.aocf_et_pick_station:
                if(stations == null) {
                    requestStation(this);
                } else {
                    showList(null, stations, aocf_et_pick_station);
                }
                break;

            case R.id.aocf_tv_date1:
                DataHelper.pickDateAndTime(this, new DataHelper.PickDateListener() {
                    @Override
                    public void onDatePick(String dateAndTime) {
                        aocf_tv_date1.setText(dateAndTime);
                    }
                });
                break;

            case R.id.locf_tv_check_know:
                if (TextUtils.isEmpty(knowUrl)) {
                    requestKnowUrl();
                } else {
                    showKnowDialog();
                }
                break;
        }
    }

    @Override
    public void onRequestComplete(List<SurveyStationInfo> stations) {
        showList(null, stations, aocf_et_pick_station);
    }
}
