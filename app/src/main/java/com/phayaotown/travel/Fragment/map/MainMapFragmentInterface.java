package com.phayaotown.travel.Fragment.map;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.phayaotown.travel.base.BaseInterface;
import com.phayaotown.travel.model.ShowAll;

class MainMapFragmentInterface {

    interface View extends BaseInterface.View {

        void loadJsonFailure();

        DatabaseReference database = FirebaseDatabase.getInstance().getReference("ShowAll");

        void loadJsonSuccess(DatabaseReference database);
    }

    interface Presenter extends BaseInterface.Presenter<MainMapFragmentInterface.View> {

        void loadJsonFromFile();
    }
}
