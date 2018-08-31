package com.littleant.carrepair.activies;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.littleant.carrepair.R;
import com.littleant.carrepair.request.excute.user.addressinfo.AddressInfoQueryCmd;
import com.mh.core.task.MHCommandCallBack;
import com.mh.core.task.MHCommandExecute;
import com.mh.core.task.command.abstracts.MHCommand;

/**
 * 添加地址
 */
public class AddAddressActivity extends BaseActivity implements View.OnClickListener {
    private EditText et_add_name, et_add_phone, et_add_address;
    private TextView et_add_city;
    private CheckBox aa_cb_default;
    private Button aa_btn_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_address;
    }

    @Override
    protected int getTitleId() {
        return R.string.text_add_address;
    }

    @Override
    protected void init() {
        super.init();
        et_add_name = findViewById(R.id.et_add_name);

        et_add_phone = findViewById(R.id.et_add_phone);

        et_add_address = findViewById(R.id.et_add_address);

        et_add_city = findViewById(R.id.et_add_city);
        et_add_city.setOnClickListener(this);

        aa_cb_default = findViewById(R.id.aa_cb_default);

        aa_btn_save = findViewById(R.id.aa_btn_save);
        aa_btn_save.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.et_add_city:
                requestAddressInfo();
                break;

            case R.id.aa_btn_save:

                break;
        }
    }

    private void requestAddressInfo() {
        AddressInfoQueryCmd addressInfoQueryCmd = new AddressInfoQueryCmd(mContext);
        addressInfoQueryCmd.setCallback(new MHCommandCallBack() {
            @Override
            public void cmdCallBack(MHCommand command) {
                if(command != null) {
                    Log.i("response", command.getResponse());
                }
            }
        });
        MHCommandExecute.getInstance().asynExecute(mContext, addressInfoQueryCmd);
    }
}
