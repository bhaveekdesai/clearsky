package io.bhaveekdesai.api.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
public class Weather {
    @Id
    private String id;
    private String city;
    private String description;
    private Double humidity;
    private Double pressure;
    private Double temperature;
    @OneToOne(cascade = CascadeType.ALL)
    private Wind wind;
    @Column(name="timestamp", columnDefinition="TIMESTAMP DEFAULT")
    private Timestamp timestamp;

    public Weather() {
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getHumidity() {
        return humidity;
    }

    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }

    public Double getPressure() {
        return pressure;
    }

    public void setPressure(Double pressure) {
        this.pressure = pressure;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
