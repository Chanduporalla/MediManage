package org.example.finall_project_1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtil {

    private static final String DB_URL =
            "jdbc:sqlite:/home/chandu/Desktop/final_project/finall_project_1/users.db";

    // Get DB connection
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    // Create users table if not exists
    public static void initDB() {
        String sql =
                "CREATE TABLE IF NOT EXISTS users (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "username TEXT UNIQUE NOT NULL, " +
                        "password TEXT NOT NULL" +
                        ");";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute(sql);
            System.out.println("✅ Database initialized successfully");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
