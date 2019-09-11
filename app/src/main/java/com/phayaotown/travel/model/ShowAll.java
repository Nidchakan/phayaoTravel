package com.phayaotown.travel.model;

public class ShowAll {

    private String Name;
    private String Image_url;
    private String Menu_Recommend;
    private String Time;
    private String Information;
    private String Cost;
    private String MenuID;
    private String Check_In;
    private String latlng;
    private String Place;
    private String Phone;
    private String History;
    private String Event;
    private String TravelID;
    private double[] location;

    public ShowAll() {
    }

    public ShowAll(String name, String image_url, String menu_Recommend, String time, String information, String cost, String menuID, String check_In, String latlng, String place, String phone, String history, String event, String travelID, double[] location) {
        Name = name;
        Image_url = image_url;
        Menu_Recommend = menu_Recommend;
        Time = time;
        Information = information;
        Cost = cost;
        MenuID = menuID;
        Check_In = check_In;
        this.latlng = latlng;
        Place = place;
        Phone = phone;
        History = history;
        Event = event;
        TravelID = travelID;
        this.location = location;
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

    public void setImage_url(String image_url) {
        Image_url = image_url;
    }

    public String getMenu_Recommend() {
        return Menu_Recommend;
    }

    public void setMenu_Recommend(String menu_Recommend) {
        Menu_Recommend = menu_Recommend;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getInformation() {
        return Information;
    }

    public void setInformation(String information) {
        Information = information;
    }

    public String getCost() {
        return Cost;
    }

    public void setCost(String cost) {
        Cost = cost;
    }

    public String getMenuID() {
        return MenuID;
    }

    public void setMenuID(String menuID) {
        MenuID = menuID;
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

    public String getHistory() {
        return History;
    }

    public void setHistory(String history) {
        History = history;
    }

    public String getEvent() {
        return Event;
    }

    public void setEvent(String event) {
        Event = event;
    }

    public String getTravelID() {
        return TravelID;
    }

    public void setTravelID(String travelID) {
        TravelID = travelID;
    }

    public double[] getLocation() {
        return location;
    }

    public void setLocation(double[] location) {
        this.location = location;
    }
}
