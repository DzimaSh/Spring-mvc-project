package org.example.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.sql.*;

@Service
public class DataBaseService {

    @Value("${dbProperties.URL}")
    private String URL;
    @Value("${dbProperties.USERNAME}")
    private String USERNAME;
    @Value("${dbProperties.PASSWORD}")
    private String PASSWORD;

    private static Connection connection = null;

    public DataBaseService() {}

    public Connection openDataBase() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT EXISTS(SELECT from pg_tables WHERE schemaname = 'public' AND tablename = 'person');"
            );
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            if (!resultSet.getBoolean("exists")) {
                connection.prepareStatement(
                                "CREATE TABLE person(id SERIAL PRIMARY KEY, name VARCHAR, age INT, email VARCHAR)").
                        executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
