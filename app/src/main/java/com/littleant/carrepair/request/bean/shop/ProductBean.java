package com.littleant.carrepair.request.bean.shop;

import java.io.Serializable;
import java.util.List;

public class ProductBean implements Serializable {
    private int id;
    private String create_time = "";
    private String update_time = "";
    private String name = "";
    private float price;
    private String pic_url = "";
    private float special_price;
    private String number = "";
    private String car_type = "";
    private String specification = "";
    private String brand = "";
    private String sort = "";
    private String unit = "";
    private String production = "";
    private String category = "";
    private String car_type_list = "";
    private ProductCatalogBean catalog;
    private int amount;
    private int sale;
    private List<String> pic_url_list;
    private String detail_url = "";

    @Override
    public String toString() {
        return "ProductBean{" +
                "id=" + id +
                ", create_time='" + create_time + '\'' +
                ", update_time='" + update_time + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", pic_url='" + pic_url + '\'' +
                ", special_price=" + special_price +
                ", number='" + number + '\'' +
                ", car_type='" + car_type + '\'' +
                ", specification='" + specification + '\'' +
                ", brand='" + brand + '\'' +
                ", sort='" + sort + '\'' +
                ", unit='" + unit + '\'' +
                ", production='" + production + '\'' +
                ", category='" + category + '\'' +
                ", car_type_list='" + car_type_list + '\'' +
                ", catalog=" + catalog +
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

    public float getSpecial_price() {
        return special_price;
    }

    public void setSpecial_price(float special_price) {
        this.special_price = special_price;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCar_type() {
        return car_type;
    }

    public void setCar_type(String car_type) {
        this.car_type = car_type;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getProduction() {
        return production;
    }

    public void setProduction(String production) {
        this.production = production;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCar_type_list() {
        return car_type_list;
    }

    public void setCar_type_list(String car_type_list) {
        this.car_type_list = car_type_list;
    }

    public ProductCatalogBean getCatalog() {
        return catalog;
    }

    public void setCatalog(ProductCatalogBean catalog) {
        this.catalog = catalog;
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
