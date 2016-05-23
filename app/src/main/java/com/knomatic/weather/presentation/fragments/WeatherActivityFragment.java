package com.knomatic.weather.presentation.fragments;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.knomatic.weather.R;
import com.knomatic.weather.model.dtos.ForecastDTO;
import com.knomatic.weather.model.utils.ForecastUtils;
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
public class WeatherActivityFragment extends Fragment {

    private static final String PARAM_KEY = "forecast";

    private List<CoupleParams> coupleParamsList;

    private TextView tvCityName;

    private TextView tvCurrentTemperature;

    private TextView tvCurrentWeather;

    private ImageView ivWeather;

    private TextView tvWindSpeed;

    private RecyclerView recyclerView;

    public static WeatherActivityFragment getInstance(List<CoupleParams> coupleParamsList) {
        WeatherActivityFragment weatherActivityFragment = new WeatherActivityFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(PARAM_KEY, (Serializable) coupleParamsList);
        weatherActivityFragment.setArguments(bundle);
        return weatherActivityFragment;

    }

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

    private void initViewComponents(View view) {
        tvCityName = (TextView) view.findViewById(R.id.tv_city_name);
        tvCurrentTemperature = (TextView) view.findViewById(R.id.tv_current_temperature);
        tvCurrentWeather = (TextView) view.findViewById(R.id.tv_current_weather);
        tvWindSpeed = (TextView) view.findViewById(R.id.tv_wind_speed);
        ivWeather = (ImageView) view.findViewById(R.id.iv_weather);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity().getApplicationContext(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
    }

    private void initComponents() {
        ForecastDTO forecastDTO = (ForecastDTO) coupleParamsList.get(2).getObject();
        tvCurrentWeather.setText(forecastDTO.getSummary());

        tvCurrentTemperature.setText(String.format("%.2f º", ForecastUtils
                .passTemperatureToCelciusDegress(forecastDTO.getTemperature())));

        tvWindSpeed.setText(String.format("%.2f KM", ForecastUtils
                .passWindSpeedToKM(forecastDTO.getWindSpeed())));

        String cityName = ForecastUtils.getCityName(((WeatherActivity) getActivity())
                .getWeatherActivityController().getLocation(), getActivity().getApplicationContext());

        if (cityName != null) {
            tvCityName.setText(cityName);
        } else {
            tvCityName.setText(coupleParamsList.get(5).getParam());
        }

        int weatherIcon = ForecastUtils.getWeatherIcon(forecastDTO.getIcon());

        ivWeather.setImageDrawable(getActivity()
                .getApplicationContext().getResources().getDrawable(weatherIcon));

        DailyForecastAdapter dailyForecastAdapter = new DailyForecastAdapter((List<ForecastDTO>) coupleParamsList.get(1).getObject(),
                getActivity().getApplicationContext());

        recyclerView.setAdapter(dailyForecastAdapter);

        recyclerView.setHasFixedSize(true);

        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()
                .getApplicationContext()));
    }


}
