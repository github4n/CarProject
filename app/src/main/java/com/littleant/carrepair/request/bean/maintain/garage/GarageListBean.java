package com.littleant.carrepair.request.bean.maintain.garage;

import com.littleant.carrepair.request.bean.BaseResponseBean;

import java.util.List;

public class GarageListBean extends BaseResponseBean {

    private List<GarageInfo> data;

    public List<GarageInfo> getData() {
        return data;
    }

    public void setData(List<GarageInfo> data) {
        this.data = data;
    }

}
