package ru.StrickenMaple05.croc.java.school;

import org.junit.jupiter.api.*;
import ru.StrickenMaple05.croc.java.school.database.DataSourceProvider;
import ru.StrickenMaple05.croc.java.school.database.MeteorologicalIndication;
import ru.StrickenMaple05.croc.java.school.database.Repository;
import ru.StrickenMaple05.croc.java.school.xml.tools.TimeAdapter;
import ru.StrickenMaple05.croc.java.school.xml.views.City;
import ru.StrickenMaple05.croc.java.school.xml.views.Indication;
import ru.StrickenMaple05.croc.java.school.xml.views.RatioStatistics;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.*;

/**
 * tests on Statistics methods
 */
@DisplayName("Statistics methods tests")
public class StatisticsTest {

    private final LocalDate start = LocalDate.now().minusDays(2);

    private final LocalDate end = LocalDate.now().plusDays(2);

    private Repository repository;

    private final File file = new File("stats.xml");

    private final File savedFile =
            new File("src/test/java/resources/stats.xml");

    private final String krasnodarName = "krasnodar";

    private final String moscowName = "moscow";

    private final MeteorologicalIndication indication1 =
            new MeteorologicalIndication(
                    1, krasnodarName,
                    LocalDate.now().minusDays(1),
                    7,755);
    private final MeteorologicalIndication indication2 =
            new MeteorologicalIndication(
                    2, moscowName, LocalDate.now().minusDays(1),
                    3, 733);
    private final MeteorologicalIndication indication3 =
            new MeteorologicalIndication(
                    3, krasnodarName, LocalDate.now(),
                    8,756);
    private final MeteorologicalIndication indication4 =
            new MeteorologicalIndication(
                    4, moscowName, LocalDate.now(),
                    1, 731);

    private final City krasnodar = new City(krasnodarName,
            toStatsIndication(indication1),
            toStatsIndication(indication3));

    private final City moscow = new City(moscowName,
            toStatsIndication(indication2),
            toStatsIndication(indication4));

    @BeforeEach
    public void init() throws IOException {
        repository = new Repository(
                new DataSourceProvider().
                        getDataSource());

        repository.create(indication1, indication2,
                          indication3, indication4);
    }

    @AfterEach
    public void clean() throws IOException {
        repository.dropTable();
        Files.deleteIfExists(file.toPath());
    }

    /**
     * test on grouping indications by cities
     */
    @DisplayName("Test on group by cities")
    @Test
    public void testGroupByCities() {

        Statistics statistics = new Statistics(repository);
        statistics.groupByCities(start, end);

        RatioStatistics ratioStatistics = new RatioStatistics(
                start, end, Arrays.asList(krasnodar, moscow));
        Assertions.assertEquals(ratioStatistics,
                statistics.getRatioStatistics());
    }

    /**
     * test on serializing ratio statistics to xml in file
     * @throws IOException ...
     */
    @DisplayName("Test on saving stats as xml")
    @Test
    public void testSaveAsFile() throws IOException {
        Statistics statistics = new Statistics(repository);
        statistics.groupByCities(start, end);
        statistics.saveAs(file);
        statistics.saveAs(savedFile);

        final String pattern = "<statistics>\n" +
                "  <period start=\"%s\" end=\"%s\"/>\n" +
                "  <cities>\n" +
                "    <city name=\"krasnodar\">\n" +
                "      <indications>\n" +
                "        <indication time=\"%s\" ratio=\"%s\"/>\n" +
                "        <indication time=\"%s\" ratio=\"%s\"/>\n" +
                "      </indications>\n" +
                "    </city>\n" +
                "    <city name=\"moscow\">\n" +
                "      <indications>\n" +
                "        <indication time=\"%s\" ratio=\"%s\"/>\n" +
                "        <indication time=\"%s\" ratio=\"%s\"/>\n" +
                "      </indications>\n" +
                "    </city>\n" +
                "  </cities>\n" +
                "</statistics>";

        FileReader reader = new FileReader(file);
        Scanner scanner = new Scanner(reader);
        String resultXml = "";
        while (scanner.hasNextLine()) {
            resultXml = resultXml.concat(scanner.nextLine() + "\n");
        }
        reader.close();

        TimeAdapter adapter = new TimeAdapter();
        String xml = String.format(pattern,
                adapter.marshal(start),
                adapter.marshal(end),
                adapter.marshal(indication1.getTime()),
                indication1.getAirTemperature()/
                        indication1.getAtmospherePressure(),
                adapter.marshal(indication3.getTime()),
                indication3.getAirTemperature()/
                        indication3.getAtmospherePressure(),
                adapter.marshal(indication2.getTime()),
                indication2.getAirTemperature()/
                        indication2.getAtmospherePressure(),
                adapter.marshal(indication4.getTime()),
                indication4.getAirTemperature()/
                        indication4.getAtmospherePressure());

                Assertions.assertEquals(xml + "\n", resultXml);
    }

    /**
     * local method to transform meteorological
     * indication to xml view called indication
     * @param indication meteorological indication
     * @return indication
     */
    private Indication toStatsIndication(
            MeteorologicalIndication indication) {
        return new Indication(
                indication.getTime(),
                indication.getAirTemperature()/
                        indication.getAtmospherePressure());
    }
}
