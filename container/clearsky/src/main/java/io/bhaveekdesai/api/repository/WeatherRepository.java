package io.bhaveekdesai.api.repository;

import io.bhaveekdesai.api.entity.Weather;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;

public interface WeatherRepository extends Repository<Weather, String> {

    Weather save(Weather weather);

    @Query("SELECT DISTINCT w.city FROM Weather w ORDER BY w.city ASC")
    List<String> findAllCities();

    List<Weather> findAll();
    Weather findTopByCityOrderByTimestampDesc(String city);

    @Query("SELECT w FROM Weather w WHERE w.city=:wcity AND w.timestamp>:wtimestamp")
    List<Weather> findByCityAndTimestampIsAfter(@Param("wcity") String city, @Param("wtimestamp") Timestamp ts);

    Boolean existsByCity(String City);

}
