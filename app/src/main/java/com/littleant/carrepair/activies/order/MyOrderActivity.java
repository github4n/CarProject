package com.littleant.carrepair.activies.order;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.Constraints;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.littleant.carrepair.R;
import com.littleant.carrepair.activies.BaseActivity;
import com.littleant.carrepair.activies.pay.PaymentActivity;
import com.littleant.carrepair.request.bean.BaseResponseBean;
import com.littleant.carrepair.request.bean.maintain.MaintainOrderListBean;
import com.littleant.carrepair.request.constant.ParamsConstant;
import com.littleant.carrepair.request.excute.maintain.list.ListQueryAllCmd;
import com.littleant.carrepair.request.excute.maintain.maintain.MaintainDeleteCmd;
import com.littleant.carrepair.request.excute.maintain.upkeep.UpkeepDeleteCmd;
import com.littleant.carrepair.utils.ProjectUtil;
import com.mh.core.task.MHCommandCallBack;
import com.mh.core.task.MHCommandExecute;
import com.mh.core.task.command.abstracts.MHCommand;
import com.mh.core.tools.MHToast;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.littleant.carrepair.activies.pay.PaymentActivity.PAYMENT_FROM;

/**
 * 我的订单
 */
public class MyOrderActivity extends BaseActivity {
    public static final String SELECT_TYPE = "select_type";
    public static final String PAY_MONEY = "pay_money";
    public static final String TYPE_UPKEEP = "upkeep";
    public static final String PAY_MAINTAIN = "maintain";
    public static final String ORDER_INFO = "order_info";
    private static final int REQUEST_PAY = 10;
    private static final int REQUEST_DETAIL = 11;
    public static final int ALL = -1;
    public static final int WAIT_PAY = 1;
    public static final int WAIT_SERVICE = 2;
    public static final int WAIT_RATE = 4;
    private RecyclerView mList;
    private RadioGroup mo_radio_group;
    private RadioButton mo_rb_all, mo_rb_wait_pay, mo_rb_wait_service, mo_rb_wait_rate;
    private int state = -1;
    private ParamsConstant.CommentStatus status = ParamsConstant.CommentStatus.NONE;
    private List<MaintainOrderListBean.OrderInfo> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestOrder(state, status);
    }

    private void requestOrder(int state, ParamsConstant.CommentStatus status) {
        //查询全部订单列表信息
        ListQueryAllCmd listQueryAllCmd = new ListQueryAllCmd(mContext, state, status);
        listQueryAllCmd.setCallback(new MHCommandCallBack() {
            @Override
            public void cmdCallBack(MHCommand command) {
                if (command != null) {
                    Log.i("response", command.getResponse());
                    MaintainOrderListBean responseBean = ProjectUtil.getBaseResponseBean(command.getResponse(), MaintainOrderListBean.class);
                    if(responseBean != null && ParamsConstant.REAPONSE_CODE_SUCCESS == responseBean.getCode()) {
                        data = responseBean.getData();
                        mList.setAdapter(new MyAdapter(data));

                    } else if(responseBean != null && !TextUtils.isEmpty(responseBean.getMsg())) {
                        MHToast.showS(mContext, responseBean.getMsg());
                    }
                } else {
                    MHToast.showS(mContext, R.string.request_fail);
                }
            }
        });
        MHCommandExecute.getInstance().asynExecute(mContext, listQueryAllCmd);
    }

    @Override
    protected void init() {
        super.init();

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            state = extras.getInt(SELECT_TYPE);
            if(state == WAIT_RATE) {
                status = ParamsConstant.CommentStatus.NOT_COMMENT;
            }
        }

        mList = findViewById(R.id.mo_list);
        mList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        mo_radio_group = findViewById(R.id.mo_radio_group);
        mo_radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.mo_rb_all:
                        requestOrder(ALL, ParamsConstant.CommentStatus.NONE);
                        break;

                    case R.id.mo_rb_wait_pay:
                        requestOrder(WAIT_PAY, ParamsConstant.CommentStatus.NONE);
                        break;

                    case R.id.mo_rb_wait_service:
                        requestOrder(WAIT_SERVICE, ParamsConstant.CommentStatus.NONE);
                        break;

                    case R.id.mo_rb_wait_rate:
                        requestOrder(WAIT_RATE, ParamsConstant.CommentStatus.NOT_COMMENT);
                        break;
                }
            }
        });

        mo_rb_all = findViewById(R.id.mo_rb_all);
        mo_rb_wait_pay = findViewById(R.id.mo_rb_wait_pay);
        mo_rb_wait_service = findViewById(R.id.mo_rb_wait_service);
        mo_rb_wait_rate = findViewById(R.id.mo_rb_wait_rate);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_order;
    }

    @Override
    protected int getTitleId() {
        return R.string.text_my_order;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_PAY && resultCode == RESULT_OK) {
            requestOrder(state, status);
        } else if(requestCode == REQUEST_DETAIL && resultCode == RESULT_OK) {

        }
    }

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        public MyAdapter(List<MaintainOrderListBean.OrderInfo> list) {
            this.list = list;
        }

        private List<MaintainOrderListBean.OrderInfo> list;

        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_my_order_item, parent, false);
            final MyAdapter.ViewHolder viewHolder = new MyAdapter.ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final MyAdapter.ViewHolder holder, int position) {
            final MaintainOrderListBean.OrderInfo orderInfo = list.get(position);
            if(orderInfo != null) {
                holder.lmoi_product_title.setText(orderInfo.getOrder_name());
                holder.lmoi_time.setText(orderInfo.getSubscribe_time());
                Picasso.with(mContext).load(Uri.parse(orderInfo.getOrder_pic_url())).into(holder.lmoi_img);
                String holdText = "";
                switch (orderInfo.getState()) {
                    case 0: //等待接单
                        holder.lmoi_btn_hold.setVisibility(View.GONE);
                        break;

                    case 1:
                        holdText = "去付款";
                        holder.lmoi_money.setText("等待维修厂发布清算");
                        holder.lmoi_tv_state.setText("未支付");
                        holder.lmoi_tv_state.setTextColor(getResources().getColor(R.color.color_not_pay));
                        holder.lmoi_btn_delete.setVisibility(View.VISIBLE);
                        break;

                    case 2:
                        holdText = "导航";
                        holder.lmoi_money.setText("等待维修厂发布清算");
                        holder.lmoi_tv_state.setText("等待服务");
                        holder.lmoi_tv_state.setTextColor(getResources().getColor(R.color.color_wait_service));
                        holder.lmoi_btn_delete.setVisibility(View.VISIBLE);
                        break;

                    case 3:
                        holdText = "确认取车";
                        holder.lmoi_money.setText("￥" + orderInfo.getNow_price() + "");
                        holder.lmoi_tv_state.setText("服务中");
                        holder.lmoi_tv_state.setTextColor(getResources().getColor(R.color.color_service_ing));
                        holder.lmoi_btn_delete.setVisibility(View.INVISIBLE);
                        if(!orderInfo.isIs_maintain() || !orderInfo.isIs_upkeep()) {
                            holder.lmoi_btn_hold.setVisibility(View.GONE);
                        } else {
                            holder.lmoi_btn_hold.setVisibility(View.VISIBLE);
                        }
                        break;

                    case 4:
                        holdText = "评价";
                        holder.lmoi_money.setText("￥" + orderInfo.getNow_price() + "");
                        holder.lmoi_tv_state.setText("服务完成");
                        holder.lmoi_tv_state.setTextColor(getResources().getColor(R.color.color_service_done));
                        holder.lmoi_btn_delete.setVisibility(View.INVISIBLE);
                        break;
                }
                holder.lmoi_btn_hold.setText(holdText);
                holder.lmoi_btn_hold.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent;
                        switch (orderInfo.getState()) {
                            case 1:
                                intent = new Intent(mContext, PaymentActivity.class);
                                if(TYPE_UPKEEP.equals(orderInfo.getType())) {
                                    intent.putExtra(PAYMENT_FROM, ParamsConstant.ORDER_UPKEEP);
                                } else if(PAY_MAINTAIN.equals(orderInfo.getType())) {
                                    intent.putExtra(PAYMENT_FROM, ParamsConstant.ORDER_MAINTAIN);
                                }
                                intent.putExtra(ORDER_INFO, orderInfo);
                                startActivityForResult(intent, REQUEST_PAY);
                                break;

                            case 2:
                                break;

                            case 3:
                                break;

                            case 4:
                                final Dialog d = new Dialog(mContext, R.style.MyTransparentDialog);
                                View contentView = View.inflate(mContext, R.layout.layout_rating, null);
                                DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();
                                int dialogWidth = (int) (dm.widthPixels * 0.6);
                                int dialogHeight = (int) (dm.heightPixels * 0.3);
                                d.setContentView(contentView, new Constraints.LayoutParams(dialogWidth, dialogHeight));
                                contentView.findViewById(R.id.lr_rating_ok).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        d.dismiss();
                                    }
                                });
                                d.show();
                                break;
                        }
                    }
                });

                holder.lmoi_btn_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final Dialog d = new Dialog(mContext, R.style.MyTransparentDialog);
                        View contentView = View.inflate(mContext, R.layout.layout_delete_order, null);
                        DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
                        int dialogWidth = (int) (dm.widthPixels * 0.8);
                        int dialogHeight = (int) (dm.heightPixels * 0.3);
                        d.setContentView(contentView, new Constraints.LayoutParams(dialogWidth, dialogHeight));
                        contentView.findViewById(R.id.ldo_btn_ok).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                d.dismiss();
                                if(TYPE_UPKEEP.equals(orderInfo.getType())) {
                                    requestDeleteUpkeepOrder(orderInfo.getId());
                                } else if(PAY_MAINTAIN.equals(orderInfo.getType())) {
                                    requestDeleteMaintainOrder(orderInfo.getId());
                                }
                            }
                        });
                        contentView.findViewById(R.id.ldo_btn_again).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                d.dismiss();
                            }
                        });
                        d.show();
                    }
                });
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //进入详情页
                        if(TYPE_UPKEEP.equals(orderInfo.getType())) {

                        } else if(PAY_MAINTAIN.equals(orderInfo.getType())) {
                            Intent intent = new Intent(mContext, ServiceOrderDetailActivity.class);
                            intent.putExtra(ORDER_INFO, orderInfo);
                            startActivityForResult(intent, REQUEST_DETAIL);
                        }
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView lmoi_product_title, lmoi_time, lmoi_money, lmoi_tv_state, lmoi_btn_delete, lmoi_btn_hold;
            ImageView lmoi_img;

            ViewHolder(View itemView) {
                super(itemView);
                lmoi_product_title = itemView.findViewById(R.id.lmoi_product_title);
                lmoi_time = itemView.findViewById(R.id.lmoi_time);
                lmoi_money = itemView.findViewById(R.id.lmoi_money);
                lmoi_tv_state = itemView.findViewById(R.id.lmoi_tv_state);
                lmoi_btn_delete = itemView.findViewById(R.id.lmoi_btn_delete);
                lmoi_btn_hold = itemView.findViewById(R.id.lmoi_btn_hold);
                lmoi_img = itemView.findViewById(R.id.lmoi_img);
            }

        }
    }

    private void requestDeleteUpkeepOrder(int id) {
        UpkeepDeleteCmd upkeepDeleteCmd = new UpkeepDeleteCmd(mContext, id);
        upkeepDeleteCmd.setCallback(new MHCommandCallBack() {
            @Override
            public void cmdCallBack(MHCommand command) {
                if (command != null) {
                    Log.i("response", command.getResponse());
                    BaseResponseBean responseBean = ProjectUtil.getBaseResponseBean(command.getResponse(), BaseResponseBean.class);
                    if(responseBean != null && ParamsConstant.REAPONSE_CODE_SUCCESS == responseBean.getCode()) {
                        MHToast.showS(mContext, R.string.delete_success);
                        requestOrder(state, status);
                    } else if(responseBean != null && !TextUtils.isEmpty(responseBean.getMsg())) {
                        MHToast.showS(mContext, responseBean.getMsg());
                    }
                } else {
                    MHToast.showS(mContext, R.string.request_fail);
                }
            }
        });
        MHCommandExecute.getInstance().asynExecute(mContext, upkeepDeleteCmd);
    }

    private void requestDeleteMaintainOrder(int id) {
        MaintainDeleteCmd maintainDeleteCmd = new MaintainDeleteCmd(mContext, id);
        maintainDeleteCmd.setCallback(new MHCommandCallBack() {
            @Override
            public void cmdCallBack(MHCommand command) {
                if (command != null) {
                    Log.i("response", command.getResponse());
                    BaseResponseBean responseBean = ProjectUtil.getBaseResponseBean(command.getResponse(), BaseResponseBean.class);
                    if(responseBean != null && ParamsConstant.REAPONSE_CODE_SUCCESS == responseBean.getCode()) {
                        MHToast.showS(mContext, R.string.delete_success);
                        requestOrder(state, status);
                    } else if(responseBean != null && !TextUtils.isEmpty(responseBean.getMsg())) {
                        MHToast.showS(mContext, responseBean.getMsg());
                    }
                } else {
                    MHToast.showS(mContext, R.string.request_fail);
                }
            }
        });
        MHCommandExecute.getInstance().asynExecute(mContext, maintainDeleteCmd);
    }
}
