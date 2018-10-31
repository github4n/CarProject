package com.littleant.carrepair.activies.aftersale;

import android.app.Activity;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.littleant.carrepair.R;
import com.littleant.carrepair.activies.BaseActivity;
import com.littleant.carrepair.activies.annualcheck.AnnualCheckDetailActivity;
import com.littleant.carrepair.activies.annualcheck.AnnualCheckFailActivity;
import com.littleant.carrepair.activies.annualcheck.AnnualCheckRecordActivity;
import com.littleant.carrepair.activies.annualcheck.CheckReturnCarActivity;
import com.littleant.carrepair.activies.annualcheck.OwnStartCheckActivity;
import com.littleant.carrepair.activies.annualcheck.PickCarActivity;
import com.littleant.carrepair.activies.annualcheck.StartCheckActivity;
import com.littleant.carrepair.activies.order.AllOrderActivity;
import com.littleant.carrepair.activies.pay.PaymentActivity;
import com.littleant.carrepair.request.bean.AllOrderListBean;
import com.littleant.carrepair.request.bean.BaseResponseBean;
import com.littleant.carrepair.request.bean.aftersale.AftersaleAllBean;
import com.littleant.carrepair.request.bean.survey.SurveyInfo;
import com.littleant.carrepair.request.bean.survey.SurveyListBean;
import com.littleant.carrepair.request.constant.ParamsConstant;
import com.littleant.carrepair.request.excute.aftersale.AftersaleAllCmd;
import com.littleant.carrepair.request.excute.aftersale.AftersaleFinshCmd;
import com.littleant.carrepair.request.excute.survey.survey.SurveyQueryAllCmd;
import com.littleant.carrepair.utils.ProjectUtil;
import com.mh.core.task.MHCommandCallBack;
import com.mh.core.task.MHCommandExecute;
import com.mh.core.task.command.abstracts.MHCommand;
import com.mh.core.tools.MHToast;

import java.util.List;

import static com.littleant.carrepair.activies.pay.PaymentActivity.PAYMENT_FROM;

/**
 * 文件描述:售后服务
 * 作者:莫进生
 * 创建时间:2018/10/19 0019
 * 版本号:1
 */


public class AftersaleActivity extends BaseActivity {
    private RecyclerView mList;
    private RadioGroup acr_radiogroup;
    private RadioButton acr_finish,acr_doing;
    private List<AftersaleAllBean.AftersaleBean> dataNotFinish;
    private List<AftersaleAllBean.AftersaleBean> dataFinish;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mList = findViewById(R.id.acr_record_list);
        mList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        requestCheckRecord(ParamsConstant.MAINTAIN_LIST_STATUS_WAIT_PAY);
        acr_finish=findViewById(R.id.acr_finish);
        acr_finish.setOnClickListener(this);
        acr_doing=findViewById(R.id.acr_finish);
        acr_doing.setOnClickListener(this);

    }
    @Override
    protected int getLayoutId() {
         return R.layout.activity_after_sale;
    }

    @Override
    protected int getTitleId() {
        return R.string.text_user_center_aftersale_record;
    }
    @Override
    protected void init() {
        super.init();
        mList = findViewById(R.id.acr_record_list);
        mList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        acr_radiogroup = findViewById(R.id.acr_radiogroup);
        acr_radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.acr_doing:
                       // queryType = ParamsConstant.SURVEY_NOT_FINISH;
                        if(dataNotFinish == null) {
                            requestCheckRecord(ParamsConstant.MAINTAIN_LIST_STATUS_WAIT_PAY);
                        } else {
                            setListItem(dataNotFinish);
                        }
                        break;

                    case R.id.acr_finish:
                       // queryType = ParamsConstant.SURVEY_FINISH;
                        if(dataFinish == null) {
                            requestCheckRecord(ParamsConstant.MAINTAIN_LIST_STATUS_WAIT_SEND);
                        } else {
                            setListItem(dataFinish);
                        }
                        break;
                }
            }
        });
    }
    @Override
    public void onClick(View v) {



    }
    private void requestCheckRecord(final int state) {

        AftersaleAllCmd aftersaleAllCmd=new AftersaleAllCmd(mContext,state );
        aftersaleAllCmd.setCallback(new MHCommandCallBack() {
            @Override
            public void cmdCallBack(MHCommand command) {
                BaseResponseBean responseBean = ProjectUtil.getBaseResponseBean(command.getResponse());
                Log.i("response", command.getResponse());
                if(responseBean != null && ParamsConstant.REAPONSE_CODE_SUCCESS == responseBean.getCode()) {
                    AftersaleAllBean listBean = ProjectUtil.getBaseResponseBean(command.getResponse(), AftersaleAllBean.class);
                    if(state==0){
                        dataNotFinish= listBean.getData();
                         setListItem(dataNotFinish);
                    }else{
                        dataFinish = listBean.getData();
                        setListItem(dataFinish);
                    }

                } else if(responseBean != null && !TextUtils.isEmpty(responseBean.getMsg())) {
                    //setListItem(null);
                    MHToast.showS(mContext, responseBean.getMsg());
                }
            }
        });
        MHCommandExecute.getInstance().asynExecute(mContext, aftersaleAllCmd);
    }
    private class MyAdapter extends RecyclerView.Adapter<AftersaleActivity.MyAdapter.ViewHolder> implements View.OnClickListener{
        private List<AftersaleAllBean.AftersaleBean> aftersaleBean;
        private String order_id,car_code,car_type,create_time;
        public MyAdapter(List<AftersaleAllBean.AftersaleBean> aftersaleBean) {
            this.aftersaleBean = aftersaleBean;
        }

        @Override
        public AftersaleActivity.MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_aftersale_record_item, parent, false);
            final AftersaleActivity.MyAdapter.ViewHolder viewHolder = new AftersaleActivity.MyAdapter.ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(AftersaleActivity.MyAdapter.ViewHolder holder, int position) {
           final AftersaleAllBean.AftersaleBean aftersale = aftersaleBean.get(position);
            holder.itemView.setTag(position);
            if(aftersaleBean != null) {
                order_id=aftersale.getOrder_id();
                car_code=aftersale.getCar_code();
                car_type=aftersale.getCar_type();
                create_time=aftersale.getCreate_time();
                holder.ari_order_no.setText(order_id);
                holder.ari_car_code.setText(car_code);
                holder.ari_car_type.setText(car_type);
                holder.ari_time.setText(create_time);
                if(aftersale.getState()==0){
                    holder.air_state.setText("服务中");
                }
                if(aftersale.getState()==1){
                    holder.air_state.setText("已完成");
                    holder.asod_btn_finish.setVisibility(View.GONE);

                }
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(AftersaleActivity.this,AfterSaleDetailActivity.class);
                        Bundle bundle=new Bundle();
                        bundle.putString("order_id",aftersale.getOrder_id());
                        bundle.putString("car_code",aftersale.getCar_code());
                        bundle.putString("car_type",aftersale.getCar_type());
                        bundle.putString("create_time",aftersale.getCreate_time());
                        bundle.putString("state",aftersale.getState()+"");
                        bundle.putString("order_pic_url",aftersale.getOrder_pic_url()+"");
                        bundle.putString("now_price",aftersale.getNow_price()+"");
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });

                holder.asod_btn_finish.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AftersaleFinshCmd aftersaleFinshCmd=new AftersaleFinshCmd(mContext,aftersale.getId()+"");
                        aftersaleFinshCmd.setCallback(new MHCommandCallBack() {
                            @Override
                            public void cmdCallBack(MHCommand command) {
                                BaseResponseBean responseBean = ProjectUtil.getBaseResponseBean(command.getResponse());
                                if(responseBean != null && ParamsConstant.REAPONSE_CODE_SUCCESS == responseBean.getCode()) {
                                    Intent intent = new Intent(AftersaleActivity.this, AftersaleActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });
                        MHCommandExecute.getInstance().asynExecute(mContext, aftersaleFinshCmd);

                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return aftersaleBean.size();
        }

        @Override
        public void onClick(View v) {


        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView ari_order_no, ari_car_code, ari_car_type, ari_time, asod_btn_finish,air_state;

            ViewHolder(View itemView) {
                super(itemView);
                ari_order_no = itemView.findViewById(R.id.ari_order_no);
                ari_car_code = itemView.findViewById(R.id.ari_car_code);
                ari_car_type = itemView.findViewById(R.id.ari_car_type);
                ari_time = itemView.findViewById(R.id.ari_time);
                air_state = itemView.findViewById(R.id.air_state);
                asod_btn_finish=itemView.findViewById(R.id.asod_btn_finish);
            }

        }
    }

    private void setListItem(List<AftersaleAllBean.AftersaleBean> listItem) {
        if(listItem != null) {
            mList.setAdapter(new AftersaleActivity.MyAdapter(listItem));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode == REQUEST_CODE_CHECK_DETAIL && resultCode == Activity.RESULT_OK) {
//            requestCheckRecord();
//        } else if(requestCode == REQUEST_CODE_RETURN && resultCode == Activity.RESULT_OK) {
//            requestCheckRecord();
//        } else if(requestCode == REQUEST_CODE_PAY  && resultCode == Activity.RESULT_OK) {
//
//        }
    }
}
