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
        int id;
        int amount;
        int score;
        float price;
        String create_time = "";
        String update_time = "";
        String car_brand = "";

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
                    '}';
        }
    }
}
