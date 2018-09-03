package com.littleant.carrepair.request.constant;

public class ParamsConstant {

    //公用部分
    public static final String USER_ID = "user_id";
    public static final String TIMESTAMP = "timestamp";
    public static final String SIGN = "sign";
    public static final String SYSTEM = "system";
    public static final String VERSION = "version";
    public static final String SYSTEM_ANDROID = "android";
    public static final String API_VERSION = "1.0.0";
    public static final String PHONE = "phone";
    public static final String NAME = "name";
    public static final String PIC = "pic";
    public static final String IS_DEFAULT = "is_default";
    public static final String TYPE = "type";
//    public static final String REAPONSE_CODE_SUCCESS = "100";
//    public static final String REAPONSE_CODE_FAIL = "200";
//    public static final String REAPONSE_CODE_AUTH_FAIL = "300";
//    public static final String REAPONSE_CODE_NO_FUNC = "999";
    public static final int REAPONSE_CODE_SUCCESS = 100;
    public static final int REAPONSE_CODE_FAIL = 200;
    public static final int REAPONSE_CODE_AUTH_FAIL = 300;
    public static final int REAPONSE_CODE_NO_FUNC = 999;

    /**
     * 查询的公用类型
     */
    public enum QueryType {
        DEFAULT,
        NONE,
        NORMAL
    }
    /**
     * 通用参数，根据功能不同传入不同ID
     */
    public static final String ID = "id";

    //登录部分
    public static final String PASSWORD = "password";
    /**
     * 验证码
     */
    public static final String MESSAGE = "message";
    public static final String TOKEN = "token";
    public static final String EXPIRE = "expire";
    /**
     * 验证码的枚举类型
     */
    public enum MessageType {
        REGISTER("register"),
        RESET("reset");

        String des;

        MessageType(String s) {
            this.des = s;
        }

        public String getDes() {
            return des;
        }
    }

    //个人中心(我)部分
    public static final String PIC_URL = "pic_url";
    public static final String POINT = "point";
    public static final String IS_PASS = "is_pass";

    //个人中心(车)部分
    public static final String BRAND = "brand";
    public static final String ENGINE = "engine";
    public static final String CODE = "code";
    public static final String BUY_TIME = "buy_time";
    public static final String MILEAGE = "mileage";

    //个人中心（地址）部分
    public static final String NODE1 = "node1";
    public static final String NODE2 = "node2";
    public static final String NODE3 = "node3";
    public static final String ADDRESS = "address";

    //服务（分类）部分
    public static final String P_ID = "p_id";
    public static final int SERVICE_QUERY_TYPE_SHOP = 0; //商城
    public static final int SERVICE_QUERY_TYPE_INSURANCE = 1; //保险

    //服务（商品）部分
    public static final String CATALOG_ID = "catalog_id";
    public static final String PRICE_ORDER = "price_order";
    public static final String SALE_ORDER = "sale_order";

    //服务（购物车）部分
    public static final String PRODUCT_ID = "product_id";
    public static final String AMOUNT = "amount";

    //服务（订单）部分
    public static final String STATUS = "status";
    public static final String IS_COMMENT = "is_comment";
    public static final int SERVICE_ORDER_STATUS_ALL = -1;
    public static final int SERVICE_ORDER_STATUS_WAIT_PAY = 0;
    public static final int SERVICE_ORDER_STATUS_WAIT_SEND = 1;
    public static final int SERVICE_ORDER_STATUS_WAIT_RECEIVE = 2;
    public static final int SERVICE_ORDER_STATUS_FINISH = 3;
    public static final String ADDRESS_ID = "address_id";
    public static final String ORDER_CAR_LIST = "order_car_list";

    //服务（付款、完成、评论订单信息）部分
    public static final String METHOD = "method";
    public static final String SCORE = "score";
    /**
     * 订单状态
     */
    public enum MethodStatus {
        PAY("pay"),
        COMMENT("comment"),
        FINISH("finish");

        String des;

        MethodStatus(String s) {
            this.des = s;
        }

        public String getDes() {
            return des;
        }
    }

    //服务（资讯）部分
    public static final String NEWS_CATALOG_ID =  "news_catalog_id";

    //维修（汽修厂）部分
    public static final String ORDERBY =  "orderby";
    public static final String LONGITUDE =  "longitude";
    public static final String LATITUDE =  "latitude";
    public static final String GARAGE_ID =  "garage_id";
    /**
     * 排列顺序
     */
    public enum OrderRule {
        ALL("all"),
        DISTANCE("distance"),
        POPULAR("popular");

        String des;

        OrderRule(String s) {
            this.des = s;
        }

        public String getDes() {
            return des;
        }
    }

    //维修（保养）部分
    public static final String FINISH =  "finish";
    public static final int MAINTAIN_ALL = 0;
    public static final int MAINTAIN_NOT_FINISH = 1;
    public static final int MAINTAIN_FINISH = 2;
    public static final String CAR_ID =  "car_id";
    public static final String SUBSCRIBE_TIME =  "subscribe_time";
    public static final String OIL_ID =  "oil_id";

    //维修（维修）部分
    public static final String CONTENT =  "content";
    public static final String NUMBER =  "number";

    //维修（保养、维修订单列表信息）部分
    public static final String STATE =  "state";
    public static final int MAINTAIN_LIST_STATUS_ALL = -1;
    public static final int MAINTAIN_LIST_STATUS_WAIT_PAY = 0;
    public static final int MAINTAIN_LIST_STATUS_WAIT_SEND = 1;
    public static final int MAINTAIN_LIST_STATUS_WAIT_RECEIVE = 2;
    public static final int MAINTAIN_LIST_STATUS_FINISH = 3;

    //年检（订单）部分
    public static final int SURVEY_ALL = 0;
    public static final int SURVEY_NOT_FINISH = 1;
    public static final int SURVEY_FINISH = 2;
    public static final String CAR_NAME =  "car_name";
    public static final String CAR_BRAND =  "car_brand";
    public static final String CAR_CODE =  "car_code";
    public static final String CAR_TYPE =  "car_type";
    public static final String SURVEYSTATION_ID =  "surveystation_id";
    public static final String ORDER_LONGITUDE =  "order_longitude";
    public static final String ORDER_LATITUDE =  "order_latitude";
    public static final String ORDER_ADDRESS =  "order_address";
    public static final String IS_SELF =  "is_self";
    public static final String COMBO_ID =  "combo_id";
    public static final String COMBOITEM_LIST =  "comboitem_list";
    /**
     * 订单状态
     */
    public enum SurveyMethodType {
        //查询费用
        GET("get"),
        //支付（自驾代驾）
        PAY("pay"),
        //确认还车（代驾）
        RETURN("return"),
        //到达年检（自驾）
        SURVEY("survey");

        String des;

        SurveyMethodType(String s) {
            this.des = s;
        }

        public String getDes() {
            return des;
        }
    }

    public static final String EXTRA_USER_ME_BEAN = "extra_user_me_bean";
    public static final int ACTIVITY_RESULT_LOGOUT = 10;
    public static final int ACTIVITY_RESULT_ME_MODIFY = 11;
    public static final int ACTIVITY_RESULT_ADD_CAR = 12;
}
