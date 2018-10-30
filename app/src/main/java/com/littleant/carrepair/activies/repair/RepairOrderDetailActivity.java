package com.littleant.carrepair.activies.repair;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.littleant.carrepair.R;
import com.littleant.carrepair.activies.BaseActivity;
import com.littleant.carrepair.activies.aftersale.AftersaleReasonActivity;
import com.littleant.carrepair.activies.pay.PaymentActivity;
import com.littleant.carrepair.activies.repair.view.RepairItemView;
import com.littleant.carrepair.activies.repair.view.RepairPicView;
import com.littleant.carrepair.request.bean.BaseResponseBean;
import com.littleant.carrepair.request.bean.car.MyCarListBean;
import com.littleant.carrepair.request.bean.maintain.MaintainOrderDetailBean;
import com.littleant.carrepair.request.bean.maintain.MaintainOrderListBean;
import com.littleant.carrepair.request.bean.pay.PayInfoBean;
import com.littleant.carrepair.request.bean.survey.ObjList;
import com.littleant.carrepair.request.constant.ParamsConstant;
import com.littleant.carrepair.request.excute.maintain.maintain.MaintainDeleteCmd;
import com.littleant.carrepair.request.excute.maintain.maintain.MaintainMethodCmd;
import com.littleant.carrepair.request.excute.maintain.maintain.MaintainQueryOneCmd;
import com.littleant.carrepair.request.utils.DataHelper;
import com.littleant.carrepair.utils.ProjectUtil;
import com.mh.core.task.MHCommandCallBack;
import com.mh.core.task.MHCommandExecute;
import com.mh.core.task.command.abstracts.MHCommand;
import com.mh.core.tools.MHToast;

import java.util.ArrayList;
import java.util.List;

import static com.littleant.carrepair.activies.order.MyOrderActivity.ORDER_INFO;
import static com.littleant.carrepair.activies.pay.PaymentActivity.PAYMENT_FROM;

/**
 * 待服务、服务中：没有照片信息，没有支付、删除按钮
 * 未付款：首次进入弹先弹确认项目，有去付款和取消订单
 * 已完成：有照片信息，没有支付、删除按钮
 * 带接单：没有费用信息和照片
 */
public class RepairOrderDetailActivity extends BaseActivity {

    public static final String TYPE_UPKEEP = "upkeep";
    public static final String PAY_MAINTAIN = "maintain";
    private MaintainOrderDetailBean.MaintainOrderDetail data;
    //订单列表的bean
    private MaintainOrderListBean.OrderInfo orderInfo;
    private TextView asod_tv_title, asod_tv_state, asod_tv_modify;
    //维修修改的项目部分,维修照片部分
    private LinearLayout asod_ll_detail, asod_ll_pic;
    //支付和删除订单部分
    private View asod_cl_buttons;
    //删除订单和支付按钮
    private TextView asod_btn_delete, asod_btn_pay;
    //订单类型、交易单号、订单单号
    private TextView asod_order_type, asod_exchange_id, asod_order_id;
    //头部基础信息
    private TextView lodb_item, lodb_time, lodb_type, lodb_code;
    private MyAdapter myAdapter;

    private static final int FLAG_NORMAL = 0;
    private static final int FLAG_UPDATE = 1;
    private static final int REQUEST_PAY = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            orderInfo = (MaintainOrderListBean.OrderInfo) extras.getSerializable(ORDER_INFO);
            if(orderInfo == null) {
                finish();
                return;
            } else {
                if(PAY_MAINTAIN.equals(orderInfo.getType())) {
                    requestMaintainOrderDetail(FLAG_NORMAL);
                }
            }

        }
        mOptionText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RepairOrderDetailActivity.this, AftersaleReasonActivity.class);
                intent.putExtra(ParamsConstant.ID,orderInfo.getId()+"");
                startActivity(intent);
            }
        });
    }

    @Override
    protected void init() {
        super.init();
        asod_tv_title = findViewById(R.id.asod_tv_title);
        asod_tv_state = findViewById(R.id.asod_tv_state);
        asod_ll_detail = findViewById(R.id.asod_ll_detail);
        asod_ll_pic = findViewById(R.id.asod_ll_pic);
        asod_cl_buttons = findViewById(R.id.asod_cl_buttons);
        asod_order_type = findViewById(R.id.asod_order_type);
        asod_exchange_id = findViewById(R.id.asod_exchange_id);
        asod_order_id = findViewById(R.id.asod_order_id);

        lodb_item = findViewById(R.id.lodb_item);
        lodb_time = findViewById(R.id.lodb_time);
        lodb_type = findViewById(R.id.lodb_type);
        lodb_code = findViewById(R.id.lodb_code);

        asod_btn_delete = findViewById(R.id.asod_btn_delete);
        asod_btn_delete.setOnClickListener(this);

        asod_btn_pay = findViewById(R.id.asod_btn_pay);
        asod_btn_pay.setOnClickListener(this);

        asod_tv_modify = findViewById(R.id.asod_tv_modify);
        asod_tv_modify.setOnClickListener(this);
    }

    private void requestMaintainOrderDetail(final int flag) {
        MaintainQueryOneCmd queryOneCmd = new MaintainQueryOneCmd(mContext, orderInfo.getId());
        queryOneCmd.setCallback(new MHCommandCallBack() {
            @Override
            public void cmdCallBack(MHCommand command) {
                if (command != null) {
                    Log.i("response", command.getResponse());
                    BaseResponseBean responseBean = ProjectUtil.getBaseResponseBean(command.getResponse(), BaseResponseBean.class);
                    if(responseBean != null && ParamsConstant.REAPONSE_CODE_SUCCESS == responseBean.getCode()) {
                        MaintainOrderDetailBean orderDetailBean = ProjectUtil.getBaseResponseBean(command.getResponse(), MaintainOrderDetailBean.class);
                        if(orderDetailBean != null) {
                            data = orderDetailBean.getData();
                            showInfo(flag, data);
                        }
                    } else if(responseBean != null && !TextUtils.isEmpty(responseBean.getMsg())) {
                        MHToast.showS(mContext, responseBean.getMsg());
                    }
                } else {
                    MHToast.showS(mContext, R.string.request_fail);
                }
            }
        });
        MHCommandExecute.getInstance().asynExecute(mContext, queryOneCmd);
    }

    private void showInfo(int flag, MaintainOrderDetailBean.MaintainOrderDetail data) {
        switch (orderInfo.getState()) {
            case 0: //等待接单
                asod_tv_state.setText("等待接单");
                asod_tv_state.setTextColor(getResources().getColor(R.color.color_survey_wait_order));
                break;

            case 1:
                asod_tv_state.setText("未支付");
                asod_tv_state.setTextColor(getResources().getColor(R.color.color_not_pay));
                if(orderInfo.isIs_setting()) { //维修厂未设定维修项目时不显示支付按钮
                    asod_tv_modify.setVisibility(View.VISIBLE);
                    asod_cl_buttons.setVisibility(View.VISIBLE);
                } else {
                    asod_tv_modify.setVisibility(View.GONE);
                    asod_cl_buttons.setVisibility(View.GONE);
                }
                break;

            case 2:
                asod_tv_state.setText("等待服务");
                asod_tv_state.setTextColor(getResources().getColor(R.color.color_wait_service));
                break;

            case 3:
                asod_tv_state.setText("服务中");
                asod_tv_state.setTextColor(getResources().getColor(R.color.color_service_ing));
                break;

            case 4:

                asod_tv_state.setText("服务完成");
                asod_tv_state.setTextColor(getResources().getColor(R.color.color_service_done));
                showPic(data);
                break;
        }

        //大标题
        asod_tv_title.setText(data.getOrder_name());

        //头部信息
        lodb_item.setText(data.getService_item());
        lodb_time.setText(data.getSubscribe_time());
        lodb_type.setText(data.getCar_type());
        lodb_code.setText(data.getCar_code());

        //订单类型、交易单号、订单单号
        asod_order_type.setText(data.getOrder_type());
        asod_exchange_id.setText(data.getDeal_id());
        asod_order_id.setText(data.getOrder_id());
        if(flag == FLAG_NORMAL) {
            showItemDetail(data.getMaintainitem_set());
        } else if(flag == FLAG_UPDATE) {
            showItemDetail(data.getMaintainitem_set_now());
        }
        if(orderInfo.getState() == 1 && !DataHelper.getRepairConfirm(this, orderInfo.getId())) { //未支付且未确认过维修项，默认弹出来
            asod_tv_modify.performClick();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_service_order_detail;
    }
    @Override
    protected int getTitleId() {
        return R.string.text_order_detail;
    }

    @Override
    protected int getOptionStringId() {
        mOptionText.setVisibility(View.VISIBLE);
        return R.string.text_after_sale;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.asod_btn_delete:
                requestDeleteMaintainOrder(orderInfo.getId());
                break;

            case R.id.asod_btn_pay:
                Intent intent = new Intent(mContext, PaymentActivity.class);
                intent.putExtra(PAYMENT_FROM, ParamsConstant.ORDER_MAINTAIN);
                intent.putExtra(ORDER_INFO, orderInfo);
                startActivityForResult(intent, REQUEST_PAY);
                break;

            case R.id.asod_tv_modify:
                DataHelper.saveRepairConfirm(this, orderInfo.getId());  //弹出确认维修项并保存已确认状态
                showList();
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_PAY && resultCode == RESULT_OK) { //支付成功，返回并刷新订单页面
            finishAndResult();
        }
    }

    private void showItemDetail(List<MaintainOrderDetailBean.MaintainSet> maintainitem_set) {
        if(maintainitem_set != null) {
            asod_ll_detail.removeAllViews();
            float total = 0;
            for (MaintainOrderDetailBean.MaintainSet set : maintainitem_set) {
                RepairItemView repairItemView = new RepairItemView(mContext, set);
                asod_ll_detail.addView(repairItemView, -1, -2);
                total += set.getPrice();
            }
            RepairItemView repairItemView = new RepairItemView(mContext, "总计：", total);
            asod_ll_detail.addView(repairItemView, -1, -2);
        }
    }

    private void showPic(MaintainOrderDetailBean.MaintainOrderDetail data) {
        List<ObjList> maintainpic_set = data.getMaintainpic_set();
        if(maintainpic_set != null && maintainpic_set.size() > 0) {
            asod_ll_pic.removeAllViews();
            for(ObjList obj : maintainpic_set) {
                RepairPicView picView = new RepairPicView(mContext, obj);
                asod_ll_pic.addView(picView, -1, -2);
            }
        }

    }

    protected void showList() {
        if(data.getMaintainitem_set() == null || data.getMaintainitem_set().size() < 1) {
            MHToast.showS(mContext, R.string.no_repair_item);
            return;
        }
        View contentView2 = LayoutInflater.from(mContext).inflate(R.layout.layout_order_detail_dialog, null);
        final Dialog d2 = setDialog(mContext, contentView2);
        d2.setContentView(contentView2);
        d2.setCancelable(false);
        d2.setCanceledOnTouchOutside(false);
        RecyclerView listView2 = contentView2.findViewById(R.id.lodd_list);
        listView2.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        myAdapter = new MyAdapter(data.getMaintainitem_set());
        listView2.setAdapter(myAdapter);

        final TextView hold = contentView2.findViewById(R.id.lodd_tv_modify);
        hold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(myAdapter.getEditMode()) { //编辑状态中
                    myAdapter.setEditMode(false);
                    ((TextView) view).setText(R.string.text_order_detail_list_modify);
                } else {
                    myAdapter.setEditMode(true);
                    ((TextView) view).setText(R.string.text_order_detail_list_finish);
                }
            }
        });
        contentView2.findViewById(R.id.lodd_btn_confrm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(myAdapter.getEditMode()) { //编辑状态中
                    myAdapter.setEditMode(false);
                    hold.setText(R.string.text_order_detail_list_modify);
                } else {
                    d2.dismiss();
                    requestModify();
                }
            }
        });

        d2.show();
    }

    private void requestModify() {
        StringBuilder itemList = new StringBuilder();
        List<MaintainOrderDetailBean.MaintainSet> currentList = myAdapter.getCurrentList();
        for (MaintainOrderDetailBean.MaintainSet temp : currentList) {
            itemList.append(temp.getId()).append(",");
        }
//        itemList.deleteCharAt(itemList.length() - 1);
        MaintainMethodCmd methodCmd = new MaintainMethodCmd(mContext, orderInfo.getId(), ParamsConstant.MethodStatus.ITEM_LIST, 0, null, itemList.toString());
        methodCmd.setCallback(new MHCommandCallBack() {
            @Override
            public void cmdCallBack(MHCommand command) {
                if (command != null) {
                    Log.i("response", command.getResponse());
                    BaseResponseBean responseBean = ProjectUtil.getBaseResponseBean(command.getResponse(), BaseResponseBean.class);
                    if(responseBean != null && ParamsConstant.REAPONSE_CODE_SUCCESS == responseBean.getCode()) {
                        requestMaintainOrderDetail(FLAG_UPDATE);
                    } else if(responseBean != null && !TextUtils.isEmpty(responseBean.getMsg())) {
                        MHToast.showS(mContext, responseBean.getMsg());
                    }
                } else {
                    MHToast.showS(mContext, R.string.request_fail);
                }
            }
        });
        MHCommandExecute.getInstance().asynExecute(mContext, methodCmd);
    }

    protected Dialog setDialog(Context activity, View contentView) {
        final Dialog d = new Dialog(activity, R.style.MyTransparentDialog);
        d.setContentView(contentView);
        DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();
        int dialogWidth = dm.widthPixels;
        int dialogHeight = (int) (dm.heightPixels * 0.4);

        Window window = d.getWindow();
        window.setGravity(Gravity.BOTTOM);
        //设置显示位置
        WindowManager.LayoutParams p = window.getAttributes(); //获取对话框当前的参数值
        p.width = WindowManager.LayoutParams.MATCH_PARENT;
        p.height = dialogHeight;
        window.setAttributes(p);

        d.setCanceledOnTouchOutside(false);
        return d;
    }

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        private List<MaintainOrderDetailBean.MaintainSet> maintainitem_set;
        private boolean editMode = false;

        public MyAdapter(List<MaintainOrderDetailBean.MaintainSet> item_set) {
            this.maintainitem_set = new ArrayList<>();
            maintainitem_set.addAll(item_set);
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_order_detail_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            MaintainOrderDetailBean.MaintainSet maintainSet = maintainitem_set.get(position);
            if(maintainSet != null) {
                if(editMode) {
                    holder.lodi_iv_delete.setVisibility(View.VISIBLE);
                    holder.lodi_iv_delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            removeData(position);
                        }
                    });
                } else {
                    holder.lodi_iv_delete.setVisibility(View.GONE);
                }
                holder.lodi_tv_name.setText(maintainSet.getName());
                holder.lodi_tv_price.setText(DataHelper.displayPrice(mContext, maintainSet.getPrice()));
            }
        }

        @Override
        public int getItemCount() {
            return maintainitem_set.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView lodi_tv_name, lodi_tv_price;
            ImageView lodi_iv_delete;

            ViewHolder(View itemView) {
                super(itemView);
                lodi_tv_name = itemView.findViewById(R.id.lodi_tv_name);
                lodi_tv_price = itemView.findViewById(R.id.lodi_tv_price);
                lodi_iv_delete = itemView.findViewById(R.id.lodi_iv_delete);
            }
        }

        public void setEditMode(boolean editMode) {
            this.editMode = editMode;
            notifyDataSetChanged();
        }

        public boolean getEditMode() {
            return editMode;
        }

        public List<MaintainOrderDetailBean.MaintainSet> getCurrentList() {
            return maintainitem_set;
        }

        // 删除数据
        private void removeData(int position) {
            maintainitem_set.remove(position);
            //删除动画
            notifyItemRemoved(position);
            notifyDataSetChanged();
        }
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
                        finishAndResult();
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

    private void finishAndResult() {
        RepairOrderDetailActivity.this.setResult(RESULT_OK);
        RepairOrderDetailActivity.this.finish();
    }
}
