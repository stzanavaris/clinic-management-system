package org.example.clinicmanagementsystem.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/org/example/clinicmanagementsystem/main.fxml")
        );

        Scene scene = new Scene(loader.load(), 800, 700);

        stage.setTitle("Clinic Management System - Unified Dashboard");
        stage.setScene(scene);
        stage.setMinWidth(600);
        stage.setMinHeight(500);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}