package com.littleant.carrepair.activies.login;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.constraint.Constraints;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.littleant.carrepair.R;
import com.littleant.carrepair.activies.BaseActivity;
import com.littleant.carrepair.request.bean.BaseResponseBean;
import com.littleant.carrepair.request.bean.login.TermUrlBean;
import com.littleant.carrepair.request.constant.ParamsConstant;
import com.littleant.carrepair.request.excute.login.MessageCmd;
import com.littleant.carrepair.request.excute.login.RegisterCmd;
import com.littleant.carrepair.request.excute.system.ServiceUserAgreementCmd;
import com.littleant.carrepair.request.utils.DataHelper;
import com.littleant.carrepair.utils.ProjectUtil;
import com.mh.core.task.MHCommandCallBack;
import com.mh.core.task.MHCommandExecute;
import com.mh.core.task.command.abstracts.MHCommand;
import com.mh.core.tools.MHToast;

public class RegisterActivity extends BaseActivity {
    private EditText ar_et_name,ar_et_phone, ar_et_auth, ar_et_new_password;// ar_et_confirm_password;
    /**
     * 获取验证码
     */
    private TextView ar_auth;
    /**
     * 注册按钮
     */
    private Button ar_btn_save;
    private String name,authCode, phone, password;
    private String termUrl;
    private View ar_term_view;
    private CheckBox ar_cb_term;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void init() {
        ar_et_name=findViewById(R.id.ar_et_name);
        ar_et_phone = findViewById(R.id.ar_et_phone);
        ar_et_auth = findViewById(R.id.ar_et_auth);
        ar_et_new_password = findViewById(R.id.ar_et_new_password);
//        ar_et_confirm_password = findViewById(R.id.ar_et_confirm_password);

        ar_auth = findViewById(R.id.ar_auth);
        ar_auth.setOnClickListener(this);

        ar_btn_save = findViewById(R.id.ar_btn_save);
        ar_btn_save.setOnClickListener(this);

        ar_term_view = findViewById(R.id.ar_term_view);
        ar_term_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(termUrl)) {
                    requestTerm();
                } else {
                    showTermDialog(RegisterActivity.this, termUrl);
                }
            }
        });

        ar_cb_term = findViewById(R.id.ar_cb_term);
        ar_cb_term.setChecked(ProjectUtil.getTermRead(this));
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
    protected int getBackgroundColor() {
        return android.R.color.transparent;
    }

    @Override
    public void onClick(View v) {
        name=ar_et_name.getText().toString().trim();
        phone = ar_et_phone.getText().toString();
        password = ar_et_new_password.getText().toString();
        authCode = ar_et_auth.getText().toString();
        switch (v.getId()) {
            case R.id.ar_btn_save:
                if (!ProjectUtil.checkPhone(mContext, name)) {
                    MHToast.showS(mContext, R.string.name_wrong);
                    return;
                }
                if (!ProjectUtil.checkPhone(mContext, phone)) {
                    MHToast.showS(mContext, R.string.phone_wrong);
                    return;
                }
//                String confirmPassword = ar_et_confirm_password.getText().toString();
//                if (!password.equals(confirmPassword)) {
//                    MHToast.showS(mContext, R.string.password_different);
//                    return;
//                }
                if (!ProjectUtil.checkPassword(mContext, password)) {
                    MHToast.showS(mContext, R.string.password_wrong);
                    return;
                }
                if(!ar_cb_term.isChecked()) {
                    MHToast.showS(mContext, R.string.agree_term_first);
                    return;
                }
                RegisterCmd registerCmd = new RegisterCmd(mContext,name, phone, password, authCode);
                registerCmd.setCallback(new MHCommandCallBack() {
                    @Override
                    public void cmdCallBack(MHCommand command) {
                        if (command != null) {
                            Log.i("register response", command.getResponse());
                            BaseResponseBean responseBean = ProjectUtil.getBaseResponseBean(command.getResponse());
                            if(responseBean != null && ParamsConstant.REAPONSE_CODE_SUCCESS == responseBean.getCode()) {
//                            if(responseBean != null && ParamsConstant.REAPONSE_CODE_SUCCESS.equals(responseBean.getCode())) {
                                DataHelper.saveContractName(mContext, name);
                                DataHelper.savePhone(mContext, phone);
                                DataHelper.savePassword(mContext, password);
                                RegisterActivity.this.setResult(ParamsConstant.REAPONSE_CODE_SUCCESS);
                                RegisterActivity.this.finish();
                            } else if(responseBean != null && !TextUtils.isEmpty(responseBean.getMsg())) {
                                MHToast.showS(mContext, responseBean.getMsg());
                            } else {
                                MHToast.showS(mContext, R.string.register_fail);
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

    private void requestTerm() {
        ServiceUserAgreementCmd serviceUserAgreementCmd = new ServiceUserAgreementCmd(mContext);
        serviceUserAgreementCmd.setCallback(new MHCommandCallBack() {
            @Override
            public void cmdCallBack(MHCommand command) {
                if(command != null) {
                    TermUrlBean termUrlBean = ProjectUtil.getBaseResponseBean(command.getResponse(), TermUrlBean.class);
                    if(termUrlBean != null) {
                        termUrl = termUrlBean.getData().getUrl();
                        showTermDialog(RegisterActivity.this, termUrl);
                    }
                }
            }
        });
        MHCommandExecute.getInstance().asynExecute(mContext, serviceUserAgreementCmd);
    }

    private void showTermDialog(Activity activity, String url) {
        final Dialog d = new Dialog(activity, R.style.MyTransparentDialog);
        View contentView = View.inflate(activity, R.layout.layout_term, null);
        DisplayMetrics dm = activity.getApplicationContext().getResources().getDisplayMetrics();
        int dialogWidth = (int) (dm.widthPixels * 0.7);
        int dialogHeight = (int) (dm.heightPixels * 0.7);
        d.setContentView(contentView, new Constraints.LayoutParams(dialogWidth, dialogHeight));
        WebView webView = contentView.findViewById(R.id.lt_webview);
        webView.loadUrl(url);
        contentView.findViewById(R.id.lt_btn_no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ar_cb_term.setChecked(true);
                d.dismiss();
            }
        });
        contentView.findViewById(R.id.lt_btn_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ar_cb_term.setChecked(true);
                d.dismiss();
            }
        });
        d.show();
    }
}
