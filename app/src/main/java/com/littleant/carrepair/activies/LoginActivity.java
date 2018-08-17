package com.littleant.carrepair.activies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.littleant.carrepair.R;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginBtn = findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(this);

        phoneEdt = findViewById(R.id.phoneEdt);

        pswEdt = findViewById(R.id.pswEdt);

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
                LoginActivity.this.startActivity(intent);
            }
        });


//        auth = findViewById(R.id.auth);
//        auth.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginBtn:
                if(TextUtils.isEmpty(phoneEdt.getText().toString()) || TextUtils.isEmpty(pswEdt.getText().toString())) {
                    Toast.makeText(this, "账号或密码为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                LoginActivity.this.startActivity(intent);
                LoginActivity.this.finish();
                break;

//            case R.id.auth:
//                Toast.makeText(this, "点击获取验证码按钮", Toast.LENGTH_SHORT).show();
//                break;
        }
    }
}
