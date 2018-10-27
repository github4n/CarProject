package com.littleant.carrepair.request.bean.car.carbrand;

import java.io.Serializable;
import java.util.List;

/**
 * 汽车品牌信息
 */
public class CarBrandLetterBean implements Serializable {
    private int id;
    private String name = "";
//    private List<CarTypeList> cartype_set;

    @Override
    public String toString() {
        return "CarBrandLetterBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
//                ", cartype_set=" + cartype_set +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public List<CarTypeList> getCartype_set() {
//        return cartype_set;
//    }
//
//    public void setCartype_set(List<CarTypeList> cartype_set) {
//        this.cartype_set = cartype_set;
//    }
}
