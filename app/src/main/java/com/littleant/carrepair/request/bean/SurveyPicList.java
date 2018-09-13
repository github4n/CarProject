package com.littleant.carrepair.request.bean;

import java.io.Serializable;
import java.util.List;

public class SurveyPicList implements Serializable {

    private String name = "";
    private List<ObjList> obj_list;

    @Override
    public String toString() {
        return "SurveyPicList{" +
                "name='" + name + '\'' +
                ", obj_list=" + obj_list +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ObjList> getObj_list() {
        return obj_list;
    }

    public void setObj_list(List<ObjList> obj_list) {
        this.obj_list = obj_list;
    }
}
