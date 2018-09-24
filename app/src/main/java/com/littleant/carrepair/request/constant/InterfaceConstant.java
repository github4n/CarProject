package com.littleant.carrepair.request.constant;

public class InterfaceConstant {

    //登录部分
    /**
     * post:用户注册
     */
    public static final String LOGIN_REGISTER = "api/login/register/";
    /**
     * post:用户注册
     */
    public static final String LOGIN_RESET = "api/login/reset/";
    /**
     * post:登陆
     */
    public static final String LOGIN_LOGIN = "api/login/login/";
    /**
     * post:登陆
     */
    public static final String LOGIN_MESSAGE = "api/login/message/";
    /**
     * get:刷新token
     */
    public static final String LOGIN_REFRESH = "api/login/refresh/";

    //个人中心部分
    /**
     * get:查询自身信息<br>
     * post:修改自身信息
     */
    public static final String USER_USER = "api/user/user/";
    /**
     * get:查询所有车辆信息 / 查询车辆信息<br>
     * post:添加车辆信息 / 修改车辆信息
     */
    public static final String USER_CAR = "api/user/car/";
    /**
     * get:删除车辆信息<br>
     */
    public static final String USER_CAR_DELETE = "api/user/car_delete/";
    /**
     * get:查询三级地址信息<br>
     */
    public static final String USER_ADDRESSINFO = "api/user/addressinfo/";
    /**
     * get:查询地址列表信息 / 查询地址信息<br>
     * post:添加地址信息 / 修改地址信息
     */
    public static final String USER_ADDRESS = "api/user/address/";
    /**
     * get:删除地址信息<br>
     */
    public static final String USER_ADDRESS_DELETE = "api/user/address_delete/";
    /**
     * get:删除地址信息<br>
     */
    public static final String USER_CARADDRESS = "api/user/caraddress/";

    //服务部分
    /**
     * get:查询分类列表信息 / 查询分类信息<br>
     */
    public static final String SERVICE_CATALOG = "api/service/catalog/";
    /**
     * get:查询物品列表信息 / 查询物品信息<br>
     */
    public static final String SERVICE_PRODUCT = "api/service/product/";
    /**
     * get:购物车信息 / 查询购物车物品信息<br>
     * post:添加购物车物品信息 / 修改购物车物品信息
     */
    public static final String SERVICE_ORDERCAR = "api/service/ordercar/";
    /**
     * get:删除购物车物品信息<br>
     */
    public static final String SERVICE_ORDERCAR_DELETE = "api/service/ordercar_delete/";
    /**
     * get:查询订单列表信息 / 查询订单信息<br>
     * post:生成订单
     */
    public static final String SERVICE_ORDER = "api/service/order/";
    /**
     * post:付款、完成、评论订单信息<br>
     */
    public static final String SERVICE_ORDER_METHOD = "api/service/order_method/";
    /**
     * get:删除订单信息<br>
     */
    public static final String SERVICE_ORDER_DELETE = "api/service/order_delete/";
    /**
     * get:查询违章记录列表信息 / 查询违章记录信息<br>
     */
    public static final String SERVICE_RULES = "api/service/rules/";
    /**
     * get:查询违章记录列表信息 / 查询资讯分类信息<br>
     */
    public static final String SERVICE_NEWSCATALOG = "api/service/newscatalog/";
    /**
     * get:查询滚动资讯列表信息<br>
     */
    public static final String SERVICE_NEWSINFO = "api/service/newsinfo/";
    /**
     * get:查询资讯列表信息 / 查询资讯信息<br>
     */
    public static final String SERVICE_NEWS = "api/service/news/";

    //维修部分
    /**
     * get:查询汽修厂列表信息 / 查询汽修厂信息<br>
     */
    public static final String MAINTAIN_GARAGE = "api/maintain/garage/";
    /**
     * get:查询机油列表信息 / 查询机油信息<br>
     */
    public static final String MAINTAIN_OIL = "api/maintain/oil/";
    /**
     * get:查询保养列表信息 / 查询保养信息 / 删除保养订单信息<br>
     * post:生成保养订单信息
     */
    public static final String MAINTAIN_UPKEEP = "api/maintain/upkeep/";
    /**
     * post:付款、评论保养订单信息<br>
     */
    public static final String MAINTAIN_UPKEEP_METHOD = "api/maintain/upkeep_method/";
    /**
     * get:查询维修列表信息 / 查询维修信息<br>
     * post:生成维修订单信息
     */
    public static final String MAINTAIN_MAINTAIN = "api/maintain/maintain/";
    /**
     * get:删除维修订单信息<br>
     */
    public static final String MAINTAIN_MAINTAIN_DELETE = "api/maintain/maintain_delete/";
    /**
     * post:付款、评论维修订单信息<br>
     */
    public static final String MAINTAIN_MAINTAIN_METHOD = "api/maintain/maintain_method/";
    /**
     * get:保养、维修订单列表信息<br>
     */
    public static final String MAINTAIN_LIST = "api/maintain/list/";

    //年检部分
    /**
     * get:查询年检站列表信息 / 查询年检站信息<br>
     */
    public static final String SURVEY_SURVEYSTATION = "api/survey/surveystation/";
    /**
     * get:查询套餐列表信息 / 查询套餐信息<br>
     */
    public static final String SURVEY_COMBO = "api/survey/combo/";
    /**
     * get:查询年检订单列表信息 / 查询年检订单信息<br>
     * post:提交年检订单信息
     */
    public static final String SURVEY_SURVEY = "api/survey/survey/";
    /**
     * get:删除年检订单信息<br>
     */
    public static final String SURVEY_SURVEY_DELETE = "api/survey/survey_delete/";
    /**
     * post:年检订单操作信息-查询费用、支付、确认还车、自驾完成<br>
     */
    public static final String SURVEY_SURVEY_METHOD = "api/survey/survey_method/";
    /**
     * post:自驾年检订单操作信息-取消、查询费用、支付、确认到达、完成订单
     */
    public static final String SURVEY_SURVEY_SELFMETHOD = "api/survey/survey_selfmethod/";
    /**
     * post:代驾年检订单操作信息-取消、查询费用、支付、失败支付、确认还车
     */
    public static final String SURVEY_SURVEY_BEHALFMETHOD = "api/survey/survey_behalfmethod/";

    //系统部分
    /**
     * get:服务首页图片信息<br>
     */
    public static final String SYSTEM_SERVICEIMG = "api/system/serviceimg/";
    /**
     * get:年检首页图片信息<br>
     */
    public static final String SYSTEM_SURVEYIMG = "api/system/surveyimg/";
}
