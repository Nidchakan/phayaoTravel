package com.phayaotown.travel.Interface;

import com.phayaotown.travel.model.Place;

import java.util.List;

public interface IBookingLoadListener {

    void iBookingLoadSuccess(List<Place> booking);
    void iBookingLoadFailed(String message);
}
