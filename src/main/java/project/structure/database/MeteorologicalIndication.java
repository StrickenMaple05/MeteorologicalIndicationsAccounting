package project.structure.database;

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
    private String cityName;
    /**
     * meteorological indication time
     */
    private LocalDate time;
    /**
     * air temperature
     */
    private double airTemperature;
    /**
     * atmosphere pressure
     */
    private double atmospherePressure;

    public MeteorologicalIndication(int id,
                                    String cityName,
                                    LocalDate time,
                                    double airTemperature,
                                    double atmospherePressure) {
        this.id = id;
        this.cityName = cityName;
        this.time = time;
        this.airTemperature = airTemperature;
        this.atmospherePressure = atmospherePressure;
    }

    public int getId() {
        return id;
    }

    public String getCityName() {
        return cityName;
    }

    public LocalDate getTime() {
        return time;
    }

    public double getAirTemperature() {
        return airTemperature;
    }

    public double getAtmospherePressure() {
        return atmospherePressure;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setTime(LocalDate time) {
        this.time = time;
    }

    public void setAirTemperature(double air_temperature) {
        this.airTemperature = air_temperature;
    }

    public void setAtmospherePressure(double atmosphere_pressure) {
        this.atmospherePressure = atmosphere_pressure;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MeteorologicalIndication that = (MeteorologicalIndication) o;
        return id == that.id &&
                Double.compare(
                        that.airTemperature,
                        airTemperature
                ) == 0 &&
                Double.compare(
                        that.atmospherePressure,
                        atmospherePressure
                ) == 0 &&
                cityName.equals(that.cityName) &&
                time.equals(that.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                id,
                cityName,
                time,
                airTemperature,
                atmospherePressure);
    }

    @Override
    public String toString() {
        return "MeteorologicalIndication{" +
                "id=" + id +
                ", city_name='" + cityName + '\'' +
                ", time=" + time +
                ", air_temperature=" + airTemperature +
                ", atmosphere_pressure=" + atmospherePressure +
                '}';
    }
}