package com.littleant.carrepair.activies;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.Constraints;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.littleant.carrepair.R;

public class OwnCheckFillInfoActivity extends BaseActivity {
    private TextView aocf_confirm_pay, aocf_et_car_type, aocf_et_pick_station;
    private String[] carType = new String[]{"汽车1", "汽车2"};
    private String[] stations = new String[]{"站点1", "站点2", "站点3"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        aocf_confirm_pay = findViewById(R.id.aocf_confirm_pay);
        aocf_confirm_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OwnCheckFillInfoActivity.this, PaymentActivity.class);
                OwnCheckFillInfoActivity.this.startActivity(intent);
            }
        });

        aocf_et_car_type = findViewById(R.id.aocf_et_car_type);
        aocf_et_car_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View contentView = LayoutInflater.from(OwnCheckFillInfoActivity.this).inflate(R.layout.layout_select_dialog, null);
//                View contentView = View.inflate(OwnCheckFillInfoActivity.this, R.layout.layout_select_dialog, null);
                final Dialog d = setDialog(OwnCheckFillInfoActivity.this, contentView);
                d.setContentView(contentView);
                contentView.findViewById(R.id.lsd_tv_cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        d.dismiss();
                    }
                });

                ListView listView = contentView.findViewById(R.id.lsd_list);
                listView.setAdapter(new ArrayAdapter<>(OwnCheckFillInfoActivity.this, android.R.layout.simple_list_item_1, carType));
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        d.dismiss();
                    }
                });
                d.show();
            }
        });

        aocf_et_pick_station = findViewById(R.id.aocf_et_pick_station);
        aocf_et_pick_station.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View contentView = LayoutInflater.from(OwnCheckFillInfoActivity.this).inflate(R.layout.layout_select_dialog, null);
//                View contentView = View.inflate(OwnCheckFillInfoActivity.this, R.layout.layout_select_dialog, null);
                final Dialog d = setDialog(OwnCheckFillInfoActivity.this, contentView);
                d.setContentView(contentView);
                contentView.findViewById(R.id.lsd_tv_cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        d.dismiss();
                    }
                });

                ListView listView = contentView.findViewById(R.id.lsd_list);
                listView.setAdapter(new ArrayAdapter<>(OwnCheckFillInfoActivity.this, android.R.layout.simple_list_item_1, stations));
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        d.dismiss();
                    }
                });
                d.show();
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
        return R.layout.activity_own_check_fill_info;
    }

    @Override
    protected int getTitleId() {
        return R.string.text_fill_info;
    }
}
