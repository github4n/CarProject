package com.littleant.carrepair.activies;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.littleant.carrepair.R;
import com.littleant.carrepair.request.bean.SurveyInfo;
import com.squareup.picasso.Picasso;

import static com.littleant.carrepair.activies.AnnualCheckRecordActivity.STATE_CHECKING;
import static com.littleant.carrepair.activies.AnnualCheckRecordActivity.STATE_WAIT_CHECK;
import static com.littleant.carrepair.activies.AnnualCheckRecordActivity.SURVEY_INFO;

public class StartCheckActivity extends BaseFlowActivity {

    private SurveyInfo info;
    private ImageView asc_iv_driver_icon;
    private TextView asc_et_check_contact, asc_et_check_time, asc_et_check_state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        lacf_tv_start.setChecked(true);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            info = (SurveyInfo) extras.getSerializable(SURVEY_INFO);
        }
        if(info != null) {
            Picasso.with(mContext).load(info.getDriver_user_pic_url()).into(asc_iv_driver_icon);
            asc_et_check_time.setText(info.getArrive_survey_time());
            // TODO: 2018/9/4 缺少司机的联系方式
//            apc_et_check_contact.setText(info.get);
            String stateText = "";
            switch (info.getState()) {
                case STATE_WAIT_CHECK:
                    stateText = getResources().getString(R.string.text_annual_check_state_wait);
                    break;

                case STATE_CHECKING:
                    stateText = getResources().getString(R.string.text_annual_check_state_ing);
                    break;
            }
            asc_et_check_state.setText(stateText);
        } else {
            finish();
        }
    }

    @Override
    protected void init() {
        super.init();

        asc_iv_driver_icon = findViewById(R.id.asc_iv_driver_icon);
        asc_et_check_contact = findViewById(R.id.asc_et_check_contact);
        asc_et_check_time = findViewById(R.id.asc_et_check_time);
        asc_et_check_state = findViewById(R.id.asc_et_check_state);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_start_check;
    }

    @Override
    protected int getTitleId() {
        return R.string.text_start_annual_check;
    }
}
