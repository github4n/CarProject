package com.littleant.carrepair.activies.aftersale;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.littleant.carrepair.R;
import com.littleant.carrepair.activies.BaseActivity;
import com.littleant.carrepair.request.bean.BaseResponseBean;
import com.littleant.carrepair.request.bean.survey.SurveyListBean;
import com.littleant.carrepair.request.constant.ParamsConstant;
import com.littleant.carrepair.request.excute.aftersale.AftersaleAllCmd;
import com.littleant.carrepair.request.excute.survey.survey.SurveyQueryAllCmd;
import com.littleant.carrepair.utils.ProjectUtil;
import com.mh.core.task.MHCommandCallBack;
import com.mh.core.task.MHCommandExecute;
import com.mh.core.task.command.abstracts.MHCommand;
import com.mh.core.tools.MHToast;

/**
 * 文件描述:售后服务
 * 作者:莫进生
 * 创建时间:2018/10/19 0019
 * 版本号:1
 */


public class AftersaleActivity extends BaseActivity {
    private RecyclerView mList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mList = findViewById(R.id.acr_record_list);
        mList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        requestCheckRecord();
    }
    @Override
    protected int getLayoutId() {
         return R.layout.activity_after_sale;
    }

    @Override
    protected int getTitleId() {
        return R.string.text_user_center_aftersale_record;
    }

    @Override
    public void onClick(View v) {

    }
    private void requestCheckRecord() {
        AftersaleAllCmd aftersaleAllCmd=new AftersaleAllCmd(mContext,ParamsConstant.MAINTAIN_LIST_STATUS_WAIT_PAY );
        aftersaleAllCmd.setCallback(new MHCommandCallBack() {
            @Override
            public void cmdCallBack(MHCommand command) {
                BaseResponseBean responseBean = ProjectUtil.getBaseResponseBean(command.getResponse());
                Log.i("response", command.getResponse());
            }
        });
        MHCommandExecute.getInstance().asynExecute(mContext, aftersaleAllCmd);
    }
}
