package com.littleant.carrepair.request.bean.survey;

import com.littleant.carrepair.request.bean.BaseResponseBean;

import java.io.Serializable;

/**
 * 文件描述:
 * 作者:莫进生
 * 创建时间:2018/11/24 0024
 * 版本号:1
 */


public class SurveyPhoneInfo extends BaseResponseBean {
    private SurveyPhone data;

    public SurveyPhone getData() {
        return data;
    }

    public void setData(SurveyPhone data) {
        this.data = data;
    }

    public class SurveyPhone implements Serializable {
        private String phone;
        private String moblie_phone;

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getMoblie_phone() {
            return moblie_phone;
        }

        public void setMoblie_phone(String moblie_phone) {
            this.moblie_phone = moblie_phone;
        }
    }
}
