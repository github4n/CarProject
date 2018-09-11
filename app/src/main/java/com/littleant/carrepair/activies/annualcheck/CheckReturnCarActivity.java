package com.littleant.carrepair.activies.annualcheck;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.littleant.carrepair.R;
import com.littleant.carrepair.activies.annualcheck.BaseFlowActivity;
import com.littleant.carrepair.request.bean.SurveyInfo;
import com.squareup.picasso.Picasso;

import static com.littleant.carrepair.activies.annualcheck.AnnualCheckRecordActivity.SURVEY_INFO;

public class CheckReturnCarActivity extends BaseFlowActivity {

    private SurveyInfo info;
    private ImageView acrc_iv_driver_icon;
    private TextView acrc_et_check_contact, acrc_et_return_time, acrc_confirm_pay;

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
            // TODO: 2018/9/4 缺少司机的联系方式
//            apc_et_check_contact.setText(info.get);
            acrc_et_return_time.setText(info.getReturn_time());
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

        acrc_confirm_pay = findViewById(R.id.acrc_confirm_pay);
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

    }
}
