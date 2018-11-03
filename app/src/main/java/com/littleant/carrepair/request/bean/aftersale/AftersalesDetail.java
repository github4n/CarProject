package com.littleant.carrepair.request.bean.aftersale;

import com.littleant.carrepair.request.bean.BaseResponseBean;
import com.littleant.carrepair.request.bean.survey.ObjList;

import java.io.Serializable;
import java.util.List;

/**
 * 文件描述:
 * 作者:莫进生
 * 创建时间:2018/11/3 0003
 * 版本号:1
 */


public class AftersalesDetail extends BaseResponseBean {


    private AftersalesOrderDetail data;

    public AftersalesOrderDetail getData() {
        return data;
    }

    public void setData(AftersalesOrderDetail data) {
        this.data = data;
    }

    public class AftersalesOrderDetail implements Serializable{

        private String id;
        private String order_id;
        private String car_code;
        private String car_type;
        private String create_time;
        private String state;
        private List<ObjList> pic_list;



        public String getId() {
            return id;
        }

        public void setId(String id) {
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

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public List<ObjList> getPic_list() {
            return pic_list;
        }

        public void setPic_list(List<ObjList> pic_list) {
            this.pic_list = pic_list;
        }
    }




}
