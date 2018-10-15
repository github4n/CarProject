package com.littleant.carrepair.request.bean;

import com.littleant.carrepair.request.bean.shop.ProductBean;

import java.io.Serializable;
import java.util.List;

public class OrderProductSetBean implements Serializable {
    private int id;
    private String create_time = "";
    private String update_time = "";
    private ProductBean product;
    private int amount;
    private int sale;
    private List<String> pic_url_list;
    private String detail_url;

    @Override
    public String toString() {
        return "OrderProductSetBean{" +
                "id=" + id +
                ", create_time='" + create_time + '\'' +
                ", update_time='" + update_time + '\'' +
                ", product=" + product +
                ", amount=" + amount +
                ", sale=" + sale +
                ", pic_url_list=" + pic_url_list +
                ", detail_url='" + detail_url + '\'' +
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

    public ProductBean getProduct() {
        return product;
    }

    public void setProduct(ProductBean product) {
        this.product = product;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getSale() {
        return sale;
    }

    public void setSale(int sale) {
        this.sale = sale;
    }

    public List<String> getPic_url_list() {
        return pic_url_list;
    }

    public void setPic_url_list(List<String> pic_url_list) {
        this.pic_url_list = pic_url_list;
    }

    public String getDetail_url() {
        return detail_url;
    }

    public void setDetail_url(String detail_url) {
        this.detail_url = detail_url;
    }
}
