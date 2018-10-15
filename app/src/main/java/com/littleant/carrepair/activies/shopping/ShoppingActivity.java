package com.littleant.carrepair.activies.shopping;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.Guideline;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.littleant.carrepair.R;
import com.littleant.carrepair.activies.BaseActivity;
import com.littleant.carrepair.request.bean.BaseResponseBean;
import com.littleant.carrepair.request.bean.shop.CatalogListBean;
import com.littleant.carrepair.request.bean.shop.ProductBean;
import com.littleant.carrepair.request.bean.shop.ProductCatalogBean;
import com.littleant.carrepair.request.bean.shop.ProductListBean;
import com.littleant.carrepair.request.constant.ParamsConstant;
import com.littleant.carrepair.request.excute.service.catalog.CatalogQueryAllCmd;
import com.littleant.carrepair.request.excute.service.ordercar.OrderCarAddCmd;
import com.littleant.carrepair.request.excute.service.product.ProductQueryAllCmd;
import com.littleant.carrepair.request.utils.DataHelper;
import com.littleant.carrepair.utils.ProjectUtil;
import com.mh.core.task.MHCommandCallBack;
import com.mh.core.task.MHCommandExecute;
import com.mh.core.task.command.abstracts.MHCommand;
import com.mh.core.tools.MHToast;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * 商城
 */
public class ShoppingActivity extends BaseActivity {

    private GridView mGridView;
    private ImageView shoppingCar;
    private List<ProductBean> productBeanList;
    private List<ProductCatalogBean> catalogBeanList;
    private RadioGroup shopping_radioGroup;
    private RadioButton shopping_tv_none, shopping_tv_price, shopping_tv_sale, shopping_tv_type;
    private Guideline s_guideline;
    private int catalog_id = -1;
    private int p_id = 0;
    protected PopupWindow popupWindow;
    private ListView list1, list2;
    public static final String PRODUCT_INFO = "product_info";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestProduct(-1, -1, -1);

    }

    private void requestCatalog(int p_id, final boolean isOutside) {
        CatalogQueryAllCmd catalogQueryAllCmd = new CatalogQueryAllCmd(mContext, ParamsConstant.SERVICE_QUERY_TYPE_SHOP, p_id);
        catalogQueryAllCmd.setCallback(new MHCommandCallBack() {
            @Override
            public void cmdCallBack(MHCommand command) {
                if (command != null) {
                    Log.i("response", command.getResponse());
                    BaseResponseBean responseBean = ProjectUtil.getBaseResponseBean(command.getResponse(), BaseResponseBean.class);
                    if(responseBean != null && ParamsConstant.REAPONSE_CODE_SUCCESS == responseBean.getCode()) {
                        CatalogListBean listBean = ProjectUtil.getBaseResponseBean(command.getResponse(), CatalogListBean.class);
                        catalogBeanList= listBean.getData();
                        if(isOutside) {
                            showPopup(catalogBeanList);
                        } else {
                            showSecondListData(list2, catalogBeanList);
                        }
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

    private void showPopup( List<ProductCatalogBean> listBean) {
        if(popupWindow == null) {
            View contentView = LayoutInflater.from(mContext).inflate(R.layout.layout_shopping_classify, null);
            popupWindow = new PopupWindow(contentView, LinearLayout.LayoutParams.MATCH_PARENT, 300, true);
            popupWindow.setOutsideTouchable(true);
            popupWindow.setFocusable(true);
            popupWindow.setBackgroundDrawable(new ColorDrawable());

            list1 = contentView.findViewById(R.id.lsc_list1);
            list2 = contentView.findViewById(R.id.lsc_list2);
        }
        showFirstListData(list1, listBean);
        //根据指定View定位
        popupWindow.showAsDropDown(s_guideline, 0, 0);
    }

    private void showFirstListData(ListView listView, List<ProductCatalogBean> listBean) {
        listView.setAdapter(new MyCatalogAdapter(listBean));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ProductCatalogBean itemAtPosition = (ProductCatalogBean) parent.getItemAtPosition(position);
                requestCatalog(itemAtPosition.getId(), false);
            }
        });
    }

    private void showSecondListData(ListView listView, List<ProductCatalogBean> listBean) {
        listView.setAdapter(new MyCatalogAdapter(listBean));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ProductCatalogBean itemAtPosition = (ProductCatalogBean) parent.getItemAtPosition(position);
                requestProduct(itemAtPosition.getId(), -1, -1);
                if(popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
            }
        });
    }

    private void requestProduct(int catalog_id, int price_order, int sale_order) {
        ProductQueryAllCmd productQueryAllCmd = new ProductQueryAllCmd(mContext, catalog_id, price_order, sale_order);
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

        s_guideline = findViewById(R.id.s_guideline);

        shopping_radioGroup = findViewById(R.id.shopping_radioGroup);
        shopping_radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.shopping_tv_none:
                        requestProduct(catalog_id, -1, -1);
                        break;

                    case R.id.shopping_tv_price:
                        requestProduct(catalog_id, 1, -1);
                        break;

                    case R.id.shopping_tv_sale:
                        requestProduct(catalog_id, -1, 1);
                        break;

                }
            }
        });

        shopping_tv_none = findViewById(R.id.shopping_tv_none);
        shopping_tv_price = findViewById(R.id.shopping_tv_price);
        shopping_tv_sale = findViewById(R.id.shopping_tv_sale);
        shopping_tv_type = findViewById(R.id.shopping_tv_type);
        shopping_tv_type.setOnClickListener(this);
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

            case R.id.shopping_tv_type:
                if(catalogBeanList == null) {
                    requestCatalog(p_id, true);
                } else {
                    list2.setAdapter(new MyCatalogAdapter(new ArrayList<ProductCatalogBean>()));
                    showFirstListData(list1, catalogBeanList);

                }
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
            final ProductBean productBean = list.get(i);
            if(productBean != null) {
                holder.si_name.setText(productBean.getName());
                holder.si_tv_money.setText(DataHelper.displayPrice(mContext, productBean.getPrice()));
                Picasso.with(mContext).load(Uri.parse(productBean.getPic_url())).into(holder.si_image);
                holder.si_btn_add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        requestAddProduct(productBean.getId());
                    }
                });
                holder.si_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, ShoppingItemDetailActivity.class);
                        intent.putExtra(PRODUCT_INFO, productBean);
                        startActivity(intent);
                    }
                });
            }

            return convertView;
        }

        private void requestAddProduct(int id) {
            OrderCarAddCmd orderCarAddCmd = new OrderCarAddCmd(mContext, id, 1);
            orderCarAddCmd.setCallback(new MHCommandCallBack() {
                @Override
                public void cmdCallBack(MHCommand command) {
                    if(command != null) {
                        BaseResponseBean responseBean = ProjectUtil.getBaseResponseBean(command.getResponse(), BaseResponseBean.class);
                        if(responseBean != null && ParamsConstant.REAPONSE_CODE_SUCCESS == responseBean.getCode()) {
                            MHToast.showS(mContext, R.string.add_to_shoppingcar_success);
                        } else if(responseBean != null && !TextUtils.isEmpty(responseBean.getMsg())) {
                            MHToast.showS(mContext, responseBean.getMsg());
                        }
                    } else {
                        MHToast.showS(mContext, R.string.request_fail);
                    }
                }
            });
            MHCommandExecute.getInstance().asynExecute(mContext, orderCarAddCmd);
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

    private class MyCatalogAdapter extends BaseAdapter {
        private List<ProductCatalogBean> list;

        public MyCatalogAdapter(List<ProductCatalogBean> list) {
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
                convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_listview_item, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            final ProductCatalogBean productCatalogBean = list.get(i);
            if(productCatalogBean != null) {
                holder.lli_text.setText(productCatalogBean.getName());
            }

            return convertView;
        }

        class ViewHolder {
            //商品名、价格
            private TextView lli_text;

            public ViewHolder(View convertView) {
                lli_text = convertView.findViewById(R.id.lli_text);
            }
        }
    }
}
