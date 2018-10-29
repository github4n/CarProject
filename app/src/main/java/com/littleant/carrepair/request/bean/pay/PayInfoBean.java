package com.littleant.carrepair.request.bean.pay;

import com.littleant.carrepair.request.bean.BaseResponseBean;

import java.io.Serializable;

public class PayInfoBean extends BaseResponseBean {
    private PayInfo data;

    public PayInfo getData() {
        return data;
    }

    public void setData(PayInfo data) {
        this.data = data;
    }

    public class PayInfo implements Serializable {
        private int status;
        private int time;
        private String to = "";
        private String order_code = "";
        private float price;
        private String params = "";
        //年检返回的
        private float base_price;
        private float combo_price;
        private float survey_price;
        private float total_price;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }

        public String getTo() {
            return to;
        }

        public void setTo(String to) {
            this.to = to;
        }

        public String getOrder_code() {
            return order_code;
        }

        public void setOrder_code(String order_code) {
            this.order_code = order_code;
        }

        public float getPrice() {
            return price;
        }

        public void setPrice(float price) {
            this.price = price;
        }

        public String getParams() {
            return params;
        }

        public void setParams(String params) {
            this.params = params;
        }

        public float getBase_price() {
            return base_price;
        }

        public void setBase_price(float base_price) {
            this.base_price = base_price;
        }

        public float getCombo_price() {
            return combo_price;
        }

        public void setCombo_price(float combo_price) {
            this.combo_price = combo_price;
        }

        public float getSurvey_price() {
            return survey_price;
        }

        public void setSurvey_price(float survey_price) {
            this.survey_price = survey_price;
        }

        public float getTotal_price() {
            return total_price;
        }

        public void setTotal_price(float total_price) {
            this.total_price = total_price;
        }
    }
}
