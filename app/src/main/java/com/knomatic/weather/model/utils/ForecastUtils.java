package com.knomatic.weather.model.utils;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import com.knomatic.weather.R;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by oscargallon on 5/22/16.
 */

public class ForecastUtils {

    private static final String CLEAR_DAY = "clear-day";

    private static final String CLEAR_NIGTH = "clear-night";

    private static final String RAIN = "rain";

    private static final String SNOW = "snow";

    private static final String WIND = "wind";

    private static final String FOG = "fog";

    private static final String CLOUDY = "cloudy";

    private static final String PARTLY_CLOUDY_DAY = "partly-cloudy-day";

    private static final String PARTLY_COUDY_NIGHT = "partly-cloudy-night";

    private static final double KM_IN_ONE_MILE = 1.609;

    private static Calendar calendar = Calendar.getInstance();

    public static Double passTemperatureToCelciusDegress(double tempature) {
        return tempature - 32;
    }

    public static Double passWindSpeedToKM(double windSpeed) {
        return windSpeed * KM_IN_ONE_MILE;
    }

    public static String getCityName(Location location, Context context) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(location.getLatitude(),
                    location.getLongitude(), 1);
            String cityName = addresses.get(0).getAddressLine(0);
            String stateName = addresses.get(0).getAddressLine(1);
            String countryName = addresses.get(0).getAddressLine(2);

            return stateName;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    public static int getWeatherIcon(String icon) {
        switch (icon) {
            case CLEAR_DAY:
                return R.drawable.ic_sun;

            case CLEAR_NIGTH:
                return R.drawable.ic_moon;

            case RAIN:
                return R.drawable.ic_cloud_drizzle_alt;

            case SNOW:
                return R.drawable.ic_snowflake;

            case WIND:
                return R.drawable.ic_cloud_wind;

            case FOG:
                return R.drawable.ic_cloud_fog_alt;

            case CLOUDY:
                return R.drawable.ic_cloud;

            case PARTLY_CLOUDY_DAY:
                return R.drawable.ic_cloud_sun;

            case PARTLY_COUDY_NIGHT:
                return R.drawable.ic_cloud_moon;

        }
        return R.drawable.ic_sun;
    }

    public static String getDayName(Date date) {
        calendar.setTime(date);
        String dayOfWeekName = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
        dayOfWeekName = Character.toString(dayOfWeekName.charAt(0)).toUpperCase()
                + dayOfWeekName.substring(1, dayOfWeekName.length());
        return dayOfWeekName;
    }
}
