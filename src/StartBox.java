import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

import static javafx.scene.text.Font.*;

import javafx.scene.text.Text;
import javafx.stage.Stage;

import static javafx.scene.text.FontPosture.*;
import static javafx.scene.text.FontWeight.*;
import static java.lang.Integer.*;


public class StartBox {

    static Stage primaryStage;

    StartBox(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }


    public static void display() {


        //Layout
        GridPane gridPane = new GridPane();
        gridPane.setVgap(8);
        gridPane.setHgap(10);

        //Labels
        Label label = new Label("How many periods do you want to enter?");
        GridPane.setConstraints(label, 0, 1);


        //TextFields
        TextField textField = new TextField();
        textField.setPrefWidth(80);
        textField.setMaxWidth(80);
        GridPane.setConstraints(textField, 0, 2);

        //text
        Text mainText = new Text(20, 50, "Welcome to the linear regression calculator!");
        Font font = font("Calibri", BOLD, ITALIC, 18);
        mainText.setFont(font);
        GridPane.setConstraints(mainText, 0, 0);


        //Button
        Button button = new Button("Calculate");
        button.setOnAction(e -> {
            PeriodBox.display(parseInt(textField.getText()));
            primaryStage.close();
        });
        GridPane.setConstraints(button, 0, 3);


        //final setup of scene and stage
        gridPane.getChildren().addAll(label, textField, mainText, button);
        Scene scene = new Scene(gridPane, 500, 300);
        primaryStage.setTitle("LR-Program");
        primaryStage.setScene(scene);
        primaryStage.show();



    }
}
