package com.littleant.carrepair.activies.repair;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.littleant.carrepair.R;
import com.littleant.carrepair.activies.BaseActivity;
import com.littleant.carrepair.activies.annualcheck.BaseFillInfoActivity;
import com.littleant.carrepair.activies.repair.view.RepairItemView;
import com.littleant.carrepair.activies.repair.view.RepairPicView;
import com.littleant.carrepair.request.bean.BaseResponseBean;
import com.littleant.carrepair.request.bean.maintain.MaintainOrderDetailBean;
import com.littleant.carrepair.request.bean.maintain.MaintainOrderListBean;
import com.littleant.carrepair.request.bean.survey.ObjList;
import com.littleant.carrepair.request.bean.survey.SurveyStationInfo;
import com.littleant.carrepair.request.constant.ParamsConstant;
import com.littleant.carrepair.request.excute.maintain.maintain.MaintainQueryOneCmd;
import com.littleant.carrepair.utils.ProjectUtil;
import com.mh.core.task.MHCommandCallBack;
import com.mh.core.task.MHCommandExecute;
import com.mh.core.task.command.abstracts.MHCommand;
import com.mh.core.tools.MHToast;

import java.util.List;

import static com.littleant.carrepair.activies.order.MyOrderActivity.ORDER_INFO;

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
                if(TYPE_UPKEEP.equals(orderInfo.getType())) {

                } else if(PAY_MAINTAIN.equals(orderInfo.getType())) {
                    requestMaintainOrderDetail();
                }
            }

        }
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

    private void requestMaintainOrderDetail() {
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
                            showInfo(data);
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

    private void showInfo(MaintainOrderDetailBean.MaintainOrderDetail data) {
        switch (orderInfo.getState()) {
            case 0: //等待接单
                asod_tv_state.setText("等待接单");
                asod_tv_state.setTextColor(getResources().getColor(R.color.color_survey_wait_order));
                break;

            case 1:
                asod_tv_state.setText("未支付");
                asod_tv_state.setTextColor(getResources().getColor(R.color.color_not_pay));
                asod_tv_modify.setVisibility(View.VISIBLE);
                asod_cl_buttons.setVisibility(View.VISIBLE);
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
                showPic();
                break;
        }
        asod_ll_detail = findViewById(R.id.asod_ll_detail); //显示维修项目
        asod_ll_pic = findViewById(R.id.asod_ll_pic);
        asod_cl_buttons = findViewById(R.id.asod_cl_buttons);

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

        showItemDetail();
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.asod_btn_delete:

                break;

            case R.id.asod_btn_pay:

                break;

            case R.id.asod_tv_modify:
                showList();
                break;
        }
    }

    private void showItemDetail() {
        List<MaintainOrderDetailBean.MaintainSet> maintainitem_set = data.getMaintainitem_set();
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

    private void showPic() {
        List<ObjList> maintainpic_set = data.getMaintainpic_set();
        if(maintainpic_set != null) {
            asod_ll_pic.removeAllViews();
            for(ObjList obj : maintainpic_set) {
                RepairPicView picView = new RepairPicView(mContext, obj);
                asod_ll_pic.addView(picView, -1, -2);
            }
        }

    }

    protected void showList() {
        View contentView2 = LayoutInflater.from(mContext).inflate(R.layout.layout_order_detail_dialog, null);
//                View contentView = View.inflate(OwnCheckFillInfoActivity.this, R.layout.layout_select_dialog, null);
        final Dialog d2 = setDialog(mContext, contentView2);
        d2.setContentView(contentView2);
        contentView2.findViewById(R.id.lodd_tv_list).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                d2.dismiss();
            }
        });

        contentView2.findViewById(R.id.lodd_tv_modify).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                d2.dismiss();
            }
        });

        RecyclerView listView2 = contentView2.findViewById(R.id.lodd_list);
//        final MyAdapter myAdapter = new MyAdapter(infos, list);
//        listView2.setAdapter(myAdapter);
//        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Log.i("listview", "onItemClick = " + position);
//            }
//        });
        d2.show();
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
}
