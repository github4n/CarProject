package com.littleant.carrepair.activies;

import android.Manifest;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.littleant.carrepair.R;
import com.littleant.carrepair.fragment.AnnualCheckFragment;
import com.littleant.carrepair.fragment.BaseFragment;
import com.littleant.carrepair.fragment.MainFragment;
import com.littleant.carrepair.fragment.ServiceFragment;
import com.littleant.carrepair.fragment.UserCenterFragment;

/**
 * 主页
 */
public class MainActivity extends AppCompatActivity implements BaseFragment.OnFragmentInteractionListener{
    /**
     * 高德地图
     */
    private static final int permission_request_code = 10;
    private RadioGroup radioGroup;
    private RadioButton mainBtn, annaulCheckBtn, serviceBtn, userCenterBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, permission_request_code);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_fragment, new MainFragment(), MainFragment.class.getSimpleName());
        transaction.commitAllowingStateLoss();

        radioGroup = findViewById(R.id.linearLayout);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                Fragment fragment = null;
                String tag = "";
                switch (i) {
                    case R.id.mainBtn:
                        fragment = new MainFragment();
                        tag = MainFragment.class.getSimpleName();
                        break;

                    case R.id.annualBtn:
                        fragment = new AnnualCheckFragment();
                        tag = AnnualCheckFragment.class.getSimpleName();
                        break;

                    case R.id.serviceBtn:
                        fragment = new ServiceFragment();
                        tag = ServiceFragment.class.getSimpleName();
                        break;

                    case R.id.mineBtn:
                        fragment = new UserCenterFragment();
                        tag = UserCenterFragment.class.getSimpleName();
                        break;
                }
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.main_fragment, fragment, tag);
                transaction.commitAllowingStateLoss();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
//        if(mMapView != null) {
//            mMapView.onResume();
//        }
    }

    @Override
    protected void onPause() {
        super.onPause();
//        if(mMapView != null) {
//            mMapView.onPause();
//        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if(mMapView != null) {
//            mMapView.onDestroy();
//        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        if(mMapView != null) {
//            mMapView.onSaveInstanceState(outState);
//        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == permission_request_code) {
            if(grantResults.length > 0 && permissions.length > 0) {
                //获取定位权限
//                MyLocationStyle myLocationStyle;
//                myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
//                myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
//                aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
//                //aMap.getUiSettings().setMyLocationButtonEnabled(true);设置默认定位按钮是否显示，非必需设置。
//                aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
            } else {
                //获取定位权限失败
                Toast.makeText(this, R.string.permission_deny_location, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
