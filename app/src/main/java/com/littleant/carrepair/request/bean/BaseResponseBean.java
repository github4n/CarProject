package com.littleant.carrepair.request.bean;

import java.lang.reflect.Field;

public class BaseResponseBean {
	
	protected int code;
	protected String msg = "";

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
//	public String getCode() {
//        return code;
//    }
//
//    public void setCode(String code) {
//        this.code = code;
//    }

    public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public String toString() {
		Class c = this.getClass();
		StringBuilder sb = new StringBuilder();
		while (c != null && c != BaseResponseBean.class.getSuperclass()) {
			Field fields[] = c.getDeclaredFields();

			for (int i = 0; i < fields.length; i++) {
				try {
					fields[i].setAccessible(true);
					String name = fields[i].getName();
					if (name.equals("serialVersionUID")) {
						continue;
					}
					Object value = fields[i].get(this);
					sb.append(name + "=" + value + ",");
					//EfunLogUtil.logD("efun", name + "=" + value);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}

			c = c.getSuperclass();
		}
		return sb.toString();
	}
}
