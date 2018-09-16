package com.littleant.carrepair.activies.shopping;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.littleant.carrepair.R;
import com.littleant.carrepair.activies.BaseActivity;
import com.littleant.carrepair.activies.annualcheck.BaseFillInfoActivity;
import com.littleant.carrepair.request.bean.BaseResponseBean;
import com.littleant.carrepair.request.bean.CatalogListBean;
import com.littleant.carrepair.request.bean.PictureListBean;
import com.littleant.carrepair.request.bean.ProductBean;
import com.littleant.carrepair.request.bean.ProductCatalogBean;
import com.littleant.carrepair.request.bean.ProductListBean;
import com.littleant.carrepair.request.constant.ParamsConstant;
import com.littleant.carrepair.request.excute.service.catalog.CatalogQueryAllCmd;
import com.littleant.carrepair.request.excute.service.product.ProductQueryAllCmd;
import com.littleant.carrepair.request.utils.DataHelper;
import com.littleant.carrepair.utils.ProjectUtil;
import com.mh.core.task.MHCommandCallBack;
import com.mh.core.task.MHCommandExecute;
import com.mh.core.task.command.abstracts.MHCommand;
import com.mh.core.tools.MHToast;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * 商城
 */
public class ShoppingActivity extends BaseActivity {

    private GridView mGridView;
    private ImageView shoppingCar;
    private List<ProductBean> productBeanList;
    private List<ProductCatalogBean> catalogBeanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        requestCatalog();
        requestProduct();

    }

    private void requestCatalog() {
        CatalogQueryAllCmd catalogQueryAllCmd = new CatalogQueryAllCmd(mContext, ParamsConstant.SERVICE_QUERY_TYPE_SHOP, 0);
        catalogQueryAllCmd.setCallback(new MHCommandCallBack() {
            @Override
            public void cmdCallBack(MHCommand command) {
                if (command != null) {
                    Log.i("response", command.getResponse());
                    BaseResponseBean responseBean = ProjectUtil.getBaseResponseBean(command.getResponse(), BaseResponseBean.class);
                    if(responseBean != null && ParamsConstant.REAPONSE_CODE_SUCCESS == responseBean.getCode()) {
                        CatalogListBean listBean = ProjectUtil.getBaseResponseBean(command.getResponse(), CatalogListBean.class);
                        catalogBeanList= listBean.getData();
                    } else if(responseBean != null && !TextUtils.isEmpty(responseBean.getMsg())) {
                        MHToast.showS(mContext, responseBean.getMsg());
                    }
                } else {
                    MHToast.showS(mContext, R.string.request_fail);
                }
            }
        });
        MHCommandExecute.getInstance().asynExecute(mContext, catalogQueryAllCmd);
    }

    private void requestProduct() {
        ProductQueryAllCmd productQueryAllCmd = new ProductQueryAllCmd(mContext, -1, -1, -1);
        productQueryAllCmd.setCallback(new MHCommandCallBack() {
            @Override
            public void cmdCallBack(MHCommand command) {
                if (command != null) {
                    Log.i("response", command.getResponse());
                    BaseResponseBean responseBean = ProjectUtil.getBaseResponseBean(command.getResponse(), BaseResponseBean.class);
                    if(responseBean != null && ParamsConstant.REAPONSE_CODE_SUCCESS == responseBean.getCode()) {
                        ProductListBean productListBean = ProjectUtil.getBaseResponseBean(command.getResponse(), ProductListBean.class);
                        productBeanList = productListBean.getData();
                        if(productBeanList != null) {
                            mGridView.setAdapter(new MyAdapter(productBeanList));
                        }

                    } else if(responseBean != null && !TextUtils.isEmpty(responseBean.getMsg())) {
                        MHToast.showS(mContext, responseBean.getMsg());
                    }
                } else {
                    MHToast.showS(mContext, R.string.request_fail);
                }

            }
        });
        MHCommandExecute.getInstance().asynExecute(mContext, productQueryAllCmd);
    }

    @Override
    protected void init() {
        super.init();
        mGridView = findViewById(R.id.s_gv_list);

        shoppingCar = findViewById(R.id.s_iv_shoppingcar);
        shoppingCar.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shopping;
    }

    @Override
    protected int getTitleId() {
        return R.string.text_shop;
    }

    @Override
    protected int getOptionBackgroundId() {
        return R.drawable.shopping_search;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.s_iv_shoppingcar:
                Intent intent = new Intent(ShoppingActivity.this, ShoppingCarActivity.class);
                ShoppingActivity.this.startActivity(intent);
                break;
        }
    }

    private class MyAdapter extends BaseAdapter {
        private List<ProductBean> list;

        public MyAdapter(List<ProductBean> list) {
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {
           ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_shop_item, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            ProductBean productBean = list.get(i);
            if(productBean != null) {
                holder.si_name.setText(productBean.getName());
                holder.si_tv_money.setText(DataHelper.displayPrice(mContext, productBean.getPrice()));
                Picasso.with(mContext).load(Uri.parse(productBean.getPic_url())).into(holder.si_image);
            }

            return convertView;
        }

        class ViewHolder {
            //商品图片
            private ImageView si_image;
            //商品名、价格
            private TextView si_name, si_tv_money;
            //添加按钮
            private ImageButton si_btn_add;

            public ViewHolder(View convertView) {
                si_image = convertView.findViewById(R.id.si_image);
                si_name = convertView.findViewById(R.id.si_name);
                si_tv_money = convertView.findViewById(R.id.si_tv_money);
                si_btn_add = convertView.findViewById(R.id.si_btn_add);
            }
        }
    }
}
