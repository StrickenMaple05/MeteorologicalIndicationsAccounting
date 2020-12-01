package ru.StrickenMaple05.croc.java.school.database;

import java.time.LocalDate;
import java.util.Objects;

public class MeteorologicalIndication {

    /**
     * record identifier
     */
    private final int id;
    /**
     * city name
     */
    private String city_name;
    /**
     * meteorological indication time
     */
    private LocalDate time;
    /**
     * air temperature
     */
    private double air_temperature;
    /**
     * atmosphere pressure
     */
    private double atmosphere_pressure;

    public MeteorologicalIndication(int id,
                                    String city_name,
                                    LocalDate time,
                                    double air_temperature,
                                    double atmosphere_pressure) {
        this.id = id;
        this.city_name = city_name;
        this.time = time;
        this.air_temperature = air_temperature;
        this.atmosphere_pressure = atmosphere_pressure;
    }

    public int getId() {
        return id;
    }

    public String getCityName() {
        return city_name;
    }

    public LocalDate getTime() {
        return time;
    }

    public double getAirTemperature() {
        return air_temperature;
    }

    public double getAtmospherePressure() {
        return atmosphere_pressure;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public void setTime(LocalDate time) {
        this.time = time;
    }

    public void setAirTemperature(double air_temperature) {
        this.air_temperature = air_temperature;
    }

    public void setAtmospherePressure(double atmosphere_pressure) {
        this.atmosphere_pressure = atmosphere_pressure;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MeteorologicalIndication that = (MeteorologicalIndication) o;
        return id == that.id &&
                Double.compare(that.air_temperature, air_temperature) == 0 &&
                Double.compare(that.atmosphere_pressure, atmosphere_pressure) == 0 &&
                city_name.equals(that.city_name) &&
                time.equals(that.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, city_name, time, air_temperature, atmosphere_pressure);
    }

    @Override
    public String toString() {
        return "MeteorologicalIndication{" +
                "id=" + id +
                ", city_name='" + city_name + '\'' +
                ", time=" + time +
                ", air_temperature=" + air_temperature +
                ", atmosphere_pressure=" + atmosphere_pressure +
                '}';
    }
}
