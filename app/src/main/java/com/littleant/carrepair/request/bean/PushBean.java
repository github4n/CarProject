package com.littleant.carrepair.request.bean;

import java.io.Serializable;

public class PushBean extends BaseResponseBean {

    private PushData data;

    public PushData getData() {
        return data;
    }

    public void setData(PushData data) {
        this.data = data;
    }

    public class PushData implements Serializable {
        private int id;
        private boolean state;
        private String msg = "";

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public boolean isState() {
            return state;
        }

        public void setState(boolean state) {
            this.state = state;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }
}
