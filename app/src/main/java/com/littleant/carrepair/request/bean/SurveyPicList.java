package com.littleant.carrepair.request.bean;

import java.io.Serializable;
import java.util.List;

public class SurveyPicList implements Serializable {

    private String type = "";
    private List<ObjList> obj_list;

    @Override
    public String toString() {
        return "SurveyPicList{" +
                "type='" + type + '\'' +
                ", obj_list=" + obj_list +
                '}';
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<ObjList> getObj_list() {
        return obj_list;
    }

    public void setObj_list(List<ObjList> obj_list) {
        this.obj_list = obj_list;
    }
}
