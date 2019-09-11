package com.phayaotown.travel.Interface;


import com.phayaotown.travel.model.Trip;

import java.util.List;

public interface IAllRecommendListener {

    void iAllRecommendLoadSuccess(List<Trip> tripList);
    void iAllRecommendLoadFailed(String message);
}
