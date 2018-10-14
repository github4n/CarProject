package com.littleant.carrepair.request.bean;

import java.util.List;

public class ViolationBean  extends BaseResponseBean {

    private List<ViolationInfo> data;

    public List<ViolationInfo> getData() {
        return data;
    }

    public void setData(List<ViolationInfo> data) {
        this.data = data;
    }

    public class ViolationInfo {
        private int id;
        private int amount;
        private int score;
        private float price;
        private String create_time = "";
        private String update_time = "";
        private String car_brand = "";
        private String car_code = "";
        private String car_pic_url = "";

        @Override
        public String toString() {
            return "ViolationInfo{" +
                    "id=" + id +
                    ", amount=" + amount +
                    ", score=" + score +
                    ", price=" + price +
                    ", create_time='" + create_time + '\'' +
                    ", update_time='" + update_time + '\'' +
                    ", car_brand='" + car_brand + '\'' +
                    ", car_code='" + car_code + '\'' +
                    ", car_pic_url='" + car_pic_url + '\'' +
                    '}';
        }

        public String getCar_pic_url() {
            return car_pic_url;
        }

        public void setCar_pic_url(String car_pic_url) {
            this.car_pic_url = car_pic_url;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public float getPrice() {
            return price;
        }

        public void setPrice(float price) {
            this.price = price;
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

        public String getCar_brand() {
            return car_brand;
        }

        public void setCar_brand(String car_brand) {
            this.car_brand = car_brand;
        }

        public String getCar_code() {
            return car_code;
        }

        public void setCar_code(String car_code) {
            this.car_code = car_code;
        }
    }
}
