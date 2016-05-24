package com.knomatic.weather.presentation.activities;


import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

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
import com.knomatic.weather.model.Forecast;
import com.knomatic.weather.presentation.fragments.SplashFragment;
import com.knomatic.weather.presentation.fragments.WeatherActivityFragment;
import com.rm.androidesentials.model.interfaces.LocationProviderUtils;
import com.rm.androidesentials.model.providers.UserLocationProvider;
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

    private String forecastResponse;

    private CoordinatorLayout mainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        initViewComponents();
        initComponents();
        /**
         * We need to check if are recreating the activity
         * to show the correct information
         */
        if (savedInstanceState == null) {
            showRequestLocationMessage();
        } else {
            final String response = savedInstanceState.getString(getString(R.string.response_key));
            final String provider = savedInstanceState.getString(getString(R.string.provider_key));
            final double lat = savedInstanceState.getDouble(getString(R.string.latitude_key));
            final double lng = savedInstanceState.getDouble(getString(R.string.longitude_key));
            Location location = null;
            if (provider != null) {
                location = new Location(provider);
                location.setLatitude(lat);
                location.setLongitude(lng);
            }
            if (response != null) {
                weatherActivityController.restartActivityWithSavedState(response, location);
            } else {
                showRequestLocationMessage();
            }
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        if (forecastResponse != null) {
            outState.putString(getString(R.string.response_key), forecastResponse);
            forecastResponse = null;
        }
        if (weatherActivityController != null) {
            final Location location = weatherActivityController.getLocation();
            if (location != null) {
                outState.putDouble(getString(R.string.latitude_key), location.getLatitude());
                outState.putDouble(getString(R.string.longitude_key), location.getLongitude());
                outState.putString(getString(R.string.provider_key), location.getProvider());
            }
        }
        super.onSaveInstanceState(outState, outPersistentState);

    }


    private void initComponents() {
        weatherActivityController = new WeatherActivityController(this);
        currentFragment = SplashFragment.newInstance();
        changeFragment(currentFragment);


        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).build();
        googleApiClient.connect();

    }


    /**
     * This method show a request window to tell the user
     * that we need his location, so he would have to enable his location provider
     */
    public void showRequestLocationMessage() {
        showLocationMessagewithGoogleApiCLient();
    }

    private void showLocationMessagewithGoogleApiCLient() {
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
                        weatherActivityController.showErrorMessageWithRetryAction(getString(R.string.alert_label),
                                getString(R.string.need_location_message));
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
                    weatherActivityController.showErrorMessageWithRetryAction(getString(R.string.alert_label),
                            getString(R.string.need_location_message));
                }
                break;
            }

        }
    }

    public void forecastGot(List<CoupleParams> coupleParamsList) {
        currentFragment = WeatherActivityFragment.getInstance(coupleParamsList);
        changeFragment(currentFragment);
        forecastResponse = coupleParamsList.get(6).getParam();
    }

    private void initViewComponents() {
        mainLayout = (CoordinatorLayout) findViewById(R.id.main_layout);
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
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        if (fragment instanceof SplashFragment) {
            sendUpdateToFragment(getApplicationContext().getString(R.string.getting_location_message));

        }
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
                .setCustomAnimations(R.anim.slide_left_enter, R.anim.slide_left_exit,
                        R.anim.slide_right_enter, R.anim.slide_right_exit)
                .replace(R.id.nv_frame_layout, fragment)
                .addToBackStack(null)
                .commitAllowingStateLoss();

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

    /**
     * We capture the back button pressed event
     * to ask the user if he wants to exit
     */
    @Override
    public void onBackPressed() {
        Snackbar.make(mainLayout,
                getString(R.string.do_you_to_exi_message), Snackbar.LENGTH_LONG)
                .setAction("CLOSE", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                })
                .setActionTextColor(getResources().getColor(R.color.colorAccent))
                .show();

    }
}
