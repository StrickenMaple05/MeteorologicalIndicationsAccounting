package project.structure.xml;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.structure.xml.tools.*;
import project.structure.xml.views.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.stream.Collectors;

public class JaxbConverterTest {

    private final LocalDate start =
            LocalDate.of(2020, 12, 3);

    private final LocalDate end = start.plusDays(3);

    private final Indication indication1 =
            new Indication(
                    start.plusDays(1),
                    0.009271523178807948
            );

    private final Indication indication2 =
            new Indication(
                    start.plusDays(1),
                    0.004092769440654843
            );

    private final Indication indication3 =
            new Indication(
                    end.minusDays(1),
                    0.010582010582010581
            );

    private final Indication indication4 =
            new Indication(
                    end.minusDays(1),
                    0.0013679890560875513
            );

    private final City krasnodar =
            new City("krasnodar", indication1, indication3);

    private final City moscow =
            new City("moscow", indication2, indication4);

    private final RatioStatistics statistics =
            new RatioStatistics(
                    start,
                    end,
                    Arrays.asList(
                            krasnodar,
                            moscow
                    )
            );

    /**
     * test on xml serialization
     * @throws IOException ...
     */
    @DisplayName("Serialization to xml test")
    @Test
    public void testToXml() throws IOException {
        JaxbConverter converter = new JaxbConverter();

        String expectedXml = Files.lines(
                Path.of("src/test/resources/pattern.xml"))
                .collect(Collectors.joining("\r\n"))
                .replace("    ", "  ")
                .concat("\r\n");

        String actualXml = converter.toXml(statistics);

        Assertions.assertEquals(expectedXml, actualXml);
    }

    /**
     * test on deserialization from xml
     * @throws IOException ...
     */
    @DisplayName("Deserialization from xml test")
    @Test
    public void testFromXml() throws IOException {
        JaxbConverter converter = new JaxbConverter();
        String xml = converter.toXml(statistics);
        Assertions.assertEquals(statistics,
                converter.fromXml(
                        xml,
                        RatioStatistics.class
                )
        );
    }
}