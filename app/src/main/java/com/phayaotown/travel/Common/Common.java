package com.phayaotown.travel.Common;


import com.phayaotown.travel.model.Food;
import com.phayaotown.travel.model.Place;
import com.phayaotown.travel.model.User;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Common {


    public static final String KEY_ENABLE_BUTTON_NEXT = "ENABLE_BUTTON_NEXT";
    public static final String KEY_PLACE = "KEY_PLACE";
    public static final String KEY_STEP = "STEP";
    public static final int TIME_TOTAL = 48;
    public static final Object DISABLE_TAG = "DISABLE";
    public static final String KEY_TIME_SLOT = "TIME_SLOT";
    public static final String KEY_DETAIL = "DETAIL";
    public static final String KEY_FOOD = "KEY_FOOD";
    public static String IS_LOGIN = "IsLogin";
    public static User currentUser;
    public static Place currentPlace;
    public static Food currentFood;
    public static int step = 0;//Init first step
    public static int currentTimeSlot = -1;
    public static Calendar bookingDate = Calendar.getInstance();
    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd_MM_yyyy");


    public static String convertTimeSlotToString(int slot) {
        switch (slot){
            case 0 :
                return "00:00 - 00:30";
            case 1 :
                return "00:30 - 01:00";
            case 2 :
                return "01:00 - 01:30";
            case 3 :
                return "01:30 - 02:00";
            case 4:
                return "02:00 - 02:30";
            case 5 :
                return "02:30 - 03:00";
            case 6 :
                return "03:00 - 03:30";
            case 7 :
                return "03:30 - 04:00";
            case 8 :
                return "04:00 - 04:30";
            case 9 :
                return "04:30 - 05:00";
            case 10:
                return "05:00 - 05:30";
            case 11 :
                return "05:30 - 06:00";
            case 12 :
                return "06:00 - 06:30";
            case 13 :
                return "06:30 - 07:00";
            case 14:
                return "07:00 - 07:30";
            case 15 :
                return "07:30 - 08:00";
            case 16 :
                return "08:00 - 08:30";
            case 17 :
                return "08:30 - 09:00";
            case 18 :
                return "09:00 - 09:30";
            case 19 :
                return "09:30 - 10:00";
            case 20:
                return "10:00 - 10:30";
            case 21 :
                return "10:30 - 11:00";
            case 22 :
                return "11:00 - 11:30";
            case 23 :
                return "11:30 - 12:00";
            case 24:
                return "12:00 - 12:30";
            case 25 :
                return "12:30 - 13:00";
            case 26 :
                return "13:00 - 13:30";
            case 27 :
                return "13:30 - 14:00";
            case 28 :
                return "14:00 - 14:30";
            case 29 :
                return "14:30 - 15:00";
            case 30:
                return "15:00 - 15:30";
            case 31 :
                return "15:30 - 16:00";
            case 32 :
                return "16:00 - 16:30";
            case 33 :
                return "16:30 - 17:00";
            case 34:
                return "17:00 - 17:30";
            case 35 :
                return "17:30 - 18:00";
            case 36 :
                return "18:00 - 18:30";
            case 37 :
                return "18:30 - 19:00";
            case 38 :
                return "19:00 - 19:30";
            case 39 :
                return "19:30 - 20:00";
            case 40:
                return "20:00 - 20:30";
            case 41 :
                return "20:30 - 21:00";
            case 42 :
                return "21:00 - 21:30";
            case 43 :
                return "21:30 - 22:00";
            case 44:
                return "22:00 - 22:30";
            case 45 :
                return "22:30 - 23:00";
            case 46 :
                return "23:00 - 23:30";
            case 47 :
                return "23:30 - 00:00";
            default :
                return "new Day!!";
        }
    }
}
