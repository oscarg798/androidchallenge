package com.knomatic.weather;

import android.test.suitebuilder.annotation.SmallTest;

import com.knomatic.weather.model.utils.ForecastUtils;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void shouldReturnClearDayIcon() {
        assertEquals(R.drawable.ic_sun, ForecastUtils.getWeatherIcon("clear-day"));
    }

    @Test
    public void shouldReturnTemperatureInCelcius() {
        double tempature = 35.52;
        assertEquals(tempature, ForecastUtils.passTemperatureToCelciusDegress(96));

    }
}