package ru.StrickenMaple05.croc.java.school.database;

import org.apache.derby.jdbc.EmbeddedDataSource;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Repository {

    /**
     * table name
     */
    private static final String TABLE_NAME = "meteorological_indication";

    private static final Field[] fields =
            MeteorologicalIndication.class.getDeclaredFields();

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
            Statement statement = connection.createStatement()) {
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            ResultSet resultSet = databaseMetaData.getTables(
                    null, null,
                    TABLE_NAME, new String[]{"TABLE"});
            if (resultSet.next()) {
                System.out.println("Table already exists!");
            } else {
                statement.executeUpdate(
                        "CREATE TABLE "
                                + TABLE_NAME + " ("
                                + fields[0].getName() + " INTEGER, "
                                + fields[1].getName() + " VARCHAR(255), "
                                + fields[2].getName() + " TIMESTAMP, "
                                + fields[3].getName() + " DOUBLE, "
                                + fields[4].getName() + " DOUBLE)");
                /*
                CREATE TABLE meteorological_indication(
                    id INTEGER IDENTITY(1,1) PRIMARY KEY,
                    city VARCHAR(255),
                    time TIMESTAMP,
                    air_temperature DOUBLE,
                    atmosphere_pressure DOUBLE);
                 */
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
            List<MeteorologicalIndication> indications = new ArrayList<>();
            while (resultSet.next()) {
                indications.add(new MeteorologicalIndication(
                        resultSet.getInt(fields[0].getName()),
                        resultSet.getString(fields[1].getName()),
                        resultSet.getDate(fields[2].getName()).toLocalDate(),
                        resultSet.getDouble(fields[3].getName()),
                        resultSet.getDouble(fields[4].getName())
                ));
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
             PreparedStatement statement = connection.prepareStatement(select)) {
            statement.setInt(1, id);
            statement.execute();

            ResultSet resultSet = statement.getResultSet();

            if (resultSet.next()) {
                return new MeteorologicalIndication(
                        resultSet.getInt(fields[0].getName()),
                        resultSet.getString(fields[1].getName()),
                        resultSet.getDate(fields[2].getName()).toLocalDate(),
                        resultSet.getDouble(fields[3].getName()),
                        resultSet.getDouble(fields[4].getName())
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
            PreparedStatement statement = connection.prepareStatement(insert)) {
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
                        fields[1].getName() + "=?, " +
                        fields[2].getName() + "=?, " +
                        fields[3].getName() + "=?, " +
                        fields[4].getName() + "=? " +
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
            PreparedStatement statement = connection.prepareStatement(delete)) {
            statement.setInt(1, indication.getId());
            statement.execute();
        } catch (SQLException exception) {
            System.out.println("Delete operation failed!");
            System.out.println(exception.getMessage());
        }
    }

}
