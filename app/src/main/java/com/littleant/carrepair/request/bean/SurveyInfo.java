package com.littleant.carrepair.request.bean;

import java.io.Serializable;
import java.util.List;

public class SurveyInfo  implements Serializable {
    private int id;
    private int driver_user_id;
    private int state;
    private double order_longitude;
    private double order_latitude;
    private float base_price;
    private float combo_price;
    private float survey_price;
    private float total_price;
    private boolean is_self;
    private boolean is_success;
    private String create_time = "";
    private String update_time = "";
    private String name = "";
    private String phone = "";
    private String car_name = "";
    private String car_brand = "";
    private String car_code = "";
    private String car_type = "";
    private SurveyStationInfo surveystation;
    private String order_address = "";
    private String subscribe_time = "";
    private String driver_user_pic_url = "";
    private String driver_user_name = "";
    private String order_time = "";
    private String receive_time = "";
    private String get_time = "";
    private String arrive_survey_time = "";
    private String survey_time = "";
    private String arrive_return_time = "";
    private String return_time = "";
    private String confirm_time = "";
    private String cancel_time = "";
    private ComboBean combo;

    @Override
    public String toString() {
        return "SurveyInfo{" +
                "id=" + id +
                ", driver_user_id=" + driver_user_id +
                ", state=" + state +
                ", order_longitude=" + order_longitude +
                ", order_latitude=" + order_latitude +
                ", base_price=" + base_price +
                ", combo_price=" + combo_price +
                ", survey_price=" + survey_price +
                ", total_price=" + total_price +
                ", is_self=" + is_self +
                ", is_success=" + is_success +
                ", create_time='" + create_time + '\'' +
                ", update_time='" + update_time + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", car_name='" + car_name + '\'' +
                ", car_brand='" + car_brand + '\'' +
                ", car_code='" + car_code + '\'' +
                ", car_type='" + car_type + '\'' +
                ", surveystation=" + surveystation +
                ", order_address='" + order_address + '\'' +
                ", subscribe_time='" + subscribe_time + '\'' +
                ", driver_user_pic_url='" + driver_user_pic_url + '\'' +
                ", driver_user_name='" + driver_user_name + '\'' +
                ", order_time='" + order_time + '\'' +
                ", receive_time='" + receive_time + '\'' +
                ", get_time='" + get_time + '\'' +
                ", arrive_survey_time='" + arrive_survey_time + '\'' +
                ", survey_time='" + survey_time + '\'' +
                ", arrive_return_time='" + arrive_return_time + '\'' +
                ", return_time='" + return_time + '\'' +
                ", confirm_time='" + confirm_time + '\'' +
                ", cancel_time='" + cancel_time + '\'' +
                ", combo=" + combo +
                ", surveypic_list=" + surveypic_list +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDriver_user_id() {
        return driver_user_id;
    }

    public void setDriver_user_id(int driver_user_id) {
        this.driver_user_id = driver_user_id;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public double getOrder_longitude() {
        return order_longitude;
    }

    public void setOrder_longitude(double order_longitude) {
        this.order_longitude = order_longitude;
    }

    public double getOrder_latitude() {
        return order_latitude;
    }

    public void setOrder_latitude(double order_latitude) {
        this.order_latitude = order_latitude;
    }

    public float getBase_price() {
        return base_price;
    }

    public void setBase_price(float base_price) {
        this.base_price = base_price;
    }

    public float getCombo_price() {
        return combo_price;
    }

    public void setCombo_price(float combo_price) {
        this.combo_price = combo_price;
    }

    public float getSurvey_price() {
        return survey_price;
    }

    public void setSurvey_price(float survey_price) {
        this.survey_price = survey_price;
    }

    public float getTotal_price() {
        return total_price;
    }

    public void setTotal_price(float total_price) {
        this.total_price = total_price;
    }

    public boolean isIs_self() {
        return is_self;
    }

    public void setIs_self(boolean is_self) {
        this.is_self = is_self;
    }

    public boolean isIs_success() {
        return is_success;
    }

    public void setIs_success(boolean is_success) {
        this.is_success = is_success;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCar_name() {
        return car_name;
    }

    public void setCar_name(String car_name) {
        this.car_name = car_name;
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

    public String getCar_type() {
        return car_type;
    }

    public void setCar_type(String car_type) {
        this.car_type = car_type;
    }

    public SurveyStationInfo getSurveystation() {
        return surveystation;
    }

    public void setSurveystation(SurveyStationInfo surveystation) {
        this.surveystation = surveystation;
    }

    public String getOrder_address() {
        return order_address;
    }

    public void setOrder_address(String order_address) {
        this.order_address = order_address;
    }

    public String getSubscribe_time() {
        return subscribe_time;
    }

    public void setSubscribe_time(String subscribe_time) {
        this.subscribe_time = subscribe_time;
    }

    public String getDriver_user_pic_url() {
        return driver_user_pic_url;
    }

    public void setDriver_user_pic_url(String driver_user_pic_url) {
        this.driver_user_pic_url = driver_user_pic_url;
    }

    public String getDriver_user_name() {
        return driver_user_name;
    }

    public void setDriver_user_name(String driver_user_name) {
        this.driver_user_name = driver_user_name;
    }

    public String getOrder_time() {
        return order_time;
    }

    public void setOrder_time(String order_time) {
        this.order_time = order_time;
    }

    public String getReceive_time() {
        return receive_time;
    }

    public void setReceive_time(String receive_time) {
        this.receive_time = receive_time;
    }

    public String getGet_time() {
        return get_time;
    }

    public void setGet_time(String get_time) {
        this.get_time = get_time;
    }

    public String getArrive_survey_time() {
        return arrive_survey_time;
    }

    public void setArrive_survey_time(String arrive_survey_time) {
        this.arrive_survey_time = arrive_survey_time;
    }

    public String getSurvey_time() {
        return survey_time;
    }

    public void setSurvey_time(String survey_time) {
        this.survey_time = survey_time;
    }

    public String getArrive_return_time() {
        return arrive_return_time;
    }

    public void setArrive_return_time(String arrive_return_time) {
        this.arrive_return_time = arrive_return_time;
    }

    public String getReturn_time() {
        return return_time;
    }

    public void setReturn_time(String return_time) {
        this.return_time = return_time;
    }

    public String getConfirm_time() {
        return confirm_time;
    }

    public void setConfirm_time(String confirm_time) {
        this.confirm_time = confirm_time;
    }

    public String getCancel_time() {
        return cancel_time;
    }

    public void setCancel_time(String cancel_time) {
        this.cancel_time = cancel_time;
    }

    public ComboBean getCombo() {
        return combo;
    }

    public void setCombo(ComboBean combo) {
        this.combo = combo;
    }

    public List<SurveyPicList> getSurveypic_list() {
        return surveypic_list;
    }

    public void setSurveypic_list(List<SurveyPicList> surveypic_list) {
        this.surveypic_list = surveypic_list;
    }

    private List<SurveyPicList> surveypic_list;
}
