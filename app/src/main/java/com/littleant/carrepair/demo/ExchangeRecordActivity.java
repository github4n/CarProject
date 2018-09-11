package com.littleant.carrepair.demo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.littleant.carrepair.R;
import com.littleant.carrepair.activies.BaseActivity;

/**
 * 余额列表
 */
public class ExchangeRecordActivity extends BaseActivity {

    private ListView exchange_record_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        exchange_record_list = findViewById(R.id.exchange_record_list);
        exchange_record_list.setAdapter(new MyAdapter());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_exchange_record;
    }

    @Override
    protected int getTitleId() {
        return R.string.text_remaining_sum_list;
    }

    @Override
    public void onClick(View v) {

    }

    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 4;
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
            view = LayoutInflater.from(ExchangeRecordActivity.this).inflate(R.layout.layout_record_item, null);
            return view;
        }
    }
}
