package io.bhaveekdesai.api.repository;

import io.bhaveekdesai.api.entity.Weather;

import java.util.List;

public interface WeatherRepository {
    Weather postWeather(Weather weather);
    List<String> getAllCities();
    List<Weather> getAllWeather();
    Weather getCurrentWeather(String city);
    List<Weather> getHourlyWeather(String city);
    List<Weather> getDailyWeather(String city);

    Boolean findCity(String city);
}
