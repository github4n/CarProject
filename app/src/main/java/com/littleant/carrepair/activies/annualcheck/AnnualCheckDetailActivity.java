package com.littleant.carrepair.activies.annualcheck;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.littleant.carrepair.R;
import com.littleant.carrepair.activies.BaseActivity;
import com.littleant.carrepair.request.bean.SurveyInfo;
import com.littleant.carrepair.request.bean.SurveyPicList;
import com.squareup.picasso.Picasso;

import java.util.Random;

import static com.littleant.carrepair.activies.annualcheck.AnnualCheckRecordActivity.STATE_FINISH;
import static com.littleant.carrepair.activies.annualcheck.AnnualCheckRecordActivity.STATE_WAIT_GET;
import static com.littleant.carrepair.activies.annualcheck.AnnualCheckRecordActivity.SURVEY_INFO;

public class AnnualCheckDetailActivity extends BaseActivity {
    private TextView lcd_brand, lcd_tv_state, lcd_code, lcd_book_time, lcd_ok_time, lcd_car_type, lcd_check_type,
            lcd_station, lcd_oid, aacd_price;
    private SurveyInfo info;
    private int state;
    private TextView aacd_tv_pic_title1, aacd_tv_pic_title2;
    private ImageView aacd_iv_pic1, aacd_iv_pic2;

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

        aacd_tv_pic_title1 = findViewById(R.id.aacd_tv_pic_title1);
        aacd_tv_pic_title2 = findViewById(R.id.aacd_tv_pic_title2);
        aacd_iv_pic1 = findViewById(R.id.aacd_iv_pic1);
        aacd_iv_pic2 = findViewById(R.id.aacd_iv_pic2);

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
            // TODO: 2018/9/14 缺少订单号
            lcd_oid.setText(new Random(10000).nextInt() + "");

            aacd_price.setText(info.getTotal_price() + "");

            if(state == STATE_FINISH) {
                SurveyPicList get_confirm = info.getGet_confirm();
                if(get_confirm != null) {
                    aacd_tv_pic_title1.setText(get_confirm.getName());
                    Picasso.with(mContext).load(Uri.parse(get_confirm.getObj_list().get(0).getPic_url())).into(aacd_iv_pic1);
                }
            }
        } else {
            finish();
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
        }
        return 0;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.header_option_text:
                finish();
            break;
        }
    }
}
