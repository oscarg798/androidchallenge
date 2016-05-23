package com.knomatic.weather.presentation.view_holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.knomatic.weather.R;

/**
 * Created by oscargallon on 5/22/16.
 */

public class DailyForecastViewHolder extends RecyclerView.ViewHolder {

    private TextView tvDay;
    private TextView tvWeather;
    private TextView tvMinTemperature;
    private TextView tvMaxTemperature;
    private ImageView ivWeather;


    public DailyForecastViewHolder(View itemView) {
        super(itemView);
        setTvDay((TextView) itemView.findViewById(R.id.tv_day));
        setTvWeather((TextView) itemView.findViewById(R.id.tv_weather));
        setTvMinTemperature((TextView) itemView.findViewById(R.id.tv_min_temperature));
        setTvMaxTemperature((TextView) itemView.findViewById(R.id.tv_max_temperature));
        setIvWeather((ImageView) itemView.findViewById(R.id.iv_weather));
    }

    public TextView getTvDay() {
        return tvDay;
    }

    public void setTvDay(TextView tvDay) {
        this.tvDay = tvDay;
    }

    public TextView getTvWeather() {
        return tvWeather;
    }

    public void setTvWeather(TextView tvWeather) {
        this.tvWeather = tvWeather;
    }

    public TextView getTvMinTemperature() {
        return tvMinTemperature;
    }

    public void setTvMinTemperature(TextView tvMinTemperature) {
        this.tvMinTemperature = tvMinTemperature;
    }

    public TextView getTvMaxTemperature() {
        return tvMaxTemperature;
    }

    public void setTvMaxTemperature(TextView tvMaxTemperature) {
        this.tvMaxTemperature = tvMaxTemperature;
    }

    public ImageView getIvWeather() {
        return ivWeather;
    }

    public void setIvWeather(ImageView ivWeather) {
        this.ivWeather = ivWeather;
    }
}
