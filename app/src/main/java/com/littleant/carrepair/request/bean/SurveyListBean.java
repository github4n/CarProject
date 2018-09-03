package com.littleant.carrepair.request.bean;

import java.util.List;

public class SurveyListBean extends BaseResponseBean {

    private List<SurveyInfo> data;

    public List<SurveyInfo> getData() {
        return data;
    }

    public void setData(List<SurveyInfo> data) {
        this.data = data;
    }

}
