package com.phayaotown.travel;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.phayaotown.travel.Fragment.BookingDateFragment;
import com.phayaotown.travel.Fragment.ScheduleStep1Fragment;


public class MyViewPagerAdapter extends FragmentPagerAdapter {


    public MyViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:
                return ScheduleStep1Fragment.getInstance();
            case 1 :
                return BookingDateFragment.getInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
