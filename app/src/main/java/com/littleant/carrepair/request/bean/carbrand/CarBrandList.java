package com.littleant.carrepair.request.bean.carbrand;

import com.littleant.carrepair.request.bean.BaseResponseBean;

public class CarBrandList extends BaseResponseBean {
    private CarBrandLetterList data;

    public CarBrandLetterList getData() {
        return data;
    }

    public void setData(CarBrandLetterList data) {
        this.data = data;
    }
}
