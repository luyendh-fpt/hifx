package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionHelper {

    private static Connection connection;
    private static final String DATABASE_NAME = "spring-hero-bank";
    private static final String DATABASE_USER = "root";
    private static final String DATABASE_PASSWORD = "abcD1234";

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/"
                    + DATABASE_NAME + "?user="
                    + DATABASE_USER + "&password="
                    + DATABASE_PASSWORD);
        }
        return connection;
    }
}
