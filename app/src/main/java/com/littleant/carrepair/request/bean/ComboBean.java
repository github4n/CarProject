package com.littleant.carrepair.request.bean;

import java.io.Serializable;
import java.util.List;

public class ComboBean extends BaseResponseBean {
    private int id;
    private String create_time = "";
    private String update_time = "";
    private String name = "";
    private String detail = "";
    private List<ComboItemSet> comboitem_set;
}


