package com.littleant.carrepair.activies.annualcheck;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.littleant.carrepair.R;
import com.littleant.carrepair.activies.pay.PaymentActivity;
import com.littleant.carrepair.request.bean.BaseResponseBean;
import com.littleant.carrepair.request.bean.SurveyStationInfo;
import com.littleant.carrepair.request.bean.SurveyStationListBean;
import com.littleant.carrepair.request.constant.ParamsConstant;
import com.littleant.carrepair.request.excute.survey.surveystation.SurveyStationQueryAllCmd;
import com.littleant.carrepair.utils.ProjectUtil;
import com.mh.core.task.MHCommandCallBack;
import com.mh.core.task.MHCommandExecute;
import com.mh.core.task.command.abstracts.MHCommand;
import com.mh.core.tools.MHToast;

import java.util.List;

public class OwnCheckFillInfoActivity extends BaseFillInfoActivity implements View.OnClickListener {
    private TextView aocf_confirm_pay, aocf_et_car_type, aocf_et_pick_station;
    private List<SurveyStationInfo> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
                if(data == null) {
                    requestStation();
                } else {
                    showList(carType, data, aocf_et_pick_station);
                }
                break;

        }
    }

    private void requestStation() {
        SurveyStationQueryAllCmd surveyStationQueryAllCmd = new SurveyStationQueryAllCmd(mContext);
        surveyStationQueryAllCmd.setCallback(new MHCommandCallBack() {
            @Override
            public void cmdCallBack(MHCommand command) {
                if (command != null) {
                    Log.i("response", command.getResponse());
                    BaseResponseBean responseBean = ProjectUtil.getBaseResponseBean(command.getResponse());
                    if(responseBean != null && ParamsConstant.REAPONSE_CODE_SUCCESS == responseBean.getCode()) {
                        SurveyStationListBean listBean = ProjectUtil.getBaseResponseBean(command.getResponse(), SurveyStationListBean.class);
                        data = listBean.getData();
                        if(data != null && data.size() > 0) {
                            showList(carType, data, aocf_et_pick_station);
                        }
                    } else if(responseBean != null && !TextUtils.isEmpty(responseBean.getMsg())) {
                        MHToast.showS(mContext, responseBean.getMsg());
                    }
                } else {
                    MHToast.showS(mContext, R.string.request_fail);
                }
            }
        });
        MHCommandExecute.getInstance().asynExecute(mContext, surveyStationQueryAllCmd);
    }

}
