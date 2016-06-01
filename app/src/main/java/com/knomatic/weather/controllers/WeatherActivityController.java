package com.knomatic.weather.controllers;

import android.app.Activity;
import android.content.DialogInterface;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

import com.knomatic.weather.R;
import com.knomatic.weather.model.Callbacks;
import com.knomatic.weather.model.Forecast;
import com.knomatic.weather.model.utils.AsyncTaskExecutor;
import com.knomatic.weather.model.utils.interfaces.IAsyncTaskExecutor;
import com.knomatic.weather.presentation.activities.WeatherActivity;
import com.rm.androidesentials.controllers.abstracts.AbstractController;
import com.rm.androidesentials.model.interfaces.LocationProviderUtils;
import com.rm.androidesentials.model.providers.UserLocationProvider;
import com.rm.androidesentials.model.utils.CoupleParams;
import com.rm.androidesentials.utils.ErrorCodes;

import java.net.InetAddress;
import java.net.UnknownHostException;
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

    private boolean hasNetworkProviderDisable = false;

    private DialogInterface.OnClickListener checkInternetConnectionPositiveOnClickListener;

    private DialogInterface.OnClickListener checkInternetConnectionNegativeOnClickListener;


    /**
     * Contructor de la clase
     *
     * @param activity actividad a la cual pertenece el controlador
     */
    public WeatherActivityController(Activity activity) {
        super(activity);
        checkInternetConnectionPositiveOnClickListener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        checkIfInternetIsAvailableAndCallTheAPI();
                    }
                };

        checkInternetConnectionNegativeOnClickListener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getActivity().finish();
                    }
                };

    }

    /**
     * This method makes a request for user location, based in his network or
     * the GPS
     */
    public void getUserLocation() {
        UserLocationProvider
                .getLocationProvider(getActivity().getApplicationContext())
                .getUserLocaton(LocationManager.NETWORK_PROVIDER, this, this);

        UserLocationProvider
                .getLocationProvider(getActivity().getApplicationContext())
                .getUserLocaton(LocationManager.GPS_PROVIDER, this, this);
    }

    /**
     * This method restart the activity, when the user where watching the forecast
     * and the applications goes to the background
     *
     * @param response forecast response saved
     * @param location location saved
     */
    public void restartActivityWithSavedState(String response, Location location) {
        ((WeatherActivity) getActivity()).sendUpdateToFragment(getActivity()
                .getApplicationContext().getString(R.string.getting_forecast_message));

        this.location = location;
        Forecast.getInstance().setiGetForecastFromLocationCallbacks(this);
        Forecast.getInstance()
                .getForecastFromLocationSuccess(response, getActivity()
                        .getApplicationContext());
    }

    /**
     * This is a callback of success for the location request, so once we have the user
     * location we call the API for the forecast information
     *
     * @param location
     */
    @Override
    public void locationGot(Location location) {
        this.location = location;
        hasNetworkProviderDisable = false;
        checkIfInternetIsAvailableAndCallTheAPI();

    }

    public void getForecastFromAPI() {
        Forecast.getInstance()
                .getForecastFromLocation(this, location, getActivity().getApplicationContext());
    }

    /**
     * We have internet connection
     */
    private void onInternetConnection() {
        getForecastFromAPI();
    }

    /**
     * Error callback for when we do not have Internet Connection
     */
    private void onNoInternetConnection() {
        showAlertDialog(getActivity().getApplicationContext()
                        .getString(R.string.alert_label), getActivity().getApplicationContext()
                        .getString(R.string.no_internet_connection_message),
                checkInternetConnectionPositiveOnClickListener,
                checkInternetConnectionNegativeOnClickListener, getActivity().getApplicationContext()
                        .getString(R.string.acept_label), getActivity().getApplicationContext()
                        .getString(R.string.cancel_label));

    }

    /**
     * Before call the API we need to check if the user has an active internet connection
     */
    public void checkIfInternetIsAvailableAndCallTheAPI() {
        AsyncTaskExecutor asyncTaskExecutor = new AsyncTaskExecutor(new IAsyncTaskExecutor() {
            @Override
            public Object execute() {

                /**
                 * Have network provider enable is not enough to access to internet
                 * so we need to try to call a server for check if we got and active
                 * internet connection
                 */
                InetAddress ipAddr = null;
                try {
                    ipAddr = InetAddress.getByName("google.com");
                    if (ipAddr.equals("")) {
                        return false;
                    } else {
                        return true;
                    }
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                    return false;
                }
            }

            /**
             * Verify if has an internet connection or not
             * @param object true if we got, false otherwise
             */
            @Override
            public void onExecuteComplete(Object object) {
                boolean res = (boolean) object;
                if (res) {
                    onInternetConnection();
                } else {
                    onNoInternetConnection();
                }
            }

            /**
             * We do not have internet conenction so we let the user choose
             * @param e error
             */
            @Override
            public void onExecuteFaliure(Exception e) {
                if (e != null) {
                    e.printStackTrace();
                }
                onNoInternetConnection();


            }
        });

        asyncTaskExecutor.execute();
    }

    /**
     * This method shows and error message, and when the user hit accept
     * we close application
     *
     * @param title   dialog title
     * @param message dialog message
     */
    private void showErrorMessageWitouhAnyActionToDo(String title, String message) {
        DialogInterface.OnClickListener positiveOnClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getActivity().finish();
            }
        };

        showAlertDialog(title, message, positiveOnClickListener, getActivity()
                .getString(R.string.acept_label));

    }

    /**
     * this method show a message with two actions one to try to obtain the user location again
     * and the otherone for close application
     *
     * @param title   dialog title
     * @param message dialog message
     */
    public void showErrorMessageWithRetryAction(String title, String message) {
        DialogInterface.OnClickListener positiveOnClickListener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ((WeatherActivity) getActivity()).showRequestLocationMessage();
                    }
                };

        DialogInterface.OnClickListener negativeOnClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getActivity().finish();
            }
        };

        showAlertDialog(title, message, positiveOnClickListener, negativeOnClickListener,
                getActivity().getString(R.string.acept_label)
                , getActivity().getString(R.string.cancel_label));


    }

    @Override
    public void errorGettingLocation(ErrorCodes error) {
        switch (error) {
            case CAN_NOT_GET_USER_LOCATION:
                showErrorMessageWithRetryAction(getActivity().getApplicationContext()
                        .getString(R.string.error_title), getActivity().getApplicationContext()
                        .getString(R.string.can_not_get_location) +
                        getActivity().getApplicationContext()
                                .getString(R.string.try_or_dissmiss_location_message));
                break;
            case NETWORK_PROVIDER_DISABLE:
                hasNetworkProviderDisable = true;
                showErrorMessageWithRetryAction(getActivity().getApplicationContext()
                        .getString(R.string.error_title), getActivity().getApplicationContext()
                        .getString(R.string.network_provider_disable) +
                        getActivity().getApplicationContext()
                                .getString(R.string.try_or_dissmiss_location_message));
                break;
            case GPS_PROVIDER_DISABLE:
                /**
                 * Perhaps the user has his GPS of but we can get userlocation via network
                 * so we need to check if we have received updates from that provider.
                 * Otherwise we show an error message
                 */
                if (this.location == null && hasNetworkProviderDisable) {
                    showErrorMessageWithRetryAction(getActivity().getApplicationContext()
                            .getString(R.string.error_title), getActivity().getApplicationContext()
                            .getString(R.string.gps_provider_disable) +
                            getActivity().getApplicationContext()
                                    .getString(R.string.try_or_dissmiss_location_message));
                }

                break;
            case LOCATION_MANAGER_UNAVALAIBLE:
                showErrorMessageWitouhAnyActionToDo(getActivity().getApplicationContext()
                        .getString(R.string.error_title), getActivity().getApplicationContext()
                        .getString(R.string.location_manager_unavaliable));
                break;
        }
    }

    @Override
    public void locationUpdateGot(Location location) {
        /**
         * It's means that the provider requested does not
         * happen a previous location saved
         */
        if (this.location == null) {
            locationGot(location);
            ((WeatherActivity) getActivity()).sendUpdateToFragment(getActivity()
                    .getApplicationContext().getString(R.string.getting_forecast_message));
        }
        this.location = location;
        UserLocationProvider.getLocationProvider(getActivity().getApplicationContext())
                .unSuscribeLocationUpdates();

    }

    /**
     * We had process the forecast data and now we send it to the activity
     * to show it
     *
     * @param coupleParamsList forecast data processed
     */
    @Override
    public void onForecastGot(List<CoupleParams> coupleParamsList) {
        this.coupleParamsList = coupleParamsList;
        ((WeatherActivity) getActivity()).forecastGot(coupleParamsList);
    }

    /**
     * We receive and error when we were trying to get te forecast
     * so we have to show and error message
     *
     * @param error
     */
    @Override
    public void onGetForecastError(Exception error) {
        /**
         * We check if we have some error to show
         */
        String errorMessage = error != null ? " " + error.getMessage() : " ";
        Log.e("ERROR", errorMessage);

        showAlertDialog(getActivity().getApplicationContext()
                        .getString(R.string.error_title), getActivity().getApplicationContext()
                        .getString(R.string.can_not_get_forecast_from_location_error),
                checkInternetConnectionPositiveOnClickListener,
                checkInternetConnectionNegativeOnClickListener,
                getActivity().getApplicationContext()
                        .getString(R.string.acept_label),
                getActivity().getApplicationContext()
                        .getString(R.string.cancel_label));

    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
