package com.littleant.carrepair.activies.upkeep;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.littleant.carrepair.R;
import com.littleant.carrepair.activies.BaseActivity;
import com.littleant.carrepair.activies.aftersale.AftersaleReasonActivity;
import com.littleant.carrepair.activies.repair.RepairOrderDetailActivity;
import com.littleant.carrepair.activies.repair.view.RepairPicView;
import com.littleant.carrepair.request.bean.BaseResponseBean;
import com.littleant.carrepair.request.bean.maintain.MaintainOrderDetailBean;
import com.littleant.carrepair.request.bean.maintain.MaintainOrderListBean;
import com.littleant.carrepair.request.bean.survey.ObjList;
import com.littleant.carrepair.request.bean.upkeep.UpkeepOrderDetailBean;
import com.littleant.carrepair.request.constant.ParamsConstant;
import com.littleant.carrepair.request.excute.maintain.maintain.MaintainQueryOneCmd;
import com.littleant.carrepair.request.excute.maintain.upkeep.UpkeepQueryOneCmd;
import com.littleant.carrepair.request.utils.DataHelper;
import com.littleant.carrepair.utils.ProjectUtil;
import com.mh.core.task.MHCommandCallBack;
import com.mh.core.task.MHCommandExecute;
import com.mh.core.task.command.abstracts.MHCommand;
import com.mh.core.tools.MHToast;

import java.util.List;

import static com.littleant.carrepair.activies.order.MyOrderActivity.ORDER_INFO;

public class UpkeepDetailActivity extends BaseActivity {
    //标题部分
    private TextView aud_tv_title, aud_tv_state;
    //明细部分
    private TextView lod_tv_title, lod_code, lod_type, lod_time, lod_item, lod_material, lod_exid, lod_oid, lod_price;
    //图片部分
    private LinearLayout aud_ll_pic;
    //订单列表的bean
    private MaintainOrderListBean.OrderInfo orderInfo;
    private UpkeepOrderDetailBean.UpkeepOrderDetail data;

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
                requestUpkeepOrderDetail();
            }

        }
        mOptionText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UpkeepDetailActivity.this, AftersaleReasonActivity.class);
                intent.putExtra(ParamsConstant.ID,orderInfo.getId()+"");
                intent.putExtra("flag","upkeep");
                startActivity(intent);
            }
        });
    }
    @Override
    protected int getOptionStringId() {
        return R.string.text_after_sale;
    }
    private void requestUpkeepOrderDetail() {
        UpkeepQueryOneCmd queryOneCmd = new UpkeepQueryOneCmd(mContext, orderInfo.getId());
        queryOneCmd.setCallback(new MHCommandCallBack() {
            @Override
            public void cmdCallBack(MHCommand command) {
                if (command != null) {
                    Log.i("response", command.getResponse());
                    BaseResponseBean responseBean = ProjectUtil.getBaseResponseBean(command.getResponse(), BaseResponseBean.class);
                    if(responseBean != null && ParamsConstant.REAPONSE_CODE_SUCCESS == responseBean.getCode()) {
                        UpkeepOrderDetailBean orderDetailBean = ProjectUtil.getBaseResponseBean(command.getResponse(), UpkeepOrderDetailBean.class);
                        if(orderDetailBean != null) {
                            data = orderDetailBean.getData();
                            showInfo(data);
                        }
                    } else if(responseBean != null && ParamsConstant.REAPONSE_CODE_AUTH_FAIL == responseBean.getCode()) {
                        Intent intent = ProjectUtil.tokenExpiredIntent(mContext);
                        startActivity(intent);
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

    private void showInfo(UpkeepOrderDetailBean.UpkeepOrderDetail data) {
        switch (orderInfo.getState()) {
            case 0: //等待接单
                aud_tv_state.setText("等待接单");
                aud_tv_state.setTextColor(getResources().getColor(R.color.color_survey_wait_order));
                break;

            case 1:
                aud_tv_state.setText("未支付");
                aud_tv_state.setTextColor(getResources().getColor(R.color.color_not_pay));
                break;

            case 2:
                aud_tv_state.setText("等待服务");
                aud_tv_state.setTextColor(getResources().getColor(R.color.color_wait_service));
                break;

            case 3:
                aud_tv_state.setText("服务中");
                aud_tv_state.setTextColor(getResources().getColor(R.color.color_service_ing));
                break;

            case 4:
                mOptionText.setVisibility(View.VISIBLE);
                aud_tv_state.setText("服务完成");
                aud_tv_state.setTextColor(getResources().getColor(R.color.color_service_done));
                break;
        }

        //大标题
        aud_tv_title.setText(data.getOrder_name());

        //头部信息
        lod_tv_title.setText(data.getService_item());
        lod_code.setText(data.getCar_code());
        lod_type.setText(data.getCar_brand());
        lod_item.setText(data.getService_item());
        lod_material.setText(data.getProject_material());
        lod_exid.setText(data.getDeal_id());
        lod_oid.setText(data.getOrder_id());
        lod_price.setText(DataHelper.displayPrice(mContext, data.getNow_price()));
        //图片
        showPic(data);
    }

    private void showPic(UpkeepOrderDetailBean.UpkeepOrderDetail data) {
        List<ObjList> upkeeppic_set = this.data.getUpkeeppic_set();
        if(upkeeppic_set != null && upkeeppic_set.size() > 0) {
            aud_ll_pic.removeAllViews();
            for(ObjList obj : upkeeppic_set) {
                RepairPicView picView = new RepairPicView(mContext, obj);
                aud_ll_pic.addView(picView, -1, -2);
            }
        }

    }

    @Override
    protected void init() {
        super.init();
        //头部
        aud_tv_title = findViewById(R.id.aud_tv_title);
        aud_tv_state = findViewById(R.id.aud_tv_state);
        //详细
        lod_tv_title = findViewById(R.id.lod_tv_title);
        lod_code = findViewById(R.id.lod_code);
        lod_type = findViewById(R.id.lod_type);
        lod_time = findViewById(R.id.lod_time);
        lod_item = findViewById(R.id.lod_item);
        lod_material = findViewById(R.id.lod_material);
        lod_exid = findViewById(R.id.lod_exid);
        lod_oid = findViewById(R.id.lod_oid);
        lod_price = findViewById(R.id.lod_price);
        //图片
        aud_ll_pic = findViewById(R.id.aud_ll_pic);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_upkeep_detail;
    }

    @Override
    protected int getTitleId() {
        return R.string.text_order_detail;
    }

    @Override
    public void onClick(View view) {

    }
}
