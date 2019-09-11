package com.phayaotown.travel.Interface;

import com.phayaotown.travel.model.TimeSlot;

import java.util.List;

public interface ITimeSlotLoadListener {

    void  onTimeSlotLoadSuccess(List<TimeSlot> timeSlotsList);

    void onTimeSlotLoadFailed(String message);

    void onTimeSlotLoadEmpty();


}
