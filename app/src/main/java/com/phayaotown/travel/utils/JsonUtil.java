package com.phayaotown.travel.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import timber.log.Timber;

public class JsonUtil {

    public <T> T getJsonToModel(String fileName, Class<T> className) {
        String json = getJsonFromResources(fileName);
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(json, className);
    }

    private String getJsonFromResources(String fileName) {
        BufferedReader reader = null;
        StringBuilder mLineResult = new StringBuilder();
        try {
            reader = new BufferedReader(
                    new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream(fileName), "UTF-8"));

            String mLine;
            while ((mLine = reader.readLine()) != null) {
                mLineResult.append(mLine);
            }
        } catch (IOException ignored) {
            Timber.e(ignored);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ignored) {
                    Timber.e(ignored);
                }
            }
        }
        return mLineResult.toString();
    }
}
