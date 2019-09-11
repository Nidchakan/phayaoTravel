package com.phayaotown.travel.Fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.phayaotown.travel.Fragment.Detail.Food_detail;
import com.phayaotown.travel.Interface.ItemClickListener;
import com.phayaotown.travel.ViewHolder.FoodViewHolder;
import com.phayaotown.travel.Common.SpaceItemDecoration;
import com.phayaotown.travel.Interface.IBrachAllPlaceLoadListener;
import com.phayaotown.travel.adapter.MyPlaceAdapter;
import com.phayaotown.travel.model.Place;
import com.phayaotown.travel.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import dmax.dialog.SpotsDialog;

public class PlaceFragment extends Fragment {//implements IBrachAllPlaceLoadListener {

    //Variable
    /*CollectionReference allPlaceRef;
    CollectionReference branchRef;
    FirebaseDatabase database;
    DatabaseReference placeId;

    IBrachAllPlaceLoadListener iBrachAllPlaceLoadListener;

    RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<Place, FoodViewHolder> adapter;
    String id = "";


    @BindView(R.id.recycler_place_fragment)
    RecyclerView rvPlace;
    @BindView(R.id.searchBar)
    MaterialSearchBar searchBar;
    @BindView(R.id.btn_travel)
    Button travel;
    @BindView(R.id.btn_eat)
    Button eat;
    @BindView(R.id.btn_rest)
    Button rest;
    @BindView(R.id.btn_all)
    Button all;

    @OnClick(R.id.btn_travel)
    void travel(){
        ShowTravel();
    }

    @OnClick(R.id.btn_eat)
    void eat(){
        ShowEat();
    }

    @OnClick(R.id.btn_rest)
    void rest(){
        ShowRest();
    }

    @OnClick(R.id.btn_all)
    void all(){
        loadAllPlace();
    }



    Unbinder unbinder;

    AlertDialog dialog;


    static PlaceFragment instance;

    public static PlaceFragment getInstance() {
        if (instance == null)
            instance = new PlaceFragment();
        return instance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        allPlaceRef = FirebaseFirestore.getInstance().collection("Place");

        //iAllPlaceLoadListener = this;
        iBrachAllPlaceLoadListener = this;

        dialog = new SpotsDialog.Builder().setContext(getActivity()).build();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View itemView = inflater.inflate(R.layout.fragment_place, container, false);
        unbinder = ButterKnife.bind(this, itemView);

        initView();
        //loadAllPlace();
        //loadAllType("Show All");

        loadAllPlace();


        return itemView;
    }

    private void loadAllPlace() {

            dialog.show();

            branchRef = FirebaseFirestore.getInstance().collection("Place").document("Show All").collection("Branch");

            branchRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    List<Place> list = new ArrayList<>();
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {

                            Place place = documentSnapshot.toObject(Place.class);
                            place.setPlaceId(documentSnapshot.getId());
                            list.add(place);
                        }
                        iBrachAllPlaceLoadListener.onBranchLoadSuccess(list);
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    iBrachAllPlaceLoadListener.onBranchLoadFailed(e.getMessage());
                }
            });

    }

    private void ShowTravel() {
        dialog.show();

        branchRef = FirebaseFirestore.getInstance().collection("Place").document("Tourist Attraction").collection("Branch");
        branchRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<Place> list = new ArrayList<>();
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                        Place place = documentSnapshot.toObject(Place.class);
                        place.setPlaceId(documentSnapshot.getId());
                        list.add(place);
                    }
                    iBrachAllPlaceLoadListener.onBranchLoadSuccess(list);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                iBrachAllPlaceLoadListener.onBranchLoadFailed(e.getMessage());
            }
        });
    }

    public void ShowEat(){
        dialog.show();

        branchRef = FirebaseFirestore.getInstance().collection("Place").document("Restaurant & Cafe'").collection("Branch");
        branchRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<Place> list = new ArrayList<>();
                if(task.isSuccessful()) {
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        Place place = documentSnapshot.toObject(Place.class);
                        place.setPlaceId(documentSnapshot.getId());
                        list.add(place);
                    }
                    //iBrachAllPlaceLoadListener.onBranchLoadSuccess(list);
                    adapter = new FirebaseRecyclerAdapter<Place, FoodViewHolder>(Place.class,
                            R.layout.layout_place_placefragment,
                            FoodViewHolder.class,
                            placeId) {
                        @Override
                        protected void populateViewHolder(FoodViewHolder viewHolder, Place model, int position) {
                            viewHolder.food_name.setText(model.getName());
                            Picasso.get().load(model.getPicture())
                                    .into(viewHolder.food_image);

                            final Place local = model;
                            viewHolder.setItemClickListener(new ItemClickListener() {
                                @Override
                                public void onClick(View view, int position, boolean isLongClick) {
                                    Intent placeDetail = new Intent(getActivity(), Food_detail.class);
                                    startActivity(placeDetail);
                                }
                            });

                        }
                    };
                }

                    //Set Adapter
                    Log.d("TAG",""+adapter.getClass());
                    rvPlace.setAdapter(adapter);


                }


        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                iBrachAllPlaceLoadListener.onBranchLoadFailed(e.getMessage());
            }
        });
    }

    public void ShowRest(){
        dialog.show();

        branchRef = FirebaseFirestore.getInstance().collection("Place").document("Rest & Hotel").collection("Branch");
        branchRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<Place> list = new ArrayList<>();
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot documentSnapshot :task.getResult()){
                        Place place = documentSnapshot.toObject(Place.class);
                        place.setPlaceId(documentSnapshot.getId());
                        list.add(place);
                    }
                    iBrachAllPlaceLoadListener.onBranchLoadSuccess(list);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                iBrachAllPlaceLoadListener.onBranchLoadFailed(e.getMessage());
            }
        });
    }

    private void ShowTravelandEat() {
        dialog.show();

        branchRef = FirebaseFirestore.getInstance().collection("Place").document("Tourist & Eat").collection("Branch");
        branchRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<Place> list = new ArrayList<>();
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot documentSnapshot :task.getResult()){
                        Place place = documentSnapshot.toObject(Place.class);
                        place.setPlaceId(documentSnapshot.getId());
                        list.add(place);
                    }
                    iBrachAllPlaceLoadListener.onBranchLoadSuccess(list);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                iBrachAllPlaceLoadListener.onBranchLoadFailed(e.getMessage());
            }
        });
    }

    private void ShowEatandRest() {
        dialog.show();

        branchRef = FirebaseFirestore.getInstance().collection("Place").document("Eat & Rest").collection("Branch");
        branchRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<Place> list = new ArrayList<>();
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot documentSnapshot :task.getResult()){
                        Place place = documentSnapshot.toObject(Place.class);
                        place.setPlaceId(documentSnapshot.getId());
                        list.add(place);
                    }
                    iBrachAllPlaceLoadListener.onBranchLoadSuccess(list);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                iBrachAllPlaceLoadListener.onBranchLoadFailed(e.getMessage());
            }
        });
    }

    private void ShowTravelAndRest() {
        dialog.show();

        branchRef = FirebaseFirestore.getInstance().collection("Place").document("Tourist & Rest").collection("Branch");
        branchRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<Place> list = new ArrayList<>();
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot documentSnapshot :task.getResult()){
                        Place place = documentSnapshot.toObject(Place.class);
                        place.setPlaceId(documentSnapshot.getId());
                        list.add(place);
                    }
                    iBrachAllPlaceLoadListener.onBranchLoadSuccess(list);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                iBrachAllPlaceLoadListener.onBranchLoadFailed(e.getMessage());
            }
        });
    }


    private void initView() {
        rvPlace.setHasFixedSize(true);
        rvPlace.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        rvPlace.addItemDecoration(new SpaceItemDecoration(4));
    }

    @Override
    public void onBranchLoadSuccess(final List<Place> placeList) {

        MyPlaceAdapter adapter = new MyPlaceAdapter(getActivity(),placeList);
        rvPlace.setAdapter(adapter);
        dialog.dismiss();

    }

    @Override
    public void onBranchLoadFailed(String message) {
        Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
        dialog.dismiss();
    }
}*/
}
