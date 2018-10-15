package com.littleant.carrepair.request.bean.survey;

import java.io.Serializable;

/**
 * 年检结果图文
 */
public class ObjList implements Serializable {
    private String pic_url = "";
    private String note = "";

    @Override
    public String toString() {
        return "ObjList{" +
                "pic_url='" + pic_url + '\'' +
                ", note='" + note + '\'' +
                '}';
    }

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
