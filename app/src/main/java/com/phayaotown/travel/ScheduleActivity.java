package com.phayaotown.travel;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.phayaotown.travel.Common.Common;
import com.phayaotown.travel.model.ScheduleInformation;
import com.shuhart.stepview.StepView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dmax.dialog.SpotsDialog;

public class ScheduleActivity extends AppCompatActivity{

    LocalBroadcastManager localBroadcastManager;
    AlertDialog dialog;
    ScheduleInformation scheduleInformation;
    SimpleDateFormat simpleDateFormat;

    @BindView(R.id.step_view)
    StepView stepView;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.btn_previous_step)
    Button btn_previous_step;
    @BindView(R.id.btn_Next_step)
    Button btn_next_step;

    //Event
    @OnClick(R.id.btn_previous_step)
    void previousClick(){
        if(Common.step == 2 || Common.step > 0){
            Common.step--;
            viewPager.setCurrentItem(Common.step);
            if(Common.step < 2)
            {
                btn_next_step.setEnabled(true);
                setColorButton();
            }
        }
    }
    @OnClick(R.id.btn_Next_step)
    void nextClick(){
        if(Common.step < 2 || Common.step == 0)
        {
            Common.step++;
            if(Common.step == 1)//after choose place
            {
                if (Common.currentPlace != null)
                    loadDate(Common.currentPlace.getMenuID());
            }else if (Common.step == 2)
                save();
            /*else if (Common.step == 2){ //after choose time
                if(Common.currentTimeSlot != -1)
                    saveInformation();
            }*/

            viewPager.setCurrentItem(Common.step);

        }
    }

    private void save() {
        Toast.makeText(getBaseContext(),"Click again to Confirm",Toast.LENGTH_SHORT).show();
        btn_next_step.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();

                //Process Timestamp use to filter booking with date
                String startTime = Common.convertTimeSlotToString(Common.currentTimeSlot);
                String[] convertTime = startTime.split("-");//Split ex :9:00 - 10:00
                //go to start time get 9:00
                String [] startTimeConvert = convertTime[0].split(":");
                int startHourInt = Integer.parseInt(startTimeConvert[0].trim()); //get 9
                int startMinInt = Integer.parseInt(startTimeConvert[1].trim()); //get 00

                Calendar bookingDateWithoutHour = Calendar.getInstance();
                bookingDateWithoutHour.setTimeInMillis(Common.bookingDate.getTimeInMillis());
                bookingDateWithoutHour.set(Calendar.HOUR_OF_DAY,startHourInt);
                bookingDateWithoutHour.set(Calendar.MINUTE,startMinInt);

                //create timestamp
                Timestamp timestamp = new Timestamp(bookingDateWithoutHour.getTime());

                //create booking information
                final ScheduleInformation scheduleInformation = new ScheduleInformation();

                scheduleInformation.setTimestamp(timestamp);
                scheduleInformation.setPlaceName(Common.currentPlace.getName());
                scheduleInformation.setTime(new StringBuilder(Common.convertTimeSlotToString(Common.currentTimeSlot))
                        .append(" at ")
                        .append(simpleDateFormat.format(bookingDateWithoutHour.getTime())).toString());
                scheduleInformation.setSlot(Long.valueOf(Common.currentTimeSlot));

                //submit
                DocumentReference bookingDate = FirebaseFirestore.getInstance()
                        .collection("User")
                        .document(Common.currentUser.getPhoneNumber())
                        .collection("Booking")
                        .document("Date")
                        .collection(Common.simpleDateFormat.format(Common.bookingDate.getTime()))
                        .document(String.valueOf(Common.currentTimeSlot));

                bookingDate.set(scheduleInformation)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                //checked
                                //if already exist
                                Toast.makeText(getBaseContext(),"Success",Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                resetStaticData();
                                finish();


                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getBaseContext(),""+e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }

    /*private void saveInformation() {
       Intent intent = new Intent(Common.KEY_CONFIRM);
        localBroadcastManager.sendBroadcast(intent);

    }*/

    private void loadDate(String placeId) {
        Intent intent = new Intent(Common.KEY_TIME_SLOT);
        localBroadcastManager.sendBroadcast(intent);
    }

    private void resetStaticData() {

        Common.step = 0;
        Common.currentTimeSlot = -1;
        Common.currentPlace = null;
        Common.bookingDate.add(Calendar.DATE,0);
    }

    //Broadcast Receiver
    private BroadcastReceiver buttonNextReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int step = intent.getIntExtra(Common.KEY_STEP,0);

            if(step == 1)
                Common.currentPlace = intent.getParcelableExtra(Common.KEY_PLACE);
            else if(step == 2)
                Common.currentTimeSlot = intent.getIntExtra(Common.KEY_TIME_SLOT,-1);
            else if (step == 3)
                save();


            btn_next_step.setEnabled(true);
            setColorButton();


        }
    };

    private BroadcastReceiver schedule = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

        }
    };

    @Override
    protected void onDestroy() {
        localBroadcastManager.unregisterReceiver(buttonNextReceiver);
        super.onDestroy();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        ButterKnife.bind(ScheduleActivity.this);

        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");


        dialog = new SpotsDialog.Builder().setContext(this).build();

        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        localBroadcastManager.registerReceiver(buttonNextReceiver,new IntentFilter(Common.KEY_ENABLE_BUTTON_NEXT));

        setupStepView();
        setColorButton();

        //View
        viewPager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager()));
        viewPager.setOffscreenPageLimit(2);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                stepView.go(i,true);
                if(i == 0)
                    btn_previous_step.setEnabled(false);

                else btn_previous_step.setEnabled(true);

                //set disable button next
                btn_next_step.setEnabled(false);
                setColorButton();

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    private void setColorButton() {

        if(btn_next_step.isEnabled()){
            btn_next_step.setBackgroundColor(getResources().getColor(R.color.colorButton));
        }
        else {
            btn_next_step.setBackgroundResource(android.R.color.darker_gray);
        }
        if(btn_previous_step.isEnabled()){
            btn_previous_step.setBackgroundResource(R.color.colorButton);
        }
        else {
            btn_previous_step.setBackgroundResource(android.R.color.darker_gray);
        }
    }

    private void setupStepView() {
        List<String> stepList = new ArrayList<>();
        stepList.add("Select Place");
        stepList.add("Select Time");
        stepView.setSteps(stepList);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}