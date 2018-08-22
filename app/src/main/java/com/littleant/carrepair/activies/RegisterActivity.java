package com.littleant.carrepair.activies;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.littleant.carrepair.R;
import com.littleant.carrepair.request.excute.RegisterCmd;
import com.littleant.carrepair.utils.ProjectUtil;
import com.mh.core.task.MHCommandCallBack;
import com.mh.core.task.MHCommandExecute;
import com.mh.core.task.command.abstracts.MHCommand;
import com.mh.core.tools.MHToast;

public class RegisterActivity extends BaseActivity {
    private EditText ar_et_phone, ar_et_auth, ar_et_new_password, ar_et_confirm_password;
    private TextView ar_auth;
    private Button ar_btn_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        ar_et_phone = findViewById(R.id.ar_et_phone);
        ar_et_auth = findViewById(R.id.ar_et_auth);
        ar_et_new_password = findViewById(R.id.ar_et_new_password);
        ar_et_confirm_password = findViewById(R.id.ar_et_confirm_password);

        ar_auth = findViewById(R.id.ar_auth);

        ar_btn_save = findViewById(R.id.ar_btn_save);
        ar_btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = ar_et_phone.getText().toString();
                if(!ProjectUtil.checkPhone(mContext, phone)) {
                    MHToast.showS(mContext, R.string.phone_wrong);
                    return;
                }
                String password = ar_et_new_password.getText().toString();
                String confirmPassword = ar_et_confirm_password.getText().toString();
                if(!password.equals(confirmPassword)) {
                    MHToast.showS(mContext, R.string.password_different);
                    return;
                }
                if(!ProjectUtil.checkPassword(mContext, password)) {
                    MHToast.showS(mContext, R.string.password_wrong);
                    return;
                }
                RegisterCmd registerCmd = new RegisterCmd(mContext, phone, password);
                registerCmd.setCommandMsg(mContext.getResources().getString(R.string.hint_waiting));
                registerCmd.setCallback(new MHCommandCallBack() {
                    @Override
                    public void cmdCallBack(MHCommand command) {
                        if(command != null) {
                            Log.i("register response", command.getResponse());
                        }
                    }
                });
                MHCommandExecute.getInstance().asynExecute(mContext, registerCmd);
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected int getTitleId() {
        return 0;
    }
}
