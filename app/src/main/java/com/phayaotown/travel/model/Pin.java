package com.phayaotown.travel.model;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class Pin implements Parcelable {

    private String pinId,Name,Pin_Image_url;
    private Double latitude,longtitude;

    public Pin(){

    }

    public Pin(String pinId, String name, String pin_Image_url, Double latitude, Double longtitude) {
        this.pinId = pinId;
        Name = name;
        Pin_Image_url = pin_Image_url;
        this.latitude = latitude;
        this.longtitude = longtitude;
    }

    public String getPinId() {
        return pinId;
    }

    public void setPinId(String pinId) {
        this.pinId = pinId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPin_Image_url() {
        return Pin_Image_url;
    }

    public void setPin_Image_url(String pin_Image_url) {
        Pin_Image_url = pin_Image_url;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(Double longtitude) {
        this.longtitude = longtitude;
    }

    protected Pin(Parcel in) {

        Name = in.readString();
        pinId = in.readString();
        Pin_Image_url = in.readString();
        latitude = in.readDouble();
        longtitude = in.readDouble();
    }

    public static final Creator<Pin> CREATOR = new Creator<Pin>() {
        @Override
        public Pin createFromParcel(Parcel in) {
            return new Pin(in);
        }

        @Override
        public Pin[] newArray(int size) {
            return new Pin[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(Name);
        dest.writeString(Pin_Image_url);
        dest.writeString(pinId);
        dest.writeDouble(latitude);
        dest.writeDouble(longtitude);
    }
}
