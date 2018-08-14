package com.littleant.carrepair.activies;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.littleant.carrepair.R;

public class AnnualCheckRecordActivity extends BaseActivity {
    private RecyclerView mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mList = findViewById(R.id.acr_record_list);
        mList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mList.setAdapter(new MyAdapter());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_annual_check_record;
    }

    @Override
    protected int getTitleId() {
        return R.string.text_check_record;
    }

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_annual_record_item, parent, false);
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
            TextView ari_book, ari_finish, ari_location, ari_driver;

            ViewHolder(View itemView) {
                super(itemView);
                ari_book = itemView.findViewById(R.id.ari_book);
                ari_finish = itemView.findViewById(R.id.ari_finish);
                ari_location = itemView.findViewById(R.id.ari_location);
                ari_driver = itemView.findViewById(R.id.ari_driver);
            }

        }
    }

}
