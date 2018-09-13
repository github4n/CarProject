package com.littleant.carrepair.request.bean;

import java.io.Serializable;
import java.util.List;

public class NewsBannerListBean extends BaseResponseBean {

    private List<BannerBean> data;

    public List<BannerBean> getData() {
        return data;
    }

    public void setData(List<BannerBean> data) {
        this.data = data;
    }

    public class BannerBean implements Serializable {
        private int id;
        private String create_time = "";
        private String update_time = "";
        private String title = "";
        private String content = "";
        private String pic_url = "";
        private String detail_url = "";
        private List<String> pic_url_list;
        private boolean is_bar;

        @Override
        public String toString() {
            return "BannerBean{" +
                    "id=" + id +
                    ", create_time='" + create_time + '\'' +
                    ", update_time='" + update_time + '\'' +
                    ", title='" + title + '\'' +
                    ", content='" + content + '\'' +
                    ", pic_url='" + pic_url + '\'' +
                    ", detail_url='" + detail_url + '\'' +
                    ", pic_url_list=" + pic_url_list +
                    ", is_bar=" + is_bar +
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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getPic_url() {
            return pic_url;
        }

        public void setPic_url(String pic_url) {
            this.pic_url = pic_url;
        }

        public String getDetail_url() {
            return detail_url;
        }

        public void setDetail_url(String detail_url) {
            this.detail_url = detail_url;
        }

        public List<String> getPic_url_list() {
            return pic_url_list;
        }

        public void setPic_url_list(List<String> pic_url_list) {
            this.pic_url_list = pic_url_list;
        }

        public boolean isIs_bar() {
            return is_bar;
        }

        public void setIs_bar(boolean is_bar) {
            this.is_bar = is_bar;
        }
    }
}
