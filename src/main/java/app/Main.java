package app;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;


public class Main extends Application {

    /**
     * Program to calculate Linear-Regression
     */
    public static void main(String[] args) {


        launch(args);
    }

    @Override
    public void start(Stage primaryStage)  {
       // TODO: It won't find image throws NUllPointerException, fix this
        primaryStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("../pictures/AppIcon.png"))));
        StartBox.display(primaryStage);
    }
}