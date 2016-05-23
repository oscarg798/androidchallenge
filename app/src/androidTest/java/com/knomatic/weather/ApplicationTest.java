package com.knomatic.weather;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import com.knomatic.weather.model.Forecast;
import com.knomatic.weather.model.dtos.ForecastDTO;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {

    private String forecastDailyJsonObject = "  {\n" +
            "        \"time\": 1464325200,\n" +
            "        \"summary\": \"Light rain throughout the day.\",\n" +
            "        \"icon\": \"rain\",\n" +
            "        \"sunriseTime\": 1464346065,\n" +
            "        \"sunsetTime\": 1464390865,\n" +
            "        \"moonPhase\": 0.69,\n" +
            "        \"precipIntensity\": 0.0262,\n" +
            "        \"precipIntensityMax\": 0.0522,\n" +
            "        \"precipIntensityMaxTime\": 1464332400,\n" +
            "        \"precipProbability\": 0.69,\n" +
            "        \"precipType\": \"rain\",\n" +
            "        \"temperatureMin\": 55.49,\n" +
            "        \"temperatureMinTime\": 1464397200,\n" +
            "        \"temperatureMax\": 66.22,\n" +
            "        \"temperatureMaxTime\": 1464368400,\n" +
            "        \"apparentTemperatureMin\": 55.49,\n" +
            "        \"apparentTemperatureMinTime\": 1464397200,\n" +
            "        \"apparentTemperatureMax\": 66.22,\n" +
            "        \"apparentTemperatureMaxTime\": 1464368400,\n" +
            "        \"dewPoint\": 57.47,\n" +
            "        \"humidity\": 0.9,\n" +
            "        \"windSpeed\": 0.92,\n" +
            "        \"windBearing\": 185,\n" +
            "        \"cloudCover\": 1,\n" +
            "        \"pressure\": 1012.65,\n" +
            "        \"ozone\": 245.24\n" +
            "      }";

    private ForecastDTO dailyForeCast = new ForecastDTO.ForecastDTOBuilder()
            .withATime(new Date(Long.parseLong("1464325200") * 1000))
            .withASummary("Light rain throughout the day.")
            .withAnIcon("rain")
            .withASunriseTime(new Date(Long.parseLong("1464346065") * 1000))
            .withASunsetTime(new Date(Long.parseLong("1464390865") * 1000))
            .withAPrecipProbability(0.69)
            .withAPrecipType("rain")
            .withAHumidity(0.9)
            .withAWindSpeed(0.92)
            .withACloudCover(1)
            .withAnApparentTemperatureMin(55.49)
            .withAnApparentTemperatureMax(66.22)
            .createForecastDTO();


    public ApplicationTest() {
        super(Application.class);
    }

    @SmallTest
    public void testGetForecastDailyFromJsonObject() {
        try {
            ForecastDTO forecastDTO = Forecast.getInstance()
                    .getForecastFromJsonObject(new JSONObject(forecastDailyJsonObject),
                            getContext(), getContext().getString(R.string.daily_key));

            assertEquals(0, forecastDTO.compare(forecastDTO, dailyForeCast));


        } catch (JSONException e) {
            e.printStackTrace();
            fail();
        }

    }
}