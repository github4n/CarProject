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
    public static final int SERVICE_ORDER_STATUS_ALL = -1; //商城
    public static final int SERVICE_ORDER_STATUS_WAIT_PAY = 0; //商城
    public static final int SERVICE_ORDER_STATUS_WAIT_SEND = 1; //商城
    public static final int SERVICE_ORDER_STATUS_WAIT_RECEIVE = 2; //商城
    public static final int SERVICE_ORDER_STATUS_FINISH = 3; //商城
    public static final String ADDRESS_ID = "address_id";
    public static final String ORDER_CAR_LIST = "order_car_list";

    //服务（付款、完成、评论订单信息）部分
    public static final String METHOD = "method";
    public static final String SCORE = "score";
    /**
     * 验证码的枚举类型
     */
    public enum OrderMethodType {
        PAY("pay"),
        COMMENT("comment"),
        FINISH("finish");

        String des;

        OrderMethodType(String s) {
            this.des = s;
        }

        public String getDes() {
            return des;
        }
    }
}
