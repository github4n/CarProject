package com.littleant.carrepair.request.bean;

import java.util.List;

public class SurveyStationListBean extends BaseResponseBean {

    private List<SurveyStationInfo> data;

    public List<SurveyStationInfo> getData() {
        return data;
    }

    public void setData(List<SurveyStationInfo> data) {
        this.data = data;
    }

}
