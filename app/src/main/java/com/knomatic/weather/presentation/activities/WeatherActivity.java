package com.knomatic.weather.presentation.activities;


import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.knomatic.weather.R;
import com.knomatic.weather.controllers.WeatherActivityController;
import com.knomatic.weather.presentation.fragments.SplashFragment;
import com.knomatic.weather.presentation.fragments.WeatherActivityFragment;
import com.rm.androidesentials.model.utils.CoupleParams;

import java.io.Serializable;
import java.util.List;

public class WeatherActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        ResultCallback<LocationSettingsResult> {

    private Toolbar toolbar;

    private WeatherActivityController weatherActivityController;

    protected GoogleApiClient googleApiClient;

    private Fragment currentFragment;

    private LocationRequest locationRequest;

    private final int REQUES_CHECK_LOCATION_SETTINGS = 201252;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        initViewComponents();
        initComponents();

    }

    private void initComponents() {
        weatherActivityController = new WeatherActivityController(this);
        currentFragment = SplashFragment.newInstance();
        changeFragment(currentFragment);
        sendUpdateToFragment(getApplicationContext().getString(R.string.getting_location_message));

        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).build();
        googleApiClient.connect();
        showRequestLocationMessage();

    }


    private void showRequestLocationMessage() {
        if (locationRequest == null) {
            locationRequest = LocationRequest.create();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(30 * 1000 * 1);
            locationRequest.setFastestInterval(5 * 1000);
        }
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);

        builder.setAlwaysShow(true);

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                final LocationSettingsStates state = result.getLocationSettingsStates();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        /**
                         * User has the gps enable
                         */
                        weatherActivityController.getUserLocation();
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:

                        try {
                            status.startResolutionForResult(
                                    WeatherActivity.this, REQUES_CHECK_LOCATION_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:

                        break;
                    case LocationSettingsStatusCodes.CANCELED:
                        /**
                         * User cancel the request
                         */
                        Log.i("CANCELED", "USER CANCEL");
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUES_CHECK_LOCATION_SETTINGS: {
                if (resultCode == RESULT_OK) {
                    weatherActivityController.getUserLocation();
                } else {
                    /**
                     * The user cancel the dialog which request for locations settings
                     */
                    showRequestLocationMessage();
                }
                break;
            }

        }
    }

    public void forecastGot(List<CoupleParams> coupleParamsList) {
        currentFragment = WeatherActivityFragment.getInstance(coupleParamsList);
        changeFragment(currentFragment);
    }

    private void initViewComponents() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_weather, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    @Override
    public void onResult(@NonNull LocationSettingsResult locationSettingsResult) {

    }

    public void changeFragment(Fragment fragment) {
        this.currentFragment = fragment;
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.nv_frame_layout, fragment)
                .addToBackStack(null)
                .commit();

    }

    public void sendUpdateToFragment(Serializable serializable) {
        if (currentFragment != null && currentFragment instanceof SplashFragment) {
            ((SplashFragment) currentFragment).receiveUpdated(serializable);
        }
    }

    public WeatherActivityController getWeatherActivityController() {
        return weatherActivityController;
    }

    public void setWeatherActivityController(WeatherActivityController weatherActivityController) {
        this.weatherActivityController = weatherActivityController;
    }
}
