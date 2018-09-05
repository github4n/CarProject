package com.littleant.carrepair.activies.login;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.littleant.carrepair.R;
import com.littleant.carrepair.activies.main.MainActivity;
import com.littleant.carrepair.request.bean.LoginBean;
import com.littleant.carrepair.request.constant.ParamsConstant;
import com.littleant.carrepair.request.excute.login.LoginCmd;
import com.littleant.carrepair.request.utils.DataHelper;
import com.littleant.carrepair.utils.ProjectUtil;
import com.mh.core.task.MHCommandCallBack;
import com.mh.core.task.MHCommandExecute;
import com.mh.core.task.command.abstracts.MHCommand;
import com.mh.core.tools.MHToast;

/**
 * 登入页面
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    //登录按钮
    private Button loginBtn;
    //手机号、密码
    private EditText phoneEdt, pswEdt;
    //获取验证码
    private TextView al_forget_pw, al_register;
    private static final int REQUEST_REGISTER = 1000;
    private static final int REQUEST_RESET = 1001;
    private String phone, password;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = this;
        loginBtn = findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(this);

        phoneEdt = findViewById(R.id.phoneEdt);
        phone = DataHelper.getPhone(this);
        if(!TextUtils.isEmpty(phone)) {
            phoneEdt.setText(phone);
        }

        pswEdt = findViewById(R.id.pswEdt);
        password = DataHelper.getPassword(this);
        if(!TextUtils.isEmpty(password)) {
            pswEdt.setText(password);
        }

        al_forget_pw = findViewById(R.id.al_forget_pw);
        al_forget_pw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                LoginActivity.this.startActivityForResult(intent, REQUEST_RESET);
            }
        });

        al_register = findViewById(R.id.al_register);
        al_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivityForResult(intent, REQUEST_REGISTER);
            }
        });

    }

    @Override
    public void onClick(View v) {
        phone = phoneEdt.getText().toString();
        password = pswEdt.getText().toString();
        switch (v.getId()) {
            case R.id.loginBtn:
                if(TextUtils.isEmpty(phoneEdt.getText().toString()) || TextUtils.isEmpty(pswEdt.getText().toString())) {
                    MHToast.showS(mContext, R.string.need_finish_info);
                    return;
                }
                if (!ProjectUtil.checkPhone(mContext, phone)) {
                    MHToast.showS(mContext, R.string.phone_wrong);
                    return;
                }
                if (!ProjectUtil.checkPassword(mContext, password)) {
                    MHToast.showS(mContext, R.string.password_wrong);
                    return;
                }
                LoginCmd loginCmd = new LoginCmd(this, phone, password);
                loginCmd.setCallback(new MHCommandCallBack() {
                    @Override
                    public void cmdCallBack(MHCommand command) {
                        if (command != null) {
                            Log.i("login response", command.getResponse());
                            LoginBean responseBean = ProjectUtil.getBaseResponseBean(command.getResponse(), LoginBean.class);
                            if(responseBean != null && ParamsConstant.REAPONSE_CODE_SUCCESS == responseBean.getCode()) {
//                            if(responseBean != null && ParamsConstant.REAPONSE_CODE_SUCCESS.equals(responseBean.getCode())) {
//                                LoginBean loginBean = new Gson().fromJson(responseBean.getData().toString(), LoginBean.class);
                                int uid = responseBean.getData().getUser_id();
                                String token = responseBean.getData().getToken();
                                String expire = responseBean.getData().getExpire();
                                DataHelper.saveUserId(mContext, uid);
                                DataHelper.saveToken(mContext, token);
                                DataHelper.saveExpire(mContext, expire);
                                DataHelper.savePhone(mContext, phone);
                                DataHelper.savePassword(mContext, password);
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                LoginActivity.this.startActivity(intent);
                                LoginActivity.this.finish();
                            } else if(responseBean != null && !TextUtils.isEmpty(responseBean.getMsg())) {
                                MHToast.showS(mContext, responseBean.getMsg());
                            } else {
                                MHToast.showS(mContext, R.string.login_fail);
                            }
                        } else {
                            MHToast.showS(mContext, R.string.request_fail);
                        }
                    }
                });
                MHCommandExecute.getInstance().asynExecute(mContext, loginCmd);
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_REGISTER && resultCode == ParamsConstant.REAPONSE_CODE_SUCCESS) {
            phone = DataHelper.getPhone(this);
            password = DataHelper.getPassword(this);
            phoneEdt.setText(phone);
            pswEdt.setText(password);
        } else if(requestCode == REQUEST_RESET && resultCode == ParamsConstant.REAPONSE_CODE_SUCCESS) {
            phone = DataHelper.getPhone(this);
            password = DataHelper.getPassword(this);
            phoneEdt.setText(phone);
            pswEdt.setText(password);
        }
    }
}
