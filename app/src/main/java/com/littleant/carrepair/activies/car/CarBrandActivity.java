package com.littleant.carrepair.activies.car;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;

import com.littleant.carrepair.R;
import com.littleant.carrepair.activies.BaseActivity;
import com.littleant.carrepair.fragment.MainFragment;
import com.littleant.carrepair.request.bean.carbrand.CarBrandLetterList;
import com.littleant.carrepair.request.bean.carbrand.CarBrandList;
import com.littleant.carrepair.request.excute.user.car.CarBrandQueryCmd;
import com.littleant.carrepair.utils.ProjectUtil;
import com.mh.core.task.MHCommandCallBack;
import com.mh.core.task.MHCommandExecute;
import com.mh.core.task.command.abstracts.MHCommand;
import com.mh.core.tools.MHToast;

public class CarBrandActivity extends BaseActivity {

//    public static final String BRAND_NAME = "brand_name";
//    public static final String BRAND_CODE = "brand_code";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestCarBrand();
    }

    private void requestCarBrand() {
        CarBrandQueryCmd carBrandQueryCmd = new CarBrandQueryCmd(mContext);
        carBrandQueryCmd.setCallback(new MHCommandCallBack() {
            @Override
            public void cmdCallBack(MHCommand command) {
                if (command != null) {
                    Log.i("response", command.getResponse());
                    CarBrandList carBrandList = ProjectUtil.getBaseResponseBean(command.getResponse(), CarBrandList.class);
                    CarBrandLetterList data = carBrandList.getData();

                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.add(R.id.acb_fragment, CarBrandFirstFragment.newInstance(data), CarBrandFirstFragment.class.getSimpleName());
                    transaction.commit();

                } else {
                    MHToast.showS(mContext, R.string.request_fail);
                }
            }
        });
        MHCommandExecute.getInstance().asynExecute(mContext, carBrandQueryCmd);
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_car_brand;
    }

    @Override
    protected int getTitleId() {
        return R.string.select_car_brand;
    }

    @Override
    public void onClick(View view) {

    }
}
