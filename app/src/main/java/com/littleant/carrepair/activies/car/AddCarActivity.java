package com.littleant.carrepair.activies.car;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.littleant.carrepair.R;
import com.littleant.carrepair.activies.BaseActivity;
import com.littleant.carrepair.activies.datetime.DateActivity;
import com.littleant.carrepair.request.bean.BaseResponseBean;
import com.littleant.carrepair.request.bean.MyCarListBean;
import com.littleant.carrepair.request.bean.SurveyInfo;
import com.littleant.carrepair.request.constant.ParamsConstant;
import com.littleant.carrepair.request.excute.user.car.CarAddCmd;
import com.littleant.carrepair.request.utils.DataHelper;
import com.littleant.carrepair.utils.ProjectUtil;
import com.mh.core.task.MHCommandCallBack;
import com.mh.core.task.MHCommandExecute;
import com.mh.core.task.command.abstracts.MHCommand;
import com.mh.core.tools.MHToast;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import static com.littleant.carrepair.activies.annualcheck.AnnualCheckRecordActivity.SURVEY_INFO;
import static com.littleant.carrepair.activies.car.MyCarActivity.CAR_INFO;

/**
 * 添加车辆
 */
public class AddCarActivity extends BaseActivity {

    private static final int REQUEST_GET_SINGLE_FILE = 100;
    private ImageView aac_iv_pic;
    private TextView aac_tv_time;
    private EditText aac_et_brand, aac_et_plate, aac_et_engine, aac_et_mile;
    private Button ac_btn_save;
    private Bitmap pic;
    private String brand, code, engine, buyTime, mile;
    private boolean isDefault;

    private MyCarListBean.CarInfo carInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_car;
    }

    @Override
    protected int getTitleId() {
        return R.string.text_add_car;
    }

    @Override
    protected void init() {
        super.init();

        aac_iv_pic = findViewById(R.id.aac_iv_pic);
        aac_iv_pic.setOnClickListener(this);

        aac_tv_time = findViewById(R.id.aac_tv_time);
        aac_tv_time.setOnClickListener(this);

        aac_et_brand = findViewById(R.id.aac_et_brand);
        aac_et_plate = findViewById(R.id.aac_et_plate);
        aac_et_engine = findViewById(R.id.aac_et_engine);
        aac_et_mile = findViewById(R.id.aac_et_mile);

        ac_btn_save = findViewById(R.id.ac_btn_save);
        ac_btn_save.setOnClickListener(this);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            carInfo = (MyCarListBean.CarInfo) extras.getSerializable(CAR_INFO);
        }
        if(carInfo != null) {
            Picasso.with(mContext).load(Uri.parse(carInfo.getPic_url())).into(aac_iv_pic);

            aac_tv_time.setText(carInfo.getBuy_time());
            aac_et_brand.setText(carInfo.getBrand());
            aac_et_engine.setText(carInfo.getEngine());
            aac_et_plate.setText(carInfo.getCode());
            aac_et_mile.setText(carInfo.getMileage() + "");

        } else {
            finish();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.aac_iv_pic:
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_GET_SINGLE_FILE);
                break;

            case R.id.aac_tv_time:
                DateActivity dateActivity = new DateActivity();
                dateActivity.setCallback(new DateActivity.SelectDateCallback() {
                    @Override
                    public void onSelectDate(int year, int month, int day) {
                        Log.i("aac_tv_time", "year -- " + year);
                        Log.i("aac_tv_time", "month -- " + month);
                        Log.i("aac_tv_time", "day -- " + day);
                        //格式示例2018-03-20
                        String date = DataHelper.parseDate(year, month, day);
                        aac_tv_time.setText(date);
                    }
                });
                dateActivity.show(getFragmentManager(), DateActivity.class.getSimpleName());
                break;

            case R.id.ac_btn_save:
                requestAddCar();
                break;
        }
    }

    private void requestAddCar() {
        brand = aac_et_brand.getText().toString();
        code = aac_et_plate.getText().toString();
        engine = aac_et_engine.getText().toString();
        buyTime = aac_tv_time.getText().toString();
        mile = aac_et_mile.getText().toString();
        if(TextUtils.isEmpty(brand) || TextUtils.isEmpty(code) || TextUtils.isEmpty(engine)
                || TextUtils.isEmpty(buyTime) || TextUtils.isEmpty(mile)) {
            MHToast.showS(mContext, R.string.need_finish_info);
            return;
        }
        if(carInfo == null && pic == null) {
            MHToast.showS(mContext, R.string.need_finish_info);
            return;
        }
        CarAddCmd carAddCmd = new CarAddCmd(mContext, brand, code, engine, buyTime, mile, false, pic);
        carAddCmd.setCallback(new MHCommandCallBack() {
            @Override
            public void cmdCallBack(MHCommand command) {
                if (command != null) {
                    Log.i("response", command.getResponse());
                    BaseResponseBean responseBean = ProjectUtil.getBaseResponseBean(command.getResponse(), BaseResponseBean.class);
                    if(responseBean != null && ParamsConstant.REAPONSE_CODE_SUCCESS == responseBean.getCode()) {
                        AddCarActivity.this.setResult(ParamsConstant.ACTIVITY_RESULT_ADD_CAR);
                        AddCarActivity.this.finish();
                    } else if(responseBean != null && !TextUtils.isEmpty(responseBean.getMsg())) {
                        MHToast.showS(mContext, responseBean.getMsg());
                    } else {
                        MHToast.showS(mContext, R.string.add_car_fail);
                    }
                } else {
                    MHToast.showS(mContext, R.string.request_fail);
                }
            }
        });
        MHCommandExecute.getInstance().asynExecute(mContext, carAddCmd);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_GET_SINGLE_FILE && resultCode == RESULT_OK) {
            Uri uri_data = data.getData();
            try {
                pic = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri_data);
//                Picasso.with(mContext).load(uri_data).into(setting_iv_icon);
                aac_iv_pic.setImageBitmap(pic);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
