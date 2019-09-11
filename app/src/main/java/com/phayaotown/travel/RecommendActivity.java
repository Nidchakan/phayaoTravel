package com.phayaotown.travel;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;

import com.phayaotown.travel.Fragment.RecommendFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

;

public class RecommendActivity extends AppCompatActivity {

    @BindView(R.id.fab)
    ImageButton fab;

    @OnClick(R.id.fab)
    void click(){
        startActivity(new Intent(getBaseContext(),ScheduleActivity.class));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);

        ButterKnife.bind(RecommendActivity.this);

        Fragment fragment = new RecommendFragment() ;
        loadFragment(fragment);

    }

    private boolean loadFragment(Fragment fragment) {

        if(fragment != null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_recommend,fragment)
                    .commit();
            return true;
        }
        return false;
    }

}
