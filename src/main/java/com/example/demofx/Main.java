package com.example.demofx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private static final String FXML_NAME = "/demo-layout.fxml";

    @Override
    public void start(Stage primaryStage) throws Exception {
        // i.e., location of fxml in the classes/jar structure
        Parent root = FXMLLoader.load(getClass().getResource(FXML_NAME));
        primaryStage.setTitle("QR Code Generation");
        primaryStage.setScene(new Scene(root, 680, 450));

        primaryStage.show();
    }

}
