package com.littleant.carrepair.activies.maintain;

import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.littleant.carrepair.R;
import com.littleant.carrepair.activies.BaseActivity;
import com.littleant.carrepair.activies.address.MyAddressActivity;
import com.littleant.carrepair.activies.car.MyCarActivity;
import com.littleant.carrepair.activies.pay.PaymentActivity;
import com.littleant.carrepair.activies.repair.RepairActivity;
import com.littleant.carrepair.activies.repair.RepairRecordActivity;
import com.littleant.carrepair.request.bean.BaseResponseBean;
import com.littleant.carrepair.request.bean.GarageInfo;
import com.littleant.carrepair.request.bean.MyCarListBean;
import com.littleant.carrepair.request.bean.OilInfo;
import com.littleant.carrepair.request.bean.OilListBean;
import com.littleant.carrepair.request.constant.ParamsConstant;
import com.littleant.carrepair.request.excute.maintain.oil.OilQueryAllCmd;
import com.littleant.carrepair.request.excute.user.car.CarQueryAllCmd;
import com.littleant.carrepair.request.utils.DataHelper;
import com.littleant.carrepair.utils.ProjectUtil;
import com.mh.core.task.MHCommandCallBack;
import com.mh.core.task.MHCommandExecute;
import com.mh.core.task.command.abstracts.MHCommand;
import com.mh.core.tools.MHToast;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.littleant.carrepair.fragment.MainFragment.GARAGE_INFO;

public class BookMaintainActivity extends BaseActivity {
    private RecyclerView mList;
    private Button bm_submit;
    private GarageInfo garageInfo;
    private MyAdapter myAdapter;
    private List<OilInfo> oilList;
    private TextView bm_tv_total_money, bm_tv_title, bm_tv_des;
    private ImageView bm_iv_icon;
    private ConstraintLayout constraintLayout;
    public static final int REQUEST_CODE_CAR = 100;
    public static final String PICK_CAR = "pick_car";
    private MyCarListBean.CarInfo carInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            garageInfo = (GarageInfo) extras.getSerializable(GARAGE_INFO);
        }
        if(garageInfo == null) {
            this.finish();
        }
        requestOil();
    }

    private void requestOil() {
        OilQueryAllCmd oilQueryAllCmd = new OilQueryAllCmd(mContext, garageInfo.getId());
        oilQueryAllCmd.setCallback(new MHCommandCallBack() {
            @Override
            public void cmdCallBack(MHCommand command) {
                if (command != null) {
                    Log.i("response", command.getResponse());
                    requestDefaultCar();
                    BaseResponseBean responseBean = ProjectUtil.getBaseResponseBean(command.getResponse());
                    if (responseBean != null && responseBean.getCode() == ParamsConstant.REAPONSE_CODE_SUCCESS) {
                        OilListBean listBean = ProjectUtil.getBaseResponseBean(command.getResponse(), OilListBean.class);
                        oilList = listBean.getData();
                        Log.i("oilList", oilList.toString());
                        if(oilList != null) {
                            setListItem(oilList);
                        }
                    }
                } else {
                    MHToast.showS(mContext, R.string.request_fail);
                }
            }
        });
        MHCommandExecute.getInstance().asynExecute(mContext, oilQueryAllCmd);
    }

    private void requestDefaultCar() {
        CarQueryAllCmd carQueryAllCmd = new CarQueryAllCmd(mContext, ParamsConstant.QueryType.DEFAULT);
        carQueryAllCmd.setCallback(new MHCommandCallBack() {
            @Override
            public void cmdCallBack(MHCommand command) {
                if (command != null) {
                    Log.i("response", command.getResponse());
                    BaseResponseBean responseBean = ProjectUtil.getBaseResponseBean(command.getResponse());
                    if(responseBean != null && ParamsConstant.REAPONSE_CODE_SUCCESS == responseBean.getCode()) {
                        MyCarListBean carListBean = ProjectUtil.getBaseResponseBean(command.getResponse(), MyCarListBean.class);
                        if(carListBean != null) {
                            carInfo = carListBean.getData().get(0);
                            setCarInfo(carInfo);
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
    protected void init() {
        super.init();
        mList = findViewById(R.id.bm_list);
        mList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//        mList.setAdapter(new MyAdapter());

        bm_tv_total_money = findViewById(R.id.bm_tv_total_money);
        bm_tv_title = findViewById(R.id.bm_tv_title);
        bm_tv_des = findViewById(R.id.bm_tv_des);
        bm_iv_icon = findViewById(R.id.bm_iv_icon);

        constraintLayout = findViewById(R.id.constraintLayout);
        constraintLayout.setOnClickListener(this);

        bm_submit = findViewById(R.id.bm_submit);
        bm_submit.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_book_maintain;
    }

    @Override
    protected int getTitleId() {
        return R.string.text_maintain;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bm_submit:
                Intent intent = new Intent(BookMaintainActivity.this, PaymentActivity.class);
                BookMaintainActivity.this.startActivity(intent);
                break;

            case R.id.constraintLayout:
                Intent i = new Intent(BookMaintainActivity.this, MyCarActivity.class);
                i.putExtra(PICK_CAR, true);
                BookMaintainActivity.this.startActivityForResult(i, REQUEST_CODE_CAR);
                break;
        }
    }

    private void setCarInfo(MyCarListBean.CarInfo carInfo) {
        if(carInfo != null) {
            bm_tv_title.setText(carInfo.getCode());
            bm_tv_des.setText(carInfo.getBrand_name());
            Picasso.with(mContext).load(Uri.parse(carInfo.getPic_url())).into(bm_iv_icon);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_CAR && resultCode == RESULT_OK) {
            carInfo = (MyCarListBean.CarInfo) data.getSerializableExtra(MyCarActivity.CAR_INFO);
            setCarInfo(carInfo);
        }
    }

    private void setListItem(List<OilInfo> listItem) {
        if(listItem != null) {
            listItem.add(new OilInfo());
            myAdapter = new MyAdapter(listItem);
            mList.setAdapter(myAdapter);
        }
    }

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        public static final int TYPE_HEADER = 0;  //说明是带有Header的
        public static final int TYPE_FOOTER = 1;  //说明是带有Footer的
        public static final int TYPE_NORMAL = 2;  //说明是不带有header和footer的
        private View mFooterView;
        private List<OilInfo> mOilList;

        public MyAdapter(List<OilInfo> oilList) {
            this.mOilList = oilList;
        }

        public View getFooterView() {
            return mFooterView;
        }

        public void setFooterView(View footerView) {
            mFooterView = footerView;
            notifyItemInserted(getItemCount()-1);
        }

        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if(viewType == TYPE_FOOTER){
                mFooterView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_book_maintain_end_item, parent, false);
                return new MyAdapter.ViewHolder(mFooterView);
            }
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_maintain_item, parent, false);
            final MyAdapter.ViewHolder viewHolder = new MyAdapter.ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {
            final OilInfo oilInfo = mOilList.get(position);
            if(oilInfo != null) {
                if (getItemViewType(position) == TYPE_NORMAL) {
                    holder.lmi_item_name.setText(oilInfo.getName());
                    holder.lmi_tv_new_price.setText(DataHelper.displayPrice(mContext, oilInfo.getNew_price()));
                    holder.lmi_tv_old_price.setText(DataHelper.displayPrice(mContext, oilInfo.getPrice()));
                    holder.lmi_gas_amount.setText(oilInfo.getL() + "");
                    holder.lmi_tv_old_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                    Picasso.with(mContext).load(Uri.parse(oilInfo.getPic_url())).into(holder.lmi_iv_itemImg);
                    holder.lmi_select.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            float price = DataHelper.getDisplayPrice(mContext, bm_tv_total_money.getText().toString());
                            if(isChecked) {
                                price += oilInfo.getNew_price();
                            } else {
                                price -= oilInfo.getNew_price();
                            }
                            bm_tv_total_money.setText(DataHelper.displayPrice(mContext, price));
                        }
                    });
                } else {
                    holder.bmei_time_price.setText(DataHelper.displayPrice(mContext, garageInfo.getFilter_price()));
                }
            }
        }

        @Override
        public int getItemCount() {
            return mOilList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            //一般子项
            private TextView lmi_item_name, lmi_gas_amount, lmi_tv_new_price, lmi_tv_old_price;
            private ImageView lmi_iv_itemImg;
            private CheckBox lmi_select;

            //底项
            private TextView bmei_time_price;

            ViewHolder(View itemView) {
                super(itemView);
                if(itemView == mFooterView) {
                    bmei_time_price = itemView.findViewById(R.id.bmei_time_price);
                } else {
                    lmi_item_name = itemView.findViewById(R.id.lmi_item_name);
                    lmi_gas_amount = itemView.findViewById(R.id.lmi_gas_amount);
                    lmi_tv_new_price = itemView.findViewById(R.id.lmi_tv_new_price);
                    lmi_tv_old_price = itemView.findViewById(R.id.lmi_tv_old_price);
                    lmi_iv_itemImg = itemView.findViewById(R.id.lmi_iv_itemImg);
                    lmi_select = itemView.findViewById(R.id.lmi_select);
                }

            }

        }

        @Override
        public int getItemViewType(int position) {

            if (position == getItemCount()-1){
                //最后一个,应该加载Footer
                return TYPE_FOOTER;
            }
            return TYPE_NORMAL;
        }
    }
}
