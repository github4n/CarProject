package com.littleant.carrepair.activies.car;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.littleant.carrepair.R;
import com.littleant.carrepair.activies.BaseActivity;
import com.littleant.carrepair.request.bean.BaseResponseBean;
import com.littleant.carrepair.request.bean.MyCarListBean;
import com.littleant.carrepair.request.constant.ParamsConstant;
import com.littleant.carrepair.request.excute.user.car.CarAddCmd;
import com.littleant.carrepair.utils.ProjectUtil;
import com.lljjcoder.Constant;
import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.citywheel.CityConfig;
import com.lljjcoder.style.citypickerview.CityPickerView;
import com.mh.core.task.MHCommandCallBack;
import com.mh.core.task.MHCommandExecute;
import com.mh.core.task.command.abstracts.MHCommand;
import com.mh.core.tools.MHToast;
import com.squareup.picasso.Picasso;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.PicassoEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.io.IOException;

import static com.littleant.carrepair.activies.car.CarBrandActivity.BRAND_CODE;
import static com.littleant.carrepair.activies.car.CarBrandActivity.BRAND_NAME;
import static com.littleant.carrepair.activies.car.MyCarActivity.CAR_INFO;

/**
 * 添加车辆
 */
public class AddCarActivity extends BaseActivity {

    private static final int REQUEST_CODE_CHOOSE = 10;//定义请求码常量
    private static final int REQUEST_CODE_BRAND = 11;
    private ImageView aac_iv_pic;
    private TextView aac_tv_brand, aac_tv_plate_type, aac_tv_car_address;
    private EditText aac_et_plate, aac_et_engine, aac_et_frame;
    private Button ac_btn_save;
    private Bitmap pic;
    private String brand, code, engine, classno;
    private boolean isDefault;
    private CheckBox aac_cb_default;
    private String hpzl = "";
    private String city_code = "";

    private MyCarListBean.CarInfo carInfo;

    private CityPickerView mPicker = new CityPickerView();

    int selectPostition = -1;
    private int car_style;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPicker.init(this, Constant.PRI_CITY_DATA);
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

        aac_tv_plate_type = findViewById(R.id.aac_tv_plate_type);
        aac_tv_plate_type.setOnClickListener(this);

        aac_tv_car_address = findViewById(R.id.aac_tv_car_address);
        aac_tv_car_address.setOnClickListener(this);

        aac_tv_brand = findViewById(R.id.aac_tv_brand);
        aac_tv_brand.setOnClickListener(this);

        aac_et_plate = findViewById(R.id.aac_et_plate);
        aac_et_engine = findViewById(R.id.aac_et_engine);
        aac_et_frame = findViewById(R.id.aac_et_frame);
        aac_cb_default = findViewById(R.id.aac_cb_default);

        ac_btn_save = findViewById(R.id.ac_btn_save);
        ac_btn_save.setOnClickListener(this);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            carInfo = (MyCarListBean.CarInfo) extras.getSerializable(CAR_INFO);
        }
        if(carInfo != null) {
            Picasso.with(mContext).load(Uri.parse(carInfo.getPic_url())).into(aac_iv_pic);

            aac_et_engine.setText(carInfo.getEngine());
            aac_et_plate.setText(carInfo.getCode());
            aac_et_frame.setText(carInfo.getClasssno());
            hpzl = carInfo.getHpzl();
            String plateType = "";
            if("01".equals(hpzl)) {
                plateType = "大型车";
            } else if("02".equals(hpzl)) {
                plateType = "小型车";
            } else if("16".equals(hpzl)) {
                plateType = "教练车";
            }
            aac_tv_plate_type.setText(plateType);
            aac_tv_car_address.setText(carInfo.getProvince_name() + carInfo.getCity_name());
            aac_cb_default.setChecked(carInfo.isIs_default());
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.aac_iv_pic:
                Matisse.from(AddCarActivity.this)
                        .choose(MimeType.ofImage())
                        .countable(true)
                        .maxSelectable(1)
//                        .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
//                        .gridExpectedSize(120)
                        .capture(true) //是否提供拍照功能
                        .captureStrategy(new CaptureStrategy(true, "com.littleant.carrepair.fileprovider"))//存储到哪里
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                        .thumbnailScale(0.85f)
                        .imageEngine(new PicassoEngine())
                        .forResult(REQUEST_CODE_CHOOSE);
//                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//                intent.addCategory(Intent.CATEGORY_OPENABLE);
//                intent.setType("image/*");
//                startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_GET_SINGLE_FILE);
                break;

//            case R.id.aac_tv_time:
//                DateActivity dateActivity = new DateActivity();
//                dateActivity.setCallback(new DateActivity.SelectDateCallback() {
//                    @Override
//                    public void onSelectDate(int year, int month, int day) {
//                        Log.i("aac_tv_time", "year -- " + year);
//                        Log.i("aac_tv_time", "month -- " + month);
//                        Log.i("aac_tv_time", "day -- " + day);
//                        //格式示例2018-03-20
//                        String date = DataHelper.parseDate(year, month, day);
//                    }
//                });
//                dateActivity.show(getFragmentManager(), DateActivity.class.getSimpleName());
//                break;

            case R.id.ac_btn_save:
                requestAddCar();
                break;

            case R.id.aac_tv_car_address:
                requestAddress();
                break;

            case R.id.aac_tv_plate_type:
                showList();
                break;

            case R.id.aac_tv_brand:
                Intent intent = new Intent(mContext, CarBrandActivity.class);
                startActivityForResult(intent, REQUEST_CODE_BRAND);
                break;
        }
    }

    private void requestAddress() {
        CityConfig cityConfig = new CityConfig.Builder().setCityWheelType(CityConfig.WheelType.PRO_CITY).build();
        mPicker.setConfig(cityConfig);
        //监听选择点击事件及返回结果
        mPicker.setOnCityItemClickListener(new OnCityItemClickListener() {
            @Override
            public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {
                String address = "";
                //省份
                if (province != null) {
                    Log.i("CityConfig", province.getId());
                    Log.i("CityConfig", province.getName());
                    address += province.getName();
                }

                //城市
                if (city != null) {
                    Log.i("CityConfig", city.getId());
                    Log.i("CityConfig", city.getName());
                    Log.i("CityConfig", city.getCity_code());
                    city_code = city.getCity_code();
                    address += city.getName();
                }

                //地区
                if (district != null) {
                }
                aac_tv_car_address.setText(address);
            }

            @Override
            public void onCancel() {
            }
        });

        //显示
        mPicker.showCityPicker();
    }

    private void requestAddCar() {
//        brand = aac_tv_brand.getText().toString();
        code = aac_et_plate.getText().toString();
        engine = aac_et_engine.getText().toString();
//        buyTime = aac_tv_time.getText().toString();
        classno = aac_et_frame.getText().toString();
//        mile = aac_et_mile.getText().toString();
        if(TextUtils.isEmpty(code) || TextUtils.isEmpty(engine) || TextUtils.isEmpty(city_code)
                || TextUtils.isEmpty(classno) || TextUtils.isEmpty(hpzl)) {
            MHToast.showS(mContext, R.string.need_finish_info);
            return;
        }
        if(carInfo == null && pic == null) {
            MHToast.showS(mContext, R.string.need_finish_info);
            return;
        }
        CarAddCmd carAddCmd = new CarAddCmd(mContext, code, car_style, engine, city_code, classno, hpzl, aac_cb_default.isChecked(), pic);
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
        if(requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            Uri uri_data = Matisse.obtainResult(data).get(0);
            try {
                pic = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri_data);
                aac_iv_pic.setImageBitmap(pic);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if(requestCode == REQUEST_CODE_BRAND && resultCode == RESULT_OK) {
            String brandName = data.getStringExtra(BRAND_NAME);
            car_style = data.getIntExtra(BRAND_CODE, 0);
            aac_tv_brand.setText(brandName);
        }
//        if(requestCode == REQUEST_GET_SINGLE_FILE && resultCode == RESULT_OK) {
//            Uri uri_data = data.getData();
//            try {
//                pic = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri_data);
////                Picasso.with(mContext).load(uri_data).into(setting_iv_icon);
//                aac_iv_pic.setImageBitmap(pic);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
    }

    protected void showList() {

        final String[] list = getResources().getStringArray(R.array.car_plate_type);
        View contentView2 = LayoutInflater.from(mContext).inflate(R.layout.layout_select_dialog, null);
//                View contentView = View.inflate(OwnCheckFillInfoActivity.this, R.layout.layout_select_dialog, null);
        final Dialog d2 = setDialog(mContext, contentView2);
        d2.setContentView(contentView2);
        contentView2.findViewById(R.id.lsd_tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                d2.dismiss();
            }
        });

        contentView2.findViewById(R.id.lsd_tv_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                d2.dismiss();
                aac_tv_plate_type.setText(list[selectPostition].split(":")[1]);
            }
        });

        ListView listView2 = contentView2.findViewById(R.id.lsd_list);
        final MyAdapter myAdapter = new MyAdapter(list);
        listView2.setAdapter(myAdapter);
        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("listview", "onItemClick = " + position);
                hpzl = list[position].split(":")[0];
                selectPostition = position;
                //改变选中状态
                myAdapter.setCurrentItem(position);
                //通知ListView改变状态
                myAdapter.notifyDataSetInvalidated();
            }
        });
        d2.show();
    }

    protected Dialog setDialog(Context activity, View contentView) {
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

    protected class MyAdapter extends BaseAdapter {
        private String[] strings;

        public MyAdapter(String[] strings) {
            this.strings = strings;
        }

        //当前Item被点击的位置
        private int currentItem = -1;

        public void setCurrentItem(int currentItem) {
            this.currentItem = currentItem;
        }

        @Override
        public int getCount() {
            return strings.length;
        }

        @Override
        public Object getItem(int position) {
            return strings[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            MyAdapter.ViewHolder holder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_listview_item, null);
                holder = new MyAdapter.ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (MyAdapter.ViewHolder) convertView.getTag();
            }

            String text;
            text = strings[position];

            holder.mTextView.setText(text.split(":")[1]);

            if (currentItem == position) {
                //如果被点击，设置当前TextView被选中
                holder.mTextView.setTextColor(getResources().getColor(R.color.color_main));
            } else {
                //如果没有被点击，设置当前TextView未被选中
                holder.mTextView.setTextColor(getResources().getColor(R.color.color_uc_text));
            }

            return convertView;
        }

        class ViewHolder {
            TextView mTextView;

            public ViewHolder(View convertView) {
                mTextView = convertView.findViewById(R.id.lli_text);
            }
        }
    }
}
