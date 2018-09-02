package com.littleant.carrepair.activies;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.littleant.carrepair.R;
import com.littleant.carrepair.request.bean.BaseResponseBean;
import com.littleant.carrepair.request.constant.ParamsConstant;
import com.littleant.carrepair.request.excute.user.address.AddressAddCmd;
import com.littleant.carrepair.utils.ProjectUtil;
import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.citywheel.CityConfig;
import com.lljjcoder.style.citypickerview.CityPickerView;
import com.mh.core.task.MHCommandCallBack;
import com.mh.core.task.MHCommandExecute;
import com.mh.core.task.command.abstracts.MHCommand;
import com.mh.core.tools.MHToast;

/**
 * 添加地址
 */
public class AddAddressActivity extends BaseActivity implements View.OnClickListener {
    private EditText et_add_name, et_add_phone, et_add_address;
    private TextView et_add_city;
    private CheckBox aa_cb_default;
    private Button aa_btn_save;
    private String name, phone, node1, node2, node3, address;
    private boolean isDefault;
    //申明对象
    CityPickerView mPicker = new CityPickerView();


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

        mPicker.init(this);

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
                //添加默认的配置，不需要自己定义
                CityConfig cityConfig = new CityConfig.Builder().build();
                mPicker.setConfig(cityConfig);
                //监听选择点击事件及返回结果
                mPicker.setOnCityItemClickListener(new OnCityItemClickListener() {
                    @Override
                    public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {

                        //省份
                        if (province != null) {
                            Log.i("CityConfig", province.getId() + "");
                            Log.i("CityConfig", "province.getName()");
                            node1 = province.getName();
                        }

                        //城市
                        if (city != null) {
                            Log.i("CityConfig", city.getId() + "");
                            Log.i("CityConfig", "city.getName()");
                            node2 = city.getName();
                        }

                        //地区
                        if (district != null) {
                            Log.i("CityConfig", district.getId() + "");
                            Log.i("CityConfig", "district.getName()");
                            node3 = district.getName();
                        }
                        et_add_city.setText(node1 + node2 + node3);
                    }

                    @Override
                    public void onCancel() {
                    }
                });

                //显示
                mPicker.showCityPicker();
                break;

            case R.id.aa_btn_save:
                name = et_add_name.getText().toString();
                phone = et_add_phone.getText().toString();
                address = et_add_address.getText().toString();
                isDefault = aa_cb_default.isChecked();
                if(TextUtils.isEmpty(name) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(node1) ||
                        TextUtils.isEmpty(node2) || TextUtils.isEmpty(node3) || TextUtils.isEmpty(address)) {
                    MHToast.showS(mContext, R.string.need_finish_info);
                    return;
                }
                requestAddAddress();
                break;
        }
    }

    private void requestAddAddress() {
        AddressAddCmd addressAddCmd = new AddressAddCmd(mContext, name, phone, node1, node2, node3, address, isDefault);
        addressAddCmd.setCallback(new MHCommandCallBack() {
            @Override
            public void cmdCallBack(MHCommand command) {
                if (command != null) {
                    Log.i("response", command.getResponse());
                    BaseResponseBean responseBean = ProjectUtil.getBaseResponseBean(command.getResponse());
                    if(responseBean != null && ParamsConstant.REAPONSE_CODE_SUCCESS == responseBean.getCode()) {

                    } else if(responseBean != null && !TextUtils.isEmpty(responseBean.getMsg())) {
                        MHToast.showS(mContext, responseBean.getMsg());
                    }
                } else {
                    MHToast.showS(mContext, R.string.request_fail);
                }
            }
        });
        MHCommandExecute.getInstance().asynExecute(mContext, addressAddCmd);
    }
}
