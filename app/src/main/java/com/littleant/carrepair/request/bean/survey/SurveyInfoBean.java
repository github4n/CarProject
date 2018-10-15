package com.littleant.carrepair.request.bean.survey;

import com.littleant.carrepair.request.bean.BaseResponseBean;

import java.io.Serializable;

/**
 * 年检须知
 */
public class SurveyInfoBean extends BaseResponseBean {
    private SurveyKnowUrl data;

    public SurveyKnowUrl getData() {
        return data;
    }

    public void setData(SurveyKnowUrl data) {
        this.data = data;
    }

    public class SurveyKnowUrl implements Serializable {
        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
