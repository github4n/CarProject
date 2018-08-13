package com.littleant.carrepair.activies;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.littleant.carrepair.R;

/**
 * 我的地址
 */
public class MyAddressActivity extends BaseActivity {
    private RecyclerView mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mList = findViewById(R.id.ma_list);
        mList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mList.setAdapter(new MyAdapter());

        mOptionContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MyAddressActivity.this, "点击添加", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_address;
    }

    @Override
    protected int getTitleId() {
        return R.string.text_my_address;
    }

    @Override
    protected int getOptionBackgroundId() {
        return R.drawable.address_add;
    }

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_address_item, parent, false);
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
            TextView lai_edit, lai_name, lai_phone, lai_address;

            ViewHolder(View itemView) {
                super(itemView);
                lai_edit = itemView.findViewById(R.id.lai_edit);
                lai_name = itemView.findViewById(R.id.lai_name);
                lai_phone = itemView.findViewById(R.id.lai_phone);
                lai_address = itemView.findViewById(R.id.lai_address);
            }

        }
    }
}
