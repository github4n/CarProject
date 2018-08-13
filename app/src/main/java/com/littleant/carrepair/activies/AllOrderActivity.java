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
import android.widget.ImageView;
import android.widget.TextView;

import com.littleant.carrepair.R;

/**
 * 全部订单
 */
public class AllOrderActivity extends BaseActivity {
    private RecyclerView mList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mList = findViewById(R.id.ao_list);
        mList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mList.setAdapter(new MyAdapter());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_all_order;
    }

    @Override
    protected int getTitleId() {
        return R.string.text_all_order;
    }

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_product_item, parent, false);
            final MyAdapter.ViewHolder viewHolder = new MyAdapter.ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {
            holder.lpr_btn_rate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final Dialog d = new Dialog(AllOrderActivity.this);
                    View contentView = View.inflate(AllOrderActivity.this, R.layout.layout_rating, null);
//                        d.setContentView(R.layout.layout_point);
                    DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();
                    int dialogWidth = (int) (dm.widthPixels * 0.6);
                    int dialogHeight = (int) (dm.heightPixels * 0.3);
                    d.setContentView(contentView, new Constraints.LayoutParams(dialogWidth, dialogHeight));
                    contentView.findViewById(R.id.lr_rating_ok).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            d.dismiss();
                        }
                    });
                    d.show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return 2;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView lpr_product_title, lpi_tv_state, lpr_per_price, lpr_product_count, lpr_tv_count, lpr_tv_sum,
                    lpr_btn_delete, lpr_btn_rate, lpr_lpi_btn_pay, lpr_line2;
            ImageView lpr_iv_pic;

            ViewHolder(View itemView) {
                super(itemView);
                lpr_product_title = itemView.findViewById(R.id.lpr_product_title);
                lpi_tv_state = itemView.findViewById(R.id.lpi_tv_state);
                lpr_iv_pic = itemView.findViewById(R.id.lpr_iv_pic);
                lpr_per_price = itemView.findViewById(R.id.lpr_per_price);
                lpr_product_count = itemView.findViewById(R.id.lpr_product_count);
                lpr_tv_count = itemView.findViewById(R.id.lpr_tv_count);
                lpr_tv_sum = itemView.findViewById(R.id.lpr_tv_sum);
                lpr_btn_delete = itemView.findViewById(R.id.lpr_btn_delete);
                lpr_btn_rate = itemView.findViewById(R.id.lpr_btn_rate);
                lpr_lpi_btn_pay = itemView.findViewById(R.id.lpr_lpi_btn_pay);
                lpr_line2 = itemView.findViewById(R.id.lpr_line2);
            }

        }
    }
}
