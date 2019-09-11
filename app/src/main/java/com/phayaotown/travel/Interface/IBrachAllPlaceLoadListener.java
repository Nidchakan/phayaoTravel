package com.phayaotown.travel.Interface;

import com.phayaotown.travel.model.Place;

import java.util.List;

public interface IBrachAllPlaceLoadListener {

    void onBranchLoadSuccess(List<Place> placeList);
    void onBranchLoadFailed(String message);
}
