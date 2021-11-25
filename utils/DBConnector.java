package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/db";
    private static final String DB_USERNAME = "blog";
    private static final String DB_PASSWORD = "blog";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
    }
}
