package com.littleant.carrepair.activies.aftersale.view;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.littleant.carrepair.R;
import com.littleant.carrepair.request.bean.survey.ObjList;
import com.squareup.picasso.Picasso;

public class AfterSalePicView extends LinearLayout {

    public AfterSalePicView(Context context) {
        super(context);
    }
    public AfterSalePicView(Context mContext, ObjList objList) {
        super(mContext);

        this.setOrientation(LinearLayout.HORIZONTAL);

        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_order_detail_pic, null, false);

        TextView nameTv = view.findViewById(R.id.lodp_tv_pic_title);
        nameTv.setText(objList.getNote());

        ImageView picIv = view.findViewById(R.id.lodp_iv_pic);
        Picasso.with(mContext).load(Uri.parse(objList.getPic_url())).into(picIv);

        this.addView(view, -1, LayoutParams.WRAP_CONTENT);
    }
}
