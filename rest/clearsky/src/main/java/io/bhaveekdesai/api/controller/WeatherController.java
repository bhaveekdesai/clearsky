package io.bhaveekdesai.api.controller;

import io.bhaveekdesai.api.constants.URI;
import io.bhaveekdesai.api.entity.Weather;
import io.bhaveekdesai.api.service.WeatherService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = URI.WEATHER)
public class WeatherController {

    private WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @CrossOrigin(origins = "http://mocker.egen.io")
    //Consume data from mock sensor
    @Transactional
    @RequestMapping(method = RequestMethod.POST)
    public Weather postWeather(@RequestBody Weather weather) {
        return weatherService.postWeather(weather);
    }

    //Get the list of cities that have ever reported their weather in the past.
    @Transactional(readOnly = true)
    @RequestMapping(method = RequestMethod.GET, value = "/cities")
    public List<String> getAllCities() {
        return weatherService.getAllCities();
    }

    //Get all the weather data.
    @Transactional(readOnly = true)
    @RequestMapping(method = RequestMethod.GET)
    public List<Weather> getAllWeather() {
        return weatherService.getAllWeather();
    }

    //Get the latest weather for a given city.
    @Transactional(readOnly = true)
    @RequestMapping(method = RequestMethod.GET, value = "/{"+URI.CITY+"}")
    public Weather getCurrentWeather(@PathVariable(URI.CITY) String city) {
        return weatherService.getCurrentWeather(city);
    }

    //Get the latest weather property for a given city,
    //e.g get the latest temperature for Chicago, get the latest humidity for Miami.
    @Transactional(readOnly = true)
    @RequestMapping(method = RequestMethod.GET, value = "/{"+URI.CITY+"}"+"/{"+URI.PARAM+"}")
    public Object getLatestParameter(@PathVariable(URI.CITY) String city, @PathVariable(URI.PARAM) String param) {
        return weatherService.getLatestParameter(city, param);
    }

    //Get average weather for a given city.
    @Transactional(readOnly = true)
    @RequestMapping(method = RequestMethod.GET, value = "/{"+URI.CITY+"}"+"/avg/{"+URI.DURATION+"}")
    public Weather getHourlyWeather(@PathVariable(URI.CITY) String city, @PathVariable(URI.DURATION) String duration) {
        return weatherService.getAverageWeather(city, duration);
    }

}
