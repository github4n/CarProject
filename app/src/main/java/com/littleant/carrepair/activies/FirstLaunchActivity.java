package com.littleant.carrepair.activies;

import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.littleant.carrepair.R;
import com.littleant.carrepair.activies.login.LoginActivity;
import com.littleant.carrepair.utils.GlideImageLoader2;
import com.littleant.carrepair.utils.ProjectUtil;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

public class FirstLaunchActivity extends AppCompatActivity {

    private Banner afl_banner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_launch);

        ProjectUtil.saveLaunched(this);

        afl_banner = findViewById(R.id.afl_banner);
        afl_banner.setImageLoader(new GlideImageLoader2());
        //设置banner样式
        afl_banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        afl_banner.isAutoPlay(false);
        List<Uri> picList = new ArrayList<Uri>();
        picList.add(Uri.parse("file:///android_asset/launch1.png"));
        picList.add(Uri.parse("file:///android_asset/launch2.png"));
        picList.add(Uri.parse("file:///android_asset/launch3.png"));
        afl_banner.setImages(picList);
        afl_banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                if (position == 2) {
                    Intent intent = new Intent(FirstLaunchActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
        afl_banner.start();
    }
}
