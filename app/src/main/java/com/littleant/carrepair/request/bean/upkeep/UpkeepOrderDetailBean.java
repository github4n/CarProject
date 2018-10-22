package com.littleant.carrepair.request.bean.upkeep;

import com.littleant.carrepair.request.bean.BaseResponseBean;
import com.littleant.carrepair.request.bean.maintain.garage.GarageInfo;
import com.littleant.carrepair.request.bean.survey.ObjList;

import java.io.Serializable;
import java.util.List;

/**
 * 维修信息
 */
public class UpkeepOrderDetailBean extends BaseResponseBean {

    private UpkeepOrderDetail data;

    public UpkeepOrderDetail getData() {
        return data;
    }

    public void setData(UpkeepOrderDetail data) {
        this.data = data;
    }

    public class UpkeepOrderDetail implements Serializable {
        private int id;
        private String create_time = "";
        private String update_time = "";
        private GarageInfo garage;
        private String name = "";
        private String phone = "";
        private double longitude;
        private double latitude;
        private String address = "";
        private float filter_price;
        private float price;
        private float discounts;
        private float now_price;
        private int state;
        private boolean is_delete;
        private boolean is_comment;
        private boolean is_upkeep;
        private float score;
        private String order_time = "";
        private String start_order_time = "";
        private String pay_time = "";
        private String get_time = "";
        private String service_time = "";
        private String upkeep_time = "";
        private String over_time = "";
        private String comment_time = "";
        private String deal_id = "";
        private String order_id = "";
        private String oil_name = "";
        private int oil_L;
        private int oil_amount;
        private float oil_price;
        private float oil_new_price;
        private String car_brand = "";
        private String car_code = "";
        private String order_name = "";
        private String service_item = "";
        private String order_type = "";
        private List<ObjList> upkeeppic_set;

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

        public float getFilter_price() {
            return filter_price;
        }

        public void setFilter_price(float filter_price) {
            this.filter_price = filter_price;
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

        public boolean isIs_upkeep() {
            return is_upkeep;
        }

        public void setIs_upkeep(boolean is_upkeep) {
            this.is_upkeep = is_upkeep;
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

        public String getUpkeep_time() {
            return upkeep_time;
        }

        public void setUpkeep_time(String upkeep_time) {
            this.upkeep_time = upkeep_time;
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

        public String getOil_name() {
            return oil_name;
        }

        public void setOil_name(String oil_name) {
            this.oil_name = oil_name;
        }

        public int getOil_L() {
            return oil_L;
        }

        public void setOil_L(int oil_L) {
            this.oil_L = oil_L;
        }

        public int getOil_amount() {
            return oil_amount;
        }

        public void setOil_amount(int oil_amount) {
            this.oil_amount = oil_amount;
        }

        public float getOil_price() {
            return oil_price;
        }

        public void setOil_price(float oil_price) {
            this.oil_price = oil_price;
        }

        public float getOil_new_price() {
            return oil_new_price;
        }

        public void setOil_new_price(float oil_new_price) {
            this.oil_new_price = oil_new_price;
        }

        public String getCar_brand() {
            return car_brand;
        }

        public void setCar_brand(String car_brand) {
            this.car_brand = car_brand;
        }

        public String getCar_code() {
            return car_code;
        }

        public void setCar_code(String car_code) {
            this.car_code = car_code;
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

        public List<ObjList> getUpkeeppic_set() {
            return upkeeppic_set;
        }

        public void setUpkeeppic_set(List<ObjList> upkeeppic_set) {
            this.upkeeppic_set = upkeeppic_set;
        }
    }
}
