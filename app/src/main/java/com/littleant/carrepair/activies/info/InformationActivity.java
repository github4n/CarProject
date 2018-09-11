package com.littleant.carrepair.activies.info;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.littleant.carrepair.R;
import com.littleant.carrepair.activies.BaseActivity;
import com.littleant.carrepair.request.bean.BaseResponseBean;
import com.littleant.carrepair.request.bean.MaintainListBean;
import com.littleant.carrepair.request.constant.ParamsConstant;
import com.littleant.carrepair.request.excute.service.newscatalog.NewsCatalogQueryAllCmd;
import com.littleant.carrepair.utils.ProjectUtil;
import com.mh.core.task.MHCommandCallBack;
import com.mh.core.task.MHCommandExecute;
import com.mh.core.task.command.abstracts.MHCommand;
import com.mh.core.tools.MHToast;

public class InformationActivity extends BaseActivity {
    private RecyclerView mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void init() {
        super.init();
        mList = findViewById(R.id.info_list);
        mList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//        mList.setAdapter(new MyAdapter());

        requestInfoList();
    }

    private void requestInfoList() {
        NewsCatalogQueryAllCmd newsCatalogQueryAllCmd = new NewsCatalogQueryAllCmd(mContext);
        newsCatalogQueryAllCmd.setCallback(new MHCommandCallBack() {
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
        MHCommandExecute.getInstance().asynExecute(mContext, newsCatalogQueryAllCmd);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_information;
    }

    @Override
    protected int getTitleId() {
        return R.string.text_info_title;
    }

    @Override
    public void onClick(View v) {

    }

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_info_item, parent, false);
            MyAdapter.ViewHolder viewHolder = new MyAdapter.ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 2;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView ii_tv_title, ii_tv_description, ii_tv_count;
            ImageView ii_iv_itemImg;

            ViewHolder(View itemView) {
                super(itemView);
                ii_tv_title = itemView.findViewById(R.id.ii_tv_title);
                ii_tv_description = itemView.findViewById(R.id.ii_tv_description);
//                ii_tv_count = itemView.findViewById(R.id.ii_tv_count);
                ii_iv_itemImg = itemView.findViewById(R.id.ii_iv_itemImg);
            }

        }
    }
}
