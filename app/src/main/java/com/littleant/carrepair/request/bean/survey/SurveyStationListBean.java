package com.littleant.carrepair.request.bean.survey;

import com.littleant.carrepair.request.bean.BaseResponseBean;

import java.util.List;

/**
 * 年检站列表
 */
public class SurveyStationListBean extends BaseResponseBean {

    private List<SurveyStationInfo> data;

    public List<SurveyStationInfo> getData() {
        return data;
    }

    public void setData(List<SurveyStationInfo> data) {
        this.data = data;
    }

}
