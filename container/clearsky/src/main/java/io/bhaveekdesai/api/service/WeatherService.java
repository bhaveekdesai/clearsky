package io.bhaveekdesai.api.service;

import io.bhaveekdesai.api.entity.Weather;

import java.util.List;

public interface WeatherService {
    Weather postWeather(Weather weather);
    List<String> getAllCities();
    List<Weather> getAllWeather();
    Weather getCurrentWeather(String city);
    Object getLatestParameter(String city, String param);
    Weather getAverageWeather(String city, String duration);
}
