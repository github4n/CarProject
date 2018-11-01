package com.littleant.carrepair.activies.annualcheck;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.amap.searchdemo.SelectPlaceActivity;
import com.littleant.carrepair.R;
import com.littleant.carrepair.activies.pay.PaymentActivity;
import com.littleant.carrepair.request.bean.BaseResponseBean;
import com.littleant.carrepair.request.bean.car.MyCarListBean;
import com.littleant.carrepair.request.bean.survey.SurveyCreateBean;
import com.littleant.carrepair.request.bean.survey.SurveyFeeBean;
import com.littleant.carrepair.request.bean.survey.SurveyInfo;
import com.littleant.carrepair.request.bean.survey.SurveyStationInfo;
import com.littleant.carrepair.request.bean.survey.combo.ComboBean;
import com.littleant.carrepair.request.bean.survey.combo.ComboItemSet;
import com.littleant.carrepair.request.bean.survey.combo.ComboListBean;
import com.littleant.carrepair.request.bean.system.user.UserMeBean;
import com.littleant.carrepair.request.constant.ParamsConstant;
import com.littleant.carrepair.request.excute.survey.combo.ComboQueryAllCmd;
import com.littleant.carrepair.request.excute.survey.survey.SurveyBehalfMethodCmd;
import com.littleant.carrepair.request.excute.survey.survey.SurveyCreateCmd;
import com.littleant.carrepair.request.utils.DataHelper;
import com.littleant.carrepair.utils.ProjectUtil;
import com.mh.core.task.MHCommandCallBack;
import com.mh.core.task.MHCommandExecute;
import com.mh.core.task.command.abstracts.MHCommand;
import com.mh.core.tools.MHToast;

import java.util.List;

import static com.littleant.carrepair.activies.annualcheck.AnnualCheckRecordActivity.SURVEY_INFO;
import static com.littleant.carrepair.activies.pay.PaymentActivity.PAYMENT_FROM;

public class AnnualCheckFillInfoActivity extends BaseFillInfoActivity implements BaseFillInfoActivity.RequestStationListener {

//    private ConstraintLayout acf_package_layout;
    private GridView acf_package_layout;
    //两个套餐
    private RadioButton acf_btn_package_a, acf_btn_package_b;
    //点击跳转或选择的参数
    private TextView acf_package_detail, acf_confirm_pay, acf_et_car_type, acf_et_pick_station, acf_tv_date1, acf_tv_package_detail;
    private TextView acf_et_pick_location;
    private static final int REQUEST_CODE_SELECT_PLACE = 11;//定义请求码常量
    //交车坐标
    private double selectLat, selectLon;
    //交车地址
    private String selectAddress;
    //输入的参数
    private EditText acf_et_contact_name, acf_et_contact_phone, acf_et_driver_name, acf_et_driver_brand,
            acf_et_driver_plate;
    //套餐子选项
    private CheckBox acf_rb_light, acf_rb_gas, acf_rb_sight;
    //套餐子选项价格
    private TextView acf_tv_price1, acf_tv_price2, acf_tv_price3;
    //四个价钱
    private TextView acf_et_fee_base, acf_et_fee_package, acf_et_fee_check, acf_et_fee_total;
    private List<ComboBean> comboList;

    private float base_price = 200, combo_price, survey_price = 100, total_price;
    private int id,combo_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lacf_tv_fill.setChecked(true);

        requestCombo();
        requestDefaultCar(new DefaultCarCallBack() {
            @Override
            public void onResponse(MyCarListBean.CarInfo carInfo) {
                acf_et_driver_brand.setText(carInfo.getBrand_name());
                acf_et_driver_plate.setText(carInfo.getCode());
            }
        });
        requestUserInfo(new MeCallBack() {
            @Override
            public void onResponse(UserMeBean.MeBean userMeBean) {
                acf_et_contact_name.setText(userMeBean.getName());
                acf_et_driver_name.setText(userMeBean.getName());
                acf_et_contact_phone.setText(userMeBean.getPhone());
            }
        });
    }

    @Override
    protected void init() {
        super.init();
        //套餐A
        acf_btn_package_a = findViewById(R.id.acf_btn_package_a);
        //套餐B
        acf_btn_package_b = findViewById(R.id.acf_btn_package_b);
        //套餐A明细布局
        acf_package_detail = findViewById(R.id.acf_package_detail);
        //套餐B明细布局
       // acf_package_layout = findViewById(R.id.acf_package_layout);
        //详细说明
        acf_tv_package_detail = findViewById(R.id.acf_tv_package_detail);


//        acf_tv_price1 = findViewById(R.id.acf_tv_price1);
//        acf_tv_price2 = findViewById(R.id.acf_tv_price2);
//        acf_tv_price3 = findViewById(R.id.acf_tv_price3);

        //输入框
        acf_et_contact_name = findViewById(R.id.acf_et_contact_name);
        acf_et_contact_phone = findViewById(R.id.acf_et_contact_phone);
        acf_et_driver_name = findViewById(R.id.acf_et_driver_name);
        acf_et_driver_brand = findViewById(R.id.acf_et_driver_brand);
        acf_et_driver_plate = findViewById(R.id.acf_et_driver_plate);

        //checkbox
//        acf_rb_light = findViewById(R.id.acf_rb_light);
//        acf_rb_light.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                int price = Integer.parseInt(acf_et_fee_package.getText().toString().split("￥")[1]);
//                if(b) {
//                    price += 200;
//                } else {
//                    price -= 200;
//                }
//                acf_et_fee_package.setText("￥" + price);
//                int totlal = 200 + price;
//                acf_et_fee_total.setText("￥" + totlal);
//            }
//        });
//        acf_rb_gas = findViewById(R.id.acf_rb_gas);
//        acf_rb_gas.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                int price = Integer.parseInt(acf_et_fee_package.getText().toString().split("￥")[1]);
//                if(b) {
//                    price += 200;
//                } else {
//                    price -= 200;
//                }
//                acf_et_fee_package.setText("￥" + price);
//                int totlal = 200 + price;
//                acf_et_fee_total.setText("￥" + totlal);
//            }
//        });
//        acf_rb_sight = findViewById(R.id.acf_rb_sight);
//        acf_rb_sight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                int price = Integer.parseInt(acf_et_fee_package.getText().toString().split("￥")[1]);
//                if(b) {
//                    price += 200;
//                } else {
//                    price -= 200;
//                }
//                acf_et_fee_package.setText("￥" + price);
//                int totlal = 200 + price;
//                acf_et_fee_total.setText("￥" + totlal);
//            }
//        });

        //价钱
        acf_et_fee_base = findViewById(R.id.acf_et_fee_base);
        acf_et_fee_package = findViewById(R.id.acf_et_fee_package);
        acf_et_fee_check = findViewById(R.id.acf_et_fee_check);
        acf_et_fee_total = findViewById(R.id.acf_et_fee_total);

        acf_confirm_pay = findViewById(R.id.acf_confirm_pay);
        acf_confirm_pay.setOnClickListener(this);

        acf_btn_package_a.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(TextUtils.isEmpty(acf_et_pick_station.getText().toString())
                        || TextUtils.isEmpty(acf_et_pick_location.getText().toString())) {
                    //缺少年检站或交车地点
                    MHToast.showS(mContext, R.string.need_annual_info);
                    compoundButton.setChecked(false);
                    return;
                }
                if(b) {
                    acf_package_detail.setText(comboList.get(0).getDetail());
                    combo_id = comboList.get(0).getId();
                    requestPrice();
                }
            }
        });

        acf_btn_package_b.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(TextUtils.isEmpty(acf_et_pick_station.getText().toString())
                        || TextUtils.isEmpty(acf_et_pick_location.getText().toString())) {
                    //缺少年检站或交车地点
                    MHToast.showS(mContext, R.string.need_annual_info);
                    compoundButton.setChecked(false);
                    return;
                }
                if(b) {
                    if(comboList.size() > 1) {
                        acf_package_detail.setText(comboList.get(1).getDetail());
                        combo_id = comboList.get(1).getId();
                        requestPrice();
                    }
                }
            }
        });

        acf_et_car_type = findViewById(R.id.acf_et_car_type);
        acf_et_car_type.setOnClickListener(this);

        acf_et_pick_station = findViewById(R.id.acf_et_pick_station);
        acf_et_pick_station.setOnClickListener(this);

        acf_tv_date1 = findViewById(R.id.acf_tv_date1);
        acf_tv_date1.setOnClickListener(this);

        acf_et_pick_location = findViewById(R.id.acf_et_pick_location);
        acf_et_pick_location.setOnClickListener(this);

        lacf_tv_check_know.setOnClickListener(this);
    }

    private Dialog setDialog(Activity activity, View contentView) {
        final Dialog d = new Dialog(activity, R.style.MyTransparentDialog);
        d.setContentView(contentView);
        DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();
        int dialogWidth = dm.widthPixels;
        int dialogHeight = (int) (dm.heightPixels * 0.4);

        Window window = d.getWindow();
        window.setGravity(Gravity.BOTTOM);
//                window.getDecorView().setPadding(0, 0, 0, 0);
        //设置显示动画
//                window.setWindowAnimations(R.style.main_menu_animstyle);
        //设置显示位置
        WindowManager.LayoutParams p = window.getAttributes(); //获取对话框当前的参数值
        p.width = WindowManager.LayoutParams.MATCH_PARENT;
        p.height = dialogHeight;
        window.setAttributes(p);

        d.setCanceledOnTouchOutside(false);
        return d;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_annual_check_fill_info;
    }

    @Override
    protected int getTitleId() {
        return R.string.text_fill_info;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.acf_confirm_pay:
                String contactName = acf_et_contact_name.getText().toString();
                String contactPhone = acf_et_contact_phone.getText().toString();
                String driverName = acf_et_driver_name.getText().toString();
                String brand = acf_et_driver_brand.getText().toString();
                String plate = acf_et_driver_plate.getText().toString();
                String type = acf_et_car_type.getText().toString();
                String station = acf_et_pick_station.getText().toString();
                String date = acf_tv_date1.getText().toString();
                if(!validateParams(contactName, contactPhone, driverName, brand, plate, type, station, date, selectAddress)) {
                    MHToast.showS(mContext, R.string.need_finish_info);
                    return;
                }
                if(selectLat == 0 || selectLon == 0) {
                    return;
                }
//                    public SurveyCreateCmd(Context context, String name, String phone, String car_name, String car_brand, String car_code,
//                    String car_type, int surveystation_id, String order_longitude, String order_latitude,
//                    String order_address, String subscribe_time, String is_self, int combo_id, String comboitem_list)
                SurveyCreateCmd surveyCreateCmd = new SurveyCreateCmd(mContext, contactName, contactPhone, driverName, brand, plate, type, selectedStation.getId(), selectLat + "",
                        selectLat + "", selectAddress, date, "0", combo_id, "");
                surveyCreateCmd.setCallback(new MHCommandCallBack() {
                    @Override
                    public void cmdCallBack(MHCommand command) {
                        Log.i("response", command.getResponse());
                        BaseResponseBean responseBean = ProjectUtil.getBaseResponseBean(command.getResponse());
                        if(responseBean != null && ParamsConstant.REAPONSE_CODE_SUCCESS == responseBean.getCode()) {
                            SurveyCreateBean createBean = ProjectUtil.getBaseResponseBean(command.getResponse(), SurveyCreateBean.class);
                            if(createBean != null && createBean.getData() != null) {
                                Intent intent = new Intent(AnnualCheckFillInfoActivity.this, PaymentActivity.class);
                                intent.putExtra(PAYMENT_FROM, ParamsConstant.ORDER_ANNUAL_CHECK);
                                SurveyInfo surveyInfo = new SurveyInfo();
                                surveyInfo.setId(createBean.getData().getId());
                                intent.putExtra(SURVEY_INFO, surveyInfo);
                                AnnualCheckFillInfoActivity.this.startActivity(intent);
                            }
                        } else if(responseBean != null && ParamsConstant.REAPONSE_CODE_AUTH_FAIL == responseBean.getCode()) {
                            Intent intent = ProjectUtil.tokenExpiredIntent(mContext);
                            startActivity(intent);
                        }

                    }
                });
                MHCommandExecute.getInstance().asynExecute(mContext, surveyCreateCmd);


                break;

            case R.id.acf_et_car_type:
                showList(carType, null, acf_et_car_type);
                break;

            case R.id.acf_et_pick_station:
                isFlag=true;
                if(stations == null) {
                    requestStation(this);
                } else {
                    showList(null, stations, acf_et_pick_station);
                }
                break;

            case R.id.acf_et_pick_location:
                Intent intent1 = new Intent(mContext, SelectPlaceActivity.class);
                startActivityForResult(intent1, REQUEST_CODE_SELECT_PLACE);
                break;

            case R.id.acf_tv_date1:
                DataHelper.pickDateAndTime(this, new DataHelper.PickDateListener() {
                    @Override
                    public void onDatePick(String dateAndTime) {
                        acf_tv_date1.setText(dateAndTime);
                    }
                });
                break;

            case R.id.lacf_tv_check_know:
                if (TextUtils.isEmpty(knowUrl)) {
                    requestKnowUrl();
                } else {
                    showKnowDialog();
                }
                break;
        }
    }

    @Override
    public void onRequestComplete(List<SurveyStationInfo> stations) {
        showList(null, stations, acf_et_pick_station);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_SELECT_PLACE && resultCode == Activity.RESULT_OK) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                selectAddress = extras.getString(SelectPlaceActivity.SELECT_PLACE_ADDRESS, "");
                selectLat = extras.getDouble(SelectPlaceActivity.SELECT_PLACE_LAT);
                selectLon = extras.getDouble(SelectPlaceActivity.SELECT_PLACE_LON);
                acf_et_pick_location.setText(selectAddress);
            }
        }
    }

    private void requestCombo() {
        ComboQueryAllCmd comboQueryAllCmd = new ComboQueryAllCmd(mContext);
        comboQueryAllCmd.setCallback(new MHCommandCallBack() {
            @Override
            public void cmdCallBack(MHCommand command) {
                if (command != null) {
                    Log.i("response", command.getResponse());
                    BaseResponseBean responseBean = ProjectUtil.getBaseResponseBean(command.getResponse());
                    if(responseBean != null && ParamsConstant.REAPONSE_CODE_SUCCESS == responseBean.getCode()) {
                        ComboListBean listBean = ProjectUtil.getBaseResponseBean(command.getResponse(), ComboListBean.class);
                        comboList = listBean.getData();
                        if(comboList != null) {
                            showCombo(comboList);
                        }
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
        MHCommandExecute.getInstance().asynExecute(mContext, comboQueryAllCmd);
    }

    private void showCombo(List<ComboBean> combos) {
        if(combos != null && combos.size() > 0) {
            acf_btn_package_a.setText(combos.get(0).getName());
            acf_package_detail.setText(combos.get(0).getDetail());
            if(combos.size() > 1) {
                acf_btn_package_b.setText(combos.get(1).getName());
            }
        }
//        for(ComboBean comboBean : combos) {
//             id = comboBean.getId();
//            if(id == 1) {
//                acf_btn_package_a.setText(comboBean.getName());
//                acf_package_detail.setText(comboBean.getDetail());
//            } else if (id == 2) {
//                acf_btn_package_b.setText(comboBean.getName());
//                List<ComboItemSet> comboitem_set = comboBean.getComboitem_set();
//                if(comboitem_set != null  && comboitem_set.size() > 0) {
//                    MyComboAdapter adapter = new MyComboAdapter(comboitem_set);
//                    acf_package_layout.setAdapter(adapter);
//                }
//                int itemSize = comboitem_set.size();
//                acf_rb_light.setText(comboitem_set.get(0).getName());
//                acf_tv_price1.setText(DataHelper.displayPrice(this, comboitem_set.get(0).getPrice()));
//
//                acf_rb_gas.setText(comboitem_set.get(1).getName());
//                acf_tv_price2.setText(DataHelper.displayPrice(this, comboitem_set.get(1).getPrice()));
//
//                acf_rb_sight.setText(comboitem_set.get(2).getName());
//                acf_tv_price3.setText(DataHelper.displayPrice(this, comboitem_set.get(2).getPrice()));
//
//            }
//        }
    }

    private void requestPrice() {
        SurveyBehalfMethodCmd cmd = new SurveyBehalfMethodCmd(mContext, 0, ParamsConstant.SurveyMethodType.GET,
                selectLon + "", selectLat + "", selectedStation.getId(), combo_id, "", null, 0);
        cmd.setCallback(new MHCommandCallBack() {
            @Override
            public void cmdCallBack(MHCommand command) {
                if (command != null) {
                    Log.i("response", command.getResponse());
                    BaseResponseBean responseBean = ProjectUtil.getBaseResponseBean(command.getResponse());
                    if(responseBean != null && ParamsConstant.REAPONSE_CODE_SUCCESS == responseBean.getCode()) {
                        SurveyFeeBean feeBean = ProjectUtil.getBaseResponseBean(command.getResponse(), SurveyFeeBean.class);
                        if(feeBean != null && feeBean.getData() != null) {
                            setPrice(feeBean);
                        }
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

    private void setPrice(SurveyFeeBean feeBean) {
        //价钱
        acf_et_fee_base.setText(DataHelper.displayPrice(mContext, feeBean.getData().getBase_price()));
        acf_et_fee_package.setText(DataHelper.displayPrice(mContext, feeBean.getData().getCombo_price()));
        acf_et_fee_check.setText(DataHelper.displayPrice(mContext, feeBean.getData().getSurvey_price()));
        acf_et_fee_total.setText(DataHelper.displayPrice(mContext, feeBean.getData().getTotal_price()));
    }

    private class MyComboAdapter extends BaseAdapter {
        private List<ComboItemSet> list;

        public MyComboAdapter(List<ComboItemSet> list) {
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_combo_item, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            final ComboItemSet comboItemSet = list.get(i);
            if(comboItemSet != null) {
                holder.lci_cb_light.setText(comboItemSet.getName());
                holder.lci_cb_light.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if(b) {
                            combo_price += comboItemSet.getPrice();
                        } else {
                            combo_price -= comboItemSet.getPrice();
                        }
//                        setPrice();
                    }
                });
                holder.lci_tv_price1.setText("￥" + comboItemSet.getPrice() + "");
            }

            return convertView;
        }

        class ViewHolder {
            //商品名、价格
            private TextView lci_tv_price1;

            private CheckBox lci_cb_light;

            public ViewHolder(View convertView) {
                lci_tv_price1 = convertView.findViewById(R.id.lci_tv_price1);
                lci_cb_light = convertView.findViewById(R.id.lci_cb_light);
            }
        }
    }
}
