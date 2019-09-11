package com.phayaotown.travel.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Food implements Parcelable {

   private String Name;
   private String Image_url;
   private String Menu_Recommend;
   private String Time;
   private String Information;
   private String Cost;
   private String MenuID;

   public Food() {

    }

    public Food(String name, String image_url, String menu_Recommend, String time, String information, String cost, String menuID) {
        Name = name;
        Image_url = image_url;
        Menu_Recommend = menu_Recommend;
        Time = time;
        Information = information;
        Cost = cost;
        MenuID = menuID;
    }

    protected Food(Parcel in) {
        Name = in.readString();
        Image_url = in.readString();
        Menu_Recommend = in.readString();
        Time = in.readString();
        Information = in.readString();
        Cost = in.readString();
        MenuID = in.readString();
    }

    public static final Creator<Food> CREATOR = new Creator<Food>() {
        @Override
        public Food createFromParcel(Parcel in) {
            return new Food(in);
        }

        @Override
        public Food[] newArray(int size) {
            return new Food[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Name);
        dest.writeString(Image_url);
        dest.writeString(Menu_Recommend);
        dest.writeString(Time);
        dest.writeString(Information);
        dest.writeString(Cost);
        dest.writeString(MenuID);
    }

}
