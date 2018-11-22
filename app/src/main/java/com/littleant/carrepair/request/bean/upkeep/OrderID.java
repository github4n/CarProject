package com.littleant.carrepair.request.bean.upkeep;

import com.littleant.carrepair.request.bean.BaseResponseBean;

/**
 * 文件描述:
 * 作者:莫进生
 * 创建时间:2018/11/21 0021
 * 版本号:1
 */


public class OrderID extends BaseResponseBean {
    private OrderIDOne data;

    public OrderIDOne getData() {
        return data;
    }

    public void setData(OrderIDOne data) {
        this.data = data;
    }

    public class OrderIDOne{
        private int id;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

}
