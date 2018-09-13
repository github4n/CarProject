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
    private TextView aocf_confirm_pay, aocf_et_car_type, aocf_et_pick_station, aocf_tv_date1;
    private EditText aocf_et_contact_name, aocf_et_contact_phone, aocf_et_driver_name, aocf_et_driver_brand, aocf_et_driver_plate;
    private String price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lacf_tv_fill.setChecked(true);
        requestPrice();
    }

    private void requestPrice() {
        SurveyMethodCmd surveyMethodCmd = new SurveyMethodCmd(mContext, "", ParamsConstant.SurveyMethodType.GET,
                "", "", 0, 0, "");
        surveyMethodCmd.setCallback(new MHCommandCallBack() {
            @Override
            public void cmdCallBack(MHCommand command) {
                if(command != null) {
                    // TODO: 2018/9/12 返回500错误
                    Log.i("register response", command.getResponse());
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

        //EditText输入框
        aocf_et_contact_name = findViewById(R.id.aocf_et_contact_name);
        aocf_et_contact_phone = findViewById(R.id.aocf_et_contact_phone);
        aocf_et_driver_name = findViewById(R.id.aocf_et_driver_name);
        aocf_et_driver_brand = findViewById(R.id.aocf_et_driver_brand);
        aocf_et_driver_plate = findViewById(R.id.aocf_et_driver_plate);
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
                    showList(carType, stations, aocf_et_pick_station);
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

        }
    }

    @Override
    public void onRequestComplete(List<SurveyStationInfo> stations) {
        showList(carType, stations, aocf_et_pick_station);
    }
}
