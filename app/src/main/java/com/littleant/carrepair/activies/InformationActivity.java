package com.littleant.carrepair.activies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.littleant.carrepair.R;

public class InformationActivity extends BaseActivity {
    private RecyclerView mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mList = findViewById(R.id.info_list);
        mList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mList.setAdapter(new MyAdapter());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_information;
    }

    @Override
    protected int getTitleId() {
        return R.string.text_info_title;
    }

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_info_item, parent, false);
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
            TextView ii_tv_title, ii_tv_description, ii_tv_count;
            ImageView ii_iv_itemImg;

            ViewHolder(View itemView) {
                super(itemView);
                ii_tv_title = itemView.findViewById(R.id.ii_tv_title);
                ii_tv_description = itemView.findViewById(R.id.ii_tv_description);
                ii_tv_count = itemView.findViewById(R.id.ii_tv_count);
                ii_iv_itemImg = itemView.findViewById(R.id.ii_iv_itemImg);
            }

        }
    }
}
