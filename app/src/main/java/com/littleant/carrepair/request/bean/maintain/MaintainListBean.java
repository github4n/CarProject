package com.littleant.carrepair.request.bean.maintain;

import com.littleant.carrepair.request.bean.BaseResponseBean;
import com.littleant.carrepair.request.bean.maintain.garage.GarageInfo;

import java.io.Serializable;
import java.util.List;

public class MaintainListBean extends BaseResponseBean {

    private List<MaintainInfo> data;

    public List<MaintainInfo> getData() {
        return data;
    }

    public void setData(List<MaintainInfo> data) {
        this.data = data;
    }

    public class MaintainInfo implements Serializable {
        private int id;
        private int state;
        private int score;
        private double longitude;
        private double latitude;
        private float price;
        private float discounts;
        private float now_price;
        private boolean is_delete;
        private boolean is_comment;
        private List<String> pic_url_list;
//        private List maintainpic_set;
        private String create_time = "";
        private String update_time = "";
        private String name = "";
        private String phone = "";
        private String address = "";
        private String subscribe_time = "";
        private String content = "";
        private String order_time = "";
        private String pay_time = "";
        private String service_time = "";
        private String comment_time = "";
        private String over_time = "";
        private String car_code = "";
        private String car_type = "";
        private String service_item = "";
        private String service_materials = "";
        private String deal_id = "";
        private String order_id = "";
        private GarageInfo garage;

        @Override
        public String toString() {
            return "MaintainInfo{" +
                    "id=" + id +
                    ", state=" + state +
                    ", score=" + score +
                    ", longitude=" + longitude +
                    ", latitude=" + latitude +
                    ", price=" + price +
                    ", discounts=" + discounts +
                    ", now_price=" + now_price +
                    ", is_delete=" + is_delete +
                    ", is_comment=" + is_comment +
                    ", pic_url_list=" + pic_url_list +
//                    ", maintainpic_set=" + maintainpic_set +
                    ", create_time='" + create_time + '\'' +
                    ", update_time='" + update_time + '\'' +
                    ", name='" + name + '\'' +
                    ", phone='" + phone + '\'' +
                    ", address='" + address + '\'' +
                    ", subscribe_time='" + subscribe_time + '\'' +
                    ", content='" + content + '\'' +
                    ", order_time='" + order_time + '\'' +
                    ", pay_time='" + pay_time + '\'' +
                    ", service_time='" + service_time + '\'' +
                    ", comment_time='" + comment_time + '\'' +
                    ", over_time='" + over_time + '\'' +
                    ", car_code='" + car_code + '\'' +
                    ", car_type='" + car_type + '\'' +
                    ", service_item='" + service_item + '\'' +
                    ", service_materials='" + service_materials + '\'' +
                    ", deal_id='" + deal_id + '\'' +
                    ", order_id='" + order_id + '\'' +
                    ", garage=" + garage +
                    '}';
        }

        public List<String> getPic_url_list() {
            return pic_url_list;
        }

        public void setPic_url_list(List<String> pic_url_list) {
            this.pic_url_list = pic_url_list;
        }

//        public List getMaintainpic_set() {
//            return maintainpic_set;
//        }
//
//        public void setMaintainpic_set(List maintainpic_set) {
//            this.maintainpic_set = maintainpic_set;
//        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public float getPrice() {
            return price;
        }

        public void setPrice(float price) {
            this.price = price;
        }

        public float getDiscounts() {
            return discounts;
        }

        public void setDiscounts(float discounts) {
            this.discounts = discounts;
        }

        public float getNow_price() {
            return now_price;
        }

        public void setNow_price(float now_price) {
            this.now_price = now_price;
        }

        public boolean isIs_delete() {
            return is_delete;
        }

        public void setIs_delete(boolean is_delete) {
            this.is_delete = is_delete;
        }

        public boolean isIs_comment() {
            return is_comment;
        }

        public void setIs_comment(boolean is_comment) {
            this.is_comment = is_comment;
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

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getSubscribe_time() {
            return subscribe_time;
        }

        public void setSubscribe_time(String subscribe_time) {
            this.subscribe_time = subscribe_time;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getOrder_time() {
            return order_time;
        }

        public void setOrder_time(String order_time) {
            this.order_time = order_time;
        }

        public String getPay_time() {
            return pay_time;
        }

        public void setPay_time(String pay_time) {
            this.pay_time = pay_time;
        }

        public String getService_time() {
            return service_time;
        }

        public void setService_time(String service_time) {
            this.service_time = service_time;
        }

        public String getComment_time() {
            return comment_time;
        }

        public void setComment_time(String comment_time) {
            this.comment_time = comment_time;
        }

        public String getOver_time() {
            return over_time;
        }

        public void setOver_time(String over_time) {
            this.over_time = over_time;
        }

        public String getCar_code() {
            return car_code;
        }

        public void setCar_code(String car_code) {
            this.car_code = car_code;
        }

        public String getCar_type() {
            return car_type;
        }

        public void setCar_type(String car_type) {
            this.car_type = car_type;
        }

        public String getService_item() {
            return service_item;
        }

        public void setService_item(String service_item) {
            this.service_item = service_item;
        }

        public String getService_materials() {
            return service_materials;
        }

        public void setService_materials(String service_materials) {
            this.service_materials = service_materials;
        }

        public String getDeal_id() {
            return deal_id;
        }

        public void setDeal_id(String deal_id) {
            this.deal_id = deal_id;
        }

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public GarageInfo getGarage() {
            return garage;
        }

        public void setGarage(GarageInfo garage) {
            this.garage = garage;
        }
    }

}
