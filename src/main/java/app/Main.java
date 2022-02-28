package app;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;


public class Main extends Application {

    /**
     * Program to calculate Linear-Regression
     */
    public static void main(String[] args) {


        launch(args);
    }

    @Override
    public void start(Stage primaryStage)  {

        StartBox startBox = new StartBox(primaryStage);
        StartBox.display();
    }
}