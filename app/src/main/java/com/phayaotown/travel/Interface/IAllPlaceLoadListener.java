package com.phayaotown.travel.Interface;

import java.util.List;

public interface IAllPlaceLoadListener {

    void onAllPlaceLoadSuccess(List<String> areaNameList);
    void onAllPlaceLoadFailed(String message);
}
