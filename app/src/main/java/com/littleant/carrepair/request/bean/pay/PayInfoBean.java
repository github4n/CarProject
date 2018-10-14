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

        @Override
        public String toString() {
            return "PayInfo{" +
                    "status=" + status +
                    ", time=" + time +
                    ", to='" + to + '\'' +
                    ", order_code='" + order_code + '\'' +
                    ", price=" + price +
                    ", params='" + params + '\'' +
                    '}';
        }

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
    }
}
