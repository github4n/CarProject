package com.littleant.carrepair.activies.annualcheck;

import android.app.Dialog;
import android.content.Intent;
import android.support.constraint.Constraints;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.CheckBox;
import android.widget.TextView;

import com.littleant.carrepair.R;
import com.littleant.carrepair.activies.BaseActivity;
import com.littleant.carrepair.request.bean.survey.SurveyInfoBean;
import com.littleant.carrepair.request.constant.ParamsConstant;
import com.littleant.carrepair.request.excute.survey.survey.SurveyInfoCmd;
import com.littleant.carrepair.utils.ProjectUtil;
import com.mh.core.task.MHCommandCallBack;
import com.mh.core.task.MHCommandExecute;
import com.mh.core.task.command.abstracts.MHCommand;
import com.mh.core.tools.MHToast;

public abstract class BaseFlowActivity extends BaseActivity {

    protected View include2;

    //代驾年检控件
    protected CheckBox lacf_tv_fill, lacf_tv_pick, lacf_tv_start, lacf_tv_return;
    protected TextView lacf_tv_check_know;

    //自驾年检控件
    protected CheckBox locf_tv_fill, locf_tv_start;
    protected TextView locf_tv_check_know;

    protected String knowUrl;

    @Override
    protected void init() {
        super.init();
        lacf_tv_fill = findViewById(R.id.lacf_tv_fill);
        lacf_tv_pick = findViewById(R.id.lacf_tv_pick);
        lacf_tv_start = findViewById(R.id.lacf_tv_start);
        lacf_tv_return = findViewById(R.id.lacf_tv_return);
        lacf_tv_check_know = findViewById(R.id.lacf_tv_check_know);

        locf_tv_fill = findViewById(R.id.locf_tv_fill);
        locf_tv_start = findViewById(R.id.locf_tv_start);
        locf_tv_check_know = findViewById(R.id.locf_tv_check_know);
    }

    protected void showKnowDialog() {
        final Dialog d = new Dialog(mContext, R.style.MyTransparentDialog);
        View contentView = View.inflate(mContext, R.layout.layout_survey_info, null);
        DisplayMetrics dm = mContext.getApplicationContext().getResources().getDisplayMetrics();
        int dialogWidth = (int) (dm.widthPixels * 0.7);
        int dialogHeight = (int) (dm.heightPixels * 0.6);
        d.setContentView(contentView, new Constraints.LayoutParams(dialogWidth, dialogHeight));
        WebView webView = contentView.findViewById(R.id.lsi_webview);
        webView.loadUrl(knowUrl);
        contentView.findViewById(R.id.lsi_btn_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                d.dismiss();
            }
        });
        d.show();
    }

    protected void requestKnowUrl() {
        SurveyInfoCmd cmd = new SurveyInfoCmd(mContext);
        cmd.setCallback(new MHCommandCallBack() {
            @Override
            public void cmdCallBack(MHCommand command) {
                if (command != null) {
                    Log.i("response", command.getResponse());
                    SurveyInfoBean responseBean = ProjectUtil.getBaseResponseBean(command.getResponse(), SurveyInfoBean.class);
                    if (responseBean != null && ParamsConstant.REAPONSE_CODE_SUCCESS == responseBean.getCode()) {
                        knowUrl = responseBean.getData().getUrl();
                        showKnowDialog();
                    } else if(responseBean != null && ParamsConstant.REAPONSE_CODE_AUTH_FAIL == responseBean.getCode()) {
                        Intent intent = ProjectUtil.tokenExpiredIntent(mContext);
                        startActivity(intent);
                    } else if (responseBean != null && !TextUtils.isEmpty(responseBean.getMsg())) {
                        MHToast.showS(mContext, responseBean.getMsg());
                    }
                } else {
                    MHToast.showS(mContext, R.string.request_fail);
                }
            }
        });
        MHCommandExecute.getInstance().asynExecute(mContext, cmd);
    }


}
