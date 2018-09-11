package com.littleant.carrepair.activies.car;

import android.content.Intent;
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
import com.littleant.carrepair.request.bean.MyCarListBean;
import com.littleant.carrepair.request.constant.ParamsConstant;
import com.littleant.carrepair.request.excute.user.car.CarQueryAllCmd;
import com.littleant.carrepair.utils.ProjectUtil;
import com.mh.core.task.MHCommandCallBack;
import com.mh.core.task.MHCommandExecute;
import com.mh.core.task.command.abstracts.MHCommand;
import com.mh.core.tools.MHToast;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * 我的汽车
 */
public class MyCarActivity extends BaseActivity{
    private RecyclerView mList;
    private static final int REQUEST_CODE_ADD_CAR = 100;
    private List<MyCarListBean.CarInfo> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestMyCarList();
    }

    private void requestMyCarList() {
        CarQueryAllCmd carQueryAllCmd = new CarQueryAllCmd(mContext, ParamsConstant.QueryType.NONE);
        carQueryAllCmd.setCallback(new MHCommandCallBack() {
            @Override
            public void cmdCallBack(MHCommand command) {
                if (command != null) {
                    Log.i("response", command.getResponse());
                    BaseResponseBean responseBean = ProjectUtil.getBaseResponseBean(command.getResponse());
                    if(responseBean != null && ParamsConstant.REAPONSE_CODE_SUCCESS == responseBean.getCode()) {
                        MyCarListBean carListBean = ProjectUtil.getBaseResponseBean(command.getResponse(), MyCarListBean.class);
                        data = carListBean.getData();
                        if(data != null && data.size() > 0){
                            mList.setAdapter(new MyAdapter(data));
                        }
                    } else if(responseBean != null && !TextUtils.isEmpty(responseBean.getMsg())) {
                        MHToast.showS(mContext, responseBean.getMsg());
                    }
                } else {
                    MHToast.showS(mContext, R.string.request_fail);
                }
            }
        });
        MHCommandExecute.getInstance().asynExecute(mContext, carQueryAllCmd);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_car;
    }

    @Override
    protected int getTitleId() {
        return R.string.text_my_car_list;
    }

    @Override
    protected int getOptionBackgroundId() {
        return R.drawable.address_add;
    }

    @Override
    protected void init() {
        super.init();
        mList = findViewById(R.id.mc_list);
        mList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//        mList.setAdapter(new MyAdapter());
        mOptionContent.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.header_option_content:
                Intent intent = new Intent(mContext, AddCarActivity.class);
                MyCarActivity.this.startActivityForResult(intent, REQUEST_CODE_ADD_CAR);
                break;
        }
    }

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        private List<MyCarListBean.CarInfo> list;

        public MyAdapter(List<MyCarListBean.CarInfo> list) {
            this.list = list;
        }

        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_my_car_item, parent, false);
            MyAdapter.ViewHolder viewHolder = new MyAdapter.ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {
            MyCarListBean.CarInfo carInfo = list.get(position);
            if(carInfo != null) {
                holder.mc_item_name.setText(carInfo.getBrand());
                holder.mc_plate.setText(carInfo.getCode());
                holder.mc_mile.setText(String.format(getResources().getString(R.string.text_my_car_miles), carInfo.getMileage() + ""));
                Picasso.with(mContext).load(R.drawable.mc_icon).into(holder.mc_iv_itemImg);
//                Picasso.with(mContext).load(Uri.parse(carInfo.getPic_url())).into(holder.mc_iv_itemImg);
            }
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView mc_item_name, mc_plate, mc_mile;
            ImageView mc_iv_itemImg;

            ViewHolder(View itemView) {
                super(itemView);
                mc_item_name = itemView.findViewById(R.id.mc_item_name);
                mc_plate = itemView.findViewById(R.id.mc_plate);
                mc_mile = itemView.findViewById(R.id.mc_mile);
                mc_iv_itemImg = itemView.findViewById(R.id.mc_iv_itemImg);
            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_ADD_CAR && resultCode == ParamsConstant.ACTIVITY_RESULT_ADD_CAR) {
            requestMyCarList();
        }
    }
}
