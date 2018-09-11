package com.littleant.carrepair.activies.shopping;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.littleant.carrepair.R;
import com.littleant.carrepair.activies.BaseActivity;

/**
 * 商城
 */
public class ShoppingActivity extends BaseActivity {

    private GridView mGridView;
    private ImageView shoppingCar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mGridView = findViewById(R.id.s_gv_list);
        mGridView.setAdapter(new MyAdapter());

        shoppingCar = findViewById(R.id.s_iv_shoppingcar);
        shoppingCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShoppingActivity.this, ShoppingCarActivity.class);
                ShoppingActivity.this.startActivity(intent);
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shopping;
    }

    @Override
    protected int getTitleId() {
        return R.string.text_shop;
    }

    @Override
    protected int getOptionBackgroundId() {
        return R.drawable.shopping_search;
    }

    @Override
    public void onClick(View v) {

    }

    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 10;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = LayoutInflater.from(ShoppingActivity.this).inflate(R.layout.layout_shop_item, viewGroup, false);
            return view;
        }
    }
}
