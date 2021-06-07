import javafx.application.Application;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Linear_Regression extends Application {

    /**
     * Program to calculate Linear-Regression
     * @author: Victor Hergert backend developmnent
     * @author: Linus Schlepp GUI "design"
     *
     */

    public static void main(String[] args) {
        launch(args);
        // Init array of actual amounts
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        StartBox startBox = new StartBox(primaryStage);
        StartBox.display();
    }
}