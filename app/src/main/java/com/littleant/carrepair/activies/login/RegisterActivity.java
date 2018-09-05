package com.littleant.carrepair.activies.login;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.littleant.carrepair.R;
import com.littleant.carrepair.activies.BaseActivity;
import com.littleant.carrepair.request.bean.BaseResponseBean;
import com.littleant.carrepair.request.constant.ParamsConstant;
import com.littleant.carrepair.request.excute.login.MessageCmd;
import com.littleant.carrepair.request.excute.login.RegisterCmd;
import com.littleant.carrepair.request.utils.DataHelper;
import com.littleant.carrepair.utils.ProjectUtil;
import com.mh.core.task.MHCommandCallBack;
import com.mh.core.task.MHCommandExecute;
import com.mh.core.task.command.abstracts.MHCommand;
import com.mh.core.tools.MHToast;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    private EditText ar_et_phone, ar_et_auth, ar_et_new_password, ar_et_confirm_password;
    /**
     * 获取验证码
     */
    private TextView ar_auth;
    /**
     * 注册按钮
     */
    private Button ar_btn_save;
    private String authCode, phone, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void init() {
        ar_et_phone = findViewById(R.id.ar_et_phone);
        ar_et_auth = findViewById(R.id.ar_et_auth);
        ar_et_new_password = findViewById(R.id.ar_et_new_password);
        ar_et_confirm_password = findViewById(R.id.ar_et_confirm_password);

        ar_auth = findViewById(R.id.ar_auth);
        ar_auth.setOnClickListener(this);

        ar_btn_save = findViewById(R.id.ar_btn_save);
        ar_btn_save.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected int getTitleId() {
        return 0;
    }

    @Override
    public void onClick(View v) {
        phone = ar_et_phone.getText().toString();
        password = ar_et_new_password.getText().toString();
        authCode = ar_et_auth.getText().toString();
        switch (v.getId()) {
            case R.id.ar_btn_save:
                if (!ProjectUtil.checkPhone(mContext, phone)) {
                    MHToast.showS(mContext, R.string.phone_wrong);
                    return;
                }
                String confirmPassword = ar_et_confirm_password.getText().toString();
                if (!password.equals(confirmPassword)) {
                    MHToast.showS(mContext, R.string.password_different);
                    return;
                }
                if (!ProjectUtil.checkPassword(mContext, password)) {
                    MHToast.showS(mContext, R.string.password_wrong);
                    return;
                }
                RegisterCmd registerCmd = new RegisterCmd(mContext, phone, password, authCode);
                registerCmd.setCallback(new MHCommandCallBack() {
                    @Override
                    public void cmdCallBack(MHCommand command) {
                        if (command != null) {
                            Log.i("register response", command.getResponse());
                            BaseResponseBean responseBean = ProjectUtil.getBaseResponseBean(command.getResponse());
                            if(responseBean != null && ParamsConstant.REAPONSE_CODE_SUCCESS == responseBean.getCode()) {
//                            if(responseBean != null && ParamsConstant.REAPONSE_CODE_SUCCESS.equals(responseBean.getCode())) {
                                DataHelper.savePhone(mContext, phone);
                                DataHelper.savePassword(mContext, password);
                                RegisterActivity.this.setResult(ParamsConstant.REAPONSE_CODE_SUCCESS);
                                RegisterActivity.this.finish();
                            }
                        } else {
                            MHToast.showS(mContext, R.string.request_fail);
                        }
                    }
                });
                MHCommandExecute.getInstance().asynExecute(mContext, registerCmd);
                break;

            case R.id.ar_auth:
                if (!ProjectUtil.checkPhone(mContext, phone)) {
                    MHToast.showS(mContext, R.string.phone_wrong);
                    return;
                }
                MessageCmd messageCmd = new MessageCmd(this, phone, ParamsConstant.MessageType.REGISTER);
                messageCmd.setCallback(new MHCommandCallBack() {
                    @Override
                    public void cmdCallBack(MHCommand command) {
                        if (command != null) {
                            BaseResponseBean responseBean = ProjectUtil.getBaseResponseBean(command.getResponse());
                            if(responseBean != null) {
                                Log.i("authcode response", responseBean.getMsg());
                                if(ParamsConstant.REAPONSE_CODE_SUCCESS != responseBean.getCode()) {
//                                if(!ParamsConstant.REAPONSE_CODE_SUCCESS.equals(responseBean.getCode())) {
                                    MHToast.showS(mContext, R.string.get_auth_code_fail);
                                }
                            }
                        } else {
                            MHToast.showS(mContext, R.string.request_fail);
                        }
                    }
                });
                MHCommandExecute.getInstance().asynExecute(mContext, messageCmd);
                ar_auth.setClickable(false);
                CountDownTimer timer = new CountDownTimer(1000 * 60, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        // 每秒刷新提示
                        int i = (int) (millisUntilFinished / 1000);
                        ar_auth.setText(i + "s");
                        ar_auth.setTextColor(mContext.getResources().getColor(R.color.color_sub_line));
                        ar_auth.setBackgroundResource(R.drawable.shape_gray);
                    }

                    @Override
                    public void onFinish() {
                        ar_auth.setClickable(true);
                        ar_auth.setText(R.string.login_request_auth_number);
                        ar_auth.setTextColor(mContext.getResources().getColor(R.color.color_main));
                        ar_auth.setBackgroundResource(R.drawable.shape_blue);
                    }
                };
                timer.start();
                break;
        }
    }
}
