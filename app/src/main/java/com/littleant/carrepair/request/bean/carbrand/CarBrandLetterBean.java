package com.littleant.carrepair.request.bean.carbrand;

import java.io.Serializable;
import java.util.List;

public class CarBrandLetterBean implements Serializable {
    private int id;
    private String name = "";
    private List<CarTypeSet> cartype_set;

    @Override
    public String toString() {
        return "CarBrandLetterBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cartype_set=" + cartype_set +
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

    public List<CarTypeSet> getCartype_set() {
        return cartype_set;
    }

    public void setCartype_set(List<CarTypeSet> cartype_set) {
        this.cartype_set = cartype_set;
    }
}
