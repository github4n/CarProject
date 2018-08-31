package com.littleant.carrepair.request.bean;

import java.io.Serializable;
import java.util.List;

public class MyCarListBean extends BaseResponseBean {

    private List<CarInfo> data;

    public List<CarInfo> getData() {
        return data;
    }

    public void setData(List<CarInfo> data) {
        this.data = data;
    }

    public class CarInfo implements Serializable {
        private int id;
        private String create_time = "";
        private String update_time = "";
        private String pic_url = "";
        private String brand = "";
        private String code = "";
        private String engine = "";
        private String buy_time = "";
        private float mileage;
        private boolean is_default;

        @Override
        public String toString() {
            return "CarInfo{" +
                    "id=" + id +
                    ", create_time='" + create_time + '\'' +
                    ", update_time='" + update_time + '\'' +
                    ", pic_url='" + pic_url + '\'' +
                    ", brand='" + brand + '\'' +
                    ", code='" + code + '\'' +
                    ", engine='" + engine + '\'' +
                    ", buy_time='" + buy_time + '\'' +
                    ", mileage=" + mileage +
                    ", is_default=" + is_default +
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

        public String getPic_url() {
            return pic_url;
        }

        public void setPic_url(String pic_url) {
            this.pic_url = pic_url;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getEngine() {
            return engine;
        }

        public void setEngine(String engine) {
            this.engine = engine;
        }

        public String getBuy_time() {
            return buy_time;
        }

        public void setBuy_time(String buy_time) {
            this.buy_time = buy_time;
        }

        public float getMileage() {
            return mileage;
        }

        public void setMileage(float mileage) {
            this.mileage = mileage;
        }

        public boolean isIs_default() {
            return is_default;
        }

        public void setIs_default(boolean is_default) {
            this.is_default = is_default;
        }
    }
}
