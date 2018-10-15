package com.littleant.carrepair.request.bean.push;

import com.littleant.carrepair.request.bean.BaseResponseBean;

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
        private String type = "";
        private PushInfo data;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public PushInfo getData() {
            return data;
        }

        public void setData(PushInfo data) {
            this.data = data;
        }
    }

    public class PushInfo implements Serializable {
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
