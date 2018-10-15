package com.littleant.carrepair.request.bean.upkeep;

import java.io.Serializable;

public class OilInfo implements Serializable {
    private int id;
    private int L;
    private float price;
    private float new_price;
    private String create_time = "";
    private String update_time = "";
    private String name = "";
    private String pic_url = "";

    @Override
    public String toString() {
        return "OilInfo{" +
                "id=" + id +
                ", L=" + L +
                ", price=" + price +
                ", new_price=" + new_price +
                ", create_time='" + create_time + '\'' +
                ", update_time='" + update_time + '\'' +
                ", name='" + name + '\'' +
                ", pic_url='" + pic_url + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getL() {
        return L;
    }

    public void setL(int l) {
        L = l;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getNew_price() {
        return new_price;
    }

    public void setNew_price(float new_price) {
        this.new_price = new_price;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }
}
