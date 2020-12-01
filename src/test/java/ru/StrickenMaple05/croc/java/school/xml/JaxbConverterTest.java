package ru.StrickenMaple05.croc.java.school.xml;

import org.junit.jupiter.api.*;
import ru.StrickenMaple05.croc.java.school.xml.tools.JaxbConverter;
import ru.StrickenMaple05.croc.java.school.xml.tools.TimeAdapter;
import ru.StrickenMaple05.croc.java.school.xml.views.City;
import ru.StrickenMaple05.croc.java.school.xml.views.Indication;
import ru.StrickenMaple05.croc.java.school.xml.views.RatioStatistics;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;

public class JaxbConverterTest {

    private final LocalDate start = LocalDate.now().minusDays(2);
    private final LocalDate end = LocalDate.now().plusDays(1);

    private final Indication indication1 = new Indication(start.plusDays(1), 0.03);
    private final Indication indication2 = new Indication(end.minusDays(1), 0.03);

    private final City krasnodar = new City("krasnodar", indication1, indication2);
    private final City moscow = new City("moscow", indication1, indication2);

    private final RatioStatistics statistics = new RatioStatistics(
            start, end, Arrays.asList(krasnodar, moscow));

    /**
     * test on xml serialization
     * @throws IOException ...
     */
    @DisplayName("Serialization to xml test")
    @Test
    public void testToXml() throws IOException {

        JaxbConverter converter = new JaxbConverter();
        String xml = converter.toXml(statistics);
        Assertions.assertEquals(fillPattern(start, end), xml);
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
                converter.fromXml(xml, RatioStatistics.class));
    }

    /**
     * local method to return xml pattern filled
     * by values based on current start/end time
     * @param start start
     * @param end end
     * @return xml
     */
    private String fillPattern(LocalDate start, LocalDate end) {
        String filledPattern;
        LocalDate time1 = start.plusDays(1);
        LocalDate time2 = end.minusDays(1);

        TimeAdapter adapter = new TimeAdapter();
        String pattern = "<statistics>\r\n" +
                "  <period start=\"%s\" end=\"%s\"/>\r\n" +
                "  <cities>\r\n" +
                "    <city name=\"krasnodar\">\r\n" +
                "      <indications>\r\n" +
                "        <indication time=\"%s\" ratio=\"0.03\"/>\r\n" +
                "        <indication time=\"%s\" ratio=\"0.03\"/>\r\n" +
                "      </indications>\r\n" +
                "    </city>\r\n" +
                "    <city name=\"moscow\">\r\n" +
                "      <indications>\r\n" +
                "        <indication time=\"%s\" ratio=\"0.03\"/>\r\n" +
                "        <indication time=\"%s\" ratio=\"0.03\"/>\r\n" +
                "      </indications>\r\n" +
                "    </city>\r\n" +
                "  </cities>\r\n" +
                "</statistics>\r\n";

        filledPattern = String.format(pattern,
                adapter.marshal(start),
                adapter.marshal(end),
                adapter.marshal(time1),
                adapter.marshal(time2),
                adapter.marshal(time1),
                adapter.marshal(time2));
        return filledPattern;
    }
}
