package com.phayaotown.travel.Fragment.Detail;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.phayaotown.travel.Common.Common;
import com.phayaotown.travel.Fragment.BookingDateFragment;
import com.phayaotown.travel.Fragment.MapFragment;
import com.phayaotown.travel.R;
import com.phayaotown.travel.ScheduleActivity;
import com.phayaotown.travel.model.Food;
import com.squareup.picasso.Picasso;

public class Food_detail extends AppCompatActivity {

    TextView name,recommend,cost,time,information;
    ImageView food_image;
    FloatingActionButton near;

    String foodId = "";

    FirebaseDatabase database;
    DatabaseReference place;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail_);

        //Firebase
        database = FirebaseDatabase.getInstance();
        place = database.getReference("Food");

        near = (FloatingActionButton)findViewById(R.id.fab_navigate);

        near.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), MapFragment.class));
            }
        });


        name = (TextView)findViewById(R.id.food_name);
        time = (TextView)findViewById(R.id.food_time);
        information = (TextView)findViewById(R.id.food_information);
        cost = (TextView)findViewById(R.id.food_cost);
        recommend = (TextView)findViewById(R.id.food_recommend);

        food_image = (ImageView)findViewById(R.id.img_food);

        if(getIntent() != null) {
            foodId = getIntent().getStringExtra("FoodId");
        }
        if (!foodId.isEmpty()){
            getDetailFood(foodId);
        }


    }

    private void getDetailFood(String foodId) {

        place.child(foodId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Food food = dataSnapshot.getValue(Food.class);

                //Set Image
                Picasso.get().load(food.getImage_url()).into(food_image);

                name.setText(food.getName());
                cost.setText(food.getCost());
                time.setText(food.getTime());
                information.setText(food.getInformation());
                recommend.setText(food.getMenu_Recommend());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
