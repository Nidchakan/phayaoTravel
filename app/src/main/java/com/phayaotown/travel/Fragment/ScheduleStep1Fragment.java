package com.phayaotown.travel.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.phayaotown.travel.Common.Common;
import com.phayaotown.travel.Common.SpaceItemDecoration;
import com.phayaotown.travel.Fragment.Detail.Food_detail;
import com.phayaotown.travel.Fragment.List.PlaceActivity;
import com.phayaotown.travel.Interface.IBrachAllPlaceLoadListener;
import com.phayaotown.travel.Interface.IRecyclerItemSelectedListener;
import com.phayaotown.travel.Interface.ItemClickListener;
import com.phayaotown.travel.R;
import com.phayaotown.travel.ViewHolder.FoodViewHolder;
import com.phayaotown.travel.ViewHolder.MenuViewHolder;
import com.phayaotown.travel.adapter.MyPlaceAdapter;
import com.phayaotown.travel.model.Food;
import com.phayaotown.travel.model.Place;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dmax.dialog.SpotsDialog;

public class ScheduleStep1Fragment extends Fragment {

    FirebaseDatabase database;
    DatabaseReference list;
    LocalBroadcastManager localBroadcastManager;

    Button btnEat, btnRest, btnTravel, btnAll;
    MaterialSearchBar searchBar;

    @BindView(R.id.recycler_place_fragment)
    RecyclerView recyclerView;

    RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<Place, MenuViewHolder> adapter;

    AlertDialog dialog;
    Unbinder unbinder;

    List<Food> placeList;
    Context context;
    List<CardView> cardViewList;


    static ScheduleStep1Fragment instance;

    public static ScheduleStep1Fragment getInstance() {
        if (instance == null)
            instance = new ScheduleStep1Fragment();
        return instance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        database = FirebaseDatabase.getInstance();
        list = database.getReference("Food");

        dialog = new SpotsDialog.Builder().setContext(getActivity()).build();

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View itemView = inflater.inflate(R.layout.fragment_place, container, false);
        unbinder = ButterKnife.bind(this, itemView);


        initView();
        loadAllPlace();

        btnAll = (Button)itemView.findViewById(R.id.btn_all);
        btnTravel = (Button) itemView.findViewById(R.id.btn_travel);
        btnRest = (Button)itemView.findViewById(R.id.btn_rest);
        btnEat = (Button) itemView.findViewById(R.id.btn_eat);

        btnAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadAllPlace();
            }
        });

        btnTravel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadTravel();
            }
        });

        btnEat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadEat();
            }
        });

        btnRest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadrest();
            }
        });

        adappter(context,placeList);
        cardViewList = new ArrayList<>();
        //loadAllPlace();
        //loadAllType("Show All");

        //loadAllPlace();

        return itemView;
    }


    private void adappter(Context context, List<Food> placeList) {

            this.context = context;
            this.placeList = placeList;
            localBroadcastManager = LocalBroadcastManager.getInstance(context);
    }

    private void initView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.addItemDecoration(new SpaceItemDecoration(4));
    }


    private void loadAllPlace() {

        dialog.show();

        database = FirebaseDatabase.getInstance();
        list = database.getReference("ShowAll");


        adapter = new FirebaseRecyclerAdapter<Place, MenuViewHolder>(Place.class, R.layout.layout_place, MenuViewHolder.class, list) {
            @Override
            protected void populateViewHolder(final MenuViewHolder viewHolder, final Place model, int position) {


                viewHolder.txtMenuName.setText(model.getName());
                Picasso.get().load(model.getImage_url()).into(viewHolder.imageView);
                viewHolder.txtMenuTime.setText(model.getTime());

                if(!cardViewList.contains(viewHolder.card_place))
                    cardViewList.add(viewHolder.card_place);


                final Place clickItem = model;
                viewHolder.setItemClickListener(new IRecyclerItemSelectedListener() {
                    @Override
                    public void onItemSelectedListener(View view, int pos) {


                        for(CardView cardView : cardViewList)
                            cardView.setCardBackgroundColor(getContext().getResources().getColor(android.R.color.white));

                        viewHolder.card_place.setCardBackgroundColor(getContext().getResources().getColor(android.R.color.holo_orange_dark));
                        viewHolder.txtMenuName.setTextColor(getContext().getResources().getColor(R.color.bpTransparent_black));
                        viewHolder.txtMenuTime.setTextColor(getContext().getResources().getColor(R.color.bpTransparent_black));

                        //send BroadCast to tell Schedule Activity enable Next Button
                        adappter(getActivity(),placeList);

                        Intent intent = new Intent(Common.KEY_ENABLE_BUTTON_NEXT);
                        intent.putExtra(Common.KEY_PLACE,model);
                        //intent.putExtra(Common.KEY_PLACE ,placeList.get(pos));
                        intent.putExtra(Common.KEY_STEP,1);
                        localBroadcastManager.sendBroadcast(intent);
                    }
                });


            }
        };
        recyclerView.setAdapter(adapter);
        dialog.dismiss();
    }

    private void loadTravel() {

        dialog.show();

        database = FirebaseDatabase.getInstance();
        list = database.getReference("Travel");


        adapter = new FirebaseRecyclerAdapter<Place, MenuViewHolder>(Place.class, R.layout.layout_place, MenuViewHolder.class, list) {
            @Override
            protected void populateViewHolder(final MenuViewHolder viewHolder, final Place model, int position) {


                viewHolder.txtMenuName.setText(model.getName());
                Picasso.get().load(model.getImage_url()).into(viewHolder.imageView);
                viewHolder.txtMenuTime.setText(model.getTime());

                if(!cardViewList.contains(viewHolder.card_place))
                    cardViewList.add(viewHolder.card_place);

                final Place clickItem = model;
                viewHolder.setItemClickListener(new IRecyclerItemSelectedListener() {
                    @Override
                    public void onItemSelectedListener(View view, int pos) {

                        for(CardView cardView : cardViewList)
                            cardView.setCardBackgroundColor(getContext().getResources().getColor(android.R.color.white));

                        viewHolder.card_place.setCardBackgroundColor(getContext().getResources().getColor(R.color.red));
                        viewHolder.txtMenuName.setTextColor(getContext().getResources().getColor(R.color.bpTransparent_black));
                        viewHolder.txtMenuTime.setTextColor(getContext().getResources().getColor(R.color.bpTransparent_black));

                        //send BroadCast to tell Schedule Activity enable Next Button
                        adappter(getActivity(),placeList);

                        Intent intent = new Intent(Common.KEY_ENABLE_BUTTON_NEXT);
                        intent.putExtra(Common.KEY_PLACE,model);
                        //intent.putExtra(Common.KEY_PLACE ,placeList.get(pos));
                        intent.putExtra(Common.KEY_STEP,1);
                        localBroadcastManager.sendBroadcast(intent);
                    }
                });


            }
        };
        recyclerView.setAdapter(adapter);
        dialog.dismiss();
    }

    private void loadEat() {

        dialog.show();

        database = FirebaseDatabase.getInstance();
        list = database.getReference("Food");


        adapter = new FirebaseRecyclerAdapter<Place, MenuViewHolder>(Place.class, R.layout.layout_place, MenuViewHolder.class, list) {
            @Override
            protected void populateViewHolder(final MenuViewHolder viewHolder, final Place model, int position) {


                viewHolder.txtMenuName.setText(model.getName());
                Picasso.get().load(model.getImage_url()).into(viewHolder.imageView);
                viewHolder.txtMenuTime.setText(model.getTime());

                if(!cardViewList.contains(viewHolder.card_place))
                    cardViewList.add(viewHolder.card_place);

                final Place clickItem = model;
                viewHolder.setItemClickListener(new IRecyclerItemSelectedListener() {
                    @Override
                    public void onItemSelectedListener(View view, int pos) {

                        for(CardView cardView : cardViewList)
                            cardView.setCardBackgroundColor(getContext().getResources().getColor(android.R.color.white));

                        viewHolder.card_place.setCardBackgroundColor(getContext().getResources().getColor(android.R.color.holo_green_dark));
                        viewHolder.txtMenuName.setTextColor(getContext().getResources().getColor(R.color.bpTransparent_black));
                        viewHolder.txtMenuTime.setTextColor(getContext().getResources().getColor(R.color.bpTransparent_black));

                        //send BroadCast to tell Schedule Activity enable Next Button
                        adappter(getActivity(),placeList);

                        Intent intent = new Intent(Common.KEY_ENABLE_BUTTON_NEXT);
                        intent.putExtra(Common.KEY_PLACE,model);
                        //intent.putExtra(Common.KEY_PLACE ,placeList.get(pos));
                        intent.putExtra(Common.KEY_STEP,1);
                        localBroadcastManager.sendBroadcast(intent);
                    }
                });


            }
        };
        recyclerView.setAdapter(adapter);
        dialog.dismiss();
    }

    private void loadrest() {
        dialog.show();

        database = FirebaseDatabase.getInstance();
        list = database.getReference("Rest");


        adapter = new FirebaseRecyclerAdapter<Place, MenuViewHolder>(Place.class, R.layout.layout_place, MenuViewHolder.class, list) {
            @Override
            protected void populateViewHolder(final MenuViewHolder viewHolder, final Place model, int position) {


                viewHolder.txtMenuName.setText(model.getName());
                Picasso.get().load(model.getImage_url()).into(viewHolder.imageView);
                viewHolder.txtMenuTime.setText(model.getTime());

                if(!cardViewList.contains(viewHolder.card_place))
                    cardViewList.add(viewHolder.card_place);

                final Place clickItem = model;
                viewHolder.setItemClickListener(new IRecyclerItemSelectedListener() {
                    @Override
                    public void onItemSelectedListener(View view, int pos) {

                        for(CardView cardView : cardViewList)
                            cardView.setCardBackgroundColor(getContext().getResources().getColor(android.R.color.white));

                        viewHolder.card_place.setCardBackgroundColor(getContext().getResources().getColor(R.color.colorButton));
                        viewHolder.txtMenuName.setTextColor(getContext().getResources().getColor(R.color.bpblack));
                        viewHolder.txtMenuTime.setTextColor(getContext().getResources().getColor(R.color.bpTransparent_black));

                        //send BroadCast to tell Schedule Activity enable Next Button
                        adappter(getActivity(),placeList);

                        Intent intent = new Intent(Common.KEY_ENABLE_BUTTON_NEXT);
                        intent.putExtra(Common.KEY_PLACE,model);
                        //intent.putExtra(Common.KEY_PLACE ,placeList.get(pos));
                        intent.putExtra(Common.KEY_STEP,1);
                        localBroadcastManager.sendBroadcast(intent);
                    }
                });


            }
        };
        recyclerView.setAdapter(adapter);
        dialog.dismiss();
    }

}
