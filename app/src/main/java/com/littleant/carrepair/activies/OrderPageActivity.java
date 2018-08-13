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
import android.widget.Toast;

import com.littleant.carrepair.R;

/**
 * 订单页面
 */
public class OrderPageActivity extends BaseActivity {

    private RecyclerView mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mList = findViewById(R.id.op_list);
        mList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mList.setAdapter(new MyAdapter());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_page;
    }

    @Override
    protected int getTitleId() {
        return R.string.text_order_page;
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
                mFooterView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_order_end_item, parent, false);
                return new MyAdapter.ViewHolder(mFooterView);
            }
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_order_item, parent, false);
            final MyAdapter.ViewHolder viewHolder = new MyAdapter.ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {
            if(position == getItemCount() - 1) {
                holder.oei_detail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(OrderPageActivity.this, "积分说明", Toast.LENGTH_SHORT).show();
                        final Dialog d = new Dialog(OrderPageActivity.this);
                        View contentView = View.inflate(OrderPageActivity.this, R.layout.layout_point, null);
//                        d.setContentView(R.layout.layout_point);
                        DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();
                        int dialogWidth = (int) (dm.widthPixels * 0.7);
                        int dialogHeight = (int) (dm.heightPixels * 0.35);
                        d.setContentView(contentView, new Constraints.LayoutParams(dialogWidth, dialogHeight));
                        contentView.findViewById(R.id.lp_iv_close).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                d.dismiss();
                            }
                        });
                        d.show();
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return 5;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView loi_product_title, loi_per_price, loi_product_count, loi_totle_count, loi_tv_sum;
            ImageView loi_img, oei_detail;

            ViewHolder(View itemView) {
                super(itemView);
                if(itemView == mFooterView) {
                    oei_detail = itemView.findViewById(R.id.oei_detail);
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
