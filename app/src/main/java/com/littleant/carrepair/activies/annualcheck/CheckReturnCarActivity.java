package com.littleant.carrepair.activies.annualcheck;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.xlhratingbar_lib.XLHRatingBar;
import com.littleant.carrepair.R;
import com.littleant.carrepair.request.bean.BaseResponseBean;
import com.littleant.carrepair.request.bean.survey.SurveyInfo;
import com.littleant.carrepair.request.constant.ParamsConstant;
import com.littleant.carrepair.request.excute.survey.survey.SurveyBehalfMethodCmd;
import com.littleant.carrepair.utils.ProjectUtil;
import com.mh.core.task.MHCommandCallBack;
import com.mh.core.task.MHCommandExecute;
import com.mh.core.task.command.abstracts.MHCommand;
import com.mh.core.tools.MHToast;
import com.squareup.picasso.Picasso;

import static com.littleant.carrepair.activies.annualcheck.AnnualCheckRecordActivity.SURVEY_INFO;

public class CheckReturnCarActivity extends BaseFlowActivity {

    private SurveyInfo info;
    private ImageView acrc_iv_driver_icon;
    private TextView acrc_et_check_contact, acrc_et_return_time, acrc_confirm_pay;
    private XLHRatingBar acrc_rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        lacf_tv_return.setChecked(true);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            info = (SurveyInfo) extras.getSerializable(SURVEY_INFO);
        }
        if(info != null) {
            Picasso.with(mContext).load(info.getDriver_user_pic_url()).into(acrc_iv_driver_icon);
            acrc_et_check_contact.setText(info.getDrive_user_phone());
            acrc_et_return_time.setText(info.getReturn_time());
            acrc_rating.setCountSelected(info.getDrive_user_score());
        } else {
            finish();
        }
    }

    @Override
    protected void init() {
        super.init();

        acrc_iv_driver_icon = findViewById(R.id.acrc_iv_driver_icon);
        acrc_et_check_contact = findViewById(R.id.acrc_et_check_contact);
        acrc_et_return_time = findViewById(R.id.acrc_et_return_time);
        acrc_rating = findViewById(R.id.acrc_rating);

        acrc_confirm_pay = findViewById(R.id.acrc_confirm_pay);
        acrc_confirm_pay.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_check_return_car;
    }

    @Override
    protected int getTitleId() {
        return R.string.text_check_return_car;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.acrc_confirm_pay:
                requestReturnCar();
                break;
        }
    }

    private void requestReturnCar() {
        SurveyBehalfMethodCmd cmd = new SurveyBehalfMethodCmd(mContext, info.getId(), ParamsConstant.SurveyMethodType.RETURN,
                "", "", 0, 0, "", null, 0);
        cmd.setCallback(new MHCommandCallBack() {
            @Override
            public void cmdCallBack(MHCommand command) {
                if (command != null) {
                    Log.i("response", command.getResponse());
                    BaseResponseBean responseBean = ProjectUtil.getBaseResponseBean(command.getResponse());
                    if(responseBean != null && ParamsConstant.REAPONSE_CODE_SUCCESS == responseBean.getCode()) {
                        setResult(RESULT_OK);
                        finish();
                    } else if(responseBean != null && ParamsConstant.REAPONSE_CODE_AUTH_FAIL == responseBean.getCode()) {
                        Intent intent = ProjectUtil.tokenExpiredIntent(mContext);
                        startActivity(intent);
                    } else if(responseBean != null && !TextUtils.isEmpty(responseBean.getMsg())) {
                        MHToast.showS(mContext, responseBean.getMsg());
                    }
                } else {
                    MHToast.showS(mContext, R.string.request_fail);
                }
            }
        });
        MHCommandExecute.getInstance().asynExecute(mContext, cmd);
    }
}
