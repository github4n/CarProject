package com.littleant.carrepair.activies.annualcheck;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.littleant.carrepair.R;
import com.littleant.carrepair.activies.BaseActivity;
import com.littleant.carrepair.activies.repair.view.RepairPicView;
import com.littleant.carrepair.request.bean.BaseResponseBean;
import com.littleant.carrepair.request.bean.survey.ObjList;
import com.littleant.carrepair.request.bean.survey.SurveyInfo;
import com.littleant.carrepair.request.bean.survey.SurveyPicList;
import com.littleant.carrepair.request.constant.ParamsConstant;
import com.littleant.carrepair.request.excute.survey.survey.SurveyBehalfMethodCmd;
import com.littleant.carrepair.request.excute.survey.survey.SurveyMethodCmd;
import com.littleant.carrepair.request.utils.DataHelper;
import com.littleant.carrepair.utils.ProjectUtil;
import com.mh.core.task.MHCommandCallBack;
import com.mh.core.task.MHCommandExecute;
import com.mh.core.task.command.abstracts.MHCommand;
import com.mh.core.tools.MHToast;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Random;

import static com.littleant.carrepair.activies.annualcheck.AnnualCheckRecordActivity.STATE_FINISH;
import static com.littleant.carrepair.activies.annualcheck.AnnualCheckRecordActivity.STATE_WAIT_GET;
import static com.littleant.carrepair.activies.annualcheck.AnnualCheckRecordActivity.SURVEY_INFO;

public class AnnualCheckDetailActivity extends BaseActivity {
    private TextView lcd_brand, lcd_tv_state, lcd_code, lcd_book_time, lcd_ok_time, lcd_car_type, lcd_check_type,
            lcd_station, lcd_oid, aacd_price;
    private SurveyInfo info;
    private int state;
//    private TextView aacd_tv_pic_title1, aacd_tv_pic_title2;
//    private ImageView aacd_iv_pic1, aacd_iv_pic2;
    private LinearLayout aacd_ll_pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void init() {
        super.init();
        lcd_brand = findViewById(R.id.lcd_brand);
        lcd_tv_state = findViewById(R.id.lcd_tv_state);
        lcd_code = findViewById(R.id.lcd_code);
        lcd_book_time = findViewById(R.id.lcd_book_time);
        lcd_ok_time = findViewById(R.id.lcd_ok_time);
        lcd_car_type = findViewById(R.id.lcd_car_type);
        lcd_check_type = findViewById(R.id.lcd_check_type);
        lcd_station = findViewById(R.id.lcd_station);
        lcd_oid = findViewById(R.id.lcd_oid);
        aacd_ll_pic = findViewById(R.id.aacd_ll_pic);

//        aacd_tv_pic_title1 = findViewById(R.id.aacd_tv_pic_title1);
//        aacd_tv_pic_title2 = findViewById(R.id.aacd_tv_pic_title2);
//        aacd_iv_pic1 = findViewById(R.id.aacd_iv_pic1);
//        aacd_iv_pic2 = findViewById(R.id.aacd_iv_pic2);

        aacd_price = findViewById(R.id.aacd_price);

        mOptionText.setOnClickListener(this);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            info = (SurveyInfo) extras.getSerializable(SURVEY_INFO);
        }
        if(info != null) {
            state = info.getState();
            lcd_brand.setText(info.getCar_brand());
            lcd_code.setText(info.getCar_code());
            lcd_book_time.setText(info.getSubscribe_time());
            if(state == STATE_FINISH) {
                lcd_ok_time.setText(info.getSurvey_time());
            } else {
                lcd_ok_time.setText("未完成");
            }
            lcd_car_type.setText(info.getCar_type());
            if(info.isIs_self()) {
                lcd_check_type.setText("自驾年检");
            } else {
                lcd_check_type.setText("代驾年检");
            }
            lcd_station.setText(info.getSurveystation().getName());
            lcd_oid.setText(info.getOrder_code());

            aacd_price.setText(DataHelper.displayPrice(mContext, info.getTotal_price()));

            if(state == STATE_FINISH) {
                SurveyPicList get_confirm = info.getSurvey_upload();
                if(get_confirm != null) {
                    showPic(get_confirm.getObj_list());
                }
            }
        } else {
            finish();
        }
    }

    private void showPic(List<ObjList> obj_list) {
        if(obj_list != null && obj_list.size() > 0) {
            aacd_ll_pic.removeAllViews();
            for(ObjList obj : obj_list) {
                RepairPicView picView = new RepairPicView(mContext, obj);
                aacd_ll_pic.addView(picView, -1, -2);
            }
        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_annual_check_detail;
    }

    @Override
    protected int getTitleId() {
        return R.string.text_check_detail;
    }

    @Override
    protected int getOptionStringId() {
        if(state == STATE_WAIT_GET) {
            return R.string.text_check_cancel_order;
        } else if(state == STATE_FINISH) {
            return R.string.text_evaluate;
        }
        return 0;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.header_option_text:
                cancelOrder();
            break;
        }
    }

    private void cancelOrder() {
        if (info.isIs_self()) {
            SurveyMethodCmd surveyMethodCmd = new SurveyMethodCmd(mContext, info.getId() + "", ParamsConstant.SurveyMethodType.CANCEL,
                    "", "", -1, -1, "");
            surveyMethodCmd.setCallback(new MHCommandCallBack() {
                @Override
                public void cmdCallBack(MHCommand command) {
                    if (command != null) {
                        Log.i("response", command.getResponse());
                        BaseResponseBean responseBean = ProjectUtil.getBaseResponseBean(command.getResponse());
                        if(responseBean != null && ParamsConstant.REAPONSE_CODE_SUCCESS == responseBean.getCode()) {
                            finishActivityForOk();
                        } else if(responseBean != null && !TextUtils.isEmpty(responseBean.getMsg())) {
                            MHToast.showS(mContext, responseBean.getMsg());
                        }
                    } else {
                        MHToast.showS(mContext, R.string.request_fail);
                    }
                }
            });
            MHCommandExecute.getInstance().asynExecute(mContext, surveyMethodCmd);
        } else {
            SurveyBehalfMethodCmd behalfMethodCmd = new SurveyBehalfMethodCmd(mContext, info.getId(), ParamsConstant.SurveyMethodType.CANCEL,
                    "", "", -1, -1, "");
            behalfMethodCmd.setCallback(new MHCommandCallBack() {
                @Override
                public void cmdCallBack(MHCommand command) {
                    if (command != null) {
                        Log.i("response", command.getResponse());
                        BaseResponseBean responseBean = ProjectUtil.getBaseResponseBean(command.getResponse());
                        if(responseBean != null && ParamsConstant.REAPONSE_CODE_SUCCESS == responseBean.getCode()) {
                            finishActivityForOk();
                        } else if(responseBean != null && !TextUtils.isEmpty(responseBean.getMsg())) {
                            MHToast.showS(mContext, responseBean.getMsg());
                        }
                    } else {
                        MHToast.showS(mContext, R.string.request_fail);
                    }
                }
            });
            MHCommandExecute.getInstance().asynExecute(mContext, behalfMethodCmd);
        }
    }

    private void finishActivityForOk() {
        setResult(Activity.RESULT_OK);
        finish();
    }
}
