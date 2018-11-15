package com.littleant.carrepair.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.Constraints;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.TextureMapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.navi.INaviInfoCallback;
import com.amap.api.navi.model.AMapNaviLocation;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.example.xlhratingbar_lib.XLHRatingBar;
import com.littleant.carrepair.R;
import com.littleant.carrepair.activies.car.AddCarActivity;
import com.littleant.carrepair.activies.upkeep.BookUpkeepActivity;
import com.littleant.carrepair.activies.repair.RepairActivity;
import com.littleant.carrepair.activies.repair.RepairStationActivity;
import com.littleant.carrepair.activies.main.SearchActivity;
import com.littleant.carrepair.request.bean.BaseResponseBean;
import com.littleant.carrepair.request.bean.car.MyCarListBean;
import com.littleant.carrepair.request.bean.maintain.garage.GarageInfo;
import com.littleant.carrepair.request.bean.maintain.garage.GarageListBean;
import com.littleant.carrepair.request.bean.system.violation.ViolationBean;
import com.littleant.carrepair.request.constant.ParamsConstant;
import com.littleant.carrepair.request.excute.maintain.garage.GarageQueryAllCmd;
import com.littleant.carrepair.request.excute.service.rule.RuleQueryAllCmd;
import com.littleant.carrepair.request.excute.user.car.CarQueryAllCmd;
import com.littleant.carrepair.request.utils.DataHelper;
import com.littleant.carrepair.utils.ProjectUtil;
import com.mh.core.task.MHCommandCallBack;
import com.mh.core.task.MHCommandExecute;
import com.mh.core.task.command.abstracts.MHCommand;
import com.mh.core.tools.MHToast;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MainFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment implements AMap.OnMyLocationChangeListener, AMapLocationListener,
        AMap.OnMarkerClickListener, AMap.OnMapClickListener, View.OnClickListener, INaviInfoCallback, AMap.OnMapLoadedListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final String TAG = MainFragment.class.getSimpleName();

    //初始化地图控制器对象
    private AMap aMap;
    private TextureMapView mMapView;
//    private MapView mMapView = null;
    private TextView m_input_search, m_location;
    private RadioGroup mRadiogroup;
    //单选项
    private RadioButton mRepair, mMaintain;
    //主页汽修厂地图指引部分
    private TextView lmfd_tv_book, lmfd_tv_phone;
    //主页汽修厂信息部分
    private TextView lmfd_tv_title, lmfd_tv_address;
    //主页汽修厂评分控件
    private XLHRatingBar lmfd_ratingBar;
    //我的位置
    private double myLatitude, myLongitude;
    //维修厂信息列表
    private static ArrayList<GarageInfo> data;
    //当前选中的维修点
    private GarageInfo selectedInfo;
    //主页维修厂View
    private View main_include;
    //导航按钮
    private ImageView lmfd_iv_navi;
    //回到我的位置
    private ImageView main_iv_my_location;
    public static final String GARAGE_LIST = "garage_list";
    public static final String MY_LATITUDE = "my_latitude";
    public static final String MY_LONGITUDE = "my_longitude";
    //
    public static final String GARAGE_INFO = "garage_info";

    private String mParam1;
    private String mParam2;
    private static MyCarListBean carListBean=null;
    private static ViolationBean violationBean=null;
    private OnFragmentInteractionListener mListener;

    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFragment.
     */
    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.i(TAG, "onCreateView");
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        mMapView = view.findViewById(R.id.m_map);
        mMapView.onCreate(savedInstanceState);// 此方法必须重写

        mRepair = view.findViewById(R.id.m_repair);
        mMaintain = view.findViewById(R.id.m_maintain);

        m_input_search = view.findViewById(R.id.m_input_search);
        m_input_search.setOnClickListener(this);

//        lmfd_tv_more = view.findViewById(R.id.lmfd_tv_more);
//        lmfd_tv_more.setOnClickListener(this);

        lmfd_tv_book = view.findViewById(R.id.lmfd_tv_book);
        lmfd_tv_book.setOnClickListener(this);

        lmfd_tv_phone = view.findViewById(R.id.lmfd_tv_phone);
        lmfd_tv_phone.setOnClickListener(this);

        lmfd_tv_title = view.findViewById(R.id.lmfd_tv_title);

        lmfd_tv_address = view.findViewById(R.id.lmfd_tv_address);

        lmfd_ratingBar = view.findViewById(R.id.lmfd_ratingBar);

        main_include = view.findViewById(R.id.main_include);
        main_include.setOnClickListener(this);

        //城市名
        m_location = view.findViewById(R.id.m_location);

        main_iv_my_location = view.findViewById(R.id.main_iv_my_location);
        main_iv_my_location.setOnClickListener(this);

        lmfd_iv_navi = view.findViewById(R.id.lmfd_iv_navi);
        lmfd_iv_navi.setOnClickListener(this);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(TAG, "onActivityCreated");
        aMap = mMapView.getMap();
        MyLocationStyle myLocationStyle;
        myLocationStyle = new MyLocationStyle();
        myLocationStyle.interval(1000 * 2);
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，并且蓝点会跟随设备移动。
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.setMyLocationEnabled(true);
        aMap.setOnMyLocationChangeListener(this);
        aMap.setOnMarkerClickListener(this);
        aMap.setOnMapClickListener(this);
        aMap.setOnMapLoadedListener(this);

        UiSettings uiSettings = aMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(false);

    }

    private void requestGarageList() {
        if(data==null){
            GarageQueryAllCmd garageQueryAllCmd = new GarageQueryAllCmd(getContext(), "", ParamsConstant.OrderRule.ALL, myLongitude, myLatitude);
            garageQueryAllCmd.setCallback(new MHCommandCallBack() {
                @Override
                public void cmdCallBack(MHCommand command) {
                    if (command != null) {
                        Log.i("response", command.getResponse());
                        BaseResponseBean responseBean = ProjectUtil.getBaseResponseBean(command.getResponse());
                        if(responseBean != null && ParamsConstant.REAPONSE_CODE_SUCCESS == responseBean.getCode()) {
                            GarageListBean garageListBean = ProjectUtil.getBaseResponseBean(command.getResponse(), GarageListBean.class);
                            data = (ArrayList<GarageInfo>) garageListBean.getData();
                            if(data != null && data.size() > 0) {
                                for(int index = 0; index < data.size(); index++) {
                                    GarageInfo info = data.get(index);
                                    LatLng latLng = new LatLng(info.getLatitude(), info.getLongitude());
                                    aMap.addMarker(new MarkerOptions().position(latLng).title(info.getName()).snippet(index + "")
                                            .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.marker_pic))));
                                }
                                selectedInfo = data.get(0);
                                lmfd_tv_title.setText(selectedInfo.getName());
                                String distance = Math.round(selectedInfo.getDistance()) + "";
                                String address = selectedInfo.getAddress();
                                lmfd_tv_address.setText(String.format(getResources().getString(R.string.text_main_garage_location), distance, address));
                                int count = Math.round(selectedInfo.getScore());
                                lmfd_ratingBar.setCountSelected(count);
                            }

                        } else if(responseBean != null && ParamsConstant.REAPONSE_CODE_AUTH_FAIL == responseBean.getCode()) {
                            Intent intent = ProjectUtil.tokenExpiredIntent(getActivity());
                            startActivity(intent);
                        } else if(responseBean != null && !TextUtils.isEmpty(responseBean.getMsg())) {
                            MHToast.showS(getContext(), responseBean.getMsg());
                        }
                    } else {
                        MHToast.showS(getContext(), R.string.request_fail);
                    }
                }
            });
            MHCommandExecute.getInstance().asynExecute(getContext(), garageQueryAllCmd);
        }else{
            for(int index = 0; index < data.size(); index++) {
                GarageInfo info = data.get(index);
                LatLng latLng = new LatLng(info.getLatitude(), info.getLongitude());
                aMap.addMarker(new MarkerOptions().position(latLng).title(info.getName()).snippet(index + "")
                        .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.marker_pic))));
            }
            selectedInfo = data.get(0);
            lmfd_tv_title.setText(selectedInfo.getName());
            String distance = Math.round(selectedInfo.getDistance()) + "";
            String address = selectedInfo.getAddress();
            lmfd_tv_address.setText(String.format(getResources().getString(R.string.text_main_garage_location), distance, address));
            int count = Math.round(selectedInfo.getScore());
            lmfd_ratingBar.setCountSelected(count);
        }

    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onMyLocationChange(Location location) {
        Log.i("map", "onLocationChanged");
        if (location != null) {
            if (myLongitude == 0 || myLatitude == 0) { //第一次进入地图，首次定位成功时，移动屏幕并请求站点信息
                //可在其中解析amapLocation获取相应内容。
                myLatitude = location.getLatitude();
                myLongitude = location.getLongitude();
                LatLng myLatLng = new LatLng(myLatitude, myLongitude);
                CameraUpdate mCameraUpdate = CameraUpdateFactory.newCameraPosition(new CameraPosition(myLatLng, 17f, 0, 0));
                aMap.moveCamera(mCameraUpdate);
                requestGarageList();
            } else { //只刷新当前位置
                myLatitude = location.getLatitude();
                myLongitude = location.getLongitude();
            }
            DataHelper.saveMyLocation(getContext(), myLatitude, myLongitude);
            String city = m_location.getText().toString();
            if(TextUtils.isEmpty(city)) {
                GeocodeSearch geocoderSearch = new GeocodeSearch(getContext());
                geocoderSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
                    @Override
                    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
                        m_location.setText(regeocodeResult.getRegeocodeAddress().getCity());
                    }

                    @Override
                    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

                    }
                });
                LatLonPoint latLonPoint = new LatLonPoint(myLatitude, myLongitude);
                RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 200, GeocodeSearch.AMAP);
                geocoderSearch.getFromLocationAsyn(query);
            }
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        int index = -1;
        try {
            index = Integer.parseInt(marker.getSnippet());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(data != null && index !=  -1) {
            selectedInfo = data.get(index);
            if(selectedInfo != null) {
//                main_include.setVisibility(View.VISIBLE);
                lmfd_tv_title.setText(selectedInfo.getName());
                String distance = Math.round(selectedInfo.getDistance()) + "";
                String address = selectedInfo.getAddress();
                lmfd_tv_address.setText(String.format(getResources().getString(R.string.text_main_garage_location), distance, address));
                int count = Math.round(selectedInfo.getScore());
                lmfd_ratingBar.setCountSelected(count);
                return true;
            }
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        if(DataHelper.getGuestLogin(getContext())) {
            getActivity().finish();
            return;
        }
        Intent intent;
        switch (v.getId()) {
            case R.id.m_input_search:
                intent = new Intent(getContext(), SearchActivity.class);
                intent.putExtra(GARAGE_LIST, data);
                intent.putExtra(MY_LATITUDE, myLatitude);
                intent.putExtra(MY_LONGITUDE, myLongitude);
                getActivity().startActivity(intent);
                break;

            case R.id.main_include:
                intent = new Intent(getContext(), RepairStationActivity.class);
                intent.putExtra(GARAGE_INFO, selectedInfo);
                intent.putExtra(MY_LATITUDE, myLatitude);
                intent.putExtra(MY_LONGITUDE, myLongitude);
                getActivity().startActivity(intent);
                break;

            case R.id.lmfd_tv_book:
                if(mRepair.isChecked()) {
                    if(selectedInfo.getType()==2){
                        MHToast.showS(getContext(), R.string.request_fail_repair);
                        return;
                    }
                    //维修
                    intent = new Intent(getContext(), RepairActivity.class);
                } else if(mMaintain.isChecked()) {
                    if(selectedInfo.getType()==1){
                        MHToast.showS(getContext(), R.string.request_fail_maintain);
                        return;
                    }
                    //保养
                    intent = new Intent(getContext(), BookUpkeepActivity.class);
                } else {
                    return;
                }
                intent.putExtra(GARAGE_INFO, selectedInfo);
                getActivity().startActivity(intent);
                break;

            case R.id.lmfd_tv_phone:
                String phone = selectedInfo.getPhone();
                if(!TextUtils.isEmpty(phone)) {
                    DataHelper.callPhone(getActivity(), phone);
                }
                break;

            case R.id.lmfd_iv_navi:
                LatLng startLocation = new LatLng(myLatitude, myLongitude);
                LatLng endLocation = new LatLng(selectedInfo.getLatitude(), selectedInfo.getLongitude());
                DataHelper.prepareNavi(getContext(), startLocation, endLocation, this);
                break;

            case R.id.main_iv_my_location:
                if(myLatitude == 0 || myLongitude == 0) {
                    return;
                }
                LatLng myLatLng = new LatLng(myLatitude, myLongitude);
                CameraUpdate mCameraUpdate = CameraUpdateFactory.newCameraPosition(new CameraPosition(myLatLng, 17f, 0, 0));
                aMap.moveCamera(mCameraUpdate);
                break;
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {
//        if(main_include != null && main_include.getVisibility() == View.VISIBLE) {
//            main_include.setVisibility(View.GONE);
//        }
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

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {

    }

    @Override
    public void onMapLoaded() {
        Log.i("carproject", "onMapLoaded");
        if(!DataHelper.getGuestLogin(getContext())) {
            requestDefaultCar();
            requestViolation();
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mMapView != null) {
            mMapView.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mMapView != null) {
            mMapView.onPause();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mMapView != null) {
            mMapView.onDestroy();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mMapView != null) {
            mMapView.onSaveInstanceState(outState);
        }
    }

    private void requestDefaultCar() {
        if(carListBean==null){
            CarQueryAllCmd carQueryAllCmd = new CarQueryAllCmd(getContext(), ParamsConstant.QueryType.DEFAULT);
            carQueryAllCmd.setCallback(new MHCommandCallBack() {
                @Override
                public void cmdCallBack(MHCommand command) {
                    if (command != null) {
                        Log.i("response", command.getResponse());
                        BaseResponseBean responseBean = ProjectUtil.getBaseResponseBean(command.getResponse());
                        if(responseBean != null && ParamsConstant.REAPONSE_CODE_SUCCESS == responseBean.getCode()) {
                            carListBean = ProjectUtil.getBaseResponseBean(command.getResponse(), MyCarListBean.class);
                            if(carListBean == null || carListBean.getData() == null || carListBean.getData().size() < 1) {
                                //沒有默認車，跳添加車輛頁面
                                Intent intent = new Intent(getContext(), AddCarActivity.class);
                                startActivity(intent);
                            }
                        } else if(responseBean != null && ParamsConstant.REAPONSE_CODE_AUTH_FAIL == responseBean.getCode()) {
                            Intent intent = ProjectUtil.tokenExpiredIntent(getContext());
                            startActivity(intent);
                        } else if(responseBean != null && !TextUtils.isEmpty(responseBean.getMsg())) {
                            MHToast.showS(getContext(), responseBean.getMsg());
                        }
                    } else {
                        MHToast.showS(getContext(), R.string.request_fail);
                    }
                }
            });
            MHCommandExecute.getInstance().asynExecute(getContext(), carQueryAllCmd);
        }

    }

    private void requestViolation() {
        if(violationBean==null){

            RuleQueryAllCmd ruleQueryAllCmd = new RuleQueryAllCmd(getContext());
            ruleQueryAllCmd.setCallback(new MHCommandCallBack() {
                @Override
                public void cmdCallBack(MHCommand command) {
                    if(command != null) {
                        Log.i("register response", command.getResponse());
                        BaseResponseBean responseBean = ProjectUtil.getBaseResponseBean(command.getResponse());
                        if(responseBean != null && ParamsConstant.REAPONSE_CODE_SUCCESS == responseBean.getCode()) {
                            violationBean= ProjectUtil.getBaseResponseBean(command.getResponse(), ViolationBean.class);
                            final List<ViolationBean.ViolationInfo> data = violationBean.getData();
                            if(data == null || data.size() < 1) {
                                return;
                            }
                            final Dialog d = new Dialog(getContext(), R.style.MyTransparentDialog);
                            View contentView = View.inflate(getContext(), R.layout.layout_violation, null);
                            DisplayMetrics dm = getContext().getResources().getDisplayMetrics();
                            int dialogWidth = (int) (dm.widthPixels * 0.8);
                            int dialogHeight = (int) (dm.heightPixels * 0.35);
                            d.setContentView(contentView, new Constraints.LayoutParams(dialogWidth, dialogHeight));
                            contentView.findViewById(R.id.lv_close).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    d.dismiss();
                                }
                            });

                            final TextView lv_tv_brand = contentView.findViewById(R.id.lv_tv_brand);
                            final TextView lv_tv_code = contentView.findViewById(R.id.lv_tv_code);
                            final TextView lv_tv_sum = contentView.findViewById(R.id.lv_tv_sum);
                            final TextView lv_tv_score = contentView.findViewById(R.id.lv_tv_score);
                            final TextView lv_tv_fine = contentView.findViewById(R.id.lv_tv_fine);
                            ImageView lv_iv_pic = contentView.findViewById(R.id.lv_iv_pic);
                            TextView lv_tv_ok = contentView.findViewById(R.id.lv_tv_ok);

                            ViolationBean.ViolationInfo info = data.remove(0);
                            Picasso.with(getContext()).load(Uri.parse(info.getCar_pic_url())).resize(115, 80).into(lv_iv_pic);
                            lv_tv_brand.setText(info.getCar_brand());
                            lv_tv_code.setText(info.getCar_code());
                            lv_tv_sum.setText(String.format(getResources().getString(R.string.text_violation_sum), info.getAmount() + ""));
                            lv_tv_score.setText(String.format(getResources().getString(R.string.text_violation_score), info.getScore() + ""));
                            lv_tv_fine.setText(String.format(getResources().getString(R.string.text_violation_fine), info.getPrice() + ""));
                            lv_tv_ok.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if(data.size() > 0) {
                                        ViolationBean.ViolationInfo info2 = data.remove(0);
                                        lv_tv_brand.setText(info2.getCar_brand());
                                        lv_tv_code.setText(info2.getCar_code());
                                        lv_tv_sum.setText(String.format(getResources().getString(R.string.text_violation_sum), info2.getAmount() + ""));
                                        lv_tv_score.setText(String.format(getResources().getString(R.string.text_violation_score), info2.getScore() + ""));
                                        lv_tv_fine.setText(String.format(getResources().getString(R.string.text_violation_fine), info2.getPrice() + ""));
                                    } else {
                                        d.dismiss();
                                    }
                                }
                            });


//                        RecyclerView mList = contentView.findViewById(R.id.lv_list);
//                        mList.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
//                        mList.setAdapter(new MyAdapter(data));
                            d.show();
                        } else if(responseBean != null && ParamsConstant.REAPONSE_CODE_AUTH_FAIL == responseBean.getCode()) {
                            Intent intent = ProjectUtil.tokenExpiredIntent(getContext());
                            startActivity(intent);
                        }
                    }
                }
            });
            MHCommandExecute.getInstance().asynExecute(getContext(), ruleQueryAllCmd);
        }

    }
}
