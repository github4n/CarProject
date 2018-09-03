package com.littleant.carrepair.activies;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.Constraints;
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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.littleant.carrepair.R;
import com.littleant.carrepair.request.bean.BaseResponseBean;
import com.littleant.carrepair.request.bean.GarageInfo;
import com.littleant.carrepair.request.bean.GarageListBean;
import com.littleant.carrepair.request.bean.SurveyStationListBean;
import com.littleant.carrepair.request.constant.ParamsConstant;
import com.littleant.carrepair.request.excute.survey.surveystation.SurveyStationQueryAllCmd;
import com.littleant.carrepair.utils.ProjectUtil;
import com.mh.core.task.MHCommandCallBack;
import com.mh.core.task.MHCommandExecute;
import com.mh.core.task.command.abstracts.MHCommand;
import com.mh.core.tools.MHToast;

import java.util.ArrayList;
import java.util.List;

public class OwnCheckFillInfoActivity extends BaseActivity implements View.OnClickListener {
    private TextView aocf_confirm_pay, aocf_et_car_type, aocf_et_pick_station;
    private String[] carType;
    private String[] stations;
    private List<SurveyStationListBean.SurveyStationInfo> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void init() {
        super.init();

        carType = getResources().getStringArray(R.array.annual_car_type);

        aocf_confirm_pay = findViewById(R.id.aocf_confirm_pay);
        aocf_confirm_pay.setOnClickListener(this);

        aocf_et_car_type = findViewById(R.id.aocf_et_car_type);
        aocf_et_car_type.setOnClickListener(this);

        aocf_et_pick_station = findViewById(R.id.aocf_et_pick_station);
        aocf_et_pick_station.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.aocf_confirm_pay:
                Intent intent = new Intent(OwnCheckFillInfoActivity.this, PaymentActivity.class);
                OwnCheckFillInfoActivity.this.startActivity(intent);
                break;

            case R.id.aocf_et_car_type:
                showList(carType, aocf_et_car_type);
                break;

            case R.id.aocf_et_pick_station:
                if(stations == null || stations.length < 1) {
                    requestStation();
                } else {
                    showList(stations, aocf_et_pick_station);
                }
                break;

        }
    }

    private void requestStation() {
        SurveyStationQueryAllCmd surveyStationQueryAllCmd = new SurveyStationQueryAllCmd(mContext);
        surveyStationQueryAllCmd.setCallback(new MHCommandCallBack() {
            @Override
            public void cmdCallBack(MHCommand command) {
                if (command != null) {
                    Log.i("response", command.getResponse());
                    BaseResponseBean responseBean = ProjectUtil.getBaseResponseBean(command.getResponse());
                    if(responseBean != null && ParamsConstant.REAPONSE_CODE_SUCCESS == responseBean.getCode()) {
                        SurveyStationListBean listBean = ProjectUtil.getBaseResponseBean(command.getResponse(), SurveyStationListBean.class);
                        data = listBean.getData();
                        if(data != null && data.size() > 0) {

                        }
                    } else if(responseBean != null && !TextUtils.isEmpty(responseBean.getMsg())) {
                        MHToast.showS(mContext, responseBean.getMsg());
                    }
                } else {
                    MHToast.showS(mContext, R.string.request_fail);
                }
            }
        });
        MHCommandExecute.getInstance().asynExecute(mContext, surveyStationQueryAllCmd);
    }

    private void showList(final String[] list, final TextView showView) {
        if(list == null || list.length < 1) {
            return;
        }
        View contentView2 = LayoutInflater.from(OwnCheckFillInfoActivity.this).inflate(R.layout.layout_select_dialog, null);
//                View contentView = View.inflate(OwnCheckFillInfoActivity.this, R.layout.layout_select_dialog, null);
        final Dialog d2 = setDialog(OwnCheckFillInfoActivity.this, contentView2);
        d2.setContentView(contentView2);
        contentView2.findViewById(R.id.lsd_tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                d2.dismiss();
            }
        });

        ListView listView2 = contentView2.findViewById(R.id.lsd_list);
        listView2.setAdapter(new ArrayAdapter<>(OwnCheckFillInfoActivity.this, android.R.layout.simple_list_item_1, list));
        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                d2.dismiss();
                showView.setText(list[i]);
            }
        });
        d2.show();
    }
}
