package org.example.finall_project_1.config;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConfig {

    private static final String DB_URL = "jdbc:sqlite:billing.db";

    static {
        try {
            Class.forName("org.sqlite.JDBC"); // 🔥 IMPORTANT
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("SQLite JDBC driver not found", e);
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(DB_URL);
        } catch (Exception e) {
            throw new RuntimeException("SQLite connection failed", e);
        }
    }
}
