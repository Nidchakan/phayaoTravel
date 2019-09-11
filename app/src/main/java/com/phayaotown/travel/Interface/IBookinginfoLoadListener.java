package com.phayaotown.travel.Interface;

import com.phayaotown.travel.model.ScheduleInformation;

public interface IBookinginfoLoadListener {

    void  onBookingLoadEmpty();
    void onBookingLoadSuccess(ScheduleInformation scheduleInformation);
    void onBookingInfoLoadFailed(String message);
}
