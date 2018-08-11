package com.littleant.carrepair.activies;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

import com.littleant.carrepair.R;

/**
 * 商城
 */
public class ShoppingActivity extends BaseActivity {

    private GridView mGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mGridView = findViewById(R.id.s_gv_list);
        mGridView.setAdapter(new MyAdapter());
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
