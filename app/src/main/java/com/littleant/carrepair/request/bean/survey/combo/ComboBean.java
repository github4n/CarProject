package com.littleant.carrepair.request.bean.survey.combo;

import com.littleant.carrepair.request.bean.BaseResponseBean;

import java.util.List;

public class ComboBean extends BaseResponseBean {
    private int id;
    private String create_time = "";
    private String update_time = "";
    private String name = "";
    private String detail = "";
    private List<ComboItemSet> comboitem_set;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public List<ComboItemSet> getComboitem_set() {
        return comboitem_set;
    }

    public void setComboitem_set(List<ComboItemSet> comboitem_set) {
        this.comboitem_set = comboitem_set;
    }
}


