package com.littleant.carrepair.activies;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.littleant.carrepair.R;

/**
 * 维修记录
 */
public class RepairRecordActivity extends BaseActivity {
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListView = findViewById(R.id.repair_record_list);
        mListView.setAdapter(new MyAdapter());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_repair_record;
    }

    @Override
    protected int getTitleId() {
        return R.string.text_repaire_record;
    }

    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 3;
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
            view = LayoutInflater.from(RepairRecordActivity.this).inflate(R.layout.layout_repair_record_item, null);
            return view;
        }
    }
}
