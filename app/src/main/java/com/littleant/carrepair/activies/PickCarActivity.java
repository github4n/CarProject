package com.littleant.carrepair.activies;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.littleant.carrepair.R;
import com.littleant.carrepair.request.bean.SurveyInfo;
import com.squareup.picasso.Picasso;

import static com.littleant.carrepair.activies.AnnualCheckRecordActivity.SURVEY_INFO;

public class PickCarActivity extends BaseFlowActivity {
    private SurveyInfo info;
    private ImageView apc_iv_driver_icon;
    private TextView apc_tv_driver_name, apc_et_check_contact, apc_et_pick_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            info = (SurveyInfo) extras.getSerializable(SURVEY_INFO);
        }
        if(info != null) {
            Picasso.with(mContext).load(info.getDriver_user_pic_url()).into(apc_iv_driver_icon);
            apc_tv_driver_name.setText(info.getDriver_user_name());
            // TODO: 2018/9/4 缺少司机的联系方式
//            apc_et_check_contact.setText(info.get);
            apc_et_pick_time.setText(info.getGet_time());
        } else {
            finish();
        }
    }

    @Override
    protected void init() {
        super.init();

        lacf_tv_pick.setChecked(true);

        apc_iv_driver_icon = findViewById(R.id.apc_iv_driver_icon);

        apc_tv_driver_name = findViewById(R.id.apc_tv_driver_name);

        apc_et_check_contact = findViewById(R.id.apc_et_check_contact);

        apc_et_pick_time = findViewById(R.id.apc_et_pick_time);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pick_car;
    }

    @Override
    protected int getTitleId() {
        return R.string.text_driving_pick_car;
    }
}
