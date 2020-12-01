package ru.StrickenMaple05.croc.java.school.database;

import org.junit.jupiter.api.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * tests on database interaction
 */
@DisplayName("Repository methods tests")
public class RepositoryTest {

    private Repository repository;
    private final MeteorologicalIndication indication =
            new MeteorologicalIndication(
                    1,
                    "krasnodar",
                    LocalDate.now(),
                    25,
                    750);

    /**
     * init repository
     * @throws IOException ...
     */
    @BeforeEach
    public void init() throws IOException {

        DataSourceProvider dataSourceProvider;
        try {
            dataSourceProvider = new DataSourceProvider();
        } catch (IOException exception) {
            System.out.println("Failed to init dataSourceProvider!");
            System.out.println(exception.getMessage());
            throw exception;
        }
        repository = new Repository(
                dataSourceProvider.getDataSource());
    }

    /**
     * drops table
     */
    @AfterEach
    public void drop() {
        repository.dropTable();
    }

    /**
     * test on record insert and select operations
     */
    @DisplayName("Test on insert and select")
    @Test
    public void testInsertAndSelect() {

        repository.create(indication);
        repository.create(indication);

        Assertions.assertEquals(indication, repository.find(1));

        List<MeteorologicalIndication> indications = new ArrayList<>();

        indications.add(indication);
        indications.add(indication);

        Assertions.assertEquals(indications, repository.findAll());
    }

    /**
     * test on update operation
     */
    @DisplayName("Test on update")
    @Test
    public void testUpdate() {

        repository.create(indication);

        Assertions.assertEquals(indication, repository.find(1));

        indication.setAirTemperature(24);
        repository.update(indication);

        Assertions.assertEquals(indication, repository.find(1));
    }

    /**
     * test on delete operation
     */
    @DisplayName("Test on delete")
    @Test
    public void deleteTest() {

        repository.create(indication);

        Assertions.assertEquals(indication, repository.find(1));

        repository.delete(indication);

        Assertions.assertNull(repository.find(1));
    }
}
