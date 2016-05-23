package com.knomatic.weather.presentation.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.knomatic.weather.R;
import com.knomatic.weather.model.dtos.ForecastDTO;
import com.knomatic.weather.model.utils.ForecastUtils;
import com.knomatic.weather.presentation.view_holders.DailyForecastViewHolder;

import java.util.List;

/**
 * Created by oscargallon on 5/22/16.
 */

public class DailyForecastAdapter extends RecyclerView.Adapter<DailyForecastViewHolder> {

    private List<ForecastDTO> dailyForecast;

    private Context context;

    public DailyForecastAdapter(List<ForecastDTO> dailyForecast, Context context) {
        this.dailyForecast = dailyForecast;
        this.context = context;
    }

    @Override
    public DailyForecastViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.daily_forecast_item, parent, false);
        return new DailyForecastViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DailyForecastViewHolder holder, int position) {
        ForecastDTO forecastDTO = dailyForecast.get(position);
        holder.getTvDay().setText(ForecastUtils.getDayName(forecastDTO.getTime()));
        holder.getTvWeather().setText(forecastDTO.getSummary());
        holder.getTvMinTemperature().setText((String.format("%.2f º", ForecastUtils
                .passTemperatureToCelciusDegress(forecastDTO.getTemperatureMin()))));
        holder.getTvMaxTemperature().setText((String.format("%.2f º", ForecastUtils
                .passTemperatureToCelciusDegress(forecastDTO.getTemperatureMax()))));
        holder.getIvWeather().setImageDrawable(context.getResources()
                .getDrawable(ForecastUtils.getWeatherIcon(forecastDTO.getIcon())));

    }

    @Override
    public int getItemCount() {
        return this.dailyForecast.size();
    }
}
