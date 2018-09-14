package com.littleant.carrepair.activies.login;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
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
import com.littleant.carrepair.request.excute.login.ResetCmd;
import com.littleant.carrepair.request.utils.DataHelper;
import com.littleant.carrepair.utils.ProjectUtil;
import com.mh.core.task.MHCommandCallBack;
import com.mh.core.task.MHCommandExecute;
import com.mh.core.task.command.abstracts.MHCommand;
import com.mh.core.tools.MHToast;

public class ForgetPasswordActivity extends BaseActivity {
    private EditText afp_et_phone, afp_et_auth, afp_et_new_password, afp_et_confirm_password;
    private TextView afp_auth;
    private Button afp_btn_save;
    private String authCode, phone, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_forget_password;
    }

    @Override
    protected int getTitleId() {
        return R.string.login_reset_password;
    }

    @Override
    protected int getBackgroundColor() {
        return android.R.color.transparent;
    }

    @Override
    protected void init() {
        super.init();
        afp_et_phone = findViewById(R.id.afp_et_phone);
        phone = DataHelper.getPhone(this);
        if(!TextUtils.isEmpty(phone)) {
            afp_et_phone.setText(phone);
        }

        afp_et_auth = findViewById(R.id.afp_et_auth);

        afp_et_new_password = findViewById(R.id.afp_et_new_password);

        afp_et_confirm_password = findViewById(R.id.afp_et_confirm_password);

        afp_auth = findViewById(R.id.afp_auth);
        afp_auth.setOnClickListener(this);

        afp_btn_save = findViewById(R.id.afp_btn_save);
        afp_btn_save.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        phone = afp_et_phone.getText().toString();
        password = afp_et_new_password.getText().toString();
        authCode = afp_et_auth.getText().toString();
        switch (v.getId()) {
            case R.id.afp_auth:
                if (!ProjectUtil.checkPhone(mContext, phone)) {
                    MHToast.showS(mContext, R.string.phone_wrong);
                    return;
                }
                MessageCmd messageCmd = new MessageCmd(this, phone, ParamsConstant.MessageType.RESET);
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
                afp_auth.setClickable(false);
                CountDownTimer timer = new CountDownTimer(1000 * 60, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        // 每秒刷新提示
                        int i = (int) (millisUntilFinished / 1000);
                        afp_auth.setText(i + "s");
                        afp_auth.setBackgroundResource(R.drawable.shape_gray);
                        afp_auth.setTextColor(mContext.getResources().getColor(R.color.color_sub_line));
                    }

                    @Override
                    public void onFinish() {
                        afp_auth.setClickable(true);
                        afp_auth.setText(R.string.login_request_auth_number);
                        afp_auth.setTextColor(mContext.getResources().getColor(R.color.color_main));
                        afp_auth.setBackgroundResource(R.drawable.shape_blue);
                    }
                };
                timer.start();
                break;

            case R.id.afp_btn_save:
                if (!ProjectUtil.checkPhone(mContext, phone)) {
                    MHToast.showS(mContext, R.string.phone_wrong);
                    return;
                }
                String confirmPassword = afp_et_confirm_password.getText().toString();
                if (!password.equals(confirmPassword)) {
                    MHToast.showS(mContext, R.string.password_different);
                    return;
                }
                if (!ProjectUtil.checkPassword(mContext, password)) {
                    MHToast.showS(mContext, R.string.password_wrong);
                    return;
                }
                ResetCmd resetCmd = new ResetCmd(mContext, phone, password, authCode);
                resetCmd.setCallback(new MHCommandCallBack() {
                    @Override
                    public void cmdCallBack(MHCommand command) {
                        if (command != null) {
                            Log.i("reset response", command.getResponse());
                            BaseResponseBean responseBean = ProjectUtil.getBaseResponseBean(command.getResponse());
                            if(responseBean != null && ParamsConstant.REAPONSE_CODE_SUCCESS == responseBean.getCode()) {
//                            if(responseBean != null && ParamsConstant.REAPONSE_CODE_SUCCESS.equals(responseBean.getCode())) {
                                DataHelper.savePhone(mContext, phone);
                                DataHelper.savePassword(mContext, password);
                                ForgetPasswordActivity.this.setResult(ParamsConstant.REAPONSE_CODE_SUCCESS);
                                ForgetPasswordActivity.this.finish();
                            }
                        } else {
                            MHToast.showS(mContext, R.string.request_fail);
                        }
                    }
                });
                MHCommandExecute.getInstance().asynExecute(mContext, resetCmd);
                break;
        }
    }
}
