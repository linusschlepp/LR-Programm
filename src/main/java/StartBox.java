import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

import static javafx.scene.text.Font.*;

import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static javafx.scene.text.FontPosture.*;
import static javafx.scene.text.FontWeight.*;



public class StartBox {

    static Stage primaryStage;
    static AtomicInteger xCord = new AtomicInteger(1);
    static AtomicInteger yCord = new AtomicInteger(5);
    static GridPane gridPane = new GridPane();
    static List<CustomItem> customItems = new ArrayList<>();
    static CustomItem1 customItem1 = new CustomItem1();


    public static void display(Stage primaryStage) {
        StartBox.primaryStage = primaryStage;


        //Layout
        gridPane.getColumnConstraints().add(new ColumnConstraints(0));
        gridPane.getRowConstraints().add(new RowConstraints(25));
        gridPane.setVgap(25);
        gridPane.setHgap(75);
        // gridPane.setGridLinesVisible(true);

        //Labels
        Label label = new Label("Enter your needs: ");
        GridPane.setConstraints(label, 1, 1, 1, 1);

        //ComboBox
        ComboBox<String> comboBox = new ComboBox<>(FXCollections.observableArrayList("1", "2", "3", "4", "5"));
        GridPane.setConstraints(comboBox, 1, 4);
        gridPane.getChildren().add(comboBox);

        //ChangeListener for ComboBox
        ChangeListener<String> changeListener1 = (observable, oldValue, newValue) -> {

            try {
                customItems.forEach(c -> gridPane.getChildren().remove(c));
                customItems.clear();
                yCord.set(5);

                for (int i = 0; i < Integer.parseInt(newValue); i++) {
                    int number = yCord.get();
                    number -= 4;
                    yCord.addAndGet(1);
                    CustomItem customItem = new CustomItem(new Label("n" + number), new TextField());
                    GridPane.setConstraints(customItem, 1, yCord.get());
                    gridPane.getChildren().add(customItem);
                    customItems.add(customItem);
                }

            } catch (NumberFormatException ignored) {
            }
        };
        comboBox.valueProperty().addListener(changeListener1);

        GridPane.setConstraints(customItem1.getPane(), xCord.get(), 3);
        gridPane.getChildren().add(customItem1.getPane());
        Text mainText = new Text(20, 50, "Welcome to the linear regression calculator!");
        mainText.setFont(font("Calibri", BOLD, ITALIC, 18));
        GridPane.setConstraints(mainText, 1, 0);

        //Button
        Button button = new Button("Calculate");
//        button.setOnAction(e -> {
//            try {
//                PeriodBox.display(parseInt(textField.getText()));
//                primaryStage.close();
//            } catch (NumberFormatException ex){
//                Text errorText = new Text(20, 50, "Enter values first!");
//            }
//        });
        GridPane.setConstraints(button, 1, 15);


        //final setup of scene and stage
        gridPane.getChildren().addAll(label /*textField*/, mainText, button);
        Scene scene = new Scene(gridPane, 1000, 700);
        primaryStage.setTitle("LR-Program");
        primaryStage.setScene(scene);
        primaryStage.show();


    }
}


