package com.phayaotown.travel.Fragment.List;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.phayaotown.travel.Common.Common;
import com.phayaotown.travel.Common.SpaceItemDecoration;
import com.phayaotown.travel.Fragment.Detail.Food_detail;
import com.phayaotown.travel.Fragment.Detail.Rest_Detail;
import com.phayaotown.travel.Fragment.Detail.Travel_Detail;
import com.phayaotown.travel.Interface.ItemClickListener;
import com.phayaotown.travel.R;
import com.phayaotown.travel.ViewHolder.FoodViewHolder;
import com.phayaotown.travel.model.Food;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PlaceActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    Button btn_eat,btn_rest,btn_travel;

    FirebaseDatabase database;
    DatabaseReference foodlist;

    String categoryId = "";

    FirebaseRecyclerAdapter<Food, FoodViewHolder> adapter;

    int i = 0;

    //Search
    FirebaseRecyclerAdapter<Food, FoodViewHolder> searchAdapter;
    List<String> suggestList = new ArrayList<>();
   // MaterialSearchBar materialSearchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);

        btn_eat = (Button) findViewById(R.id.btn_eat);
        btn_rest = (Button) findViewById(R.id.btn_rest);
        btn_travel = (Button) findViewById(R.id.btn_travel);

        // loadAll();
        loadTravel();

        btn_eat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFood();
            }
        });

        btn_travel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getBaseContext(),"สถานที่ท่องเที่ยว",Toast.LENGTH_SHORT).show();
                loadTravel();
            }
        });
        btn_rest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadRest();
            }
        });


        //search
        /*materialSearchBar = (MaterialSearchBar) findViewById(R.id.searchBar);
        materialSearchBar.setHint("Find your place");
        materialSearchBar.setSpeechMode(true);
        loadSuggest();
        materialSearchBar.setLastSuggestions(suggestList);
        materialSearchBar.setCardViewElevation(10);
        materialSearchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                List<String> suggest = new ArrayList<String>();
                for (String search : suggestList){
                    if (search.toLowerCase().contains(materialSearchBar.getText().toLowerCase()))
                        suggest.add(search);
                }
                materialSearchBar.setLastSuggestions(suggest);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                //when search bar is close restore origin suggest
                if (!enabled)
                    recyclerView.setAdapter(adapter);
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                //when search finish show result
                startSearch(text);

            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });

    }

    private void startSearch(CharSequence text) {
        foodlist = database.getReference("ShowAll");
        searchAdapter = new FirebaseRecyclerAdapter<Food, FoodViewHolder>(
                Food.class,
                R.layout.layout_place_placefragment,
                FoodViewHolder.class,
                foodlist.orderByChild("Name").equalTo(text.toString())

        ) {
            @Override
            protected void populateViewHolder(FoodViewHolder viewHolder, Food model, int position) {
                viewHolder.food_name.setText(model.getName());
                Picasso.get().load(model.getImage_url())
                        .into(viewHolder.food_image);

                final Food clickItem = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent travelList = new Intent(PlaceActivity.this,Travel_Detail.class);
                        travelList.putExtra("TravelId",adapter.getRef(position).getKey());
                        startActivity(travelList);
                    }
                });


               /* final Food clickItem = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        //Get Category and send to new Activity
                        Intent foodList = new Intent(PlaceActivity.this,Food_detail.class);
                        foodList.putExtra("FoodId", adapter.getRef(position).getKey());
                        //foodList.putExtra("TravelId",adapter.getRef(position).getKey());
                        startActivity(foodList);


                    }
                });
            }
        };
        recyclerView.setAdapter(searchAdapter);//set adapter for search result
    }

    private void loadSuggest() {
        foodlist = database.getReference("ShowAll");
                foodlist.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                            Food item = postSnapshot.getValue(Food.class);
                            suggestList.add(item.getName());
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }*/
    }

    private void loadRest() {
        database = FirebaseDatabase.getInstance();
        foodlist = database.getReference("Rest");

        recyclerView = (RecyclerView) findViewById(R.id.recycler_place_Ac);
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(getBaseContext(), 2);
        recyclerView.addItemDecoration(new SpaceItemDecoration(4));
        recyclerView.setLayoutManager(layoutManager);


        adapter = new FirebaseRecyclerAdapter<Food, FoodViewHolder>(Food.class, R.layout.layout_place_placefragment, FoodViewHolder.class, foodlist) {
            @Override
            protected void populateViewHolder(FoodViewHolder viewHolder, Food model, int position) {

                viewHolder.food_name.setText(model.getName());
                Picasso.get().load(model.getImage_url())
                        .into(viewHolder.food_image);

                final Food clickItem = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        //Get Category and send to new Activity
                        Intent foodList = new Intent(PlaceActivity.this, Rest_Detail.class);
                        foodList.putExtra("RestId", adapter.getRef(position).getKey());
                        startActivity(foodList);
                    }
                });


            }
        };
        recyclerView.setAdapter(adapter);
    }

    private void loadTravel() {

        database = FirebaseDatabase.getInstance();
        foodlist = database.getReference("Travel");

        recyclerView = (RecyclerView) findViewById(R.id.recycler_place_Ac);
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(getBaseContext(), 2);
        recyclerView.addItemDecoration(new SpaceItemDecoration(4));
        recyclerView.setLayoutManager(layoutManager);


        adapter = new FirebaseRecyclerAdapter<Food, FoodViewHolder>(Food.class, R.layout.layout_place_placefragment, FoodViewHolder.class, foodlist) {
            @Override
            protected void populateViewHolder(FoodViewHolder viewHolder, Food model, int position) {

                viewHolder.food_name.setText(model.getName());
                Picasso.get().load(model.getImage_url())
                        .into(viewHolder.food_image);

                final Food clickItem = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        //Get Category and send to new Activity
                        Intent foodList = new Intent(PlaceActivity.this, Travel_Detail.class);
                        foodList.putExtra("TravelId", adapter.getRef(position).getKey());
                        startActivity(foodList);
                    }
                });


            }
        };
        recyclerView.setAdapter(adapter);
    }


    private void loadFood() {

        database = FirebaseDatabase.getInstance();
        foodlist = database.getReference("Food");

        recyclerView = (RecyclerView) findViewById(R.id.recycler_place_Ac);
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(getBaseContext(), 2);
        recyclerView.addItemDecoration(new SpaceItemDecoration(4));
        recyclerView.setLayoutManager(layoutManager);


        adapter = new FirebaseRecyclerAdapter<Food, FoodViewHolder>(Food.class, R.layout.layout_place_placefragment, FoodViewHolder.class, foodlist) {
            @Override
            protected void populateViewHolder(FoodViewHolder viewHolder, Food model, int position) {

                viewHolder.food_name.setText(model.getName());
                Picasso.get().load(model.getImage_url())
                        .into(viewHolder.food_image);

                final Food clickItem = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        //Get Category and send to new Activity
                        Intent foodList = new Intent(PlaceActivity.this, Food_detail.class);
                        foodList.putExtra("FoodId", adapter.getRef(position).getKey());
                        startActivity(foodList);
                    }
                });


            }
        };
        recyclerView.setAdapter(adapter);

    }
}
