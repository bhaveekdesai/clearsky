package io.bhaveekdesai.api.service.impl;

import io.bhaveekdesai.api.constants.URI;
import io.bhaveekdesai.api.entity.Weather;
import io.bhaveekdesai.api.entity.Wind;
import io.bhaveekdesai.api.exception.BadRequestException;
import io.bhaveekdesai.api.exception.NotFoundException;
import io.bhaveekdesai.api.repository.WeatherRepository;
import io.bhaveekdesai.api.service.WeatherService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class WeatherServiceImpl implements WeatherService {

    private WeatherRepository weatherRepository;

    public WeatherServiceImpl(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    @Override
    public Weather postWeather(Weather weather) {
        return weatherRepository.postWeather(weather);
    }

    @Override
    public List<String> getAllCities() {
        return weatherRepository.getAllCities();
    }

    @Override
    public List<Weather> getAllWeather() {
        return weatherRepository.getAllWeather();
    }

    @Override
    public Weather getCurrentWeather(String city) {
        if(!weatherRepository.findCity(city)) {
            throw new NotFoundException("City not found");
        }
        return weatherRepository.getCurrentWeather(city);
    }

    @Override
    public Object getLatestParameter(String city, String param) {
        if(!weatherRepository.findCity(city)) {
            throw new NotFoundException("City not found: "+city);
        }

        switch(param) {
            case URI.TEMPERATURE:
                return weatherRepository.getCurrentWeather(city).getTemperature();

            case URI.HUMIDITY:
                return weatherRepository.getCurrentWeather(city).getHumidity();

            case URI.PRESSURE:
                return weatherRepository.getCurrentWeather(city).getPressure();

            case URI.WIND:
                return weatherRepository.getCurrentWeather(city).getWind();

            default:
                throw new BadRequestException("Invalid weather parameter entered: "+param);
        }
    }

    @Override
    public Weather getAverageWeather(String city, String duration) {
        if(!weatherRepository.findCity(city)) {
            throw new NotFoundException("City not found: "+city);
        }

        List<Weather> records;
        switch(duration) {
            case URI.HOURLY:
                records = weatherRepository.getHourlyWeather(city);
                break;

            case URI.DAILY:
                records = weatherRepository.getDailyWeather(city);
                break;

            default:
                throw new BadRequestException("Invalid duration: "+duration);

        }

        Weather weather = new Weather();
        weather.setCity(city);
        weather.setDescription("");
        weather.setTimestamp(new Timestamp(System.currentTimeMillis()));

        weather.setHumidity(
                records.stream()
                        .mapToDouble(Weather::getHumidity)
                        .average()
                        .orElse(Double.NaN)
        );

        weather.setTemperature(
                records.stream()
                        .mapToDouble(Weather::getTemperature)
                        .average()
                        .orElse(Double.NaN)

        );

        weather.setPressure(
                records.stream()
                        .mapToDouble(Weather::getPressure)
                        .average()
                        .orElse(Double.NaN)
        );

        Wind wind = new Wind();
        wind.setSpeed(
                records.stream()
                        .mapToDouble(w -> w.getWind().getSpeed())
                        .average()
                        .orElse(Double.NaN)
        );
        wind.setDegree(
                records.stream()
                        .mapToDouble(w -> w.getWind().getDegree())
                        .average()
                        .orElse(Double.NaN)
        );

        weather.setWind(wind);

        return weather;
    }
}
