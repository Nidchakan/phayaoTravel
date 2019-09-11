package com.phayaotown.travel.Fragment.map;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.phayaotown.travel.base.BasePresenter;
import com.phayaotown.travel.model.ShowAll;
import com.phayaotown.travel.utils.JsonUtil;

class MainMapFragmentPresenter extends BasePresenter<MainMapFragmentInterface.View>
        implements MainMapFragmentInterface.Presenter {

    public static MainMapFragmentInterface.Presenter create() {
        return new MainMapFragmentPresenter();
    }

    @Override
    public void onViewCreate() {

    }

    @Override
    public void onViewDestroy() {

    }

    @Override
    public void onViewStart() {

    }

    @Override
    public void onViewStop() {

    }

    @Override
    public void loadJsonFromFile() {
        if (getView() != null) {
            DatabaseReference modelref = FirebaseDatabase.getInstance().getReference("ShowAll");
            if (modelref == null) getView().loadJsonFailure();
            else getView().loadJsonSuccess(modelref);
        }
    }
}
