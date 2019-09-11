package com.phayaotown.travel.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.phayaotown.travel.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class DetailFragment extends Fragment {

    Unbinder unbinder;

    static DetailFragment instance;
    public static DetailFragment getInstance(){
        if(instance == null)
            instance = new DetailFragment();
        return instance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View itemView = inflater.inflate(R.layout.fragment_place, container, false);
        unbinder = ButterKnife.bind(this, itemView);

        //loadAllPlace();
        //loadAllType("Show All");

        ShowDetail();

        return itemView;
    }

    private void ShowDetail() {
        Toast.makeText(getContext(),"Show Detail",Toast.LENGTH_SHORT).show();
    }
}
