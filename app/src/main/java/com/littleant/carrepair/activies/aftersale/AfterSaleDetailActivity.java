package com.littleant.carrepair.activies.aftersale;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.littleant.carrepair.R;
import com.littleant.carrepair.activies.BaseActivity;
import com.littleant.carrepair.activies.repair.view.RepairPicView;
import com.littleant.carrepair.request.bean.BaseResponseBean;
import com.littleant.carrepair.request.bean.aftersale.AftersaleAllBean;
import com.littleant.carrepair.request.bean.aftersale.AftersalesDetail;
import com.littleant.carrepair.request.bean.survey.ObjList;
import com.littleant.carrepair.request.constant.ParamsConstant;
import com.littleant.carrepair.request.excute.aftersale.AfterSaleSubmitMaintainDetailCmd;
import com.littleant.carrepair.request.excute.aftersale.AfterSaleSubmitupkeepDetailCmd;
import com.littleant.carrepair.utils.ProjectUtil;
import com.mh.core.task.MHCommandCallBack;
import com.mh.core.task.MHCommandExecute;
import com.mh.core.task.command.abstracts.MHCommand;
import com.mh.core.tools.MHLogUtil;
import com.mh.core.tools.MHStringUtil;
import com.mh.core.tools.MHToast;

import java.util.ArrayList;
import java.util.List;

/**
 * 文件描述:
 * 作者:莫进生
 * 创建时间:2018/10/31 0031
 * 版本号:1
 */


public class AfterSaleDetailActivity extends BaseActivity {
    private TextView asod_order_no,asod_car_nuber,asod_tv_order_time_text,asod_car_type;
    private String order_id,car_code,car_type,create_time,order_pic_url,now_price,type,id;
    private int state;
    private LinearLayout asod_ll_detail, asod_ll_pic;
    private List<ObjList> listMaintain=new ArrayList<>();
    private List<ObjList> listUpkeep=new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_after_sale_order_detail;
    }

    @Override
    protected int getTitleId() {
        return R.string.text_after_sale_detail;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void init() {
        super.init();
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            id = extras.getString("id");
            order_id = extras.getString("order_id");
            car_code = extras.getString("car_code");
            type = extras.getString("type");
            car_type = extras.getString("car_type");
            create_time = extras.getString("create_time");
            state =Integer.parseInt(extras.getString("state")) ;
            if(state==1){
                if("maintain_aftersales".equals(type)){
                    AfterSaleSubmitMaintainDetailCmd afterSaleSubmitDetailCmd=new AfterSaleSubmitMaintainDetailCmd(mContext,id);
                    afterSaleSubmitDetailCmd.setCallback(new MHCommandCallBack() {
                        @Override
                        public void cmdCallBack(MHCommand command) {
                            BaseResponseBean responseBean = ProjectUtil.getBaseResponseBean(command.getResponse());
                            if(responseBean != null && ParamsConstant.REAPONSE_CODE_SUCCESS == responseBean.getCode()) {
                                AftersalesDetail listBean = ProjectUtil.getBaseResponseBean(command.getResponse(), AftersalesDetail.class);
                                listMaintain=listBean.getData().getPic_list();
                                showPic(listMaintain);
                            } else {
                                MHToast.showS(mContext, responseBean.getMsg());
                            }
                        }
                    });
                    MHCommandExecute.getInstance().asynExecute(mContext, afterSaleSubmitDetailCmd);


                }else  if("upkeep_aftersales".equals(type)){
                    AfterSaleSubmitupkeepDetailCmd afterSaleSubmitDetailCmd=new AfterSaleSubmitupkeepDetailCmd(mContext,id);
                    afterSaleSubmitDetailCmd.setCallback(new MHCommandCallBack() {
                        @Override
                        public void cmdCallBack(MHCommand command) {
                            BaseResponseBean responseBean = ProjectUtil.getBaseResponseBean(command.getResponse());
                            if(responseBean != null && ParamsConstant.REAPONSE_CODE_SUCCESS == responseBean.getCode()) {
                                AftersalesDetail listBean = ProjectUtil.getBaseResponseBean(command.getResponse(), AftersalesDetail.class);
                                listBean.getData().getPic_list();
//                                ObjList objList=new ObjList();
//                                objList.setPic_url(listBean.getAftersalesOrderDetail().getPic_url());
//                                objList.setNote(listBean.getAftersalesOrderDetail().getNote());
//                                listUpkeep.add(objList);
                                showPic(listUpkeep);
                            } else {
                                MHToast.showS(mContext, responseBean.getMsg());
                            }
                        }
                    });
                    MHCommandExecute.getInstance().asynExecute(mContext, afterSaleSubmitDetailCmd);

                }
            }
            order_pic_url = extras.getString("order_pic_url");
            now_price = extras.getString("now_price");
//            if(!MHStringUtil.isEmpty(order_pic_url)){
//                ObjList objList=new ObjList();
//                objList.setPic_url(order_pic_url);
//                list.add(objList);
//            }
//            if(!MHStringUtil.isEmpty(now_price)){
//                ObjList objList1=new ObjList();
//                objList1.setPic_url(order_pic_url);
//                list.add(objList1);
//
//            }

            MHLogUtil.logI(getClass().getSimpleName() + order_id+","+car_code+","+car_type+","+create_time);

        }
        asod_ll_pic = findViewById(R.id.asod_ll_pic);
        asod_order_no=findViewById(R.id.asod_order_no);
        asod_car_nuber=findViewById(R.id.asod_car_nuber);
        asod_tv_order_time_text=findViewById(R.id.asod_tv_order_time_text);
        asod_car_type=findViewById(R.id.asod_car_type);

        asod_order_no.setText(order_id);
        asod_car_nuber.setText(car_code);
        asod_tv_order_time_text.setText(create_time);
        asod_car_type.setText(car_type);
        if(state==1){

        }


    }
    private void showPic(List<ObjList> list) {
        if(list != null && list.size() > 0) {
            asod_ll_pic.removeAllViews();
            for(ObjList obj : list) {
                RepairPicView picView = new RepairPicView(mContext, obj);
                asod_ll_pic.addView(picView, -1, -2);
            }
        }

    }
}
