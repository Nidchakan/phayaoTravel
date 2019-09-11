package com.phayaotown.travel.model;

public class Rest {

    private String Name;
    private String Image_url;
    private String Time;
    private String Check_In;
    private String latlng;
    private String Place;
    private String Phone;

    public Rest() {
    }

    public Rest(String name, String image_url, String time, String check_In, String latlng, String place, String phone) {
        Name = name;
        Image_url = image_url;
        Time = time;
        Check_In = check_In;
        this.latlng = latlng;
        Place = place;
        Phone = phone;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage_url() {
        return Image_url;
    }

    public void setImage_url(String image_Url) {
        Image_url = image_Url;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getCheck_In() {
        return Check_In;
    }

    public void setCheck_In(String check_In) {
        Check_In = check_In;
    }

    public String getLatlng() {
        return latlng;
    }

    public void setLatlng(String latlng) {
        this.latlng = latlng;
    }

    public String getPlace() {
        return Place;
    }

    public void setPlace(String place) {
        Place = place;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

}
