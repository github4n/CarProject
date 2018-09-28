package com.littleant.carrepair.request.bean;

public class TermUrlBean extends BaseResponseBean {
    private TermUrl data;

    public TermUrl getData() {
        return data;
    }

    public void setData(TermUrl data) {
        this.data = data;
    }

    public class TermUrl {
        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
