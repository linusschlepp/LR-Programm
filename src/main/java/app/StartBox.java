package app;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

import static javafx.scene.text.Font.*;

import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import static javafx.scene.text.FontPosture.*;
import static javafx.scene.text.FontWeight.*;


public class StartBox {


    private static final AtomicInteger xCord = new AtomicInteger(1);
    private static final AtomicInteger yCord = new AtomicInteger(5);
    static GridPane gridPane = new GridPane();
    private static final List<CustomGridN> customItems = new ArrayList<>();
    private static final CustomGridNeeds customItemNeed = new CustomGridNeeds();
    static Text errorText = new Text(20, 50, "Enter valid values!");
    static Stage primaryStage;


    StartBox(Stage primaryStage){
        StartBox.primaryStage = primaryStage;
    }

    /**
     * Displays the layout of this Pane
     *
     */
    public static void display() {

        //Adds icon to stage
        primaryStage.getIcons().add(new Image(Objects.requireNonNull(StartBox.class.getClassLoader().getResourceAsStream("AppIcon.png"))));
        //Layout of this stage
        gridPane.getColumnConstraints().add(new ColumnConstraints(0));
        gridPane.getRowConstraints().add(new RowConstraints(25));
        gridPane.setVgap(10);
        gridPane.setHgap(10);

        //errorText
        errorText.setFont(font("Calibri", BOLD, ITALIC, 14));
        errorText.setStroke(Color.RED);
        GridPane.setConstraints(errorText, 2, 15);

        //Labels
        Label label = new Label("Enter your needs: ");
        GridPane.setConstraints(label, 1, 1, 1, 1);

        //ComboBox
        ComboBox<String> comboBox = new ComboBox<>(FXCollections.observableArrayList("1", "2", "3", "4", "5"));
        GridPane.setConstraints(comboBox, 1, 4);
        gridPane.getChildren().add(comboBox);

        //Label for ComboBox
        Label labelComboBox = new Label("How many n's do you need?");
        GridPane.setConstraints(labelComboBox, 1, 3);
        gridPane.getChildren().add(labelComboBox);


        //ChangeListener for ComboBox
        ChangeListener<String> changeListenerComboBox = (observable, oldValue, newValue) -> {

            gridPane.getChildren().remove(errorText);
            try {
                //Every item gets removed from the layout
                customItems.forEach(c -> gridPane.getChildren().remove(c));
                customItems.clear();
                //yCord is set to default again
                yCord.set(5);

                // Number of customItems get added to the layout
                for (int i = 0; i < Integer.parseInt(newValue); i++) {
                    int number = yCord.get();
                    number -= 4;
                    yCord.addAndGet(1);
                    CustomGridN customItem = new CustomGridN(new Label("n" + number), new TextField());
                    GridPane.setConstraints(customItem, 1, yCord.get());
                    gridPane.getChildren().add(customItem);
                    customItems.add(customItem);
                }
            // if the user enters non-valid values the errorText is being displayed
            } catch (NumberFormatException e) {
                gridPane.getChildren().add(errorText);
            }
        };
        //changeListenerComboBox is added to the ComboBox
        comboBox.valueProperty().addListener(changeListenerComboBox);

        GridPane.setConstraints(customItemNeed.getPane(), xCord.get(), 2);
        gridPane.getChildren().add(customItemNeed.getPane());
        Text mainText = new Text(20, 50, "Welcome to the linear regression calculator!");
        mainText.setFont(font("Calibri", BOLD, ITALIC, 18));
        GridPane.setConstraints(mainText, 1, 0);

        //Button
        Button calculateButton = new Button("Calculate");
        calculateButton.setOnAction(e -> {
            try {
                // Empty entries are removed from the list
                customItemNeed.getTextFieldList().removeIf(c -> c.getText().isEmpty());
                //if the user has not entered any values/ selected n a NumberFormatException is being thrown
                if (customItemNeed.getTextFieldList().size() == 0)
                    throw new NumberFormatException();
                // Both lists are converted to double Arrays and passed to app.Calculations
                new Calculations(Arrays.stream(customItems.toArray(CustomGridN[]::new)).
                        mapToDouble(t -> Double.parseDouble(t.getTextField().getText())).toArray(),
                        Arrays.stream(customItemNeed.getTextFieldList().toArray(TextField[]::new)).
                                mapToDouble(t -> Double.parseDouble(t.getText())).toArray());
                // Stage gets closed after we pass the values to app.Calculations
                primaryStage.close();
            } catch (NumberFormatException ex) {
                gridPane.getChildren().add(errorText);
            }
        });
        GridPane.setConstraints(calculateButton, 1, 15);


        //final setup of scene and stage
        gridPane.getChildren().addAll(label, mainText, calculateButton);
        Scene scene = new Scene(gridPane, 1500, 700);
        primaryStage.setTitle("LR-Program");
        primaryStage.setScene(scene);
        primaryStage.show();


    }


}


