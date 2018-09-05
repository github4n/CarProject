package com.littleant.carrepair.activies.repair;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.littleant.carrepair.R;
import com.littleant.carrepair.activies.BaseActivity;
import com.littleant.carrepair.request.bean.BaseResponseBean;
import com.littleant.carrepair.request.bean.GarageInfo;
import com.littleant.carrepair.request.bean.MaintainListBean;
import com.littleant.carrepair.request.constant.ParamsConstant;
import com.littleant.carrepair.request.excute.maintain.maintain.MaintainQueryAllCmd;
import com.littleant.carrepair.utils.ProjectUtil;
import com.mh.core.task.MHCommandCallBack;
import com.mh.core.task.MHCommandExecute;
import com.mh.core.task.command.abstracts.MHCommand;
import com.mh.core.tools.MHToast;

import java.util.ArrayList;
import java.util.List;

/**
 * 维修记录
 */
public class RepairRecordActivity extends BaseActivity {

    private RecyclerView mList;
    private RadioGroup rr_radiogroup;
    private List<MaintainListBean.MaintainInfo> dataNotFinish;
    private List<MaintainListBean.MaintainInfo> dataFinish;
    private int queryType = ParamsConstant.MAINTAIN_NOT_FINISH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void init() {
        super.init();
        mList = findViewById(R.id.repair_record_list);
        mList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        rr_radiogroup = findViewById(R.id.rr_radiogroup);
        rr_radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rr_doing:
                        queryType = ParamsConstant.MAINTAIN_NOT_FINISH;
                        if(dataNotFinish == null) {
                            requestMaintainRecord();
                        } else {
                            setListItem(dataNotFinish);
                        }
                        break;

                    case R.id.rr_finish:
                        queryType = ParamsConstant.MAINTAIN_FINISH;
                        if(dataFinish == null) {
                            requestMaintainRecord();
                        } else {
                            setListItem(dataFinish);
                        }
                        break;
                }
            }
        });

        requestMaintainRecord();
    }

    private void requestMaintainRecord() {
        MaintainQueryAllCmd maintainQueryAllCmd = new MaintainQueryAllCmd(mContext, queryType);
        maintainQueryAllCmd.setCallback(new MHCommandCallBack() {
            @Override
            public void cmdCallBack(MHCommand command) {
                if (command != null) {
                    Log.i("response", command.getResponse());
                    BaseResponseBean responseBean = ProjectUtil.getBaseResponseBean(command.getResponse());
                    if(responseBean != null && ParamsConstant.REAPONSE_CODE_SUCCESS == responseBean.getCode()) {
                        MaintainListBean maintainListBean = ProjectUtil.getBaseResponseBean(command.getResponse(), MaintainListBean.class);
                        if(queryType == ParamsConstant.MAINTAIN_NOT_FINISH) {
                            dataNotFinish = maintainListBean.getData();
                            setListItem(dataNotFinish);
                        } else if(queryType == ParamsConstant.MAINTAIN_FINISH) {
                            dataFinish = maintainListBean.getData();
                            setListItem(dataFinish);
                        }
                    } else if(responseBean != null && !TextUtils.isEmpty(responseBean.getMsg())) {
                        MHToast.showS(mContext, responseBean.getMsg());
                    }
                } else {
                    MHToast.showS(mContext, R.string.request_fail);
                }
            }
        });
        MHCommandExecute.getInstance().asynExecute(mContext, maintainQueryAllCmd);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_repair_record;
    }

    @Override
    protected int getTitleId() {
        return R.string.text_repaire_record;
    }

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        private List<MaintainListBean.MaintainInfo> infos;

        public MyAdapter(List<MaintainListBean.MaintainInfo> infos) {
            this.infos = infos;
        }

        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_repair_record_item, parent, false);
            final MyAdapter.ViewHolder viewHolder = new MyAdapter.ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {
            MaintainListBean.MaintainInfo maintainInfo = infos.get(position);
            if(maintainInfo != null) {
                holder.rri_tv_title.setText(maintainInfo.getContent());
                holder.rri_plate.setText(maintainInfo.getCar_code());
                holder.rri_model.setText(maintainInfo.getCar_type());
                holder.rri_time.setText(maintainInfo.getSubscribe_time());
                // TODO: 2018/9/3 缺少未取车时间以及判断方式
                holder.rri_get_time.setText(mContext.getResources().getString(R.string.text_repaire_record_not_get_car));
            }
        }

        @Override
        public int getItemCount() {
            return infos.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView rri_tv_title, rri_plate, rri_model, rri_time, rri_get_time;

            ViewHolder(View itemView) {
                super(itemView);
                rri_tv_title = itemView.findViewById(R.id.rri_tv_title);
                rri_plate = itemView.findViewById(R.id.rri_plate);
                rri_model = itemView.findViewById(R.id.rri_model);
                rri_time = itemView.findViewById(R.id.rri_time);
                rri_get_time = itemView.findViewById(R.id.rri_get_time);
            }

        }
    }

    private void setListItem(List<MaintainListBean.MaintainInfo> listItem) {
        if(listItem != null) {
            mList.setAdapter(new MyAdapter(listItem));
        }
    }
}
