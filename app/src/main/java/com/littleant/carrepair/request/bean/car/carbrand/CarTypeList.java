package com.littleant.carrepair.request.bean.car.carbrand;

import java.io.Serializable;
import java.util.List;

/**
 * 汽车品牌下的大车型
 */
public class CarTypeList implements Serializable {
    private List<CarBaseBean> data;

    public List<CarBaseBean> getData() {
        return data;
    }

    public void setData(List<CarBaseBean> data) {
        this.data = data;
    }
}
