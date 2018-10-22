package com.littleant.carrepair.activies.repair.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.littleant.carrepair.R;
import com.littleant.carrepair.request.bean.maintain.MaintainOrderDetailBean;
import com.littleant.carrepair.request.utils.DataHelper;

public class RepairItemView extends LinearLayout {

    public RepairItemView(Context mContext, String name, float price) {
        super(mContext);

        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_order_detial_text_item, null, false);

        TextView nameTv = view.findViewById(R.id.lodti_tv_name);
        nameTv.setText(name);

        TextView priceTv = view.findViewById(R.id.lodti_tv_price);
        priceTv.setText(DataHelper.displayPrice(mContext, price));

        this.addView(view, -1, LayoutParams.WRAP_CONTENT);
    }

    public RepairItemView(Context mContext, MaintainOrderDetailBean.MaintainSet set) {
        super(mContext);

        this.setOrientation(LinearLayout.HORIZONTAL);

        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_order_detial_text_item, null, false);

        TextView nameTv = view.findViewById(R.id.lodti_tv_name);
        nameTv.setText(set.getName());

        TextView priceTv = view.findViewById(R.id.lodti_tv_price);
        priceTv.setText(DataHelper.displayPrice(mContext, set.getPrice()));

        this.addView(view, -1, LayoutParams.WRAP_CONTENT);
    }
}
