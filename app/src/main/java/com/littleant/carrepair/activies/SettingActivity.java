package com.littleant.carrepair.activies;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.littleant.carrepair.R;
import com.littleant.carrepair.request.bean.BaseResponseBean;
import com.littleant.carrepair.request.bean.UserMeBean;
import com.littleant.carrepair.request.constant.ParamsConstant;
import com.littleant.carrepair.request.excute.user.user.UserModifyMeCmd;
import com.littleant.carrepair.utils.ProjectUtil;
import com.littleant.circleimageview.CircleImageView;
import com.mh.core.task.MHCommandCallBack;
import com.mh.core.task.MHCommandExecute;
import com.mh.core.task.command.abstracts.MHCommand;
import com.mh.core.tools.MHToast;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class SettingActivity extends BaseActivity implements View.OnClickListener{

    private static final int REQUEST_GET_SINGLE_FILE = 100;
    private Button setting_logout;
    private CircleImageView setting_iv_icon;
    private EditText setting_name, setting_phone;
    private String phone, name, picUrl;
    private UserMeBean meBean;
    private Bitmap meIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            meBean = (UserMeBean) extras.getSerializable(ParamsConstant.EXTRA_USER_ME_BEAN);
        }

        upDateWidget();
    }

    private void upDateWidget() {
        if(meBean != null) {
            phone = meBean.getData().getPhone();
            name = meBean.getData().getName();
            picUrl = meBean.getData().getPic_url();
            setting_phone.setText(phone);
            setting_name.setText(name);
            Picasso.with(mContext).load(picUrl).into(setting_iv_icon);
        }
    }

    @Override
    protected void init() {
        super.init();
        setting_logout = findViewById(R.id.setting_logout);
        setting_logout.setOnClickListener(this);

        setting_name = findViewById(R.id.setting_name);

        setting_phone = findViewById(R.id.setting_phone);

        setting_iv_icon = findViewById(R.id.setting_iv_icon);
        setting_iv_icon.setOnClickListener(this);

        mOptionText.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected int getTitleId() {
        return R.string.text_setting;
    }

    @Override
    protected int getOptionStringId() {
        return R.string.text_setting_save;
    }

    @Override
    public void onClick(View v) {
        String newPhone = setting_phone.getText().toString();
        String newName = setting_name.getText().toString();
        switch (v.getId()) {
            case R.id.setting_logout:
                SettingActivity.this.setResult(ParamsConstant.ACTIVITY_RESULT_LOGOUT);
                SettingActivity.this.finish();
                break;

            case R.id.header_option_text: //保存信息
                if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(name)) {
                    MHToast.showS(mContext, R.string.need_finish_info);
                    return;
                }
                if (!ProjectUtil.checkPhone(mContext, phone)) {
                    MHToast.showS(mContext, R.string.phone_wrong);
                    return;
                }
                if(newPhone.equals(phone) && newName.equals(name) && meIcon == null) {
                    MHToast.showS(mContext, R.string.info_not_modify);
                    return;
                }
                // TODO: 2018/8/29 确认没有修改头像时，是否不传pic参数
                UserModifyMeCmd userModifyMeCmd = new UserModifyMeCmd(mContext, phone, name, meIcon);
                userModifyMeCmd.setCallback(new MHCommandCallBack() {
                    @Override
                    public void cmdCallBack(MHCommand command) {
                        if (command != null) {
                            Log.i("ModifyMeCmd response", command.getResponse());
                            BaseResponseBean responseBean = ProjectUtil.getBaseResponseBean(command.getResponse());
                            if (responseBean != null && responseBean.getCode() == ParamsConstant.REAPONSE_CODE_SUCCESS) {
                                SettingActivity.this.setResult(ParamsConstant.ACTIVITY_RESULT_ME_MODIFY);
                                SettingActivity.this.finish();
                            }
                        } else {
                            MHToast.showS(mContext, R.string.request_fail);
                        }
                    }
                });
                MHCommandExecute.getInstance().asynExecute(mContext, userModifyMeCmd);
                break;

            case R.id.setting_iv_icon://选择图片
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select Picture"),REQUEST_GET_SINGLE_FILE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_GET_SINGLE_FILE && resultCode == RESULT_OK) {
            Uri uri_data = data.getData();
            try {
                meIcon = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri_data);
//                Picasso.with(mContext).load(uri_data).into(setting_iv_icon);
                setting_iv_icon.setImageBitmap(meIcon);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
