package io.bhaveekdesai.api.controller;

import io.bhaveekdesai.api.constants.URI;
import io.bhaveekdesai.api.entity.Weather;
import io.bhaveekdesai.api.service.WeatherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = URI.WEATHER)
@Api(tags = "weather")
public class WeatherController {

    private WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    //Consume data from mock sensor
    @ApiOperation(value = "Post weather data in JSON", notes = "Returns weather data saved in database")
    @ApiResponses(value={
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @CrossOrigin(origins = "http://mocker.egen.io")
    @Transactional
    @RequestMapping(method = RequestMethod.POST)
    public Weather postWeather(@RequestBody Weather weather) {
        return weatherService.postWeather(weather);
    }

    //Get the list of cities that have ever reported their weather in the past.
    @ApiOperation(value = "Get names of cities", notes = "Returns names of cities for whom weather data is available")
    @ApiResponses(value={
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @Transactional(readOnly = true)
    @RequestMapping(method = RequestMethod.GET, value = "/cities")
    public List<String> getAllCities() {
        return weatherService.getAllCities();
    }

    //Get all the weather data.
    @ApiOperation(value = "Get all weather data", notes = "Returns all records of weather")
    @ApiResponses(value={
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @Transactional(readOnly = true)
    @RequestMapping(method = RequestMethod.GET)
    public List<Weather> getAllWeather() {
        return weatherService.getAllWeather();
    }

    //Get the latest weather for a given city.
    @ApiOperation(value = "Get latest weather ", notes = "Returns latest weather for a given city")
    @ApiResponses(value={
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @Transactional(readOnly = true)
    @RequestMapping(method = RequestMethod.GET, value = "/{"+URI.CITY+"}")
    public Weather getCurrentWeather(@PathVariable(URI.CITY) String city) {
        return weatherService.getCurrentWeather(city);
    }

    //Get the latest weather property for a given city,
    //e.g get the latest temperature for Chicago, get the latest humidity for Miami.
    @ApiOperation(value = "Get latest weather parameter info", notes = "Returns latest weather parameter info for a given city")
    @ApiResponses(value={
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 401, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @Transactional(readOnly = true)
    @RequestMapping(method = RequestMethod.GET, value = "/{"+URI.CITY+"}"+"/{"+URI.PARAM+"}")
    public Object getLatestParameter(@PathVariable(URI.CITY) String city, @PathVariable(URI.PARAM) String param) {
        return weatherService.getLatestParameter(city, param);
    }

    //Get average weather for a given city.
    @ApiOperation(value = "Get average weather", notes = "Returns average weather (daily/hourly) for a given city")
    @ApiResponses(value={
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 401, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @Transactional(readOnly = true)
    @RequestMapping(method = RequestMethod.GET, value = "/{"+URI.CITY+"}"+"/avg/{"+URI.DURATION+"}")
    public Weather getHourlyWeather(@PathVariable(URI.CITY) String city, @PathVariable(URI.DURATION) String duration) {
        return weatherService.getAverageWeather(city, duration);
    }

}
