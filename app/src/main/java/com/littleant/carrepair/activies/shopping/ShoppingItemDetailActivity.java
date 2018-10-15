package com.littleant.carrepair.activies.shopping;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.littleant.carrepair.R;
import com.littleant.carrepair.activies.BaseActivity;
import com.littleant.carrepair.request.bean.shop.ProductBean;

import static com.littleant.carrepair.activies.shopping.ShoppingActivity.PRODUCT_INFO;

public class ShoppingItemDetailActivity extends BaseActivity {

    private WebView asid_webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            ProductBean productInfo = (ProductBean) extras.getSerializable(PRODUCT_INFO);
            if(productInfo != null && !TextUtils.isEmpty(productInfo.getDetail_url())) {
                asid_webview.loadUrl(productInfo.getDetail_url());
            } else {
                finish();
            }
        } else {
            finish();
        }
    }

    @Override
    protected void init() {
        super.init();
        asid_webview = findViewById(R.id.asid_webview);
        asid_webview.setWebViewClient(new WebViewClient());
        asid_webview.setWebChromeClient(new WebChromeClient());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shopping_item_detail;
    }

    @Override
    protected int getTitleId() {
        return 0;
    }

    @Override
    protected int getBackgroundColor() {
        return android.R.color.transparent;
    }

    @Override
    public void onClick(View v) {

    }
}
