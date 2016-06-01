package com.knomatic.weather.presentation.fragments;

import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.knomatic.weather.R;
import com.knomatic.weather.model.dtos.ForecastDTO;
import com.knomatic.weather.model.utils.AsyncTaskExecutor;
import com.knomatic.weather.model.utils.ForecastUtils;
import com.knomatic.weather.model.utils.interfaces.IAsyncTaskExecutor;
import com.knomatic.weather.presentation.activities.WeatherActivity;
import com.knomatic.weather.presentation.adapters.DailyForecastAdapter;
import com.knomatic.weather.presentation.utils.SimpleDividerItemDecoration;
import com.rm.androidesentials.model.utils.CoupleParams;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class WeatherActivityFragment extends Fragment implements IAsyncTaskExecutor{

    private static final String PARAM_KEY = "forecast";

    private List<CoupleParams> coupleParamsList;

    private TextView tvCityName;

    private TextView tvCurrentTemperature;

    private TextView tvCurrentWeather;

    private ImageView ivWeather;

    private TextView tvWindSpeed;

    private RecyclerView recyclerView;

    private DailyForecastAdapter dailyForecastAdapter;

    private ProgressBar pbDailyForecast;

    private RelativeLayout weatherFragmentMainLayout;

    /**
     * Use this method for get this fragment, because we need to pass
     * the list of the forecast that we will show.
     *
     * @param coupleParamsList list with forecats information
     * @return return a  weatherActivityFragment instance
     */
    public static WeatherActivityFragment getInstance(List<CoupleParams> coupleParamsList) {
        WeatherActivityFragment weatherActivityFragment = new WeatherActivityFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(PARAM_KEY, (Serializable) coupleParamsList);
        weatherActivityFragment.setArguments(bundle);
        return weatherActivityFragment;

    }

    /**
     * Fragment must have a public empty constructor
     */
    public WeatherActivityFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.coupleParamsList = (List<CoupleParams>) getArguments()
                    .getSerializable(PARAM_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);
        initViewComponents(view);
        initComponents();
        return view;
    }

    /**
     * this method load the layout view components
     *
     * @param view layout inflated
     */
    private void initViewComponents(View view) {
        weatherFragmentMainLayout = (RelativeLayout) view.findViewById(R.id.weather_fragment_main_layout);
        tvCityName = (TextView) view.findViewById(R.id.tv_city_name);
        tvCurrentTemperature = (TextView) view.findViewById(R.id.tv_current_temperature);
        tvCurrentWeather = (TextView) view.findViewById(R.id.tv_current_weather);
        tvWindSpeed = (TextView) view.findViewById(R.id.tv_wind_speed);
        ivWeather = (ImageView) view.findViewById(R.id.iv_weather);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        pbDailyForecast = (ProgressBar) view.findViewById(R.id.pb_daily_forecast);


    }

    private void initComponents() {
        ForecastDTO forecastDTO = (ForecastDTO) coupleParamsList.get(2).getObject();
        tvCurrentWeather.setText(forecastDTO.getSummary());

        tvCurrentTemperature.setText(String.format("%.2f º", ForecastUtils
                .passTemperatureToCelciusDegress(forecastDTO.getTemperature())));

        tvWindSpeed.setText(String.format("%.2f KM", ForecastUtils
                .passWindSpeedToKM(forecastDTO.getWindSpeed())));

        /**
         * We need to find de drawable constant for the right icon
         */
        int weatherIcon = ForecastUtils.getWeatherIcon(forecastDTO.getIcon());

        ivWeather.setImageDrawable(getActivity()
                .getApplicationContext().getResources().getDrawable(weatherIcon));
        /**
         * Create the forecast for eachday maybe takes a while so
         * an async task is need it, for run the process on background
         */
        AsyncTaskExecutor asyncTaskExecutor = new AsyncTaskExecutor(this);
        asyncTaskExecutor.execute();


    }


    @Override
    public Object execute() {

        /**
         * Check the city name from lat, lng
         */
        String cityName = ForecastUtils.getCityName(((WeatherActivity) getActivity())
                .getWeatherActivityController().getLocation(), getActivity().getApplicationContext());

        /**
         * If we can not get the city name from a lat lng point
         * we user the returned value from forecast api
         */
        if (cityName == null) {
            cityName = coupleParamsList.get(5).getParam();
        }

        /**
         * create the recycler view adapter
         */
        dailyForecastAdapter =
                new DailyForecastAdapter((List<ForecastDTO>) coupleParamsList.get(1).getObject(),
                        getActivity().getApplicationContext());

        /**
         * We return the adapter
         */
        return cityName;
    }

    @Override
    public void onExecuteComplete(Object object) {
        /**
         * We check if we got a String
         */
        if (object != null && object instanceof String) {
            String cityName = (String) object;
            tvCityName.setText(cityName);
        }

        /**
         * We put the adapter
         */
        recyclerView.setAdapter(dailyForecastAdapter);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()
                .getApplicationContext()));

        GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity().getApplicationContext(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        pbDailyForecast.setVisibility(View.INVISIBLE);

    }

    /**
     * In this fragment we are using the {@link AsyncTaskExecutor}
     * for load the data that might take a while, so we do not
     * care fot the failure status of those operations
     *
     * @param e
     */
    @Override
    public void onExecuteFaliure(Exception e) {

    }





}
