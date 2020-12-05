package project.structure;

import project.structure.database.*;
import project.structure.xml.tools.JaxbConverter;
import project.structure.xml.views.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * class generating ratio statistics and managing xml output
 */
public class Statistics {

    /**
     * repository
     */
    private final Repository repository;

    /**
     * ratio statistics
     */
    private RatioStatistics ratioStatistics;

    public Statistics(Repository repository) {
        this.repository = repository;
        this.ratioStatistics = null;
    }

    /**
     * takes indications in given period,
     * groups by cities and creates ratio statistics
     * @param start period start-time
     * @param end period end-time
     */
    public void groupByCities(LocalDate start,
                              LocalDate end) {
        Stream<MeteorologicalIndication> indications =
                repository.findAll().stream().filter(
                        i -> i.getTime().isAfter(start) &&
                                i.getTime().isBefore(end));
        Map<String, City> cities = new LinkedHashMap<>();
        indications.forEach(i -> addIndication(i,cities));
        ratioStatistics = new RatioStatistics(
                start, end, new ArrayList<>(cities.values()));
    }

    /**
     * saves current ratio statistics to the file
     * @param file file path
     */
    public void saveAs(File file) {
        if (ratioStatistics == null) {
            return;
        }
        try (BufferedWriter writer =
                     new BufferedWriter(
                             new FileWriter(file)
                     )
        ) {
            writer.write(new JaxbConverter().toXml(ratioStatistics));
            writer.flush();
        } catch (IOException ignored) {}
    }

    public Repository getRepository() {
        return repository;
    }

    public RatioStatistics getRatioStatistics() {
        return ratioStatistics;
    }

    /**
     * local method to turn meteorological
     * indication to xml view called indication
     * @param meteorologicalIndication meteorological indication
     * @param cities cities map
     */
    private void addIndication(
            MeteorologicalIndication meteorologicalIndication,
            Map<String, City> cities) {

        Indication indication = new Indication(
                meteorologicalIndication.getTime(),
                meteorologicalIndication
                        .getAirTemperature()/
                        meteorologicalIndication
                                .getAtmospherePressure());

        String cityName = meteorologicalIndication.getCityName();

        if (!cities.containsKey(cityName)) {
            cities.put(cityName, new City(cityName));
        }
        cities.get(cityName).add(indication);
    }
}