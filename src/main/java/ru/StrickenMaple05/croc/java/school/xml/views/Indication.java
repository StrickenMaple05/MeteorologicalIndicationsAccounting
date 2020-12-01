package ru.StrickenMaple05.croc.java.school.xml.views;

import ru.StrickenMaple05.croc.java.school.xml.tools.TimeAdapter;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;
import java.util.Objects;

@XmlRootElement(name = "indication")
public class Indication {

    /**
     * time indication was carried out
     */
    @XmlAttribute(name = "time")
    @XmlJavaTypeAdapter(TimeAdapter.class)
    private final LocalDate time;

    /**
     * air temperature to atmosphere pressure ratio
     */
    @XmlAttribute(name = "ratio")
    private final double ratio;

    public Indication() {
        time = LocalDate.now();
        ratio = 0;
    }

    public Indication(LocalDate time, double ratio) {
        this.time = time;
        this.ratio = ratio;
    }

    public LocalDate getTime() {
        return time;
    }

    public double getRatio() {
        return ratio;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Indication that = (Indication) o;
        return Double.compare(that.ratio, ratio) == 0 &&
                time.equals(that.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(time, ratio);
    }

    @Override
    public String toString() {
        return "Indication{" +
                "time=" + time +
                ", ratio=" + ratio +
                '}';
    }
}
