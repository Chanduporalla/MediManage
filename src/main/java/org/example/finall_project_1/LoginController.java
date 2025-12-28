package org.example.finall_project_1;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginController {

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Label message;

    @FXML
    private void initialize() {
        DBUtil.initDB();
    }

    @FXML
    private void handleLogin() {
        String user = username.getText();
        String pass = password.getText();

        if (authenticate(user, pass)) {
            message.setText("Login Successful ✅");
        } else {
            message.setText("Invalid Username or Password ❌");
        }
    }

    private boolean authenticate(String user, String pass) {
        String sql = "SELECT * FROM users WHERE username=? AND password=?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user);
            ps.setString(2, pass);

            ResultSet rs = ps.executeQuery();
            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
