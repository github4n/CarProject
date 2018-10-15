package com.littleant.carrepair.request.bean.survey;

import com.littleant.carrepair.request.bean.BaseResponseBean;

import java.io.Serializable;

/**
 * 年检费用
 */
public class SurveyFeeBean extends BaseResponseBean {

    private SurveyFee data;

    public SurveyFee getData() {
        return data;
    }

    public void setData(SurveyFee data) {
        this.data = data;
    }

    public class SurveyFee implements Serializable {
        private float base_price;
        private float combo_price;
        private float survey_price;
        private float total_price;

        @Override
        public String toString() {
            return "SurveyFee{" +
                    "base_price=" + base_price +
                    ", combo_price=" + combo_price +
                    ", survey_price=" + survey_price +
                    ", total_price=" + total_price +
                    '}';
        }

        public float getBase_price() {
            return base_price;
        }

        public void setBase_price(float base_price) {
            this.base_price = base_price;
        }

        public float getCombo_price() {
            return combo_price;
        }

        public void setCombo_price(float combo_price) {
            this.combo_price = combo_price;
        }

        public float getSurvey_price() {
            return survey_price;
        }

        public void setSurvey_price(float survey_price) {
            this.survey_price = survey_price;
        }

        public float getTotal_price() {
            return total_price;
        }

        public void setTotal_price(float total_price) {
            this.total_price = total_price;
        }
    }
}
