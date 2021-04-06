package ru.dolmatova.controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataBaseController {
    private static Connection c;

    public static void createConnection() {
        try {
            c = DriverManager.getConnection("jdbc:hsqldb:file:testdb", "SA", "");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void closeConnection() {
        executeStatement("SHUTDOWN");
    }

    private static void executeStatement(final String statement) {
        try {
            c.prepareStatement(statement).execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private static ResultSet executeQuery(final String query) {
        try {
            return c.createStatement().executeQuery(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }
}
