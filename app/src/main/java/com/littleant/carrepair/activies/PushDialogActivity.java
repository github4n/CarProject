package com.littleant.carrepair.activies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.littleant.carrepair.fragment.PushDialogFragment;
import com.littleant.carrepair.request.bean.push.PushBean;
import com.littleant.carrepair.utils.ProjectUtil;

import cn.jpush.android.api.JPushInterface;

public class PushDialogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        boolean isSuccess = false;
        int id = 0;
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            String json = extras.getString(JPushInterface.EXTRA_EXTRA, "");
            PushBean pushBean = ProjectUtil.getBaseResponseBean(json, PushBean.class);
            id = pushBean.getData().getData().getId();
            isSuccess = pushBean.getData().getData().isState();
        } else {
            finish();
        }

        PushDialogFragment dialogFragment = PushDialogFragment.newInstance(isSuccess);
        dialogFragment.setListener(new PushDialogFragment.PushDialogListener() {
            @Override
            public void onClickAgain() {
                finish();
            }

            @Override
            public void onClickOk() {
                finish();
            }
        });

        dialogFragment.show(getFragmentManager(), PushDialogActivity.class.getSimpleName());


    }

}
