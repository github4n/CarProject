package com.littleant.carrepair.activies;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.littleant.carrepair.R;
import com.littleant.carrepair.request.bean.BaseResponseBean;
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
                LoginActivity.this.startActivity(intent);
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
                    Toast.makeText(this, "账号或密码为空", Toast.LENGTH_SHORT).show();
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
                            Log.i("register response", command.getResponse());
                            BaseResponseBean responseBean = ProjectUtil.getBaseResponseBean(command.getResponse());
                            if(responseBean != null && ParamsConstant.REAPONSE_CODE_SUCCESS.equals(responseBean.getCode())) {
                                LoginBean loginBean = new Gson().fromJson(responseBean.getData().toString(), LoginBean.class);
                                int uid = loginBean.getUser_id();
                                String token = loginBean.getToken();
                                String expire = loginBean.getExpire();
                                DataHelper.saveUserId(mContext, uid);
                                DataHelper.saveToken(mContext, token);
                                DataHelper.saveExpire(mContext, expire);
                            } else {
                                MHToast.showS(mContext, R.string.login_fail);
                            }
                        } else {
                            MHToast.showS(mContext, R.string.request_fail);
                        }
                    }
                });
                MHCommandExecute.getInstance().asynExecute(mContext, loginCmd);
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                LoginActivity.this.startActivity(intent);
                LoginActivity.this.finish();
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_REGISTER && resultCode == 100) {
            phone = DataHelper.getPhone(this);
            password = DataHelper.getPassword(this);
            phoneEdt.setText(phone);
            pswEdt.setText(password);
        }
    }
}
