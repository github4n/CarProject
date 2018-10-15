package com.littleant.carrepair.request.bean.system;

import com.littleant.carrepair.request.bean.BaseResponseBean;

import java.io.Serializable;
import java.util.List;

/**
 * 启动图片
 */
public class SystemCoverBean extends BaseResponseBean {
    private CoverList data;

    public CoverList getData() {
        return data;
    }

    public void setData(CoverList data) {
        this.data = data;
    }

    public class CoverList implements Serializable {
        private String update_time = "";
        private List<String> pic_url_list;

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }

        public List<String> getPic_url_list() {
            return pic_url_list;
        }

        public void setPic_url_list(List<String> pic_url_list) {
            this.pic_url_list = pic_url_list;
        }
    }
}
