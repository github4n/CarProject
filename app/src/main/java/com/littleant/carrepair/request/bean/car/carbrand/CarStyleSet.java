package com.littleant.carrepair.request.bean.car.carbrand;

import java.io.Serializable;

/**
 * 大车型下的细分车型
 */
public class CarStyleSet implements Serializable {
    private int id;
    private String name = "";
    private int year;

    @Override
    public String toString() {
        return "CarStyleSet{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", year=" + year +
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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
