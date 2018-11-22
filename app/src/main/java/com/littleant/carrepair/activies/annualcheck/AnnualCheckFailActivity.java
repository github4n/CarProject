package com.littleant.carrepair.activies.annualcheck;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.littleant.carrepair.R;
import com.littleant.carrepair.activies.pay.PaymentActivity;
import com.littleant.carrepair.activies.repair.view.RepairItemView;
import com.littleant.carrepair.request.bean.survey.FailureListBean;
import com.littleant.carrepair.request.bean.survey.ObjList;
import com.littleant.carrepair.request.bean.survey.SurveyInfo;
import com.littleant.carrepair.request.bean.survey.SurveyPicList;
import com.littleant.carrepair.request.constant.ParamsConstant;
import com.mh.core.tools.MHToast;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.littleant.carrepair.activies.annualcheck.AnnualCheckRecordActivity.STATE_CHECKING;
import static com.littleant.carrepair.activies.annualcheck.AnnualCheckRecordActivity.STATE_WAIT_CHECK;
import static com.littleant.carrepair.activies.annualcheck.AnnualCheckRecordActivity.SURVEY_INFO;
import static com.littleant.carrepair.activies.pay.PaymentActivity.PAYMENT_FROM;

/**
 * 年检没过的详情页
 */
public class AnnualCheckFailActivity extends BaseFlowActivity {

    //图片1、图片2
    private ImageView aacf_iv_pic1, aacf_iv_pic2;
    //图片描述1、图片描述2、确认支付
    private TextView aacf_tv_des1, aacf_tv_des2, acf_confirm_pay;
    //年检未过项
    private LinearLayout aacf_ll_detail;
    private SurveyInfo info;
    private   List<ObjList> obj_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lacf_tv_start.setChecked(true);
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            info = (SurveyInfo) extras.getSerializable(SURVEY_INFO);
        }
        if(info != null) {
            SurveyPicList survey_fail_upload = info.getSurvey_fail_upload();
            if(survey_fail_upload != null) {
               obj_list = survey_fail_upload.getObj_list();
                if(obj_list != null && obj_list.size() > 0) {
                    Picasso.with(mContext).load(Uri.parse(obj_list.get(0).getPic_url())).resize(160, 100).into(aacf_iv_pic1);
                    aacf_tv_des1.setText(obj_list.get(0).getNote());
                    if(obj_list.size() > 1) {
                        Picasso.with(mContext).load(Uri.parse(obj_list.get(1).getPic_url())).resize(160, 100).into(aacf_iv_pic2);
                        aacf_tv_des2.setText(obj_list.get(1).getNote());
                    }
                }
            }
            showItemDetail(info.getFailure_list());
        } else {
            finish();
        }
    }

    @Override
    protected void init() {
        super.init();
        aacf_iv_pic1 = findViewById(R.id.aacf_iv_pic1);
        aacf_iv_pic1.setOnClickListener(this);
        aacf_iv_pic2 = findViewById(R.id.aacf_iv_pic2);
        aacf_iv_pic2.setOnClickListener(this);
        aacf_tv_des1 = findViewById(R.id.aacf_tv_des1);
        aacf_tv_des2 = findViewById(R.id.aacf_tv_des2);
        aacf_ll_detail = findViewById(R.id.aacf_ll_detail);
        acf_confirm_pay = findViewById(R.id.acf_confirm_pay);
        acf_confirm_pay.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_annual_check_fail;
    }

    @Override
    protected int getTitleId() {
        return R.string.text_check_feedback;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.acf_confirm_pay:
                Intent intent = new Intent(mContext, PaymentActivity.class);
                String from;
                if(info.isIs_self()) {
                    from = ParamsConstant.ORDER_ANNUAL_CHECK_OWN;
                } else {
                    from = ParamsConstant.ORDER_ANNUAL_CHECK;
                }
                intent.putExtra(PAYMENT_FROM, from);
                intent.putExtra(SURVEY_INFO ,info);
                startActivity(intent);
                break;
            case R.id.aacf_iv_pic1:
                initImageView(obj_list.get(0).getPic_url());
                break;
            case R.id.aacf_iv_pic2:
                initImageView(obj_list.get(1).getPic_url());
                break;
        }
    }

    private void showItemDetail(List<FailureListBean> failure_list) {
        if(failure_list != null && failure_list.size() > 0) {
            aacf_ll_detail.removeAllViews();
            FailureListBean failureListBean = failure_list.get(0);
            float total = failureListBean.getPrice();
            if(failureListBean.getFailureitem_list() != null && failureListBean.getFailureitem_list().size() > 0) {
                List<FailureListBean.FailureItemList> failureitem_list = failureListBean.getFailureitem_list();
                for (FailureListBean.FailureItemList bean : failureitem_list) {
                    RepairItemView repairItemView = new RepairItemView(mContext, bean.getName(), bean.getPrice());
                    aacf_ll_detail.addView(repairItemView, -1, -2);
                }
            }
            RepairItemView repairItemView = new RepairItemView(mContext, "费用总计：", total);
            aacf_ll_detail.addView(repairItemView, -1, -2);
        }
    }
    private void initImageView(String url) {
        final WindowManager windowManager = getWindowManager();
        final RelativeLayout relativeLayout = new RelativeLayout(this);
        relativeLayout.setBackgroundColor(Color.parseColor("#501F1F1F"));

        //relativeLayout.getBackground().setAlpha(100);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.width = LinearLayout.LayoutParams.MATCH_PARENT;
        layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        //FLAG_LAYOUT_IN_SCREEN
        layoutParams.flags = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        layoutParams.format = PixelFormat.RGBA_8888;//让背景透明，放大过程可以看到当前界面
        layoutParams.verticalMargin = 0;
        windowManager.addView(relativeLayout,layoutParams);

        final ImageView animationIV = new ImageView(this);
        animationIV.setScaleType(ImageView.ScaleType.FIT_CENTER);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        relativeLayout.addView(animationIV,params);
        relativeLayout.setFocusableInTouchMode(true);
        Picasso.with(this).load(url).into(animationIV);

        animationIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                windowManager.removeView(relativeLayout);
            }
        });

        relativeLayout.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (null != windowManager && null != relativeLayout) {
                        windowManager.removeView(relativeLayout);
                    }
                    return true;
                }
                return false;
            }
        });
    }

}
