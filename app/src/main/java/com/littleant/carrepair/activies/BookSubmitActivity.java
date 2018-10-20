package com.littleant.carrepair.activies;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.constraint.Constraints;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.searchdemo.SelectPlaceActivity;
import com.littleant.carrepair.R;
import com.littleant.carrepair.activies.car.MyCarActivity;
import com.littleant.carrepair.activies.maintain.BookMaintainActivity;
import com.littleant.carrepair.activies.order.MyOrderActivity;
import com.littleant.carrepair.activies.pay.PaymentActivity;
import com.littleant.carrepair.activies.repair.RepairActivity;
import com.littleant.carrepair.activies.repair.RepairRecordActivity;
import com.littleant.carrepair.request.bean.BaseResponseBean;
import com.littleant.carrepair.request.bean.maintain.garage.GarageInfo;
import com.littleant.carrepair.request.bean.car.MyCarListBean;
import com.littleant.carrepair.request.constant.ParamsConstant;
import com.littleant.carrepair.request.excute.maintain.maintain.MaintainCreateCmd;
import com.littleant.carrepair.request.excute.maintain.upkeep.UpkeepCreateCmd;
import com.littleant.carrepair.request.excute.user.car.CarQueryAllCmd;
import com.littleant.carrepair.request.utils.DataHelper;
import com.littleant.carrepair.utils.ProjectUtil;
import com.mh.core.task.MHCommandCallBack;
import com.mh.core.task.MHCommandExecute;
import com.mh.core.task.command.abstracts.MHCommand;
import com.mh.core.tools.MHToast;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.littleant.carrepair.activies.maintain.BookMaintainActivity.OIL_AMOUNT;
import static com.littleant.carrepair.activies.maintain.BookMaintainActivity.OIL_ID;
import static com.littleant.carrepair.activies.repair.RepairActivity.CONTENT;
import static com.littleant.carrepair.activies.repair.RepairActivity.PIC_LIST;
import static com.littleant.carrepair.fragment.MainFragment.GARAGE_INFO;

/**
 * 提交预约
 */
public class BookSubmitActivity extends BaseActivity {
    //时间、地点
    private TextView abs_tv_time_display, abs_tv_location_display;
    //门店、地址
    private TextView abs_name, abs_address;
    //金额
    private TextView abs_price;
    //联系人、电话
    private EditText abs_et_contact, abs_et_phone;
    //确认按钮
    private Button abs_btn_confrm;
    //汽车信息
    private View constraintLayout;
    private TextView bm_tv_title, bm_tv_des;
    private ImageView bm_iv_icon;
    private GarageInfo garageInfo;

    private static final int REQUEST_CODE_SELECT_PLACE = 11;//定义请求码常量
    public static final int REQUEST_CODE_CAR = 100;
    public static final String PICK_CAR = "pick_car";
    public static final String FROM = "from";
    private MyCarListBean.CarInfo carInfo;

    private double selectLat, selectLon;
    private String selectAddress;

    private String from;
    //从维修进来
    private String content;
    private ArrayList<Uri> picList;
    //从维护进来
    private int oilId, oilAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            garageInfo = (GarageInfo) extras.getSerializable(GARAGE_INFO);
            float price=extras.getFloat("oil_price");
            abs_name.setText(garageInfo.getName());
            abs_address.setText(garageInfo.getAddress());
            abs_price.setText(price+"");
            from = extras.getString(FROM);
            if(RepairActivity.class.getSimpleName().equals(from)) {
                content = extras.getString(CONTENT);
                picList = extras.getParcelableArrayList(PIC_LIST);
            } else if(BookMaintainActivity.class.getSimpleName().equals(from)) {
                oilId = extras.getInt(OIL_ID);
                oilAmount = extras.getInt(OIL_AMOUNT);
            }

        }
        if(garageInfo == null) {
            this.finish();
        }

        requestDefaultCar();
    }

    @Override
    protected void init() {
        super.init();
        abs_tv_time_display = findViewById(R.id.abs_tv_time_display);
        abs_tv_time_display.setOnClickListener(this);

        abs_tv_location_display = findViewById(R.id.abs_tv_location_display);
        abs_tv_location_display.setOnClickListener(this);

        abs_name = findViewById(R.id.abs_name);

        abs_address = findViewById(R.id.abs_address);

        abs_price = findViewById(R.id.abs_price);

        abs_et_contact = findViewById(R.id.abs_et_contact);
        //光标放到最右边
        abs_et_contact.setSelection(abs_et_contact.getText().length());
        abs_et_phone = findViewById(R.id.abs_et_phone);
        //光标放到最右边
        abs_et_phone.setSelection(abs_et_phone.getText().length());

        abs_btn_confrm = findViewById(R.id.abs_btn_confrm);
        abs_btn_confrm.setOnClickListener(this);

        constraintLayout = findViewById(R.id.include2);
        constraintLayout.setOnClickListener(this);

        bm_tv_title = findViewById(R.id.bm_tv_title);
        bm_tv_des = findViewById(R.id.bm_tv_des);
        bm_iv_icon = findViewById(R.id.bm_iv_icon);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_book_submit;
    }

    @Override
    protected int getTitleId() {
        return R.string.text_book_submit;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.abs_btn_confrm:
                if(RepairActivity.class.getSimpleName().equals(from)) {
                    requestRepair();
                } else if(BookMaintainActivity.class.getSimpleName().equals(from)) {
                    requestMaintain();
                }
                break;

            case R.id.abs_tv_time_display:
                DataHelper.pickDateAndTime(this, new DataHelper.PickDateListener() {
                    @Override
                    public void onDatePick(String dateAndTime) {
                        abs_tv_time_display.setText(dateAndTime);
                    }
                });
                break;

            case R.id.abs_tv_location_display:
                Intent intent1 = new Intent(mContext, SelectPlaceActivity.class);
                startActivityForResult(intent1, REQUEST_CODE_SELECT_PLACE);
                break;

            case R.id.include2:
                Intent i = new Intent(mContext, MyCarActivity.class);
                i.putExtra(PICK_CAR, true);
                startActivityForResult(i, REQUEST_CODE_CAR);
                break;
        }
    }

    private void showSuccessDialog() {
        final Dialog d = new Dialog(mContext, R.style.MyTransparentDialog);
        View contentView = View.inflate(mContext, R.layout.layout_book_success, null);
        DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
        int dialogWidth = (int) (dm.widthPixels * 0.8);
        int dialogHeight = (int) (dm.heightPixels * 0.3);
        d.setContentView(contentView, new Constraints.LayoutParams(dialogWidth, dialogHeight));
        contentView.findViewById(R.id.lbs_btn_confrm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                d.dismiss();
                Intent intent = new Intent(mContext, RepairRecordActivity.class);
                startActivity(intent);
                finish();
            }
        });
        d.show();
    }

    private void showFailDialog() {
        final Dialog d = new Dialog(mContext, R.style.MyTransparentDialog);
        View contentView = View.inflate(mContext, R.layout.layout_book_fail, null);
        DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
        int dialogWidth = (int) (dm.widthPixels * 0.8);
        int dialogHeight = (int) (dm.heightPixels * 0.3);
        d.setContentView(contentView, new Constraints.LayoutParams(dialogWidth, dialogHeight));
        contentView.findViewById(R.id.lbf_btn_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                d.dismiss();
            }
        });
        contentView.findViewById(R.id.lbf_btn_again).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                d.dismiss();
                if(RepairActivity.class.getSimpleName().equals(from)) {
                    requestRepair();
                } else if(BookMaintainActivity.class.getSimpleName().equals(from)) {
                    requestMaintain();
                }
            }
        });
        d.show();
    }

    private void requestMaintain() {
        int garage_id = garageInfo.getId();
        int car_id = carInfo.getId();
        String name = abs_et_contact.getText().toString();

        String phone = abs_et_phone.getText().toString();
//        String subscribe_time = abs_tv_time_display.getText().toString();
        String date = DataHelper.parseDate(2018, 10, 31);
        String time = DataHelper.parseTime(20, 15);
        String subscribe_time = date + " " + time;
        String longitude = selectLon + "";
        String latitude = selectLat + "";
        String address = abs_tv_location_display.getText().toString();
        if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(name)
                || TextUtils.isEmpty(longitude) || TextUtils.isEmpty(latitude) || TextUtils.isEmpty(address)
                || oilAmount == 0) {
            MHToast.showS(mContext, R.string.need_finish_info);
            return;
        }
        if (!ProjectUtil.checkPhone(mContext, phone)) {
            MHToast.showS(mContext, R.string.phone_wrong);
            return;
        }
        UpkeepCreateCmd upkeepCreateCmd = new UpkeepCreateCmd(mContext, garage_id, car_id, name, phone,
                subscribe_time, longitude, latitude, address, oilId, oilAmount);
        upkeepCreateCmd.setCallback(new MHCommandCallBack() {
            @Override
            public void cmdCallBack(MHCommand command) {
                if (command != null) {
                    Log.i("response", command.getResponse());
                    BaseResponseBean responseBean = ProjectUtil.getBaseResponseBean(command.getResponse());
                    if (responseBean != null && responseBean.getCode() == 100) {
                        Intent intent = new Intent(mContext, PaymentActivity.class);
                        startActivity(intent);
                        finish();
                    }
                } else {
                    MHToast.showS(mContext, R.string.request_fail);
                }
            }
        });
        MHCommandExecute.getInstance().asynExecute(mContext, upkeepCreateCmd);
    }

    private void requestRepair() {
        int garage_id = garageInfo.getId();
        int car_id = carInfo.getId();
        String name = abs_et_contact.getText().toString();
        String phone = abs_et_phone.getText().toString();
//        String subscribe_time = abs_tv_time_display.getText().toString();
        String date = DataHelper.parseDate(2018, 10, 31);
        String time = DataHelper.parseTime(20, 15);
        String subscribe_time = date + " " + time;
        String longitude = selectLon + "";
        String latitude = selectLat + "";
        String address = abs_tv_location_display.getText().toString();
        if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(name) || TextUtils.isEmpty(subscribe_time)
                || TextUtils.isEmpty(longitude) || TextUtils.isEmpty(latitude) || TextUtils.isEmpty(address) || TextUtils.isEmpty(content)) {
            MHToast.showS(mContext, R.string.need_finish_info);
            return;
        }
        if (!ProjectUtil.checkPhone(mContext, phone)) {
            MHToast.showS(mContext, R.string.phone_wrong);
            return;
        }
        Bitmap[] pics = DataHelper.parseUriList2BitmapArray(this, picList);
        MaintainCreateCmd maintainCreateCmd = new MaintainCreateCmd(mContext, garage_id, car_id, name, phone,
                subscribe_time, longitude, latitude, address, content, pics);
        maintainCreateCmd.setCallback(new MHCommandCallBack() {
            @Override
            public void cmdCallBack(MHCommand command) {
                if (command != null) {
                    Log.i("response", command.getResponse());
                    BaseResponseBean responseBean = ProjectUtil.getBaseResponseBean(command.getResponse());
                    if (responseBean != null && responseBean.getCode() == ParamsConstant.REAPONSE_CODE_SUCCESS) {
                        Intent intent = new Intent(mContext, MyOrderActivity.class);
                        startActivity(intent);
                        finish();
                    }
                } else {
                    MHToast.showS(mContext, R.string.request_fail);
                }
            }
        });
        MHCommandExecute.getInstance().asynExecute(mContext, maintainCreateCmd);
    }

    private void requestDefaultCar() {
        CarQueryAllCmd carQueryAllCmd = new CarQueryAllCmd(mContext, ParamsConstant.QueryType.DEFAULT);
        carQueryAllCmd.setCallback(new MHCommandCallBack() {
            @Override
            public void cmdCallBack(MHCommand command) {
                if (command != null) {
                    Log.i("response", command.getResponse());
                    BaseResponseBean responseBean = ProjectUtil.getBaseResponseBean(command.getResponse());
                    if(responseBean != null && ParamsConstant.REAPONSE_CODE_SUCCESS == responseBean.getCode()) {
                        MyCarListBean carListBean = ProjectUtil.getBaseResponseBean(command.getResponse(), MyCarListBean.class);
                        if(carListBean != null) {
                            carInfo = carListBean.getData().get(0);
                            setCarInfo(carInfo);
                        }
                    } else if(responseBean != null && !TextUtils.isEmpty(responseBean.getMsg())) {
                        MHToast.showS(mContext, responseBean.getMsg());
                    }
                } else {
                    MHToast.showS(mContext, R.string.request_fail);
                }
            }
        });
        MHCommandExecute.getInstance().asynExecute(mContext, carQueryAllCmd);
    }

    private void setCarInfo(MyCarListBean.CarInfo carInfo) {
        if(carInfo != null) {
            bm_tv_title.setText(carInfo.getCode());
            bm_tv_des.setText(carInfo.getBrand_name());
            Picasso.with(mContext).load(Uri.parse(carInfo.getPic_url())).into(bm_iv_icon);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_CAR && resultCode == RESULT_OK) {
            carInfo = (MyCarListBean.CarInfo) data.getSerializableExtra(MyCarActivity.CAR_INFO);
            setCarInfo(carInfo);
        } else if(requestCode == REQUEST_CODE_SELECT_PLACE && resultCode == Activity.RESULT_OK) {
            Bundle extras = data.getExtras();
            if(extras != null) {
                selectAddress = extras.getString(SelectPlaceActivity.SELECT_PLACE_ADDRESS, "");
                selectLat = extras.getDouble(SelectPlaceActivity.SELECT_PLACE_LAT);
                selectLon = extras.getDouble(SelectPlaceActivity.SELECT_PLACE_LON);
                abs_tv_location_display.setText(selectAddress);
            }
        }
    }
}
