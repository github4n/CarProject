package com.littleant.carrepair.activies.repair;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.maps.model.LatLng;
import com.amap.api.navi.INaviInfoCallback;
import com.amap.api.navi.model.AMapNaviLocation;
import com.example.xlhratingbar_lib.XLHRatingBar;
import com.littleant.carrepair.R;
import com.littleant.carrepair.activies.upkeep.BookUpkeepActivity;
import com.littleant.carrepair.fragment.MainFragment;
import com.littleant.carrepair.request.bean.maintain.garage.GarageInfo;
import com.littleant.carrepair.request.utils.DataHelper;
import com.mh.core.tools.MHToast;
import com.squareup.picasso.Picasso;

import static com.littleant.carrepair.fragment.MainFragment.GARAGE_INFO;
import static java.security.AccessController.getContext;

/**
 * 维修点
 */
public class RepairStationActivity extends AppCompatActivity implements View.OnClickListener, INaviInfoCallback {

    private Context mContext;
    private ImageView rs_iv_back, rs_iv_like;
    //private TextView rs_btn_navi, rs_btn_contact;
    private GarageInfo garageInfo;
    //控件
    private TextView rs_tv_title, rs_contact, rs_phone, rs_address;
    private ImageView rs_tv_banner,repair_call,repair_navis;
    private double myLatitude, myLongitude;
    private XLHRatingBar rs_ratingBar;
    private TextView rs_btn_repair,rs_btn_upkepp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repair_station);

        mContext = this;

        rs_iv_back = findViewById(R.id.rs_iv_back);
        rs_iv_back.setOnClickListener(this);

        rs_iv_like = findViewById(R.id.rs_iv_like);
        rs_iv_like.setOnClickListener(this);

        repair_call = findViewById(R.id.repair_call);
        repair_call.setOnClickListener(this);

        repair_navis = findViewById(R.id.repair_navis);
        repair_navis.setOnClickListener(this);

        rs_btn_repair=findViewById(R.id.rs_btn_repair);
        rs_btn_repair.setOnClickListener(this);

        rs_btn_upkepp=findViewById(R.id.rs_btn_upkepp);
        rs_btn_upkepp.setOnClickListener(this);

        rs_tv_title = findViewById(R.id.rs_tv_title);
        rs_ratingBar = findViewById(R.id.rs_ratingBar);
//        rs_tv_like_amount = findViewById(R.id.rs_tv_like_amount);
        rs_contact = findViewById(R.id.rs_contact);
        rs_phone = findViewById(R.id.rs_phone);
        rs_address = findViewById(R.id.rs_address);
        rs_tv_banner = findViewById(R.id.rs_tv_banner);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
           garageInfo = (GarageInfo) extras.getSerializable(GARAGE_INFO);
           myLatitude = DataHelper.getMyLocation(this)[0];
           myLongitude = DataHelper.getMyLocation(this)[1];;
        }
        if(garageInfo == null) {
            this.finish();
        }
        rs_tv_title.setText(garageInfo.getName());
//        rs_tv_like_amount.setText(garageInfo.getPopular() + "");
        rs_ratingBar.setCountSelected(garageInfo.getScore());
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

            case R.id.repair_navis:
                LatLng startLocation = new LatLng(myLatitude, myLongitude);
                LatLng endLocation = new LatLng(garageInfo.getLatitude(), garageInfo.getLongitude());
                DataHelper.prepareNavi(mContext, startLocation, endLocation, this);
                break;

            case R.id.repair_call:
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

            case R.id.rs_btn_repair:
                if(garageInfo.getType()==2){
                    MHToast.showS(this, R.string.request_fail_repair);
                    return;
                }
               Intent intent = new Intent(this, RepairActivity.class);
               intent.putExtra(GARAGE_INFO, garageInfo);
               startActivity(intent);
                break;
            case R.id.rs_btn_upkepp:
                if(garageInfo.getType()==1){
                    MHToast.showS(this, R.string.request_fail_maintain);
                    return;
                }
                //保养
                Intent intent1 = new Intent(this, BookUpkeepActivity.class);
                intent1.putExtra(GARAGE_INFO, garageInfo);
                startActivity(intent1);
                break;
        }
    }

    @Override
    public void onInitNaviFailure() {

    }

    @Override
    public void onGetNavigationText(String s) {

    }

    @Override
    public void onLocationChange(AMapNaviLocation aMapNaviLocation) {

    }

    @Override
    public void onArriveDestination(boolean b) {

    }

    @Override
    public void onStartNavi(int i) {

    }

    @Override
    public void onCalculateRouteSuccess(int[] ints) {

    }

    @Override
    public void onCalculateRouteFailure(int i) {

    }

    @Override
    public void onStopSpeaking() {

    }

    @Override
    public void onReCalculateRoute(int i) {

    }

    @Override
    public void onExitPage(int i) {

    }

    @Override
    public void onStrategyChanged(int i) {

    }

    @Override
    public View getCustomNaviBottomView() {
        return null;
    }

    @Override
    public View getCustomNaviView() {
        return null;
    }

    @Override
    public void onArrivedWayPoint(int i) {

    }
}
