package com.littleant.carrepair.activies.shopping;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
import com.littleant.carrepair.activies.order.OrderPageActivity;
import com.littleant.carrepair.request.bean.BaseResponseBean;
import com.littleant.carrepair.request.bean.ShoppingCarItemBean;
import com.littleant.carrepair.request.bean.ShoppingCarListBean;
import com.littleant.carrepair.request.constant.ParamsConstant;
import com.littleant.carrepair.request.excute.service.ordercar.OrderCarAddCmd;
import com.littleant.carrepair.request.excute.service.ordercar.OrderCarDeleteCmd;
import com.littleant.carrepair.request.excute.service.ordercar.OrderCarQueryAllCmd;
import com.littleant.carrepair.request.utils.DataHelper;
import com.littleant.carrepair.utils.ProjectUtil;
import com.mh.core.task.MHCommandCallBack;
import com.mh.core.task.MHCommandExecute;
import com.mh.core.task.command.abstracts.MHCommand;
import com.mh.core.tools.MHToast;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 购物车
 */
public class ShoppingCarActivity extends BaseActivity {

    private RecyclerView mList;
    private Button sc_go_pay;
    private List<ShoppingCarItemBean> itemBeans;
    private CheckBox sc_cb_select_all;
    private TextView sc_tv_total_money;
    private MyAdapter myAdapter;
    private float price;
    private ArrayList<Integer> selectProductIdList = new ArrayList<>();
    private Set<Integer> selectProductIdSet = new HashSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestItem();
    }

    private void requestItem() {
        OrderCarQueryAllCmd orderCarQueryAllCmd = new OrderCarQueryAllCmd(mContext);
        orderCarQueryAllCmd.setCallback(new MHCommandCallBack() {
            @Override
            public void cmdCallBack(MHCommand command) {
                if (command != null) {
                    Log.i("response", command.getResponse());
                    BaseResponseBean responseBean = ProjectUtil.getBaseResponseBean(command.getResponse(), BaseResponseBean.class);
                    if(responseBean != null && ParamsConstant.REAPONSE_CODE_SUCCESS == responseBean.getCode()) {
                        ShoppingCarListBean listBean = ProjectUtil.getBaseResponseBean(command.getResponse(), ShoppingCarListBean.class);
                        itemBeans = listBean.getData();
                        if(itemBeans != null) {
                            myAdapter = new MyAdapter(itemBeans);
                            mList.setAdapter(myAdapter);
                        }
                    } else if(responseBean != null && !TextUtils.isEmpty(responseBean.getMsg())) {
                        MHToast.showS(mContext, responseBean.getMsg());
                    }
                } else {
                    MHToast.showS(mContext, R.string.request_fail);
                }
            }
        });
        MHCommandExecute.getInstance().asynExecute(mContext, orderCarQueryAllCmd);
    }

    @Override
    protected void init() {
        super.init();
        mList = findViewById(R.id.sc_list);
        mList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        sc_go_pay = findViewById(R.id.sc_go_pay);
        sc_go_pay.setOnClickListener(this);

        sc_cb_select_all = findViewById(R.id.sc_cb_select_all);
        sc_cb_select_all.setOnClickListener(this);
//        sc_cb_select_all.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                myAdapter.selectAll(b);
//            }
//        });

        sc_tv_total_money = findViewById(R.id.sc_tv_total_money);

        mOptionText.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shopping_car;
    }

    @Override
    protected int getTitleId() {
        return R.string.text_shopping_car;
    }

    @Override
    protected int getOptionStringId() {
        return R.string.text_sc_delete;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sc_go_pay:
                Intent intent = new Intent(ShoppingCarActivity.this, OrderPageActivity.class);
                ShoppingCarActivity.this.startActivity(intent);
                break;

            case R.id.sc_cb_select_all:
                myAdapter.selectAll(sc_cb_select_all.isChecked());
                break;

            case R.id.header_option_text:
                if(selectProductIdSet.isEmpty()) {
                    MHToast.showS(mContext, R.string.no_select_item);
                } else {
                    StringBuilder ids = new StringBuilder();
                    for(Integer i : selectProductIdSet) {
                        ids.append(i).append(",");
                    }
                    ids.deleteCharAt(ids.length() - 1);
                    requestDeleteItem(ids.toString());
                }
                break;
        }
    }

    private void requestDeleteItem(String id) {
        OrderCarDeleteCmd orderDeleteCmd = new OrderCarDeleteCmd(mContext, id);
        orderDeleteCmd.setCallback(new MHCommandCallBack() {
            @Override
            public void cmdCallBack(MHCommand command) {
                if(command != null) {
                    BaseResponseBean responseBean = ProjectUtil.getBaseResponseBean(command.getResponse(), BaseResponseBean.class);
                    if(responseBean != null && ParamsConstant.REAPONSE_CODE_SUCCESS == responseBean.getCode()) {
                        MHToast.showS(mContext, R.string.delete_success);
                        requestItem();
                    } else if(responseBean != null && !TextUtils.isEmpty(responseBean.getMsg())) {
                        MHToast.showS(mContext, responseBean.getMsg());
                    }
                } else {
                    MHToast.showS(mContext, R.string.request_fail);
                }
            }
        });
        MHCommandExecute.getInstance().asynExecute(mContext, orderDeleteCmd);
    }

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        private List<ShoppingCarItemBean> list;
        private boolean isSelectAll = false;

        public MyAdapter(List<ShoppingCarItemBean> itemBeans) {
            this.list = itemBeans;
        }

        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_shopping_car_item, parent, false);
            final MyAdapter.ViewHolder viewHolder = new MyAdapter.ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final MyAdapter.ViewHolder holder, int position) {
            final ShoppingCarItemBean shoppingCarItemBean = list.get(position);
            if(shoppingCarItemBean != null) {
                holder.sci_item_name.setText(shoppingCarItemBean.getProduct().getName());
                holder.sci_item_price.setText(DataHelper.displayPrice(mContext, shoppingCarItemBean.getProduct().getPrice()));
                Picasso.with(mContext).load(Uri.parse(shoppingCarItemBean.getProduct().getPic_url())).into(holder.sci_iv_itemImg);
                holder.sci_amount.setText("  " + shoppingCarItemBean.getAmount() + "  ");
                holder.sci_plus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int amount = Integer.parseInt(holder.sci_amount.getText().toString().trim());
                        amount++;
//                        holder.sci_amount.setText("  " + amount + "  ");
                        requestAddProduct(shoppingCarItemBean.getProduct().getId(), 1);
                    }
                });
                holder.sci_reduce.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int amount = Integer.parseInt(holder.sci_amount.getText().toString().trim());
                        if(amount > 0) {
                            amount--;
//                            holder.sci_amount.setText("  " + amount + "  ");
                            requestAddProduct(shoppingCarItemBean.getProduct().getId(), -1);
                        }
                    }
                });
                holder.sci_select.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if(b) {
                            selectProductIdSet.add(shoppingCarItemBean.getId());
                            price += shoppingCarItemBean.getProduct().getPrice() * shoppingCarItemBean.getAmount();
                        } else {
                            selectProductIdSet.remove(shoppingCarItemBean.getId());
                            price -= shoppingCarItemBean.getProduct().getPrice() * shoppingCarItemBean.getAmount();
                            sc_cb_select_all.setChecked(false);
                        }
                        sc_tv_total_money.setText(DataHelper.displayPrice(mContext, price));
                    }
                });
                if(isSelectAll) {
                    holder.sci_select.setChecked(true);
                } else {
                    holder.sci_select.setChecked(false);
                }

            }
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            private TextView sci_item_name, sci_item_price, sci_reduce, sci_plus, sci_amount;
            private ImageView sci_iv_itemImg;
            private CheckBox sci_select;

            ViewHolder(View itemView) {
                super(itemView);
                sci_item_name = itemView.findViewById(R.id.sci_item_name);
                sci_item_price = itemView.findViewById(R.id.sci_item_price);
                sci_iv_itemImg = itemView.findViewById(R.id.sci_iv_itemImg);
                sci_reduce = itemView.findViewById(R.id.sci_reduce);
                sci_plus = itemView.findViewById(R.id.sci_plus);
                sci_amount = itemView.findViewById(R.id.sci_amount);
                sci_select = itemView.findViewById(R.id.sci_select);
            }
        }

        public void selectAll(boolean selectAll) {
            this.isSelectAll = selectAll;
            notifyDataSetChanged();
        }

        private void requestAddProduct(int id, int amount) {
            OrderCarAddCmd orderCarAddCmd = new OrderCarAddCmd(mContext, id, amount);
            orderCarAddCmd.setCallback(new MHCommandCallBack() {
                @Override
                public void cmdCallBack(MHCommand command) {
                    if(command != null) {
                        BaseResponseBean responseBean = ProjectUtil.getBaseResponseBean(command.getResponse(), BaseResponseBean.class);
                        if(responseBean != null && ParamsConstant.REAPONSE_CODE_SUCCESS == responseBean.getCode()) {
                            requestItem();
                        } else if(responseBean != null && !TextUtils.isEmpty(responseBean.getMsg())) {
                            MHToast.showS(mContext, responseBean.getMsg());
                        }
                    } else {
                        MHToast.showS(mContext, R.string.request_fail);
                    }
                }
            });
            MHCommandExecute.getInstance().asynExecute(mContext, orderCarAddCmd);
        }
    }
}
