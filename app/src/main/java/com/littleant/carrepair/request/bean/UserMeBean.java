package com.littleant.carrepair.request.bean;

public class UserMeBean extends BaseResponseBean{

    public MeBean data;

    public MeBean getData() {
        return data;
    }

    public void setData(MeBean data) {
        this.data = data;
    }

    public class MeBean {
        private String phone = "";
        private String name = "";
        private String pic_url = "";
        private int id;
        private int point;
        private boolean is_pass;

        @Override
        public String toString() {
            return "MeBean{" +
                    "phone='" + phone + '\'' +
                    ", name='" + name + '\'' +
                    ", pic_url='" + pic_url + '\'' +
                    ", id=" + id +
                    ", point=" + point +
                    ", is_pass=" + is_pass +
                    '}';
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPic_url() {
            return pic_url;
        }

        public void setPic_url(String pic_url) {
            this.pic_url = pic_url;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getPoint() {
            return point;
        }

        public void setPoint(int point) {
            this.point = point;
        }

        public boolean isIs_pass() {
            return is_pass;
        }

        public void setIs_pass(boolean is_pass) {
            this.is_pass = is_pass;
        }
    }
}
