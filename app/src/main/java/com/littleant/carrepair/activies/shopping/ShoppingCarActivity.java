package com.littleant.carrepair.activies.shopping;

import android.content.Intent;
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
import com.littleant.carrepair.activies.BaseActivity;
import com.littleant.carrepair.activies.order.OrderPageActivity;

/**
 * 购物车
 */
public class ShoppingCarActivity extends BaseActivity {

    private RecyclerView mList;
    private Button sc_go_pay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mList = findViewById(R.id.sc_list);
        mList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mList.setAdapter(new MyAdapter());

        sc_go_pay = findViewById(R.id.sc_go_pay);
        sc_go_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShoppingCarActivity.this, OrderPageActivity.class);
                ShoppingCarActivity.this.startActivity(intent);
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shopping_car;
    }

    @Override
    protected int getTitleId() {
        return R.string.text_shopping_car;
    }

    @Override
    protected int getOptionStringId() {
        return R.string.text_sc_delete;
    }

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_shopping_car_item, parent, false);
            final MyAdapter.ViewHolder viewHolder = new MyAdapter.ViewHolder(view);
            viewHolder.sci_amount.setText("  1  ");
//            viewHolder.sci_amount.setText(String.format(ShoppingCarActivity.this.getResources().getString(R.string.text_sc_amount), "1"));
            viewHolder.sci_plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int amount = Integer.parseInt(viewHolder.sci_amount.getText().toString().trim());
                    amount++;
                    viewHolder.sci_amount.setText("  " + amount + "  ");
                }
            });
            viewHolder.sci_reduce.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int amount = Integer.parseInt(viewHolder.sci_amount.getText().toString().trim());
                    if(amount > 0) {
                        amount--;
                        viewHolder.sci_amount.setText("  " + amount + "  ");
                    }
                }
            });

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
            TextView sci_item_name, sci_item_price, sci_reduce, sci_plus, sci_amount;
            ImageView sci_iv_itemImg;

            ViewHolder(View itemView) {
                super(itemView);
                sci_item_name = itemView.findViewById(R.id.sci_item_name);
                sci_item_price = itemView.findViewById(R.id.sci_item_price);
                sci_iv_itemImg = itemView.findViewById(R.id.sci_iv_itemImg);
                sci_reduce = itemView.findViewById(R.id.sci_reduce);
                sci_plus = itemView.findViewById(R.id.sci_plus);
                sci_amount = itemView.findViewById(R.id.sci_amount);
            }

        }
    }
}
