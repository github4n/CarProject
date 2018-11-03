package com.littleant.carrepair.activies.annualcheck;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
                List<ObjList> obj_list = survey_fail_upload.getObj_list();
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
        aacf_iv_pic2 = findViewById(R.id.aacf_iv_pic2);
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
}
