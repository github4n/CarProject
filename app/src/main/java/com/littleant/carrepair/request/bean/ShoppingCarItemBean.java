
package com.littleant.carrepair.request.bean;

import java.io.Serializable;

public class ShoppingCarItemBean implements Serializable {
    private int id;
    private String create_time = "";
    private String update_time = "";
    private ProductBean product;
    private int amount;

    @Override
    public String toString() {
        return "ShoppingCarItemBean{" +
                "id=" + id +
                ", create_time='" + create_time + '\'' +
                ", update_time='" + update_time + '\'' +
                ", product=" + product +
                ", amount=" + amount +
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
}
