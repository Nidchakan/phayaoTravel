package com.phayaotown.travel.model;

public class Category {

    private String Name;
    private String Image_url;

    public Category(){

    }

    public Category(String name, String image_url) {
        Name = name;
        Image_url = image_url;
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
}
