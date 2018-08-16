package com.littleant.carrepair.activies;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.littleant.carrepair.R;

public class BookMaintainActivity extends BaseActivity {
    private RecyclerView mList;
    private Button bm_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mList = findViewById(R.id.bm_list);
        mList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mList.setAdapter(new MyAdapter());

        bm_submit = findViewById(R.id.bm_submit);
        bm_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BookMaintainActivity.this, PaymentActivity.class);
                BookMaintainActivity.this.startActivity(intent);
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_book_maintain;
    }

    @Override
    protected int getTitleId() {
        return R.string.text_maintain;
    }

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        public static final int TYPE_HEADER = 0;  //说明是带有Header的
        public static final int TYPE_FOOTER = 1;  //说明是带有Footer的
        public static final int TYPE_NORMAL = 2;  //说明是不带有header和footer的
        private View mFooterView;

        public View getFooterView() {
            return mFooterView;
        }
        public void setFooterView(View footerView) {
            mFooterView = footerView;
            notifyItemInserted(getItemCount()-1);
        }

        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if(viewType == TYPE_FOOTER){
                mFooterView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_book_maintain_end_item, parent, false);
                return new MyAdapter.ViewHolder(mFooterView);
            }
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_maintain_item, parent, false);
            final MyAdapter.ViewHolder viewHolder = new MyAdapter.ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {
            if(position != getItemCount() - 1) {
                holder.lmi_tv_old_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            }
        }

        @Override
        public int getItemCount() {
            return 5;
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            //一般子项
            private TextView lmi_item_name, lmi_gas_amount, lmi_tv_new_price, lmi_tv_old_price;
            private ImageView lmi_iv_itemImg;

            //底项
            private TextView bmei_time_price;

            ViewHolder(View itemView) {
                super(itemView);
                if(itemView == mFooterView) {
                    bmei_time_price = itemView.findViewById(R.id.bmei_time_price);
                } else {
                    lmi_item_name = itemView.findViewById(R.id.lmi_item_name);
                    lmi_gas_amount = itemView.findViewById(R.id.lmi_gas_amount);
                    lmi_tv_new_price = itemView.findViewById(R.id.lmi_tv_new_price);
                    lmi_tv_old_price = itemView.findViewById(R.id.lmi_tv_old_price);
                    lmi_iv_itemImg = itemView.findViewById(R.id.lmi_iv_itemImg);
                }

            }

        }

        @Override
        public int getItemViewType(int position) {

            if (position == getItemCount()-1){
                //最后一个,应该加载Footer
                return TYPE_FOOTER;
            }
            return TYPE_NORMAL;
        }
    }
}
