package com.knomatic.weather.controllers;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;

import com.knomatic.weather.R;
import com.knomatic.weather.model.Callbacks;
import com.knomatic.weather.model.Forecast;
import com.knomatic.weather.model.dtos.ForecastDTO;
import com.knomatic.weather.presentation.activities.WeatherActivity;
import com.rm.androidesentials.controllers.abstracts.AbstractController;
import com.rm.androidesentials.model.interfaces.LocationProviderUtils;
import com.rm.androidesentials.model.providers.UserLocationProvider;
import com.rm.androidesentials.model.utils.CoupleParams;
import com.rm.androidesentials.utils.ErrorCodes;

import java.util.List;

/**
 * Created by oscargallon on 5/22/16.
 */

public class WeatherActivityController extends AbstractController implements
        LocationProviderUtils.onSubscribeforLocationUpdates,
        LocationProviderUtils.onGetLocation,
        Callbacks.IGetForecastFromLocationCallbacks {

    private Location location;

    private List<CoupleParams> coupleParamsList;

    /**
     * Contructor de la clase
     *
     * @param activity actividad a la cual pertenece el controlador
     */
    public WeatherActivityController(Activity activity) {
        super(activity);
    }

    public void getUserLocation() {
        UserLocationProvider
                .getLocationProvider(getActivity().getApplicationContext())
                .getUserLocaton(LocationManager.NETWORK_PROVIDER, this, this);

        UserLocationProvider
                .getLocationProvider(getActivity().getApplicationContext())
                .getUserLocaton(LocationManager.GPS_PROVIDER, this, this);
    }

    @Override
    public void locationGot(Location location) {
        this.location = location;
        Forecast.getInstance()
                .getForecastFromLocation(this, location, getActivity().getApplicationContext());
    }

    @Override
    public void errorGettingLocation(ErrorCodes error) {
        switch (error) {
            case CAN_NOT_GET_USER_LOCATION:
                showAlertDialog(getActivity().getApplicationContext()
                        .getString(R.string.error_title), getActivity().getApplicationContext()
                        .getString(R.string.can_not_get_location));
                break;
            case NETWORK_PROVIDER_DISABLE:
                showAlertDialog(getActivity().getApplicationContext()
                        .getString(R.string.error_title), getActivity().getApplicationContext()
                        .getString(R.string.network_provider_disable));
                break;
            case GPS_PROVIDER_DISABLE:

                showAlertDialog(getActivity().getApplicationContext()
                                .getString(R.string.error_title), getActivity().getApplicationContext()
                                .getString(R.string.gps_provider_disable), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                getActivity().startActivity(myIntent);
                            }
                        }, null, getActivity().getApplicationContext().getString(R.string.ok_button_label),
                        null);
                break;
            case LOCATION_MANAGER_UNAVALAIBLE:
                showAlertDialog(getActivity().getApplicationContext()
                        .getString(R.string.error_title), getActivity().getApplicationContext()
                        .getString(R.string.location_manager_unavaliable));
                break;
        }
    }

    @Override
    public void locationUpdateGot(Location location) {
        this.location = location;
    }

    @Override
    public void onForecastGot(List<CoupleParams> coupleParamsList) {
        this.coupleParamsList = coupleParamsList;
        ((WeatherActivity) getActivity()).forecastGot(coupleParamsList);
    }

    @Override
    public void onGetForecastError(Exception error) {
        showAlertDialog("ERROR", "ERROR NO TENEMOS CLIMA " + error.getMessage());

    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
