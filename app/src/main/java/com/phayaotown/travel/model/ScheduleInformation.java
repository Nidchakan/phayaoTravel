package com.phayaotown.travel.model;


import com.google.firebase.Timestamp;

public class ScheduleInformation {

    private String time,placeName;
    private Long slot;
    private Timestamp timestamp;

    public ScheduleInformation() {
    }

    public ScheduleInformation(String time, String placeName, Long slot, Timestamp timestamp) {
        this.time = time;
        this.placeName = placeName;
        this.slot = slot;
        this.timestamp = timestamp;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public Long getSlot() {
        return slot;
    }

    public void setSlot(Long slot) {
        this.slot = slot;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }



}
