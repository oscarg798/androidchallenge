package com.knomatic.weather.model.dtos;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;

/**
 * Created by oscargallon on 5/22/16.
 */
public class ForecastDTO implements Serializable, Comparator<ForecastDTO> {


    private final Date time;
    private final String summary;
    private final String icon;
    private final Date sunriseTime;
    private final Date sunsetTime;
    private final double moonPhase;
    private final double precipIntensity;
    private final double precipIntensityMax;
    private final Date precipIntensityMaxTime;
    private final double precipProbability;
    private final String precipType;
    private final double temperatureMin;
    private final Date temperatureMinTime;
    private final double temperatureMax;
    private final Date temperatureMaxTime;
    private final double apparentTemperatureMin;
    private final Date apparentTemperatureMinTime;
    private final double apparentTemperatureMax;
    private final Date apparentTemperatureMaxTime;
    private final double dewPoint;
    private final double humidity;
    private final double windSpeed;
    private final double windBearing;
    private final double cloudCover;
    private final double pressure;
    private final double ozone;
    private final double temperature;

    private ForecastDTO(Date time, String summary, String icon, Date sunriseTime,
                        Date sunsetTime, double moonPhase, double precipIntensity,
                        double precipIntensityMax, Date precipIntensityMaxTime,
                        double precipProbability, String precipType, double temperatureMin,
                        Date temperatureMinTime, double temperatureMax, Date temperatureMaxTime,
                        double apparentTemperatureMin, Date apparentTemperatureMinTime,
                        double apparentTemperatureMax, Date apparentTemperatureMaxTime,
                        double dewPoint, double humidity, double windSpeed, double windBearing,
                        double cloudCover, double pressure, double ozone, double temperature) {
        this.time = time;
        this.summary = summary;
        this.icon = icon;
        this.sunriseTime = sunriseTime;
        this.sunsetTime = sunsetTime;
        this.moonPhase = moonPhase;
        this.precipIntensity = precipIntensity;
        this.precipIntensityMax = precipIntensityMax;
        this.precipIntensityMaxTime = precipIntensityMaxTime;
        this.precipProbability = precipProbability;
        this.precipType = precipType;
        this.temperatureMin = temperatureMin;
        this.temperatureMinTime = temperatureMinTime;
        this.temperatureMax = temperatureMax;
        this.temperatureMaxTime = temperatureMaxTime;
        this.apparentTemperatureMin = apparentTemperatureMin;
        this.apparentTemperatureMinTime = apparentTemperatureMinTime;
        this.apparentTemperatureMax = apparentTemperatureMax;
        this.apparentTemperatureMaxTime = apparentTemperatureMaxTime;
        this.dewPoint = dewPoint;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.windBearing = windBearing;
        this.cloudCover = cloudCover;
        this.pressure = pressure;
        this.ozone = ozone;
        this.temperature = temperature;
    }

    @Override
    public int compare(ForecastDTO lhs, ForecastDTO rhs) {
        if (!lhs.getTime().equals(rhs.getTime())) {
            return lhs.getTime().compareTo(rhs.getTime());
        }

        if (lhs.getTemperatureMin() != rhs.getTemperatureMin()) {
            return lhs.getTemperatureMin() > rhs.getTemperatureMin() ? 1 : -1;
        }

        if (lhs.getApparentTemperatureMax() != rhs.getApparentTemperatureMax()) {
            return lhs.getApparentTemperatureMax() > rhs.getApparentTemperatureMax() ? 1 : -1;
        }

        if (!lhs.getSummary().equals(rhs.getSummary())) {
            return lhs.getSummary().compareTo(rhs.getSummary());
        }

        if (!lhs.getIcon().equals(rhs.getIcon())) {
            return lhs.getIcon().compareTo(rhs.getIcon());
        }

        if (!lhs.getSunriseTime().equals(rhs.getSunriseTime())) {
            return lhs.getSunriseTime().compareTo(rhs.getSunriseTime());
        }

        if (!lhs.getSunsetTime().equals(rhs.getSunsetTime())) {
            return lhs.getSunsetTime().compareTo(rhs.getSunsetTime());
        }

        if (lhs.getPrecipProbability() != rhs.getPrecipProbability()) {
            return lhs.getPrecipProbability() > rhs.getPrecipProbability() ? 1 : -1;
        }

        if (lhs.getHumidity() != rhs.getHumidity()) {
            return lhs.getHumidity() > rhs.getHumidity() ? 1 : -1;
        }

        if (lhs.getWindSpeed() != rhs.getWindSpeed()) {
            return lhs.getWindSpeed() > rhs.getWindSpeed() ? 1 : -1;
        }

        if (lhs.getCloudCover() != rhs.getCloudCover()) {
            return lhs.getCloudCover() > rhs.getCloudCover() ? 1 : -1;
        }

        if (!lhs.getPrecipType().equals(rhs.getPrecipType())) {
            return lhs.getPrecipType().compareTo(rhs.getPrecipType());
        }

        return 0;
    }

    public Date getTime() {
        return time;
    }

    public String getSummary() {
        return summary;
    }

    public String getIcon() {
        return icon;
    }

    public Date getSunriseTime() {
        return sunriseTime;
    }

    public Date getSunsetTime() {
        return sunsetTime;
    }

    public double getMoonPhase() {
        return moonPhase;
    }

    public double getPrecipIntensity() {
        return precipIntensity;
    }

    public double getPrecipIntensityMax() {
        return precipIntensityMax;
    }

    public Date getPrecipIntensityMaxTime() {
        return precipIntensityMaxTime;
    }

    public double getPrecipProbability() {
        return precipProbability;
    }

    public String getPrecipType() {
        return precipType;
    }

    public double getTemperatureMin() {
        return temperatureMin;
    }

    public Date getTemperatureMinTime() {
        return temperatureMinTime;
    }

    public double getTemperatureMax() {
        return temperatureMax;
    }

    public Date getTemperatureMaxTime() {
        return temperatureMaxTime;
    }

    public double getApparentTemperatureMin() {
        return apparentTemperatureMin;
    }

    public Date getApparentTemperatureMinTime() {
        return apparentTemperatureMinTime;
    }

    public double getApparentTemperatureMax() {
        return apparentTemperatureMax;
    }

    public Date getApparentTemperatureMaxTime() {
        return apparentTemperatureMaxTime;
    }

    public double getDewPoint() {
        return dewPoint;
    }

    public double getHumidity() {
        return humidity;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public double getWindBearing() {
        return windBearing;
    }

    public double getCloudCover() {
        return cloudCover;
    }

    public double getPressure() {
        return pressure;
    }

    public double getOzone() {
        return ozone;
    }

    public double getTemperature() {
        return temperature;
    }

    public static class ForecastDTOBuilder {
        private Date time;
        private String summary;
        private String icon;
        private Date sunriseTime;
        private Date sunsetTime;
        private double moonPhase;
        private double precipIntensity;
        private double precipIntensityMax;
        private Date precipIntensityMaxTime;
        private double precipProbability;
        private String precipType;
        private double temperatureMin;
        private Date temperatureMinTime;
        private double temperatureMax;
        private Date temperatureMaxTime;
        private double apparentTemperatureMin;
        private Date apparentTemperatureMinTime;
        private double apparentTemperatureMax;
        private Date apparentTemperatureMaxTime;
        private double dewPoint;
        private double humidity;
        private double windSpeed;
        private double windBearing;
        private double cloudCover;
        private double pressure;
        private double ozone;
        private double temperature;

        public ForecastDTOBuilder withATime(Date time) {
            this.time = time;
            return this;
        }

        public ForecastDTOBuilder withASummary(String summary) {
            this.summary = summary;
            return this;
        }

        public ForecastDTOBuilder withAnIcon(String icon) {
            this.icon = icon;
            return this;
        }

        public ForecastDTOBuilder withASunriseTime(Date sunriseTime) {
            this.sunriseTime = sunriseTime;
            return this;
        }

        public ForecastDTOBuilder withASunsetTime(Date sunsetTime) {
            this.sunsetTime = sunsetTime;
            return this;
        }

        public ForecastDTOBuilder withAMoonPhase(double moonPhase) {
            this.moonPhase = moonPhase;
            return this;
        }

        public ForecastDTOBuilder withAPrecipIntensity(double precipIntensity) {
            this.precipIntensity = precipIntensity;
            return this;
        }

        public ForecastDTOBuilder withAPrecipIntensityMax(double precipIntensityMax) {
            this.precipIntensityMax = precipIntensityMax;
            return this;
        }

        public ForecastDTOBuilder withAPrecipIntensityMaxTime(Date precipIntensityMaxTime) {
            this.precipIntensityMaxTime = precipIntensityMaxTime;
            return this;
        }

        public ForecastDTOBuilder withAPrecipProbability(double precipProbability) {
            this.precipProbability = precipProbability;
            return this;
        }

        public ForecastDTOBuilder withAPrecipType(String precipType) {
            this.precipType = precipType;
            return this;
        }

        public ForecastDTOBuilder withATemperatureMin(double temperatureMin) {
            this.temperatureMin = temperatureMin;
            return this;
        }

        public ForecastDTOBuilder withATemperatureMinTime(Date temperatureMinTime) {
            this.temperatureMinTime = temperatureMinTime;
            return this;
        }

        public ForecastDTOBuilder withATemperatureMax(double temperatureMax) {
            this.temperatureMax = temperatureMax;
            return this;
        }

        public ForecastDTOBuilder withATemperatureMaxTime(Date temperatureMaxTime) {
            this.temperatureMaxTime = temperatureMaxTime;
            return this;
        }

        public ForecastDTOBuilder withAnApparentTemperatureMin(double apparentTemperatureMin) {
            this.apparentTemperatureMin = apparentTemperatureMin;
            return this;
        }

        public ForecastDTOBuilder withAnApparentTemperatureMinTime(Date apparentTemperatureMinTime) {
            this.apparentTemperatureMinTime = apparentTemperatureMinTime;
            return this;
        }

        public ForecastDTOBuilder withAnApparentTemperatureMax(double apparentTemperatureMax) {
            this.apparentTemperatureMax = apparentTemperatureMax;
            return this;
        }

        public ForecastDTOBuilder withAnApparentTemperatureMaxTime(Date apparentTemperatureMaxTime) {
            this.apparentTemperatureMaxTime = apparentTemperatureMaxTime;
            return this;
        }

        public ForecastDTOBuilder withAHumidity(double humidity) {
            this.humidity = humidity;
            return this;
        }

        public ForecastDTOBuilder withADewPoint(double dewPoint) {
            this.dewPoint = dewPoint;
            return this;
        }

        public ForecastDTOBuilder withAWindSpeed(double windSpeed) {
            this.windSpeed = windSpeed;
            return this;
        }

        public ForecastDTOBuilder withAWindBearing(double windBearing) {
            this.windBearing = windBearing;
            return this;
        }

        public ForecastDTOBuilder withACloudCover(double cloudCover) {
            this.cloudCover = cloudCover;
            return this;
        }


        public ForecastDTOBuilder withAPressure(double pressure) {
            this.pressure = pressure;
            return this;
        }

        public ForecastDTOBuilder withAnOzone(double ozone) {
            this.ozone = ozone;
            return this;
        }

        public ForecastDTOBuilder withAnTemperature(double temperature) {
            this.temperature = temperature;
            return this;
        }

        public ForecastDTO createForecastDTO() {
            return new ForecastDTO(time, summary, icon, sunriseTime, sunsetTime, moonPhase, precipIntensity,
                    precipIntensityMax, precipIntensityMaxTime, precipProbability, precipType, temperatureMin,
                    temperatureMinTime, temperatureMax, temperatureMaxTime, apparentTemperatureMin,
                    apparentTemperatureMinTime, apparentTemperatureMax, apparentTemperatureMaxTime,
                    dewPoint, humidity, windSpeed, windBearing, cloudCover, pressure, ozone, temperature);
        }

    }
}
