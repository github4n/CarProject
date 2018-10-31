package com.littleant.carrepair.request.bean.car.carbrand;

import com.littleant.carrepair.request.bean.BaseResponseBean;

import java.io.Serializable;
import java.util.List;

/**
 * 汽车品牌下的大车型
 */
public class CarTypeList extends BaseResponseBean {
    private List<CarBaseBean> data;

    public List<CarBaseBean> getData() {
        return data;
    }

    public void setData(List<CarBaseBean> data) {
        this.data = data;
    }
}
