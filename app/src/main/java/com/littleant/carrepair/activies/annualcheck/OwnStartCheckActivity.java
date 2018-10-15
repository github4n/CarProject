package com.littleant.carrepair.activies.annualcheck;

import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.navi.INaviInfoCallback;
import com.amap.api.navi.model.AMapNaviLocation;
import com.littleant.carrepair.R;
import com.littleant.carrepair.activies.BaseActivity;
import com.littleant.carrepair.request.bean.survey.SurveyInfo;
import com.littleant.carrepair.request.utils.DataHelper;

import static com.littleant.carrepair.activies.annualcheck.AnnualCheckRecordActivity.SURVEY_INFO;

public class OwnStartCheckActivity extends BaseActivity implements AMap.OnMyLocationChangeListener, AMapLocationListener, INaviInfoCallback {
    private CheckBox losf_tv_fill, losf_tv_start;
    private TextView aosc_type, aosc_location, aosc_time;
    private ImageView aosc_iv_call, aosc_iv_navi;
    private MapView aosc_map;
    private SurveyInfo info;
    private AMap aMap;
    private double myLongitude, myLatitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        losf_tv_start = findViewById(R.id.losf_tv_start);
        losf_tv_start.setChecked(true);

        aosc_map.onCreate(savedInstanceState);
        aMap = aosc_map.getMap();
        MyLocationStyle myLocationStyle;
        myLocationStyle = new MyLocationStyle();
        myLocationStyle.interval(1000 * 2);
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，并且蓝点会跟随设备移动。
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.setMyLocationEnabled(true);
        aMap.setOnMyLocationChangeListener(this);

        UiSettings uiSettings = aMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(false);
    }

    @Override
    protected void init() {
        super.init();
        aosc_type = findViewById(R.id.aosc_type);
        aosc_location = findViewById(R.id.aosc_location);
        aosc_time = findViewById(R.id.aosc_time);

        aosc_iv_call = findViewById(R.id.aosc_iv_call);
        aosc_iv_call.setOnClickListener(this);

        aosc_iv_navi = findViewById(R.id.aosc_iv_navi);
        aosc_iv_navi.setOnClickListener(this);

        aosc_map = findViewById(R.id.aosc_map);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            info = (SurveyInfo) extras.getSerializable(SURVEY_INFO);
        }
        if (info != null) {
            aosc_type.setText(info.getCar_type());
            aosc_location.setText(info.getSurveystation().getAddress());
            aosc_time.setText(info.getArrive_survey_time());

        } else {
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (aosc_map != null) {
            aosc_map.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (aosc_map != null) {
            aosc_map.onPause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (aosc_map != null) {
            aosc_map.onDestroy();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (aosc_map != null) {
            aosc_map.onSaveInstanceState(outState);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_own_start_check;
    }

    @Override
    protected int getTitleId() {
        return R.string.text_start_annual_check;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.aosc_iv_call:
                // TODO: 2018/9/14 此处应该是年检站电话
                DataHelper.callPhone(this, info.getDrive_user_phone());
                break;

            case R.id.aosc_iv_navi:
                LatLng startLocation = new LatLng(myLatitude, myLongitude);
                LatLng endLocation = new LatLng(info.getSurveystation().getLatitude(), info.getSurveystation().getLongitude());
                DataHelper.prepareNavi(mContext, startLocation, endLocation, this);
                break;
        }
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {

    }

    @Override
    public void onMyLocationChange(Location location) {
        if (location != null) {
            if (myLongitude == 0 || myLatitude == 0) { //第一次进入地图，首次定位成功时，移动屏幕并请求站点信息
                //可在其中解析amapLocation获取相应内容。
                myLatitude = location.getLatitude();
                myLongitude = location.getLongitude();
                LatLng myLatLng = new LatLng(myLatitude, myLongitude);
                CameraUpdate mCameraUpdate = CameraUpdateFactory.newCameraPosition(new CameraPosition(myLatLng, 12f, 0, 0));
                aMap.moveCamera(mCameraUpdate);
            } else { //只刷新当前位置
                myLatitude = location.getLatitude();
                myLongitude = location.getLongitude();
            }
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
