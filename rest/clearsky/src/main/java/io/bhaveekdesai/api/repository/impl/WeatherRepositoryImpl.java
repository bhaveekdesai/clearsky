package io.bhaveekdesai.api.repository.impl;

import io.bhaveekdesai.api.entity.Weather;
import io.bhaveekdesai.api.repository.WeatherRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.sql.Timestamp;
import java.util.List;

@Repository
public class WeatherRepositoryImpl implements WeatherRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Weather postWeather(Weather weather) {
        em.persist(weather.getWind());
        em.persist(weather);
        return weather;
    }

    @Override
    public List<String> getAllCities() {
        TypedQuery<String> query = em.createNamedQuery("weather.listCities", String.class);
        List<String> cities = query.getResultList();
        return cities;
    }

    @Override
    public List<Weather> getAllWeather() {
        TypedQuery<Weather> query = em.createNamedQuery("weather.findAll", Weather.class);
        List<Weather> records = query.getResultList();
        return records;
    }

    @Override
    public Weather getCurrentWeather(String city) {
        TypedQuery<Weather> query = em.createNamedQuery("weather.findCurrent", Weather.class)
                                        .setParameter("wcity", city)
                                        .setMaxResults(1);
        Weather weather = query.getSingleResult();
        return weather;
    }

    @Override
    public List<Weather> getHourlyWeather(String city) {
        Timestamp ts = new Timestamp(System.currentTimeMillis()-3600000);
        TypedQuery<Weather> query = em.createNamedQuery("weather.findAvg", Weather.class)
                                        .setParameter("wtimestamp", ts)
                                        .setParameter("wcity", city);
        return query.getResultList();
    }

    @Override
    public List<Weather> getDailyWeather(String city) {
        Timestamp ts = new Timestamp(System.currentTimeMillis()-86400000);
        TypedQuery<Weather> query = em.createNamedQuery("weather.findAvg", Weather.class)
                                        .setParameter("wtimestamp", ts)
                                        .setParameter("wcity", city);
        return query.getResultList();
    }

    @Override
    public Boolean findCity(String city) {
        if(getCurrentWeather(city)==null) return false;
        else return true;
    }
}
