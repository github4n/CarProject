package com.littleant.carrepair.request.bean;

import java.util.List;

public class ShoppingCarListBean extends BaseResponseBean {
    private List<ShoppingCarItemBean> data;

    public List<ShoppingCarItemBean> getData() {
        return data;
    }

    public void setData(List<ShoppingCarItemBean> data) {
        this.data = data;
    }
}
