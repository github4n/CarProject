package com.littleant.carrepair.activies.order;

import android.app.Dialog;
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
import android.widget.TextView;

import com.littleant.carrepair.R;
import com.littleant.carrepair.activies.BaseActivity;
import com.littleant.carrepair.request.bean.AllOrderListBean;
import com.littleant.carrepair.request.bean.BaseResponseBean;
import com.littleant.carrepair.request.bean.OrderItemBean;
import com.littleant.carrepair.request.bean.ShoppingCarListBean;
import com.littleant.carrepair.request.constant.ParamsConstant;
import com.littleant.carrepair.request.excute.service.order.OrderQueryAllCmd;
import com.littleant.carrepair.request.excute.service.ordercar.OrderCarQueryAllCmd;
import com.littleant.carrepair.request.utils.DataHelper;
import com.littleant.carrepair.utils.ProjectUtil;
import com.mh.core.task.MHCommandCallBack;
import com.mh.core.task.MHCommandExecute;
import com.mh.core.task.command.abstracts.MHCommand;
import com.mh.core.tools.MHToast;

import java.util.List;

/**
 * 全部订单
 */
public class AllOrderActivity extends BaseActivity {
    private RecyclerView mList;
    private List<OrderItemBean> itemBeanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestAllOrder();
    }

    private void requestAllOrder() {
        OrderQueryAllCmd orderQueryAllCmd = new OrderQueryAllCmd(mContext, ParamsConstant.SERVICE_ORDER_STATUS_ALL, ParamsConstant.CommentStatus.NONE);
        orderQueryAllCmd.setCallback(new MHCommandCallBack() {
            @Override
            public void cmdCallBack(MHCommand command) {
                if (command != null) {
                    Log.i("response", command.getResponse());
                    BaseResponseBean responseBean = ProjectUtil.getBaseResponseBean(command.getResponse(), BaseResponseBean.class);
                    if(responseBean != null && ParamsConstant.REAPONSE_CODE_SUCCESS == responseBean.getCode()) {
                        AllOrderListBean listBean = ProjectUtil.getBaseResponseBean(command.getResponse(), AllOrderListBean.class);
                        itemBeanList = listBean.getData();
                        if(itemBeanList != null) {
                            mList.setAdapter(new MyAdapter(itemBeanList));
                        }
                    } else if(responseBean != null && !TextUtils.isEmpty(responseBean.getMsg())) {
                        MHToast.showS(mContext, responseBean.getMsg());
                    }
                } else {
                    MHToast.showS(mContext, R.string.request_fail);
                }
            }
        });
        MHCommandExecute.getInstance().asynExecute(mContext, orderQueryAllCmd);
    }

    @Override
    protected void init() {
        super.init();
        mList = findViewById(R.id.ao_list);
        mList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_all_order;
    }

    @Override
    protected int getTitleId() {
        return R.string.text_all_order;
    }

    @Override
    public void onClick(View v) {

    }

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        private List<OrderItemBean> list;

        public MyAdapter(List<OrderItemBean> list) {
            this.list = list;
        }

        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_product_item, parent, false);
            final MyAdapter.ViewHolder viewHolder = new MyAdapter.ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {
            OrderItemBean orderItemBean = list.get(position);
            if(orderItemBean != null) {
                holder.lpr_product_title.setText(orderItemBean.getName());
                String status = "";
                switch (orderItemBean.getStatus()) {
                    case 0:
                        status = "待付款";
                        holder.lpr_btn_delete.setVisibility(View.VISIBLE);
                        holder.lpr_lpi_btn_pay.setVisibility(View.VISIBLE);
                        holder.lpr_btn_rate.setVisibility(View.GONE);
                        break;

                    case 1:
                        status = "待发货";
                        holder.lpr_btn_delete.setVisibility(View.GONE);
                        holder.lpr_lpi_btn_pay.setVisibility(View.GONE);
                        holder.lpr_btn_rate.setVisibility(View.GONE);
                        break;

                    case 2:
                        status = "待收货";
                        holder.lpr_btn_delete.setVisibility(View.GONE);
                        holder.lpr_lpi_btn_pay.setVisibility(View.GONE);
                        holder.lpr_btn_rate.setVisibility(View.GONE);
                        break;

                    case 3:
                        status = "已完成";
                        holder.lpr_btn_delete.setVisibility(View.GONE);
                        holder.lpr_lpi_btn_pay.setVisibility(View.GONE);
                        if(orderItemBean.getIs_comment() == 0) {
                            holder.lpr_btn_rate.setVisibility(View.VISIBLE);
                        } else {
                            holder.lpr_btn_rate.setVisibility(View.GONE);
                        }
                        break;
                }
                holder.lpi_tv_state.setText(status);
                holder.lpr_per_price.setText(DataHelper.displayPrice(mContext, orderItemBean.getAll_price()));
            }
            holder.lpr_btn_rate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final Dialog d = new Dialog(AllOrderActivity.this, R.style.MyTransparentDialog);
                    View contentView = View.inflate(AllOrderActivity.this, R.layout.layout_rating, null);
//                        d.setContentView(R.layout.layout_point);
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
                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView lpr_product_title, lpi_tv_state, lpr_per_price, lpr_product_count, lpr_tv_count, lpr_tv_sum,
                    lpr_btn_delete, lpr_btn_rate, lpr_lpi_btn_pay, lpr_line2;
            ImageView lpr_iv_pic;

            ViewHolder(View itemView) {
                super(itemView);
                lpr_product_title = itemView.findViewById(R.id.lpr_product_title);
                lpi_tv_state = itemView.findViewById(R.id.lpi_tv_state);
                lpr_iv_pic = itemView.findViewById(R.id.lpr_iv_pic);
                lpr_per_price = itemView.findViewById(R.id.lpr_per_price);
                lpr_product_count = itemView.findViewById(R.id.lpr_product_count);
                lpr_tv_count = itemView.findViewById(R.id.lpr_tv_count);
                lpr_tv_sum = itemView.findViewById(R.id.lpr_tv_sum);
                lpr_btn_delete = itemView.findViewById(R.id.lpr_btn_delete);
                lpr_btn_rate = itemView.findViewById(R.id.lpr_btn_rate);
                lpr_lpi_btn_pay = itemView.findViewById(R.id.lpr_lpi_btn_pay);
                lpr_line2 = itemView.findViewById(R.id.lpr_line2);
            }

        }
    }
}
