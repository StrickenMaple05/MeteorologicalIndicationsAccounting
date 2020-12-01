package ru.StrickenMaple05.croc.java.school.database;

import org.apache.derby.jdbc.EmbeddedDataSource;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class DataSourceProvider {

    private EmbeddedDataSource dataSource;

    /**
     * configuration properties
     */
    private final Map<String, String> properties;

    public DataSourceProvider() throws IOException {
        properties = new HashMap<>();
        loadProperties();
    }

    /**
     * loads properties from config.properties
     * @throws IOException ...
     */
    private void loadProperties() throws IOException {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("src/main/resources/config.properties"));
            for (Map.Entry<Object, Object> entry : properties.entrySet()) {
                this.properties.put(entry.getKey().toString(), entry.getValue().toString());
            }
        } catch (IOException exception) {
                System.out.println("Failed to read properties!");
                throw(exception);
            }
    }

    /**
     * gets or initializes and gets dataSource
     * @return dataSource
     */
    public EmbeddedDataSource getDataSource() {
        if (dataSource == null) {
            dataSource = new EmbeddedDataSource();
            dataSource.setDatabaseName(properties.get("databaseName"));
            dataSource.setUser(properties.get("user"));
            dataSource.setPassword(properties.get("password"));
            dataSource.setCreateDatabase("create");
        }
        return dataSource;
    }
}
