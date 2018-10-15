package com.littleant.carrepair.request.bean.car.carbrand;

import java.io.Serializable;
import java.util.List;

/**
 * 汽车品牌下的大车型
 */
public class CarTypeSet implements Serializable {
    private int id;
    private String name = "";
    private List<CarStyleSet> carstyle_set;

    @Override
    public String toString() {
        return "CarTypeSet{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", carstyle_set=" + carstyle_set +
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

    public List<CarStyleSet> getCarstyle_set() {
        return carstyle_set;
    }

    public void setCarstyle_set(List<CarStyleSet> carstyle_set) {
        this.carstyle_set = carstyle_set;
    }
}
