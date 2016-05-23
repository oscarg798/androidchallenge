package com.knomatic.weather.model.interfaces;

import android.content.Context;
import android.location.Location;

import com.knomatic.weather.model.Callbacks;
import com.knomatic.weather.model.dtos.ForecastDTO;
import com.rm.androidesentials.model.utils.CoupleParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by oscargallon on 5/22/16.
 */

public interface IForecast {

    List<ForecastDTO> getDailyForecastFromJsonArray(JSONArray jsonArray, Context context) throws JSONException;

    ForecastDTO getForecastFromJsonObject(JSONObject jsonObject,
                                          Context context, String forecastType)
            throws JSONException, ClassCastException;

    List<ForecastDTO> getHourlyForecastDTOFromJsonArray(JSONArray jsonArray, Context context) throws JSONException;

    void getForecastFromLocation(Callbacks.IGetForecastFromLocationCallbacks
                                         iGetForecastFromLocationCallbacks,  Location location,
                                 Context context);


}
