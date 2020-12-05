package project.structure.xml.tools;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

import java.io.IOException;

/**
 * objects serialization/deserialization
 */
public class JaxbConverter {

    /**
     * creates XmlMapper
     * @return XmlMapper
     */
    private XmlMapper createXmlMapper() {
        XmlMapper mapper = new XmlMapper();
        mapper.registerModule(new JaxbAnnotationModule());
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        return mapper;
    }

    /**
     * turns object to xml view
     * @param object object
     * @return xml view
     * @throws JsonProcessingException ...
     */
    public String toXml(Object object) throws JsonProcessingException {
        return createXmlMapper().writeValueAsString(object);
    }

    /**
     * turns xml view to object with given type
     * @param xml xml view
     * @param type object type
     * @return object
     * @throws IOException ...
     */
    public<T> T fromXml(String xml, Class<T> type) throws IOException {
        return createXmlMapper().readValue(xml, type);
    }
}
