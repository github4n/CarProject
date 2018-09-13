package com.littleant.carrepair.request.bean;

import java.io.Serializable;
import java.util.List;

public class ComboListBean implements Serializable {

    private List<ComboBean> data;

    public List<ComboBean> getData() {
        return data;
    }

    public void setData(List<ComboBean> data) {
        this.data = data;
    }
}
