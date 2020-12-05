package project.structure.xml.views;

import project.structure.xml.tools.TimeAdapter;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;
import java.util.Objects;

/**
 * xml view keeping period start and end
 */
@XmlRootElement(name = "period")
public class TimePeriod {

    /**
     * period start
     */
    @XmlAttribute(name = "start")
    @XmlJavaTypeAdapter(TimeAdapter.class)
    private final LocalDate start;

    /**
     * period end
     */
    @XmlAttribute(name = "end")
    @XmlJavaTypeAdapter(TimeAdapter.class)
    private final LocalDate end;

    public TimePeriod() {
        start = LocalDate.now();
        end = LocalDate.now();
    }

    public TimePeriod(LocalDate start,
                      LocalDate end) {
        this.start = start;
        this.end = end;
    }

    public LocalDate getStart() {
        return start;
    }

    public LocalDate getEnd() {
        return end;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimePeriod that = (TimePeriod) o;
        return start.equals(that.start) &&
                end.equals(that.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }

    @Override
    public String toString() {
        return "TimePeriod{" +
                "start=" + start +
                ", end=" + end +
                '}';
    }
}
