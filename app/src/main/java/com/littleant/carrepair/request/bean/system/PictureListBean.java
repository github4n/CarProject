package com.littleant.carrepair.request.bean.system;

import com.littleant.carrepair.request.bean.BaseResponseBean;

import java.io.Serializable;
import java.util.List;

/**
 * 年检、服务fragment的轮播图
 */
public class PictureListBean extends BaseResponseBean {

    private PictureBean data;

    public PictureBean getData() {
        return data;
    }

    public void setData(PictureBean data) {
        this.data = data;
    }

    public class PictureBean implements Serializable {
        private String pic_url;
        private List<String> pic_url_list;

        @Override
        public String toString() {
            return "PictureBean{" +
                    "pic_url='" + pic_url + '\'' +
                    ", pic_url_list=" + pic_url_list +
                    '}';
        }

        public String getPic_url() {
            return pic_url;
        }

        public void setPic_url(String pic_url) {
            this.pic_url = pic_url;
        }

        public List<String> getPic_url_list() {
            return pic_url_list;
        }

        public void setPic_url_list(List<String> pic_url_list) {
            this.pic_url_list = pic_url_list;
        }
    }
}
