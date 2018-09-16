package com.littleant.carrepair.request.bean;

import java.io.Serializable;

public class ProductCatalogBean implements Serializable {
    private int id;
    private String create_time = "";
    private String update_time = "";
    private String name = "";
    private int p_id;
    private int type;
    private String pic_url = "";

    @Override
    public String toString() {
        return "ProductCatalogBean{" +
                "id=" + id +
                ", create_time='" + create_time + '\'' +
                ", update_time='" + update_time + '\'' +
                ", name='" + name + '\'' +
                ", p_id=" + p_id +
                ", type=" + type +
                ", pic_url='" + pic_url + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getP_id() {
        return p_id;
    }

    public void setP_id(int p_id) {
        this.p_id = p_id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }
}
