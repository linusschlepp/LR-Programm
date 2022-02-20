import javafx.beans.value.ChangeListener;
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
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static javafx.scene.text.FontPosture.*;
import static javafx.scene.text.FontWeight.*;



public class StartBox {

    static Stage primaryStage;
    static AtomicInteger xCord = new AtomicInteger(1);
    static AtomicInteger yCord = new AtomicInteger(5);
    static GridPane gridPane = new GridPane();
    static List<CustomGridPeriods> customItems = new ArrayList<>();
    static CustomGridNeeds customItem1 = new CustomGridNeeds();
    static Text errorText;

    //TODO: Add Intents to GridPane and move things within layout to position x:0
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

        //Label for ComboBox
        Label labelComboBox = new Label("How many n's do you need?");
        GridPane.setConstraints(labelComboBox, 1,3);
        gridPane.getChildren().add(labelComboBox);


        //ChangeListener for ComboBox
        ChangeListener<String> changeListener1 = (observable, oldValue, newValue) -> {

            gridPane.getChildren().remove(errorText);
            try {
                customItems.forEach(c -> gridPane.getChildren().remove(c));
                customItems.clear();
                yCord.set(5);

                for (int i = 0; i < Integer.parseInt(newValue); i++) {
                    int number = yCord.get();
                    number -= 4;
                    yCord.addAndGet(1);
                    CustomGridPeriods customItem = new CustomGridPeriods(new Label("n" + number), new TextField());
                    GridPane.setConstraints(customItem, 1, yCord.get());
                    gridPane.getChildren().add(customItem);
                    customItems.add(customItem);
                }

            } catch (NumberFormatException ignored) {
            }
        };
        comboBox.valueProperty().addListener(changeListener1);

        GridPane.setConstraints(customItem1.getPane(), xCord.get(), 2);
        gridPane.getChildren().add(customItem1.getPane());
        Text mainText = new Text(20, 50, "Welcome to the linear regression calculator!");
        mainText.setFont(font("Calibri", BOLD, ITALIC, 18));
        GridPane.setConstraints(mainText, 1, 0);

        //Button
        Button button = new Button("Calculate");
        button.setOnAction(e -> {
            try {
                customItem1.getTextFieldList().removeIf(c -> c.getText().isEmpty());
                if (customItem1.getTextFieldList().size() == 0)
                        throw new NumberFormatException();
                new Calculations(Arrays.stream(customItems.toArray(CustomGridPeriods[]::new)).
                                mapToDouble(t -> Double.parseDouble(t.getTextField().getText())).toArray(),
                        Arrays.stream(customItem1.getTextFieldList().toArray(TextField[]::new)).
                        mapToDouble(t -> Double.parseDouble(t.getText())).toArray());
             //   primaryStage.close();
            } catch (NumberFormatException ex){
                errorText = new Text(20, 50, "Enter values first!");
                errorText.setFont(font("Calibri", BOLD, ITALIC, 14));
                errorText.setStroke(Color.RED);
                GridPane.setConstraints(errorText, 2, 15);
                gridPane.getChildren().add(errorText);
            }
        });
        GridPane.setConstraints(button, 1, 15);


        //final setup of scene and stage
        gridPane.getChildren().addAll(label , mainText, button);
        Scene scene = new Scene(gridPane, 1500, 700);
        primaryStage.setTitle("LR-Program");
        primaryStage.setScene(scene);
        primaryStage.show();


    }


    public static void closeStage(){
        primaryStage.close();
    }
}


