package com.phayaotown.travel.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.accountkit.AccountKit;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.phayaotown.travel.Common.Common;
import com.phayaotown.travel.Interface.IBannerLoadListener;
import com.phayaotown.travel.Interface.IPlaceBookLoadListener;
import com.phayaotown.travel.R;
import com.phayaotown.travel.RecommendActivity;
import com.phayaotown.travel.ScheduleActivity;
import com.phayaotown.travel.Services.PicassoImageLoadingServices;
import com.phayaotown.travel.TimeTableActivity;
import com.phayaotown.travel.adapter.HomeSlideAdapter;
import com.phayaotown.travel.model.Banner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import ss.com.bannerslider.Slider;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements IBannerLoadListener, IPlaceBookLoadListener {

    private Unbinder unbinder;

    LocalBroadcastManager localBroadcastManager;

    @BindView(R.id.layout_user_information)
    LinearLayout layout_user_information;
    @BindView(R.id.txt_user_name)
    TextView txt_user_name;
    @BindView(R.id.banner_slider)
    Slider banner_slider;
    @BindView(R.id.recycler_place)
    RecyclerView recycler_place;
    @BindView(R.id.card_view_plan)
    CardView card_view_plan;
    @BindView(R.id.card_booking_info)
    CardView card_booking_info;
    @BindView(R.id.txt_time)
    TextView txt_time;
    @BindView(R.id.first_place)
    TextView first_place;
    @BindView(R.id.txt_time_remain)
    TextView txt_time_remain;

    @OnClick(R.id.card_view_plan)
    void schedule(){
        startActivity(new Intent(getActivity(), ScheduleActivity.class));
    }

    @OnClick(R.id.card_view_recommend)
    void recommend(){
        startActivity(new Intent(getActivity(), RecommendActivity.class));
    }

    @OnClick(R.id.card_view_timetable)
    void timeTable(){ startActivity(new Intent(getActivity(), TimeTableActivity.class));}

    //Firestore
    CollectionReference bannerRef,placeref;

    //Interface
    IBannerLoadListener iBannerLoadListener;
    IPlaceBookLoadListener iPlaceBookLoadListener;



    public HomeFragment() {
        bannerRef = FirebaseFirestore.getInstance().collection("Banner");
        placeref = FirebaseFirestore.getInstance().collection("PlaceBook");
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        unbinder = ButterKnife.bind(this,view);

        //Init
        Slider.init(new PicassoImageLoadingServices());
        iBannerLoadListener = this;
        iPlaceBookLoadListener = this;

        //Check is logged
        if(AccountKit.getCurrentAccessToken() != null){
            setUserInformation();
            loadBanner();
            loadPlaceLook();
        }
        return view;
    }

    private void loadPlaceLook() {

        placeref.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        List<Banner> placebook = new ArrayList<>();
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot placeBookSnapshot:task.getResult()){
                                Banner banner = placeBookSnapshot.toObject(Banner.class);
                                placebook.add(banner);
                            }
                            iPlaceBookLoadListener.onPlaceBookLoadSuccess(placebook);
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                iPlaceBookLoadListener.onPlaceBookFailed(e.getMessage());
            }
        });
    }


    private void loadBanner() {

        bannerRef = FirebaseFirestore.getInstance().collection("Banner");
        bannerRef.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        List<Banner> banners = new ArrayList<>();
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot bannerSnapshot:task.getResult()){
                                Banner banner = bannerSnapshot.toObject(Banner.class);
                                banners.add(banner);
                            }
                            iBannerLoadListener.onBannerLoadSuccess(banners);
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                iBannerLoadListener.onBannerLoadFailed(e.getMessage());
            }
        });
    }

    private void setUserInformation() {
        layout_user_information.setVisibility(View.VISIBLE);
        txt_user_name.setText(Common.currentUser.getName());
    }

    @Override
    public void onBannerLoadSuccess(List<Banner> banners) {

        banner_slider.setAdapter(new HomeSlideAdapter(banners));

    }

    @Override
    public void onBannerLoadFailed(String message) {

        Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onPlaceBookLoadSuccess(List<Banner> banners) {
        recycler_place.setHasFixedSize(true);
        recycler_place.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

    @Override
    public void onPlaceBookFailed(String message) {

        Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
    }

}
