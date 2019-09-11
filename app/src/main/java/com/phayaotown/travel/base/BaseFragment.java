package com.phayaotown.travel.base;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.phayaotown.travel.R;
import com.phayaotown.travel.base.exception.MvpNotSetLayoutException;
import com.phayaotown.travel.base.exception.MvpPresenterNotCreateException;
import com.phayaotown.travel.model.Pin;
import com.phayaotown.travel.utils.DialogUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.content.Context.LOCATION_SERVICE;
import static android.content.Context.MODE_PRIVATE;

public abstract class BaseFragment<P extends BaseInterface.Presenter> extends Fragment
        implements BaseInterface.View {

    private P presenter;

    @Nullable
    @BindView(android.R.id.content)
    View contentView;

    private ProgressDialog progressDialog;
    private SharedPreferences spf;

    @LayoutRes
    protected abstract int layoutToInflate();

    public abstract P createPresenter();

    protected abstract void startView();

    protected abstract void stopView();

    protected abstract void bindView(View view);

    protected abstract void setupInstance();

    protected abstract void setupView();

    protected abstract void initialize();

    protected abstract void saveInstanceState(Bundle outState);

    public abstract void restoreView(Bundle savedInstanceState);

    @SuppressWarnings("unchecked")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (layoutToInflate() == 0) throw new MvpNotSetLayoutException();
        presenter = createPresenter();
        presenter.attachView(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (layoutToInflate() == 0) throw new MvpNotSetLayoutException();
        View view = inflater.inflate(layoutToInflate(), container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        spf = getActivity().getSharedPreferences("app_language", MODE_PRIVATE);
        bindView(view);
        setupInstance();
        setupView();
        setupProgressDialog();
        getPresenter().onViewCreate();
        if (savedInstanceState == null) initialize();
        else restoreView(savedInstanceState);
    }

    @Override
    public void showProgressDialog() {
        if (!progressDialog.isShowing()) progressDialog.show();
    }

    @Override
    public void hideProgressDialog() {
        if (progressDialog != null) progressDialog.dismiss();
    }

    @Override
    public void showError(String errorMessage) {
        showSnackBar(errorMessage);
    }

    @Override
    public void showError(@StringRes int errorMessage) {
        showSnackBar(getString(errorMessage));
    }

    @Override
    public void showMessage(String message) {
        showSnackBar(message);
    }

    @Override
    public void showMessage(@StringRes int message) {
        showSnackBar(getString(message));
    }

    @Override
    public void unAuthorizedApi() {
        showSnackBar(getResources().getString(R.string.un_authorize_api));
    }

    @Override
    public void onStart() {
        super.onStart();
        startView();
        getPresenter().onViewStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        stopView();
        getPresenter().onViewStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (progressDialog != null) progressDialog.cancel();
        getPresenter().onViewDestroy();
        presenter.detachView();
    }

    @Override
    public P getPresenter() {
        if (presenter != null) return presenter;
        throw new MvpPresenterNotCreateException();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        saveInstanceState(outState);
    }

    private void setupProgressDialog() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(getResources().getString(R.string.loading));
    }

    private void showSnackBar(@NonNull String message) {
        if (contentView != null) Snackbar.make(contentView, message, Snackbar.LENGTH_SHORT).show();
    }

    public void showDialogError() {
        DialogUtil.getInstance().showDialogWarning(this.getContext(), R.string.error, R.string.please_try_again);
    }

    public Location getLastKnownLocation() {
        if (ActivityCompat.checkSelfPermission(this.getActivity(), ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.getContext(),
                ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }
        LocationManager locationManager = (LocationManager) this.getActivity().getSystemService(LOCATION_SERVICE);
        List<String> providers;
        if (locationManager != null) {
            providers = locationManager.getProviders(true);
            Location bestLocation = null;
            for (String provider : providers) {
                Location l = locationManager.getLastKnownLocation(provider);
                if (l == null) {
                    continue;
                }
                if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                    bestLocation = l;
                }
            }
            return bestLocation;
        }
        return null;
    }

    public int getDrawableId(String image) {
        return  getResources().getIdentifier(image, "drawable", getContext().getPackageName());
    }

    public String getLanguage() {
        return spf.getString("key_code", "th");
    }
}
