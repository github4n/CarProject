package com.littleant.carrepair.request.bean.survey;

import com.littleant.carrepair.request.bean.survey.ObjList;

import java.io.Serializable;
import java.util.List;

/**
 * 年检失败信息
 */
public class FailureListBean implements Serializable {
    private String name = "";
    private float price;
//    private List<FailureItemList> failureitem_list;

    @Override
    public String toString() {
        return "FailureListBean{" +
                "name='" + name + '\'' +
                ", price=" + price +
//                ", failureitem_list=" + failureitem_list +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

//    public List<FailureItemList> getFailureitem_list() {
//        return failureitem_list;
//    }
//
//    public void setFailureitem_list(List<FailureItemList> failureitem_list) {
//        this.failureitem_list = failureitem_list;
//    }

    public class FailureItemList implements Serializable {
        private String name = "";
        private int price;
        private List<ObjList> failurepic_list;

        @Override
        public String toString() {
            return "FailureItemList{" +
                    "name='" + name + '\'' +
                    ", price=" + price +
                    ", failurepic_list=" + failurepic_list +
                    '}';
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public List<ObjList> getFailurepic_list() {
            return failurepic_list;
        }

        public void setFailurepic_list(List<ObjList> failurepic_list) {
            this.failurepic_list = failurepic_list;
        }
    }

}
