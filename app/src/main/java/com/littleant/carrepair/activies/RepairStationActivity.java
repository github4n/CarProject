package com.littleant.carrepair.activies;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.littleant.carrepair.R;
import com.littleant.carrepair.request.bean.GarageListBean;
import com.littleant.carrepair.request.utils.DataHelper;
import com.squareup.picasso.Picasso;

import static com.littleant.carrepair.fragment.MainFragment.GARAGE_INFO;

/**
 * 维修点
 */
public class RepairStationActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;
    private ImageView rs_iv_back, rs_iv_like;
    private TextView rs_btn_navi, rs_btn_contact;
    private GarageListBean.GarageInfo garageInfo;
    //控件
    private TextView rs_tv_title, rs_tv_like_amount, rs_contact, rs_phone, rs_address;
    private ImageView rs_tv_banner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repair_station);

        mContext = this;

        rs_iv_back = findViewById(R.id.rs_iv_back);
        rs_iv_back.setOnClickListener(this);

        rs_iv_like = findViewById(R.id.rs_iv_like);
        rs_iv_like.setOnClickListener(this);

        rs_btn_navi = findViewById(R.id.rs_btn_navi);
        rs_btn_navi.setOnClickListener(this);

        rs_btn_contact = findViewById(R.id.rs_btn_contact);
        rs_btn_contact.setOnClickListener(this);

        rs_tv_title = findViewById(R.id.rs_tv_title);
        rs_tv_like_amount = findViewById(R.id.rs_tv_like_amount);
        rs_contact = findViewById(R.id.rs_contact);
        rs_phone = findViewById(R.id.rs_phone);
        rs_address = findViewById(R.id.rs_address);
        rs_tv_banner = findViewById(R.id.rs_tv_banner);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
           garageInfo = (GarageListBean.GarageInfo) extras.getSerializable(GARAGE_INFO);
        }
        if(garageInfo == null) {
            this.finish();
        }
        rs_tv_title.setText(garageInfo.getName());
        rs_tv_like_amount.setText(garageInfo.getPopular() + "");
        rs_contact.setText(garageInfo.getUser_name() + " | " + garageInfo.getPhone());
        rs_phone.setText(garageInfo.getMobile_phone());
        rs_address.setText(garageInfo.getAddress());
        Picasso.with(mContext).load(Uri.parse(garageInfo.getPic_url())).into(rs_tv_banner);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rs_iv_back:
                RepairStationActivity.this.finish();
                break;

            case R.id.rs_btn_navi:

                break;

            case R.id.rs_btn_contact:
                DataHelper.callPhone(RepairStationActivity.this, garageInfo.getPhone());
                break;

            case R.id.rs_iv_like:
                // TODO: 2018/9/3 缺少评分接口
//                final Dialog d = new Dialog(mContext);
//                View contentView = View.inflate(mContext, R.layout.layout_rating, null);
//                DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
//                int dialogWidth = (int) (dm.widthPixels * 0.6);
//                int dialogHeight = (int) (dm.heightPixels * 0.3);
//                d.setContentView(contentView, new Constraints.LayoutParams(dialogWidth, dialogHeight));
//                contentView.findViewById(R.id.lr_rating_ok).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        d.dismiss();
//                    }
//                });
//                d.show();
                break;
        }
    }
}
