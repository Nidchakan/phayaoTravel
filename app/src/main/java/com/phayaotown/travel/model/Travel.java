package com.phayaotown.travel.model;

public class Travel {

    private String Name;
    private String Image_url;
    private String History;
    private String Information;
    private String Time;
    private String Place;
    private String Event;
    private String TravelID;
    private String Latlng;

    public Travel(){

    }

    public Travel(String name, String image_url, String history, String information, String time, String place, String event, String travelID, String latlng) {
        Name = name;
        Image_url = image_url;
        History = history;
        Information = information;
        Time = time;
        Place = place;
        Event = event;
        TravelID = travelID;
        Latlng = latlng;
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

    public String getHistory() {
        return History;
    }

    public void setHistory(String history) {
        History = history;
    }

    public String getInformation() {
        return Information;
    }

    public void setInformation(String information) {
        Information = information;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getPlace() {
        return Place;
    }

    public void setPlace(String place) {
        Place = place;
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

    public String getLatlng() {
        return Latlng;
    }

    public void setLatlng(String latlng) {
        Latlng = latlng;
    }
}
