package com.phayaotown.travel.Interface;

import com.phayaotown.travel.model.Banner;

import java.util.List;

public interface IPlaceBookLoadListener {

    void onPlaceBookLoadSuccess(List<Banner> banners);
    void onPlaceBookFailed(String message);
}
