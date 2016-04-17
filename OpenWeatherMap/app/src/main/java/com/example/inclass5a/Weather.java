package com.example.inclass5a;

/**
 * Created by Rakesh Balan on 9/28/2015.
 */
public class Weather {

    private String temperature;
    private String  humidity;
    private String pressure;
    private String windSpeed;
    private String windDirection;
    private String clouds;
    private String precipitation;
    private String symbol;


    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public String getClouds() {
        return clouds;
    }

    public void setClouds(String clouds) {
        this.clouds = clouds;
    }

    public String getPrecipitation() {
        return precipitation;
    }

    public void setPrecipitation(String precipitation) {
        this.precipitation = precipitation;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "temperature='" + temperature + '\'' +
                ", humidity='" + humidity + '\'' +
                ", pressure='" + pressure + '\'' +
                ", windSpeed='" + windSpeed + '\'' +
                ", windDirection='" + windDirection + '\'' +
                ", clouds='" + clouds + '\'' +
                ", precipitation='" + precipitation + '\'' +
                ", symbol='" + symbol + '\'' +
                '}';
    }
}
