package com.littleant.carrepair.activies.annualcheck;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.amap.searchdemo.SelectPlaceActivity;
import com.littleant.carrepair.R;
import com.littleant.carrepair.activies.BaseActivity;
import com.littleant.carrepair.activies.datetime.DateActivity;
import com.littleant.carrepair.activies.pay.PaymentActivity;

public class AnnualCheckFillInfoActivity extends BaseActivity {

    private ConstraintLayout acf_package_layout;
    private RadioButton acf_btn_package_a, acf_btn_package_b;
    private TextView acf_package_detail, acf_confirm_pay, acf_et_car_type, acf_et_pick_station, acf_tv_date1;
    private TextView acf_et_pick_location;
    private String[] carType = new String[]{"汽车1", "汽车2"};
    private String[] stations = new String[]{"站点1", "站点2", "站点3"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        acf_package_layout = findViewById(R.id.acf_package_layout);

        acf_confirm_pay = findViewById(R.id.acf_confirm_pay);
        acf_confirm_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AnnualCheckFillInfoActivity.this, PaymentActivity.class);
                AnnualCheckFillInfoActivity.this.startActivity(intent);
            }
        });

        acf_btn_package_a.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    acf_package_layout.setVisibility(View.GONE);
                    acf_package_detail.setVisibility(View.VISIBLE);
                }
            }
        });

        acf_btn_package_b.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    acf_package_detail.setVisibility(View.GONE);
                    acf_package_layout.setVisibility(View.VISIBLE);
                }
            }
        });

        acf_et_car_type = findViewById(R.id.acf_et_car_type);
        acf_et_car_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View contentView = LayoutInflater.from(AnnualCheckFillInfoActivity.this).inflate(R.layout.layout_select_dialog, null);
//                View contentView = View.inflate(OwnCheckFillInfoActivity.this, R.layout.layout_select_dialog, null);
                final Dialog d = setDialog(AnnualCheckFillInfoActivity.this, contentView);
                d.setContentView(contentView);
                contentView.findViewById(R.id.lsd_tv_cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        d.dismiss();
                    }
                });

                ListView listView = contentView.findViewById(R.id.lsd_list);
                listView.setAdapter(new ArrayAdapter<>(AnnualCheckFillInfoActivity.this, android.R.layout.simple_list_item_1, carType));
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        d.dismiss();
                    }
                });
                d.show();
            }
        });

        acf_et_pick_station = findViewById(R.id.acf_et_pick_station);
        acf_et_pick_station.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View contentView = LayoutInflater.from(AnnualCheckFillInfoActivity.this).inflate(R.layout.layout_select_dialog, null);
//                View contentView = View.inflate(OwnCheckFillInfoActivity.this, R.layout.layout_select_dialog, null);
                final Dialog d = setDialog(AnnualCheckFillInfoActivity.this, contentView);
                d.setContentView(contentView);
                contentView.findViewById(R.id.lsd_tv_cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        d.dismiss();
                    }
                });

                ListView listView = contentView.findViewById(R.id.lsd_list);
                listView.setAdapter(new ArrayAdapter<>(AnnualCheckFillInfoActivity.this, android.R.layout.simple_list_item_1, stations));
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        d.dismiss();
                    }
                });
                d.show();
            }
        });

        acf_tv_date1 = findViewById(R.id.acf_tv_date1);
        acf_tv_date1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateActivity dateActivity = new DateActivity();
                dateActivity.show(getFragmentManager(), DateActivity.class.getSimpleName());
            }
        });

        acf_et_pick_location = findViewById(R.id.acf_et_pick_location);
        acf_et_pick_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AnnualCheckFillInfoActivity.this, SelectPlaceActivity.class);
                AnnualCheckFillInfoActivity.this.startActivity(intent);
            }
        });
    }

    private Dialog setDialog(Activity activity, View contentView) {
        final Dialog d = new Dialog(activity);
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

    }
}
