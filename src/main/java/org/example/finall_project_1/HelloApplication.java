package org.example.finall_project_1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader =
                new FXMLLoader(getClass().getResource("login-view.fxml"));

        Scene scene = new Scene(loader.load(), 420, 520);
        scene.getStylesheets().add(
                getClass().getResource("style.css").toExternalForm()
        );

        stage.setTitle("Medical Billing System - Login");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
