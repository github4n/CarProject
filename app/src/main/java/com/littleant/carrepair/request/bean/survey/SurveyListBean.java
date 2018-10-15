package com.littleant.carrepair.request.bean.survey;

import com.littleant.carrepair.request.bean.BaseResponseBean;

import java.util.List;

/**
 * 年检记录列表
 */
public class SurveyListBean extends BaseResponseBean {

    private List<SurveyInfo> data;

    public List<SurveyInfo> getData() {
        return data;
    }

    public void setData(List<SurveyInfo> data) {
        this.data = data;
    }

}
