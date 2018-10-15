package com.littleant.carrepair.request.bean.maintain;

import com.littleant.carrepair.request.bean.BaseResponseBean;

import java.io.Serializable;
import java.util.List;

/**
 * 保养、维修订单列表信息
 */
public class MaintainOrderListBean extends BaseResponseBean {

    private List<OrderInfo> data;

    public List<OrderInfo> getData() {
        return data;
    }

    public void setData(List<OrderInfo> data) {
        this.data = data;
    }

    public class OrderInfo implements Serializable {
        private int id;
        private int state;
        private float now_price;
        private String order_name = "";
        private String order_pic_url = "";
        private String type = "";
        private String subscribe_time = "";
        private boolean is_comment;
        private boolean is_setting;
        private boolean is_maintain;
        private boolean is_upkeep;

        @Override
        public String toString() {
            return "OrderInfo{" +
                    "id=" + id +
                    ", state=" + state +
                    ", now_price=" + now_price +
                    ", order_name='" + order_name + '\'' +
                    ", order_pic_url='" + order_pic_url + '\'' +
                    ", type='" + type + '\'' +
                    ", subscribe_time='" + subscribe_time + '\'' +
                    ", is_comment=" + is_comment +
                    ", is_setting=" + is_setting +
                    ", is_maintain=" + is_maintain +
                    ", is_upkeep=" + is_upkeep +
                    '}';
        }

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

        public float getNow_price() {
            return now_price;
        }

        public void setNow_price(float now_price) {
            this.now_price = now_price;
        }

        public String getOrder_name() {
            return order_name;
        }

        public void setOrder_name(String order_name) {
            this.order_name = order_name;
        }

        public String getOrder_pic_url() {
            return order_pic_url;
        }

        public void setOrder_pic_url(String order_pic_url) {
            this.order_pic_url = order_pic_url;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getSubscribe_time() {
            return subscribe_time;
        }

        public void setSubscribe_time(String subscribe_time) {
            this.subscribe_time = subscribe_time;
        }

        public boolean isIs_comment() {
            return is_comment;
        }

        public void setIs_comment(boolean is_comment) {
            this.is_comment = is_comment;
        }

        public boolean isIs_setting() {
            return is_setting;
        }

        public void setIs_setting(boolean is_setting) {
            this.is_setting = is_setting;
        }

        public boolean isIs_maintain() {
            return is_maintain;
        }

        public void setIs_maintain(boolean is_maintain) {
            this.is_maintain = is_maintain;
        }

        public boolean isIs_upkeep() {
            return is_upkeep;
        }

        public void setIs_upkeep(boolean is_upkeep) {
            this.is_upkeep = is_upkeep;
        }

    }
}
