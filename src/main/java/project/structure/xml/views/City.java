package project.structure.xml.views;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * xml view necessary for ratio statistics xml output
 */
@XmlType(name = "city")
public class City {

    /**
     * city name
     */
    @XmlAttribute(name = "name")
    private final String name;

    /**
     * city meteorological indications
     */
    @XmlElementWrapper(name = "indications", nillable = true)
    @XmlElement(name = "indication")
    private final List<Indication> indications;

    public City() {
        name = "";
        indications = new ArrayList<>();
    }

    public City(String name) {
        this.name = name;
        indications = new ArrayList<>();
    }

    public City(String name, Indication... indications) {
        this.name = name;
        this.indications = new ArrayList<>();
        this.indications.addAll(Arrays.asList(indications));
    }

    /**
     * adds indication
     * @param indication indication
     */
    public void add(Indication indication) {
        indications.add(indication);
    }

    /**
     * adds indications
     * @param indications indications
     */
    public void add(Indication... indications) {
        for (Indication indication : indications) {
            add(indication);
        }
    }

    public String getName() {
        return name;
    }

    public List<Indication> getIndications() {
        return indications;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return name.equals(city.name) &&
                indications.equals(city.indications);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, indications);
    }

    @Override
    public String toString() {
        return "City{" +
                "name='" + name + '\'' +
                ", indications=" + indications +
                '}';
    }
}
