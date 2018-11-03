package com.littleant.carrepair.activies.annualcheck;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.xlhratingbar_lib.XLHRatingBar;
import com.littleant.carrepair.R;
import com.littleant.carrepair.request.bean.survey.SurveyInfo;
import com.squareup.picasso.Picasso;

import static com.littleant.carrepair.activies.annualcheck.AnnualCheckRecordActivity.STATE_CHECKING;
import static com.littleant.carrepair.activies.annualcheck.AnnualCheckRecordActivity.STATE_WAIT_CHECK;
import static com.littleant.carrepair.activies.annualcheck.AnnualCheckRecordActivity.SURVEY_INFO;

public class StartCheckActivity extends BaseFlowActivity {

    private SurveyInfo info;
    private ImageView asc_iv_driver_icon;
    private TextView asc_et_check_contact, asc_et_check_time, asc_et_check_state;
    private XLHRatingBar asc_rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        lacf_tv_start.setChecked(true);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            info = (SurveyInfo) extras.getSerializable(SURVEY_INFO);
        }
        if(info != null) {
            Picasso.with(mContext).load(info.getDriver_user_pic_url()).resize(150, 150).into(asc_iv_driver_icon);
            asc_et_check_time.setText(info.getArrive_survey_time());
            asc_et_check_contact.setText(info.getDrive_user_phone());
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
            asc_rating.setCountSelected(info.getDrive_user_score());
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
        asc_rating = findViewById(R.id.asc_rating);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_start_check;
    }

    @Override
    protected int getTitleId() {
        return R.string.text_start_annual_check;
    }

    @Override
    public void onClick(View v) {

    }
}
