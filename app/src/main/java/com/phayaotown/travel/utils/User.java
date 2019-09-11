package com.phayaotown.travel.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class User {

    private static User instance;
    private static final String SPF_NAME = "user_data";
    private static final String KEY_LANGUAGE = "key_language";
    private static final String KEY_SELECTED = "key_selected";
    private final SharedPreferences sharedPref;

    private User(Context context) {
        sharedPref = context.getSharedPreferences(SPF_NAME, Context.MODE_PRIVATE);
    }

    public static User getInstance(Context context) {
        if (instance == null) {
            instance = new User(context);
        }
        return instance;
    }

    public void setKeyLanguage(String language) {
        sharedPref.edit().putString(KEY_LANGUAGE, language)
                .apply();
    }

    public String getKeyLanguage() {
        return sharedPref.getString(KEY_LANGUAGE, "en");
    }

    public void setKeySelected(boolean keySelected) {
        sharedPref.edit().putBoolean(KEY_SELECTED, keySelected)
                .apply();
    }

    public boolean isSelected() {
        return sharedPref.getBoolean(KEY_SELECTED, false);
    }
}
