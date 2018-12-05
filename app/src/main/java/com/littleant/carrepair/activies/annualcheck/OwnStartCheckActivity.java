package com.littleant.carrepair.activies.annualcheck;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.constraint.Constraints;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
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
import com.littleant.carrepair.activies.pay.PaymentActivity;
import com.littleant.carrepair.request.bean.BaseResponseBean;
import com.littleant.carrepair.request.bean.login.TermUrlBean;
import com.littleant.carrepair.request.bean.survey.SurveyCreateBean;
import com.littleant.carrepair.request.bean.survey.SurveyInfo;
import com.littleant.carrepair.request.bean.survey.SurveyPhoneInfo;
import com.littleant.carrepair.request.constant.ParamsConstant;
import com.littleant.carrepair.request.excute.survey.survey.SurveyMethodCmd;
import com.littleant.carrepair.request.excute.survey.survey.SurveyPphoneCmd;
import com.littleant.carrepair.request.excute.survey.survey.SurveyUserCancelInfoCmd;
import com.littleant.carrepair.request.utils.DataHelper;
import com.littleant.carrepair.utils.ProjectUtil;
import com.mh.core.task.MHCommandCallBack;
import com.mh.core.task.MHCommandExecute;
import com.mh.core.task.command.abstracts.MHCommand;
import com.mh.core.tools.MHToast;

import static com.littleant.carrepair.activies.annualcheck.AnnualCheckRecordActivity.SURVEY_INFO;

public class OwnStartCheckActivity extends BaseActivity implements AMap.OnMyLocationChangeListener, AMapLocationListener, INaviInfoCallback {
    private CheckBox losf_tv_fill, losf_tv_start;
    private TextView aosc_type, aosc_location, aosc_time,header_option_text,aose_pay;
    private ImageView aosc_iv_call, aosc_iv_navi;
    private MapView aosc_map;
    private SurveyInfo info;
    private AMap aMap;
    private double myLongitude, myLatitude;
    private String phone;

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
        header_option_text=findViewById(R.id.header_option_text);
        header_option_text.setOnClickListener(this);
        aosc_iv_call = findViewById(R.id.aosc_iv_call);
        aosc_iv_call.setOnClickListener(this);

        aosc_iv_navi = findViewById(R.id.aosc_iv_navi);
        aosc_iv_navi.setOnClickListener(this);

        aosc_map = findViewById(R.id.aosc_map);
        //确认到达
        aose_pay=findViewById(R.id.aose_pay);
        aose_pay.setOnClickListener(this);

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
    protected int getOptionStringId() {
        return R.string.btn_delete_order;
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
                SurveyPphoneCmd surveyPphoneCmd=new SurveyPphoneCmd(this);
                surveyPphoneCmd.setCallback(new MHCommandCallBack() {
                    @Override
                    public void cmdCallBack(MHCommand command) {
                        BaseResponseBean responseBean = ProjectUtil.getBaseResponseBean(command.getResponse());
                        if(responseBean != null && ParamsConstant.REAPONSE_CODE_SUCCESS == responseBean.getCode()) {
                            SurveyPhoneInfo createBean = ProjectUtil.getBaseResponseBean(command.getResponse(), SurveyPhoneInfo.class);
                            if(createBean.getData().getPhone()!=null){
                                phone=createBean.getData().getPhone();
                            }
                            if(createBean.getData().getMoblie_phone()!=null){
                                phone=createBean.getData().getMoblie_phone();
                            }

                            DataHelper.callPhone(OwnStartCheckActivity.this, phone);
                        }

                    }
                });
                MHCommandExecute.getInstance().asynExecute(mContext, surveyPphoneCmd);

                break;

            case R.id.aosc_iv_navi:
                LatLng startLocation = new LatLng(myLatitude, myLongitude);
                LatLng endLocation = new LatLng(info.getSurveystation().getLatitude(), info.getSurveystation().getLongitude());
                DataHelper.prepareNavi(mContext, startLocation, endLocation, this);
                break;
            case R.id.header_option_text:
                requestCancelInfo();
//                SurveyMethodCmd surveyMethodCmd=new SurveyMethodCmd(this,info.getId(), ParamsConstant.SurveyMethodType.CANCEL,"","",0,0,"",ParamsConstant.PayChannel.ALI);
//                surveyMethodCmd.setCallback(new MHCommandCallBack() {
//                    @Override
//                    public void cmdCallBack(MHCommand command) {
//                        BaseResponseBean responseBean = ProjectUtil.getBaseResponseBean(command.getResponse());
//
//                    }
//                });
//                MHCommandExecute.getInstance().asynExecute(mContext, surveyMethodCmd);

                break;
            case R.id.aose_pay:
                SurveyMethodCmd surveyMethodCmd=new SurveyMethodCmd(this,info.getId(), ParamsConstant.SurveyMethodType.SURVEY,"","",0,0,"",null);
                surveyMethodCmd.setCallback(new MHCommandCallBack() {
                    @Override
                    public void cmdCallBack(MHCommand command) {
                        BaseResponseBean responseBean = ProjectUtil.getBaseResponseBean(command.getResponse());
                        if(responseBean != null && ParamsConstant.REAPONSE_CODE_SUCCESS == responseBean.getCode()) {
                            MHToast.showS(mContext, responseBean.getMsg());
                            finish();
                            Intent intent = new Intent(OwnStartCheckActivity.this, AnnualCheckRecordActivity.class);
                            OwnStartCheckActivity.this.startActivity(intent);
                        }else{
                            MHToast.showS(mContext, responseBean.getMsg());

                        }
                    }
                });
                MHCommandExecute.getInstance().asynExecute(mContext, surveyMethodCmd);

                break;
        }
    }
    private void requestCancelInfo() {
        SurveyUserCancelInfoCmd cmd = new SurveyUserCancelInfoCmd(mContext);
        cmd.setCallback(new MHCommandCallBack() {
            @Override
            public void cmdCallBack(MHCommand command) {
                if (command != null) {
                    Log.i("response", command.getResponse());
                    TermUrlBean responseBean = ProjectUtil.getBaseResponseBean(command.getResponse(), TermUrlBean.class);
                    if(responseBean != null && ParamsConstant.REAPONSE_CODE_SUCCESS == responseBean.getCode()) {
                        String termUrl = responseBean.getData().getUrl();
                        showTermDialog(termUrl);
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
        MHCommandExecute.getInstance().asynExecute(mContext, cmd);
    }
    private void showTermDialog(String termUrl) {
        final Dialog d = new Dialog(mContext, R.style.MyTransparentDialog);
        View contentView = View.inflate(mContext, R.layout.layout_cancel_dialog, null);
        DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
        int dialogWidth = (int) (dm.widthPixels * 0.8);
        int dialogHeight = (int) (dm.heightPixels * 0.4);
        d.setContentView(contentView, new Constraints.LayoutParams(dialogWidth, dialogHeight));
        d.setCanceledOnTouchOutside(false);
        d.setCancelable(false);
        WebView webView = contentView.findViewById(R.id.lcd_webview);
        webView.loadUrl(termUrl);
        contentView.findViewById(R.id.lcd_btn_think).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                d.dismiss();
            }
        });
        contentView.findViewById(R.id.lcd_btn_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                d.dismiss();
                cancelOrder();
            }
        });
        d.show();
    }
    private void cancelOrder() {
                        SurveyMethodCmd surveyMethodCmd=new SurveyMethodCmd(this,info.getId(), ParamsConstant.SurveyMethodType.CANCEL,"","",0,0,"",ParamsConstant.PayChannel.ALI);
                surveyMethodCmd.setCallback(new MHCommandCallBack() {
                    @Override
                    public void cmdCallBack(MHCommand command) {
                        BaseResponseBean responseBean = ProjectUtil.getBaseResponseBean(command.getResponse());
                        if(responseBean != null && ParamsConstant.REAPONSE_CODE_SUCCESS == responseBean.getCode()) {
                            finishActivityForOk();
                        }else {
                            MHToast.showS(mContext, responseBean.getMsg());

                        }

                    }
                });
                MHCommandExecute.getInstance().asynExecute(mContext, surveyMethodCmd);
    }
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {

    }
    private void finishActivityForOk() {
        setResult(Activity.RESULT_OK);
        finish();
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
