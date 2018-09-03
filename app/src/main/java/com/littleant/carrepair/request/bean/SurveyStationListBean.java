package com.littleant.carrepair.request.bean;

import java.io.Serializable;
import java.util.List;

public class SurveyStationListBean extends BaseResponseBean {

    private List<SurveyStationInfo> data;

    public List<SurveyStationInfo> getData() {
        return data;
    }

    public void setData(List<SurveyStationInfo> data) {
        this.data = data;
    }

    public class SurveyStationInfo implements Serializable {
        private int id;
        private String create_time = "";
        private String update_time = "";
        private String name = "";
        private String address = "";
        private double longitude;
        private double latitude;
        private float price;

        @Override
        public String toString() {
            return "SurveyStationInfo{" +
                    "id=" + id +
                    ", create_time='" + create_time + '\'' +
                    ", update_time='" + update_time + '\'' +
                    ", name='" + name + '\'' +
                    ", address='" + address + '\'' +
                    ", longitude=" + longitude +
                    ", latitude=" + latitude +
                    ", price=" + price +
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

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
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
    }
}
