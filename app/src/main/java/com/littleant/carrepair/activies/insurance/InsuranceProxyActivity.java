package com.littleant.carrepair.activies.insurance;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.littleant.carrepair.R;
import com.littleant.carrepair.activies.BaseActivity;

public class InsuranceProxyActivity extends BaseActivity {

    private RecyclerView mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mList = findViewById(R.id.ip_list);
        mList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mList.setAdapter(new MyAdapter());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_insurance_proxy;
    }

    @Override
    protected int getTitleId() {
        return R.string.text_insurance_proxy;
    }

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_insurance_item, parent, false);
            MyAdapter.ViewHolder viewHolder = new MyAdapter.ViewHolder(view);
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
            TextView lii_tv_title, lii_tv_description, lii_tv_count;
            ImageView lii_iv_itemImg;

            ViewHolder(View itemView) {
                super(itemView);
                lii_tv_title = itemView.findViewById(R.id.lii_tv_title);
                lii_tv_description = itemView.findViewById(R.id.lii_tv_description);
                lii_tv_count = itemView.findViewById(R.id.lii_tv_count);
                lii_iv_itemImg = itemView.findViewById(R.id.lii_iv_itemImg);
            }

        }
    }
}
