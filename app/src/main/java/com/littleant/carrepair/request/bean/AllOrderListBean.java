package com.littleant.carrepair.request.bean;

import java.util.List;

public class AllOrderListBean extends BaseResponseBean {
    private List<OrderItemBean> data;

    public List<OrderItemBean> getData() {
        return data;
    }

    public void setData(List<OrderItemBean> data) {
        this.data = data;
    }
}
