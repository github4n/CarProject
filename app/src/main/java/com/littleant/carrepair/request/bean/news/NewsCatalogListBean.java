package com.littleant.carrepair.request.bean.news;

import com.littleant.carrepair.request.bean.BaseResponseBean;

import java.io.Serializable;
import java.util.List;

public class NewsCatalogListBean extends BaseResponseBean {

    private List<InfoBean> data;

    public List<InfoBean> getData() {
        return data;
    }

    public void setData(List<InfoBean> data) {
        this.data = data;
    }

    public class InfoBean implements Serializable {
        private int id;
        private String create_time = "";
        private String update_time = "";
        private String name = "";

        @Override
        public String toString() {
            return "InfoBean{" +
                    "id=" + id +
                    ", create_time='" + create_time + '\'' +
                    ", update_time='" + update_time + '\'' +
                    ", name='" + name + '\'' +
                    '}';
        }

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
    }
}
