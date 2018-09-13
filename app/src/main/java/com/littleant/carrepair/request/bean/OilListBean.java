package com.littleant.carrepair.request.bean;

import java.util.List;

public class OilListBean extends BaseResponseBean {

    private List<OilInfo> data;

    public List<OilInfo> getData() {
        return data;
    }

    public void setData(List<OilInfo> data) {
        this.data = data;
    }

}
