package com.littleant.carrepair.activies;

import android.app.Dialog;
import android.os.Bundle;
import android.support.constraint.Constraints;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.littleant.carrepair.R;

/**
 * 维修记录
 */
public class RepairRecordActivity extends BaseActivity {
    private RecyclerView mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mList = findViewById(R.id.repair_record_list);
        mList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mList.setAdapter(new MyAdapter());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_repair_record;
    }

    @Override
    protected int getTitleId() {
        return R.string.text_repaire_record;
    }

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_repair_record_item, parent, false);
            final MyAdapter.ViewHolder viewHolder = new MyAdapter.ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 2;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView rri_tv_title, rri_plate, rri_model, rri_time, rri_get_time;

            ViewHolder(View itemView) {
                super(itemView);
                rri_tv_title = itemView.findViewById(R.id.rri_tv_title);
                rri_plate = itemView.findViewById(R.id.rri_plate);
                rri_model = itemView.findViewById(R.id.rri_model);
                rri_time = itemView.findViewById(R.id.rri_time);
                rri_get_time = itemView.findViewById(R.id.rri_get_time);
            }

        }
    }
}
