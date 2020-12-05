package project.structure;

import org.junit.jupiter.api.*;

import project.structure.database.*;
import project.structure.xml.views.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * tests on Statistics methods
 */
@DisplayName("Statistics methods tests")
public class StatisticsTest {

    private final LocalDate start =
            LocalDate.of(2020, 12, 3);

    private final LocalDate end = start.plusDays(3);

    private Repository repository;

    private final File file = new File("temp.xml");

    private final File pattern =
            new File("src/test/resources/pattern.xml");

    private final MeteorologicalIndication indication1 =
            new MeteorologicalIndication(
                    1,
                    "krasnodar",
                    start.plusDays(1),
                    7,755);

    private final MeteorologicalIndication indication2 =
            new MeteorologicalIndication(
                    2,
                    "moscow",
                    start.plusDays(1),
                    3,
                    733);

    private final MeteorologicalIndication indication3 =
            new MeteorologicalIndication(
                    3,
                    "krasnodar",
                    end.minusDays(1),
                    8,
                    756);

    private final MeteorologicalIndication indication4 =
            new MeteorologicalIndication(
                    4,
                    "moscow",
                    end.minusDays(1),
                    1,
                    731);

    private final City krasnodar = new City("krasnodar",
            toStatsIndication(indication1),
            toStatsIndication(indication3));

    private final City moscow = new City("moscow",
            toStatsIndication(indication2),
            toStatsIndication(indication4));

    @BeforeEach
    public void init() {

        try {
            repository = new Repository(
                    new DataSourceProvider().
                            getDataSource());
        } catch (IOException exception) {
            exception.printStackTrace();
        }

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
     */
    @DisplayName("Test on saving stats as xml")
    @Test
    public void testSaveAsFile() {

        Statistics statistics = new Statistics(repository);
        statistics.groupByCities(start, end);
        statistics.saveAs(file);

        try {
            String expectedXml = Files.lines(pattern.toPath())
                    .collect(Collectors.joining("\r\n"))
                    .replace("    ", "  ")
                    .concat("\r\n");

            String actualXml = Files.lines(file.toPath())
                    .collect(Collectors.joining("\r\n"))
                    .concat("\r\n");

            Assertions.assertEquals(expectedXml, actualXml);

        } catch (IOException exception) {
            exception.printStackTrace();
        }

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
