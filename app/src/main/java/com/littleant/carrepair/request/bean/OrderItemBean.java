package com.littleant.carrepair.request.bean;

import java.io.Serializable;
import java.util.List;

public class OrderItemBean implements Serializable {
    private int id;
    private String create_time = "";
    private String update_time = "";
    private String order_code = "";
    private String name = "";
    private String phone = "";
    private String city = "";
    private String address = "";
    private int status;
    private int is_delete;
    private int is_comment;
    private float all_price;
    private float point_price;
    private float now_price;
    private int cost_point;
    private String order_time = "";
    private String wait_time = "";
    private String send_time = "";
    private String receive_time = "";
    private String confirm_time = "";
    private List<OrderProductSetBean> orderproduct_set;

    @Override
    public String toString() {
        return "OrderItemBean{" +
                "id=" + id +
                ", create_time='" + create_time + '\'' +
                ", update_time='" + update_time + '\'' +
                ", order_code='" + order_code + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", city='" + city + '\'' +
                ", address='" + address + '\'' +
                ", status=" + status +
                ", is_delete=" + is_delete +
                ", is_comment=" + is_comment +
                ", all_price=" + all_price +
                ", point_price=" + point_price +
                ", now_price=" + now_price +
                ", cost_point=" + cost_point +
                ", order_time='" + order_time + '\'' +
                ", wait_time='" + wait_time + '\'' +
                ", send_time='" + send_time + '\'' +
                ", receive_time='" + receive_time + '\'' +
                ", confirm_time='" + confirm_time + '\'' +
                ", orderproduct_set=" + orderproduct_set +
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

    public String getOrder_code() {
        return order_code;
    }

    public void setOrder_code(String order_code) {
        this.order_code = order_code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getIs_delete() {
        return is_delete;
    }

    public void setIs_delete(int is_delete) {
        this.is_delete = is_delete;
    }

    public int getIs_comment() {
        return is_comment;
    }

    public void setIs_comment(int is_comment) {
        this.is_comment = is_comment;
    }

    public float getAll_price() {
        return all_price;
    }

    public void setAll_price(float all_price) {
        this.all_price = all_price;
    }

    public float getPoint_price() {
        return point_price;
    }

    public void setPoint_price(float point_price) {
        this.point_price = point_price;
    }

    public float getNow_price() {
        return now_price;
    }

    public void setNow_price(float now_price) {
        this.now_price = now_price;
    }

    public int getCost_point() {
        return cost_point;
    }

    public void setCost_point(int cost_point) {
        this.cost_point = cost_point;
    }

    public String getOrder_time() {
        return order_time;
    }

    public void setOrder_time(String order_time) {
        this.order_time = order_time;
    }

    public String getWait_time() {
        return wait_time;
    }

    public void setWait_time(String wait_time) {
        this.wait_time = wait_time;
    }

    public String getSend_time() {
        return send_time;
    }

    public void setSend_time(String send_time) {
        this.send_time = send_time;
    }

    public String getReceive_time() {
        return receive_time;
    }

    public void setReceive_time(String receive_time) {
        this.receive_time = receive_time;
    }

    public String getConfirm_time() {
        return confirm_time;
    }

    public void setConfirm_time(String confirm_time) {
        this.confirm_time = confirm_time;
    }

    public List<OrderProductSetBean> getOrderproduct_set() {
        return orderproduct_set;
    }

    public void setOrderproduct_set(List<OrderProductSetBean> orderproduct_set) {
        this.orderproduct_set = orderproduct_set;
    }
}
