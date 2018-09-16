package com.littleant.carrepair.request.bean;

import java.util.List;

public class ProductListBean extends BaseResponseBean {
    private List<ProductBean> data;

    public List<ProductBean> getData() {
        return data;
    }

    public void setData(List<ProductBean> data) {
        this.data = data;
    }
}
