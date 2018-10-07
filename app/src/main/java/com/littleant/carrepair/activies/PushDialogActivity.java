package com.littleant.carrepair.activies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.littleant.carrepair.fragment.PushDialogFragment;
import com.littleant.carrepair.request.bean.PushBean;
import com.littleant.carrepair.utils.ProjectUtil;

import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;

public class PushDialogActivity extends AppCompatActivity {

    /*{
        'data':{
        'id':0,
                'state':false,
                'msg':'失败'
    }
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        boolean isSuccess = false;
        int id = 0;
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            String json = extras.getString(JPushInterface.EXTRA_ALERT, "");
            PushBean pushBean = ProjectUtil.getBaseResponseBean(json, PushBean.class);
            id = pushBean.getData().getId();
            isSuccess = pushBean.getData().isState();
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
