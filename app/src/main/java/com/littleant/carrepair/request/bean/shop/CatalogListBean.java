package com.littleant.carrepair.request.bean.shop;

import com.littleant.carrepair.request.bean.BaseResponseBean;

import java.util.List;

public class CatalogListBean extends BaseResponseBean {
    private List<ProductCatalogBean> data;

    public List<ProductCatalogBean> getData() {
        return data;
    }

    public void setData(List<ProductCatalogBean> data) {
        this.data = data;
    }
}
