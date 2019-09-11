package com.phayaotown.travel.Interface;

import com.phayaotown.travel.model.Banner;

import java.util.List;

public interface IBannerLoadListener {

    void onBannerLoadSuccess(List<Banner> banners);
    void onBannerLoadFailed(String message);
}
