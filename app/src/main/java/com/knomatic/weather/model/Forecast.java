package com.knomatic.weather.model;

import android.content.Context;
import android.location.Location;

import com.knomatic.weather.R;
import com.knomatic.weather.model.dtos.ForecastDTO;
import com.knomatic.weather.model.interfaces.IForecast;
import com.knomatic.weather.model.utils.AsyncTaskExecutor;
import com.knomatic.weather.model.utils.interfaces.IAsyncTaskExecutor;
import com.rm.androidesentials.model.utils.CoupleParams;
import com.rm.androidesentials.services.http.HTTPServices;
import com.rm.androidesentials.services.interfaces.IHTTPServices;
import com.rm.androidesentials.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by oscargallon on 5/22/16.
 */
public class Forecast implements IForecast {
    private static Forecast ourInstance = new Forecast();

    private Callbacks.IGetForecastFromLocationCallbacks iGetForecastFromLocationCallbacks;

    private final String SLASH = "/";

    private final String COMMA = ",";

    public static Forecast getInstance() {
        return ourInstance;
    }

    private Forecast() {
    }

    /**
     * this method obtains the daily forecast from a json array
     *
     * @param jsonArray @{@link JSONArray} json array with the forecast for each day
     * @param context   @{@link Context} current application context
     * @return @{@link List} with the daily forecast or null if error occurs
     */
    @Override
    public List<ForecastDTO> getDailyForecastFromJsonArray(JSONArray jsonArray, Context context)
            throws JSONException {

        List<ForecastDTO> dailyForecast = new ArrayList<>();
        JSONObject jsonObject = null;
        for (int i = 0; i < jsonArray.length(); i++) {
            jsonObject = jsonArray.getJSONObject(i);

            dailyForecast.add(getForecastFromJsonObject(jsonObject, context,
                    context.getString(R.string.daily_key)));
        }
        return dailyForecast;
    }

    /**
     * This method obtains the forecast of the request day, from a json object
     *
     * @param jsonObject   @{@link JSONObject} json object with the current forecast
     * @param context      @{@link Context} current application context
     * @param forecastType @{@link String} type of forecast request {current, hourly, daily}
     * @return {@link ForecastDTO} or null if error occurs
     */
    @Override
    public ForecastDTO getForecastFromJsonObject(JSONObject jsonObject, Context context,
                                                 String forecastType) throws JSONException, ClassCastException {

        ForecastDTO.ForecastDTOBuilder forecastDTOBuilder
                = new ForecastDTO.ForecastDTOBuilder();

        if (Utils.jsonHasProperty(jsonObject.names(), context.getString(R.string.time_key))) {
            forecastDTOBuilder.withATime(new Date(Long.parseLong(jsonObject.getString(context
                    .getString(R.string.time_key))) * 1000));
        }

        if (Utils.jsonHasProperty(jsonObject.names(), context.getString(R.string.summary_key))) {
            forecastDTOBuilder.withASummary(jsonObject.getString(context
                    .getString(R.string.summary_key)));
        }

        if (Utils.jsonHasProperty(jsonObject.names(), context.getString(R.string.icon_key))) {
            forecastDTOBuilder.withAnIcon(jsonObject.getString(context
                    .getString(R.string.icon_key)));
        }
        if (Utils.jsonHasProperty(jsonObject.names(), context.getString(R.string.precipProbability_key))) {
            forecastDTOBuilder.withAPrecipProbability(jsonObject.getDouble(context
                    .getString(R.string.precipProbability_key)));
        }

        if (Utils.jsonHasProperty(jsonObject.names(), context.getString(R.string.precipType_key))) {
            forecastDTOBuilder.withAPrecipType(jsonObject.getString(context
                    .getString(R.string.precipType_key)));
        }

        if (Utils.jsonHasProperty(jsonObject.names(), context.getString(R.string.humidity_key))) {
            forecastDTOBuilder.withAHumidity(jsonObject.getDouble(context
                    .getString(R.string.humidity_key)));
        }


        if (Utils.jsonHasProperty(jsonObject.names(), context.getString(R.string.windSpeed_key))) {
            forecastDTOBuilder.withAWindSpeed(jsonObject.getDouble(context
                    .getString(R.string.windSpeed_key)));
        }

        if (Utils.jsonHasProperty(jsonObject.names(), context.getString(R.string.cloudCover_key))) {
            forecastDTOBuilder.withACloudCover(jsonObject
                    .getDouble(context.getString(R.string.cloudCover_key)));
        }

        if (forecastType.equals(context.getString(R.string.daily_key))) {

            if (Utils.jsonHasProperty(jsonObject.names(), context.getString(R.string.sunriseTime_key))) {
                forecastDTOBuilder.withASunriseTime(new Date(Long
                        .parseLong(jsonObject.getString(context.getString(R.string.sunriseTime_key))) * 1000));
            }

            if (Utils.jsonHasProperty(jsonObject.names(), context.getString(R.string.sunriseTime_key))) {
                forecastDTOBuilder.withASunsetTime(new Date(Long
                        .parseLong(jsonObject.getString(context.getString(R.string.sunsetTime_key))) * 1000));
            }

            if (Utils.jsonHasProperty(jsonObject.names(), context.getString(R.string.temperatureMin_key))) {
                forecastDTOBuilder.withATemperatureMin(jsonObject
                        .getDouble(context.getString(R.string.temperatureMin_key)));
            }

            if (Utils.jsonHasProperty(jsonObject.names(), context.getString(R.string.temperatureMax_key))) {
                forecastDTOBuilder.withATemperatureMax(jsonObject
                        .getDouble(context.getString(R.string.temperatureMax_key)));
            }
        } else {
            if (Utils.jsonHasProperty(jsonObject.names(), context.getString(R.string.temperature_key))) {
                forecastDTOBuilder.withAnTemperature(jsonObject
                        .getDouble(context.getString(R.string.temperature_key)));
            }
        }


        return forecastDTOBuilder.createForecastDTO();
    }

    /**
     * This method obtains the hourly forecast from a json array
     *
     * @param jsonArray @{@link JSONArray} json array with the forcast for each hour of the day
     * @param context   @{@link Context} current application context
     * @return @{@link List} of forecast
     * null if and error error occurs
     */
    @Override
    public List<ForecastDTO> getHourlyForecastDTOFromJsonArray(JSONArray jsonArray, Context context)
            throws JSONException {

        List<ForecastDTO> hourlyForecast = new ArrayList<>();
        JSONObject jsonObject = null;
        for (int i = 0; i < jsonArray.length(); i++) {
            jsonObject = jsonArray.getJSONObject(i);

            hourlyForecast.add(getForecastFromJsonObject(jsonObject, context,
                    context.getString(R.string.hourly_key)));
        }
        return hourlyForecast;
    }

    private void getForecastFromLocationSuccess(final String response, final Context context) {
        final AsyncTaskExecutor asyncTaskExecutor = new AsyncTaskExecutor(new IAsyncTaskExecutor() {
            @Override
            public Object execute() {

                try {
                    JSONObject forecastResponse = new JSONObject(response);

                    JSONObject currentlyForecastJsonObject = forecastResponse.getJSONObject(context
                            .getString(R.string.currently_key));

                    JSONArray hourlyForecastJsonObject = forecastResponse.getJSONObject(context
                            .getString(R.string.hourly_key)).getJSONArray(context
                            .getString(R.string.data_key));

                    JSONArray dailyForecastJsonObject = forecastResponse.getJSONObject(context
                            .getString(R.string.daily_key)).getJSONArray(context
                            .getString(R.string.data_key));

                    String hourlySummary = forecastResponse.getJSONObject(context
                            .getString(R.string.hourly_key)).getString(context.getString(R.string.summary_key));

                    String dailySummary = forecastResponse.getJSONObject(context
                            .getString(R.string.daily_key)).getString(context.getString(R.string.summary_key));

                    String timeZone = forecastResponse.getString(context.getString(R.string.time_zone_key));

                    List<ForecastDTO> hourlyForecastList = getHourlyForecastDTOFromJsonArray(hourlyForecastJsonObject, context);

                    List<ForecastDTO> dailyForecastList = getDailyForecastFromJsonArray(dailyForecastJsonObject, context);

                    ForecastDTO forecastDTO = getForecastFromJsonObject(currentlyForecastJsonObject, context,
                            context.getString(R.string.currently_key));

                    List<CoupleParams> coupleParamsList = new ArrayList<>();
                    coupleParamsList.add(new CoupleParams.CoupleParamBuilder(context.getString(R.string.hourly_key))
                            .nestedObject((Serializable) hourlyForecastList).createCoupleParam());
                    coupleParamsList.add(new CoupleParams.CoupleParamBuilder(context.getString(R.string.daily_key))
                            .nestedObject((Serializable) dailyForecastList).createCoupleParam());
                    coupleParamsList.add(new CoupleParams.CoupleParamBuilder(context.getString(R.string.currently_key))
                            .nestedObject(forecastDTO).createCoupleParam());
                    coupleParamsList.add(new CoupleParams.CoupleParamBuilder(context.getString(R.string.daily_summary_key))
                            .nestedParam(dailySummary).createCoupleParam());
                    coupleParamsList.add(new CoupleParams.CoupleParamBuilder(context.getString(R.string.hourly_key))
                            .nestedParam(hourlySummary).createCoupleParam());
                    coupleParamsList.add(new CoupleParams.CoupleParamBuilder(context
                            .getString(R.string.time_zone_key))
                            .nestedParam(timeZone).createCoupleParam());


                    return coupleParamsList;


                } catch (JSONException e) {
                    e.printStackTrace();
                    onExecuteFaliure(e);
                } catch (ClassCastException e) {
                    e.printStackTrace();
                    onExecuteFaliure(e);
                } catch (Exception e) {
                    e.printStackTrace();
                    onExecuteFaliure(e);

                }
                return null;
            }

            @Override
            public void onExecuteComplete(Object object) {
                if (object != null) {
                    iGetForecastFromLocationCallbacks.onForecastGot((List<CoupleParams>) object);
                } else {
                    Exception e = new Exception(context.getString(R.string.can_not_get_forecast_from_location_error));
                    onExecuteFaliure(e);
                }
            }

            @Override
            public void onExecuteFaliure(Exception e) {
                iGetForecastFromLocationCallbacks.onGetForecastError(e);
            }
        });

        asyncTaskExecutor.execute();


    }

    private void getForecastFromLocationFail(String error) {
        Exception e = new Exception(error);
        iGetForecastFromLocationCallbacks.onGetForecastError(e);

    }

    /**
     * This method do a request for the forecast based on a location
     *
     * @param iGetForecastFromLocationCallbacks methods to call when request finish
     */
    @Override
    public void getForecastFromLocation(Callbacks.IGetForecastFromLocationCallbacks iGetForecastFromLocationCallbacks,
                                        Location location, final Context context) {
        this.iGetForecastFromLocationCallbacks = iGetForecastFromLocationCallbacks;
        HTTPServices httpServices = new HTTPServices(new IHTTPServices() {
            @Override
            public void successFullResponse(String response) {
                getForecastFromLocationSuccess(response, context);
            }

            @Override
            public void errorResponse(String message, JSONObject jsonObject) {
                getForecastFromLocationFail(message);
            }
        }, null, "GET", true);

        httpServices.execute(context.getString(R.string.base_url) + context.getString(R.string.api_key)
                + SLASH + location.getLatitude() + COMMA + location.getLongitude());


    }


}
