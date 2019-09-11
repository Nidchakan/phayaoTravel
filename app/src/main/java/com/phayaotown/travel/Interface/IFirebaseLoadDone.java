package com.phayaotown.travel.Interface;

import com.phayaotown.travel.model.Type_room;

import java.util.List;

public interface IFirebaseLoadDone {

    void onFirebaseLoadSuccess(List<Type_room> typeRoomList);
    void onFirebaseLoadFailed (String message);
}
