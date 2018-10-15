package com.littleant.carrepair.activies;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.littleant.carrepair.R;
import com.littleant.carrepair.activies.login.LoginActivity;
import com.littleant.carrepair.request.bean.BaseResponseBean;
import com.littleant.carrepair.request.bean.system.SystemCoverBean;
import com.littleant.carrepair.request.constant.ParamsConstant;
import com.littleant.carrepair.request.excute.system.SystemCoverCmd;
import com.littleant.carrepair.utils.ProjectUtil;
import com.mh.core.task.MHCommandCallBack;
import com.mh.core.task.MHCommandExecute;
import com.mh.core.task.command.abstracts.MHCommand;
import com.mh.core.tools.MHLogUtil;
import com.mh.core.tools.MHToast;

import cn.jpush.android.api.JPushInterface;

public class SplashActivity extends AppCompatActivity {
    private Context mContext;
    private SystemCoverBean.CoverList data;
    public static final String COVER_LIST = "cover_list";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // TODO: 2018/9/17 正式发布时去掉日志
        MHLogUtil.enableLogLevel(Log.VERBOSE);

        mContext = this;

        JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);

        final boolean launched = ProjectUtil.getLaunched(SplashActivity.this);
        if(!launched) {
            requestLaunchBanner();
        }

        CountDownTimer countDownTimer = new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                Intent intent;
                if(launched) {
                    intent = new Intent(SplashActivity.this, LoginActivity.class);
                } else {
                    intent = new Intent(SplashActivity.this, FirstLaunchActivity.class);
                    if(data != null && data.getPic_url_list() != null && data.getPic_url_list().size() > 0) {
                        intent.putExtra(COVER_LIST, data);
                    }
                }
                startActivity(intent);
                finish();
            }
        };
        countDownTimer.start();
    }

    private void requestLaunchBanner() {
        SystemCoverCmd systemCoverCmd = new SystemCoverCmd(this);
        systemCoverCmd.setCallback(new MHCommandCallBack() {
            @Override
            public void cmdCallBack(MHCommand command) {
                if (command != null) {
                    Log.i("response", command.getResponse());
                    BaseResponseBean responseBean = ProjectUtil.getBaseResponseBean(command.getResponse(), BaseResponseBean.class);
                    if(responseBean != null && ParamsConstant.REAPONSE_CODE_SUCCESS == responseBean.getCode()) {
                        SystemCoverBean systemCoverBean = ProjectUtil.getBaseResponseBean(command.getResponse(), SystemCoverBean.class);
                        data = systemCoverBean.getData();
                    }
                } else {
                    MHToast.showS(mContext, R.string.request_fail);
                }

            }
        });
        MHCommandExecute.getInstance().asynExecute(mContext, systemCoverCmd);
    }

    @Override
    public void onBackPressed() {
    }
}
