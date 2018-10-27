package com.littleant.carrepair.request.bean.car.carbrand;

import android.os.Parcel;
import android.os.Parcelable;

import com.littleant.carrepair.request.bean.BaseResponseBean;

public class CarBaseBean extends BaseResponseBean implements Parcelable {
    private int id;
    private String name = "";
    private int year;

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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int i) {
        out.writeInt(id);
        out.writeString(name);
        out.writeInt(year);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {

        @Override
        public Object createFromParcel(Parcel parcel) {
            return new CarBaseBean(parcel);
        }

        @Override
        public Object[] newArray(int i) {
            return new CarBaseBean[i];
        }
    };

    private CarBaseBean(Parcel parcelable) {
        this.id = parcelable.readInt();
        this.name = parcelable.readString();
        this.year = parcelable.readInt();
    }
}
