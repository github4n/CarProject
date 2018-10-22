package com.littleant.carrepair.request.bean.maintain;

import com.littleant.carrepair.request.bean.BaseResponseBean;
import com.littleant.carrepair.request.bean.maintain.garage.GarageInfo;
import com.littleant.carrepair.request.bean.survey.ObjList;

import java.io.Serializable;
import java.util.List;

/**
 * 维修信息
 */
public class MaintainOrderDetailBean extends BaseResponseBean {

    private MaintainOrderDetail data;

    public MaintainOrderDetail getData() {
        return data;
    }

    public void setData(MaintainOrderDetail data) {
        this.data = data;
    }

    public class MaintainOrderDetail implements Serializable {
        private int id;
        private String create_time = "";
        private String update_time = "";
        private GarageInfo garage;
        private String name = "";
        private String phone = "";
        private double longitude;
        private double latitude;
        private String address = "";
        private String subscribe_time = "";
        private String content = "";
        private List<String> pic_url_list;
        private float price;
        private float discounts;
        private float now_price;
        private int state;
        private boolean is_delete;
        private boolean is_comment;
        private boolean is_maintain;
        private boolean is_setting;
        private float score;
        private String order_time = "";
        private String start_order_time = "";
        private String pay_time = "";
        private String get_time = "";
        private String service_time = "";
        private String maintain_time = "";
        private String over_time = "";
        private String comment_time = "";
        private String car_code = "";
        private String car_type = "";
        private String deal_id = "";
        private String order_id = "";
        private String order_name = "";
        private String service_item = "";
        private String order_type = "";
        private List<ObjList> maintainpic_set;
        private List<MaintainSet> maintainitem_set;
        private List<MaintainSet> maintainitem_set_now;

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

        public GarageInfo getGarage() {
            return garage;
        }

        public void setGarage(GarageInfo garage) {
            this.garage = garage;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getSubscribe_time() {
            return subscribe_time;
        }

        public void setSubscribe_time(String subscribe_time) {
            this.subscribe_time = subscribe_time;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public List<String> getPic_url_list() {
            return pic_url_list;
        }

        public void setPic_url_list(List<String> pic_url_list) {
            this.pic_url_list = pic_url_list;
        }

        public float getPrice() {
            return price;
        }

        public void setPrice(float price) {
            this.price = price;
        }

        public float getDiscounts() {
            return discounts;
        }

        public void setDiscounts(float discounts) {
            this.discounts = discounts;
        }

        public float getNow_price() {
            return now_price;
        }

        public void setNow_price(float now_price) {
            this.now_price = now_price;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public boolean isIs_delete() {
            return is_delete;
        }

        public void setIs_delete(boolean is_delete) {
            this.is_delete = is_delete;
        }

        public boolean isIs_comment() {
            return is_comment;
        }

        public void setIs_comment(boolean is_comment) {
            this.is_comment = is_comment;
        }

        public boolean isIs_maintain() {
            return is_maintain;
        }

        public void setIs_maintain(boolean is_maintain) {
            this.is_maintain = is_maintain;
        }

        public boolean isIs_setting() {
            return is_setting;
        }

        public void setIs_setting(boolean is_setting) {
            this.is_setting = is_setting;
        }

        public float getScore() {
            return score;
        }

        public void setScore(float score) {
            this.score = score;
        }

        public String getOrder_time() {
            return order_time;
        }

        public void setOrder_time(String order_time) {
            this.order_time = order_time;
        }

        public String getStart_order_time() {
            return start_order_time;
        }

        public void setStart_order_time(String start_order_time) {
            this.start_order_time = start_order_time;
        }

        public String getPay_time() {
            return pay_time;
        }

        public void setPay_time(String pay_time) {
            this.pay_time = pay_time;
        }

        public String getGet_time() {
            return get_time;
        }

        public void setGet_time(String get_time) {
            this.get_time = get_time;
        }

        public String getService_time() {
            return service_time;
        }

        public void setService_time(String service_time) {
            this.service_time = service_time;
        }

        public String getMaintain_time() {
            return maintain_time;
        }

        public void setMaintain_time(String maintain_time) {
            this.maintain_time = maintain_time;
        }

        public String getOver_time() {
            return over_time;
        }

        public void setOver_time(String over_time) {
            this.over_time = over_time;
        }

        public String getComment_time() {
            return comment_time;
        }

        public void setComment_time(String comment_time) {
            this.comment_time = comment_time;
        }

        public String getCar_code() {
            return car_code;
        }

        public void setCar_code(String car_code) {
            this.car_code = car_code;
        }

        public String getCar_type() {
            return car_type;
        }

        public void setCar_type(String car_type) {
            this.car_type = car_type;
        }

        public String getDeal_id() {
            return deal_id;
        }

        public void setDeal_id(String deal_id) {
            this.deal_id = deal_id;
        }

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public String getOrder_name() {
            return order_name;
        }

        public void setOrder_name(String order_name) {
            this.order_name = order_name;
        }

        public String getService_item() {
            return service_item;
        }

        public void setService_item(String service_item) {
            this.service_item = service_item;
        }

        public String getOrder_type() {
            return order_type;
        }

        public void setOrder_type(String order_type) {
            this.order_type = order_type;
        }

        public List<ObjList> getMaintainpic_set() {
            return maintainpic_set;
        }

        public void setMaintainpic_set(List<ObjList> maintainpic_set) {
            this.maintainpic_set = maintainpic_set;
        }

        public List<MaintainSet> getMaintainitem_set() {
            return maintainitem_set;
        }

        public void setMaintainitem_set(List<MaintainSet> maintainitem_set) {
            this.maintainitem_set = maintainitem_set;
        }

        public List<MaintainSet> getMaintainitem_set_now() {
            return maintainitem_set_now;
        }

        public void setMaintainitem_set_now(List<MaintainSet> maintainitem_set_now) {
            this.maintainitem_set_now = maintainitem_set_now;
        }
    }

    public class MaintainSet implements Serializable {
        private int id;
        private String name = "";
        private float price;

        @Override
        public String toString() {
            return "MaintainSet{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", price=" + price +
                    '}';
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public float getPrice() {
            return price;
        }

        public void setPrice(float price) {
            this.price = price;
        }
    }
}
