package com.phayaotown.travel.model;

public class Type_room {
    private String Cost;
    private String Image_url;
    private String Name;
    private String Information;


    public Type_room() {
    }

    public Type_room(String cost, String image_url, String name, String information) {
        Cost = cost;
        Image_url = image_url;
        Name = name;
        Information = information;
    }

    public String getCost() {
        return Cost;
    }

    public void setCost(String cost) {
        Cost = cost;
    }

    public String getImage_url() {
        return Image_url;
    }

    public void setImage_url(String image_url) {
        Image_url = image_url;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getInformation() {
        return Information;
    }

    public void setInformation(String information) {
        Information = information;
    }
}
