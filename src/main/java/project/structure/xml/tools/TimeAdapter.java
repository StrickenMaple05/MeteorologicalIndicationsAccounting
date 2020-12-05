package project.structure.xml.tools;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TimeAdapter extends XmlAdapter<String, LocalDate> {

    /**
     * date time formatter based on given pattern
     */
    private final DateTimeFormatter dateTimeFormatter =
            DateTimeFormatter.ofPattern("dd.MM.yyyy");

    /**
     * returns LocalDate object parsed by s
     * @param s string value
     * @return LocalDate object
     */
    @Override
    public LocalDate unmarshal(String s) {
        return LocalDate.parse(s, dateTimeFormatter);
    }

    /**
     * returns string value obtained by formatting LocalDate object
     * @param time time
     * @return string value
     */
    @Override
    public String marshal(LocalDate time){
        return time.format(dateTimeFormatter);
    }
}
