package com.littleant.carrepair.request.bean;

public class LoginBean extends BaseResponseBean{


    public UserBean data;

    public UserBean getData() {
        return data;
    }

    public void setData(UserBean data) {
        this.data = data;
    }

    public class UserBean {
        private int user_id;
        private String token = "";
        private String expire = "";

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getExpire() {
            return expire;
        }

        public void setExpire(String expire) {
            this.expire = expire;
        }

        @Override
        public String toString() {
            return "LoginBean{" +
                    "user_id='" + user_id + '\'' +
                    ", token='" + token + '\'' +
                    ", expire='" + expire + '\'' +
                    '}';
        }
    }
}
