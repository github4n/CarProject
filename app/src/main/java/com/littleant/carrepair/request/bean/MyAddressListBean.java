package com.littleant.carrepair.request.bean;

import java.io.Serializable;
import java.util.List;

public class MyAddressListBean extends BaseResponseBean {

    private List<AddressInfo> data;

    public List<AddressInfo> getData() {
        return data;
    }

    public void setData(List<AddressInfo> data) {
        this.data = data;
    }

    public class AddressInfo implements Serializable {
        private int id;
        private String create_time = "";
        private String update_time = "";
        private String name = "";
        private String phone = "";
        private Node node1;
        private Node node2;
        private Node node3;
        private String address = "";
        private boolean is_default;

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

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public Node getNode1() {
            return node1;
        }

        public void setNode1(Node node1) {
            this.node1 = node1;
        }

        public Node getNode2() {
            return node2;
        }

        public void setNode2(Node node2) {
            this.node2 = node2;
        }

        public Node getNode3() {
            return node3;
        }

        public void setNode3(Node node3) {
            this.node3 = node3;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public boolean isIs_default() {
            return is_default;
        }

        public void setIs_default(boolean is_default) {
            this.is_default = is_default;
        }
    }

    public class Node implements Serializable {
        private int id;
        private int p_id;
        private String create_time = "";
        private String update_time = "";
        private String name = "";

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getP_id() {
            return p_id;
        }

        public void setP_id(int p_id) {
            this.p_id = p_id;
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
    }
}
