package com.littleant.carrepair.request.bean;

import java.util.List;

public class ComboListBean extends BaseResponseBean {

    private List<ComboBean> data;

    public List<ComboBean> getData() {
        return data;
    }

    public void setData(List<ComboBean> data) {
        this.data = data;
    }
}
