package com.littleant.carrepair.request.bean.aftersale;

import com.littleant.carrepair.request.bean.BaseResponseBean;
import com.littleant.carrepair.request.bean.car.MyCarListBean;

import java.util.List;

/**
 * 文件描述:
 * 作者:莫进生
 * 创建时间:2018/10/30 0030
 * 版本号:1
 * 维修保养售后全部信息
 */


public class AftersaleAllBean extends BaseResponseBean {

    private List<AftersaleBean> data;

    public List<AftersaleBean> getData() {
        return data;
    }

    public void setData(List<AftersaleBean> data) {
        this.data = data;
    }

    public class AftersaleBean{

        private int id;
        private String order_id;
        private String car_code;
        private String car_type;
        private String type;
        private String create_time;
        private int state;
        private String now_price;
        private String order_pic_url;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
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

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getNow_price() {
            return now_price;
        }

        public void setNow_price(String now_price) {
            this.now_price = now_price;
        }

        public String getOrder_pic_url() {
            return order_pic_url;
        }

        public void setOrder_pic_url(String order_pic_url) {
            this.order_pic_url = order_pic_url;
        }
    }
}
