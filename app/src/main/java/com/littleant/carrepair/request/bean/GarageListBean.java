package com.littleant.carrepair.request.bean;

import java.io.Serializable;
import java.util.List;

public class GarageListBean extends BaseResponseBean {

    private List<GarageInfo> data;

    public List<GarageInfo> getData() {
        return data;
    }

    public void setData(List<GarageInfo> data) {
        this.data = data;
    }

    public class GarageInfo implements Serializable {
        private int id;
        private int popular;
        private float filter_price;
        private float distance;
        private String create_time = "";
        private String update_time = "";
        private String name = "";
        private String user_name = "";
        private double longitude;
        private double latitude;
        private String address = "";
        private String mobile_phone = "";
        private String phone = "";
        private String pic_url = "";

        @Override
        public String toString() {
            return "GarageInfo{" +
                    "id=" + id +
                    ", popular=" + popular +
                    ", filter_price=" + filter_price +
                    ", distance=" + distance +
                    ", create_time='" + create_time + '\'' +
                    ", update_time='" + update_time + '\'' +
                    ", name='" + name + '\'' +
                    ", user_name='" + user_name + '\'' +
                    ", longitude='" + longitude + '\'' +
                    ", latitude='" + latitude + '\'' +
                    ", address='" + address + '\'' +
                    ", mobile_phone='" + mobile_phone + '\'' +
                    ", phone='" + phone + '\'' +
                    ", pic_url='" + pic_url + '\'' +
                    '}';
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getPopular() {
            return popular;
        }

        public void setPopular(int popular) {
            this.popular = popular;
        }

        public float getFilter_price() {
            return filter_price;
        }

        public void setFilter_price(float filter_price) {
            this.filter_price = filter_price;
        }

        public float getDistance() {
            return distance;
        }

        public void setDistance(float distance) {
            this.distance = distance;
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

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
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

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getMobile_phone() {
            return mobile_phone;
        }

        public void setMobile_phone(String mobile_phone) {
            this.mobile_phone = mobile_phone;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getPic_url() {
            return pic_url;
        }

        public void setPic_url(String pic_url) {
            this.pic_url = pic_url;
        }
    }
}
