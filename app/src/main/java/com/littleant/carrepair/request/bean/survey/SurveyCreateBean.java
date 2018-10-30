package com.littleant.carrepair.request.bean.survey;

import com.littleant.carrepair.request.bean.BaseResponseBean;

import java.io.Serializable;

public class SurveyCreateBean extends BaseResponseBean {
    private ID data;

    public ID getData() {
        return data;
    }

    public void setData(ID data) {
        this.data = data;
    }

    public class ID implements Serializable {
        private int id;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
