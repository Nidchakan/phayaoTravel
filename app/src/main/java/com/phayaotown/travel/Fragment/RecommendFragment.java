package com.phayaotown.travel.Fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.phayaotown.travel.adapter.RecommendAdapter;
import com.phayaotown.travel.Common.SpaceItemDecoration;
import com.phayaotown.travel.Interface.IAllRecommendListener;
import com.phayaotown.travel.model.Trip;
import com.phayaotown.travel.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dmax.dialog.SpotsDialog;

public class RecommendFragment extends Fragment implements IAllRecommendListener {

    CollectionReference all_recommend;
    AlertDialog dialog;

    IAllRecommendListener iAllRecommendListener;

    Unbinder unbinder;

    @BindView(R.id.recycler_recommend)
    RecyclerView recycler_recommmend;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        all_recommend = FirebaseFirestore.getInstance().collection("RecommendTrip").document("Recommend Trip")
                .collection("Branch");

        iAllRecommendListener = this;

        dialog = new SpotsDialog.Builder().setContext(getActivity()).build();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View itemView =  inflater.inflate(R.layout.fragment_recommend,container,false);

        unbinder = ButterKnife.bind(this,itemView);

        initView();
        loadRecommend();

        return itemView;


    }

    private void initView() {

        recycler_recommmend.setHasFixedSize(true);
        recycler_recommmend.setLayoutManager(new GridLayoutManager(getActivity(),1));
        recycler_recommmend.addItemDecoration(new SpaceItemDecoration(4));
    }

    private void loadRecommend() {

        dialog.show();

        all_recommend = FirebaseFirestore.getInstance().collection("RecommendTrip").document("Recommend Trip")
                .collection("Branch");

        all_recommend.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<Trip> tripList = new ArrayList<>();
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot queryDocumentSnapshot :task.getResult()){
                        Trip trip = queryDocumentSnapshot.toObject(Trip.class);
                        trip.setTripId(queryDocumentSnapshot.getId());
                        tripList.add(trip);
                    }
                    iAllRecommendListener.iAllRecommendLoadSuccess(tripList);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                iAllRecommendListener.iAllRecommendLoadFailed(e.getMessage());
            }
        });

    }

    @Override
    public void iAllRecommendLoadSuccess(List<Trip> tripList) {

        RecommendAdapter adapter = new RecommendAdapter(getActivity(),tripList);
        recycler_recommmend.setAdapter(adapter);

        dialog.dismiss();

    }

    @Override
    public void iAllRecommendLoadFailed(String message) {

        Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
        dialog.dismiss();

    }
}
