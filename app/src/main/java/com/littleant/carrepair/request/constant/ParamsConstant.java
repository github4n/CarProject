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
    public static final String NAME = "name";
    public static final String PIC = "pic";
    /**
     * 通用参数，根据功能不同传入不同ID
     */
    public static final String ID = "id";

    //登录部分
    public static final String PHONE = "phone";
    public static final String PASSWORD = "password";
    /**
     * 验证码
     */
    public static final String MESSAGE = "message";
    public static final String TYPE = "type";
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
    public enum CarQueryType {
        DEFAULT,
        NONE,
        NORMAL
    }
    public static final String IS_DEFAULT = "is_default";
    public static final String BRAND = "brand";
    public static final String ENGINE = "engine";
    public static final String CODE = "code";
    public static final String BUY_TIME = "buy_time";
    public static final String MILEAGE = "mileage";
}
