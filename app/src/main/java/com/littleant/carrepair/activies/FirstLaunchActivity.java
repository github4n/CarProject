package com.littleant.carrepair.activies;

import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.littleant.carrepair.R;
import com.littleant.carrepair.activies.login.LoginActivity;
import com.littleant.carrepair.request.bean.system.SystemCoverBean;
import com.littleant.carrepair.utils.GlideImageLoader;
import com.littleant.carrepair.utils.GlideImageLoader2;
import com.littleant.carrepair.utils.ProjectUtil;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import static com.littleant.carrepair.activies.SplashActivity.COVER_LIST;

public class FirstLaunchActivity extends AppCompatActivity {

    private Banner afl_banner;
    private TextView afl_auth;
    private SystemCoverBean.CoverList data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_launch);

        ProjectUtil.saveLaunched(this);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            data = (SystemCoverBean.CoverList) extras.getSerializable(COVER_LIST);
        }

        afl_auth = findViewById(R.id.afl_auth);
        afl_auth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirstLaunchActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        afl_banner = findViewById(R.id.afl_banner);
        //设置banner样式
        afl_banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        afl_banner.isAutoPlay(false);
        if(data != null) {
            afl_banner.setImageLoader(new GlideImageLoader());
            afl_banner.setImages(data.getPic_url_list());
        } else {
            afl_banner.setImageLoader(new GlideImageLoader2());
            List<Uri> picList = new ArrayList<Uri>();
            picList.add(Uri.parse("file:///android_asset/launch1.png"));
            picList.add(Uri.parse("file:///android_asset/launch2.png"));
            picList.add(Uri.parse("file:///android_asset/launch3.png"));
            afl_banner.setImages(picList);
        }
        afl_banner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                Log.i("PageChange", "onPageScrolled : " + position);
//                afl_auth.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onPageSelected(int position) {
                Log.i("PageChange", "onPageSelected : " + position);
                if(position == 2) {
                    afl_auth.setVisibility(View.VISIBLE);
                } else {
                    afl_auth.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
//                Log.i("PageChange", "onPageScrollStateChanged : " + state);
            }
        });
/*        afl_banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                if (position == 2) {
                    Intent intent = new Intent(FirstLaunchActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });*/
        afl_banner.start();
    }
}
