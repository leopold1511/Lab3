package Lab3.Readers;


import Lab3.Reactor.Reactor;
import Lab3.Reactor.ReactorDB;

import java.sql.*;
import java.util.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DBReader {
    public ArrayList<ReactorDB> readDB(ArrayList<Reactor> reactorTypes) {
        Connection connection = connect();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<ReactorDB> reactors = new ArrayList<>();
        try {
            String select = """
                            SELECT reactor.id, reactor_name, thermal_capacity, country_name, region_name, type_name, operator_name, owner_name, shutdown_date, connection_date
                            FROM reactor
                            LEFT JOIN country ON country_id = country.id
                            LEFT JOIN region ON region_id = region.id
                            LEFT JOIN type ON type_id = type.id
                            LEFT JOIN operator ON operator_id = operator.id
                            LEFT JOIN owner ON owner_id = owner.id""";
            preparedStatement = connection.prepareStatement(select);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ReactorDB reactor = new ReactorDB();
                reactor.setName(resultSet.getString("reactor_name"));
                reactor.setCountry(resultSet.getString("country_name"));
                reactor.setRegion(resultSet.getString("region_name"));
                reactor.setOwner(resultSet.getString("owner_name"));
                reactor.setOperator(resultSet.getString("operator_name"));
                reactor.setThermalCapacity(resultSet.getInt("thermal_capacity"));
                reactor.setType(resultSet.getString("type_name"), reactorTypes);
                reactor.setShutdownYear(extractYearFromDate(resultSet.getString("shutdown_date")));
                reactor.setConnectionYear(extractYearFromDate(resultSet.getString("connection_date")));
                reactor.setLoadFactor(readLoadFactor(connection, resultSet.getInt("id")));
                reactors.add(reactor);
            }
            System.out.println("БД успешно прочитана");
        } catch (SQLException e) {
            System.out.println("Ошибка при чтении из БД " + e.getMessage());
            throw new RuntimeException(e);
        } finally {
            closeResources(preparedStatement, connection, resultSet);
        }
        return reactors;
    }

    private Connection connect() {
        Connection connection = null;
        String url = "jdbc:postgresql://aws-0-eu-central-1.pooler.supabase.com:6543/postgres";
        String user = "postgres.cfskywyqvhvyikyrimdq";
        String password = "zeCtQsY85vtm9AKb";
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("You successfully connected to database now");
        } catch (Exception e) {
            System.out.println("Connection Failed");// Consider logging instead of printing
            throw new RuntimeException(e);
        }
        return connection;
    }

    private Map<Integer, Double> readLoadFactor(Connection connection, int reactorId) {
        Map<Integer, Double> loadFactor = new HashMap<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            String select = """
                            SELECT load_factor, year
                            FROM loadfactor
                            WHERE reactor_id =?""";
            preparedStatement = connection.prepareStatement(select);
            preparedStatement.setInt(1, reactorId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                loadFactor.put(resultSet.getInt("year"), resultSet.getDouble("load_factor"));
            }
        } catch (SQLException e) {
            System.out.println("Oшибка при чтении из БД " + e.getMessage());
            throw new RuntimeException(e);
        } finally {
            closeResources(preparedStatement, null, resultSet);
        }
        return loadFactor;
    }

    private int extractYearFromDate(String date) {
        if (date!= null) {
            return Integer.parseInt(date.substring(0, 4));
        } else {
            return 2025;
        }
    }

    private void closeResources(PreparedStatement preparedStatement, Connection connection, ResultSet resultSet) {
        try {
            if (preparedStatement!= null) {
                preparedStatement.close();
            }
            if (connection!= null) {
                connection.close();
            }
            if (resultSet!= null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            System.out.println("Oшибка при закрытии " + e.getMessage());
        }
    }
}
