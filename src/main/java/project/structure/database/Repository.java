package project.structure.database;

import org.apache.derby.jdbc.EmbeddedDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Repository {

    /**
     * table name
     */
    private static final String TABLE_NAME = "meteorological_indication";

    private final EmbeddedDataSource dataSource;

    public Repository(EmbeddedDataSource dataSource) {
        this.dataSource = dataSource;
        initTable();
    }

    /**
     * initializes table
     */
    private void initTable() {
        try (Connection connection = dataSource.getConnection();
            Statement statement =connection.createStatement()) {
            DatabaseMetaData databaseMetaData =
                    connection.getMetaData();
            ResultSet resultSet =
                    databaseMetaData.getTables(
                            null,
                            null,
                            TABLE_NAME,
                            new String[]{"TABLE"});
            if (resultSet.next()) {
                System.out.println("Table already exists!");
            } else {
                statement.executeUpdate(
                        "CREATE TABLE "
                                + TABLE_NAME + " ("
                                + "id INTEGER, "
                                + "cityName VARCHAR(255), "
                                + "time TIMESTAMP, "
                                + "airTemperature DOUBLE, "
                                + "atmospherePressure DOUBLE)");
            }
            resultSet.close();
        } catch (SQLException exception) {
            System.out.println("Failed to create table!");
            exception.printStackTrace();
        }
    }

    /**
     * drops table
     */
    public void dropTable() {
        try (Connection connection = dataSource.getConnection();
                Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE " + TABLE_NAME);
        } catch (SQLException exception) {
            System.out.println("Failed to drop table!");
            exception.printStackTrace();
        }
    }

    /**
     * returns all indications from the table
     * @return indications
     */
    public List<MeteorologicalIndication> findAll() {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(
                     "SELECT * FROM " + TABLE_NAME)) {
            List<MeteorologicalIndication> indications =
                    new ArrayList<>();
            while (resultSet.next()) {
                indications.add(new MeteorologicalIndication(
                        resultSet.getInt("id"),
                        resultSet.getString("cityName"),
                        resultSet.getDate("time").toLocalDate(),
                        resultSet.getDouble("airTemperature"),
                        resultSet.getDouble("atmospherePressure")
                        )
                );
            }
            return indications;
        } catch (SQLException exception) {
            System.out.println("Failed to find indications!");
            exception.printStackTrace();
        }
        return new ArrayList<>();
    }

    /**
     * selects indication with this id
     * @param id id
     * @return indication
     */
    public MeteorologicalIndication find(int id) {
        String select = "SELECT * FROM " +
                TABLE_NAME + ' ' +
                "WHERE id=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(select)) {
            statement.setInt(1, id);
            statement.execute();

            ResultSet resultSet = statement.getResultSet();

            if (resultSet.next()) {
                return new MeteorologicalIndication(
                        resultSet.getInt("id"),
                        resultSet.getString("cityName"),
                        resultSet.getDate("time").toLocalDate(),
                        resultSet.getDouble("airTemperature"),
                        resultSet.getDouble("atmospherePressure")
                );
            }
        } catch (SQLException exception) {
            System.out.println("Select operation failed!");
            System.out.println(exception.getMessage());
        }
        return null;
    }

    /**
     * inserts indication
     * @param indication indication
     */
    public void create(MeteorologicalIndication indication) {
        String insert = "INSERT INTO " + TABLE_NAME +
                        " VALUES(?,?,?,?,?)";
        try (Connection connection = dataSource.getConnection();
            PreparedStatement statement =
                    connection.prepareStatement(insert)) {
            statement.setInt(1, indication.getId());
            statement.setString(2, indication.getCityName());
            statement.setDate(3, Date.valueOf(indication.getTime()));
            statement.setDouble(4, indication.getAirTemperature());
            statement.setDouble(5, indication.getAtmospherePressure());
            statement.execute();
        } catch (SQLException exception) {
            System.out.println("Entity insertion failed!");
            exception.printStackTrace();
        }
    }

    /**
     * inserts indications
     * @param indications indications
     */
    public void create(MeteorologicalIndication... indications) {
        for (MeteorologicalIndication indication : indications) {
            create(indication);
        }
    }


    /**
     * updates indication
     * @param indication indication
     */
    public void update(MeteorologicalIndication indication) {
        String update = "UPDATE " + TABLE_NAME + ' ' +
                        "SET " +
                        "cityName=?, " +
                        "time=?, " +
                        "airTemperature=?, " +
                        "atmospherePressure=? " +
                        "WHERE id=?";

        try (Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(update)) {
            statement.setString(1, indication.getCityName());
            statement.setDate(2, Date.valueOf(indication.getTime()));
            statement.setDouble(3, indication.getAirTemperature());
            statement.setDouble(4, indication.getAtmospherePressure());
            statement.setInt(5, indication.getId());
            statement.execute();
        } catch (SQLException exception) {
            System.out.println("Update operation failed!");
            System.out.println(exception.getMessage());
        }
    }

    /**
     * deletes indication
     * @param indication indication
     */
    public void delete(MeteorologicalIndication indication) {
        if (indication == null) {
            return;
        }
        String delete = "DELETE FROM " + TABLE_NAME + " " +
                        "WHERE id=?";

        try (Connection connection = dataSource.getConnection();
            PreparedStatement statement =
                    connection.prepareStatement(delete)) {
            statement.setInt(1, indication.getId());
            statement.execute();
        } catch (SQLException exception) {
            System.out.println("Delete operation failed!");
            System.out.println(exception.getMessage());
        }
    }

}
