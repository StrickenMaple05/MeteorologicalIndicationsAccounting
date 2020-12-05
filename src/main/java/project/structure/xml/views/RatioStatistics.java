package project.structure.xml.views;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * ratio statistics obtained by time period
 */
@XmlRootElement(name = "statistics")
public class RatioStatistics {

    /**
     * time period
     */
    @XmlElement(name = "period")
    private final TimePeriod period;

    /**
     * cities by which stats are grouped
     */
    @XmlElementWrapper(name = "cities", nillable = true)
    @XmlElement(name = "city")
    private final List<City> cities;

    public RatioStatistics() {
        period = new TimePeriod();
        cities = new ArrayList<>();
    }

    public RatioStatistics(LocalDate start,
                           LocalDate end,
                           List<City> cities) {
        period = new TimePeriod(start, end);
        this.cities = cities;
    }

    public TimePeriod getPeriod() {
        return period;
    }

    public List<City> getCities() {
        return cities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RatioStatistics that = (RatioStatistics) o;
        return period.equals(that.period) &&
                cities.equals(that.cities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(period, cities);
    }

    @Override
    public String toString() {
        return "RatioStatistics{" +
                "period=" + period +
                ", cities=" + cities +
                '}';
    }
}
