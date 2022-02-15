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
import static java.lang.Integer.*;


public class StartBox {

    static Stage primaryStage;
    static AtomicInteger xCord = new AtomicInteger(1);
    static AtomicInteger yCord = new AtomicInteger(5);
    static GridPane gridPane = new GridPane();
    static List<Label> labelList = new ArrayList<>();
    static List<TextField> textFieldlist = new ArrayList<>();


    public static void display(Stage primaryStage) {
        StartBox.primaryStage = primaryStage;


        //Layout
        gridPane.getColumnConstraints().add(new ColumnConstraints(50));
        gridPane.getRowConstraints().add(new RowConstraints(25));
        gridPane.setVgap(25);
        gridPane.setHgap(75);
        //gridPane.setGridLinesVisible(true);

        //Labels
        Label label = new Label("Enter your needs: ");
        GridPane.setConstraints(label, 0, 1, 1, 1);

        //ComboBox
        ComboBox comboBox = new ComboBox(FXCollections.observableArrayList("1", "2", "3", "4", "5"));
        GridPane.setConstraints(comboBox, 0, 4);
        gridPane.getChildren().add(comboBox);

        ChangeListener<String> changeListener1 = new ChangeListener<>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                try {


                        labelList.forEach(l -> gridPane.getChildren().remove(l));
                        textFieldlist.forEach(t -> gridPane.getChildren().remove(t));
                        labelList.clear();
                        textFieldlist.clear();
                        yCord.set(5);

                        for (int i = 0; i < Integer.parseInt(newValue); i++) {
                            int temp = yCord.get();
                            temp -= 4;
                            Label label1 = new Label("n" + temp);
                            yCord.addAndGet(1);
                            TextField t2 = new TextField();
                            t2.setPrefWidth(60);
                            t2.setMaxWidth(60);
                            GridPane.setConstraints(t2, 1, yCord.get());
                            GridPane.setConstraints(label1, 0, yCord.get());
                            gridPane.getChildren().addAll(t2, label1);
                            textFieldlist.add(t2);
                            labelList.add(label1);
                        }


                } catch(NumberFormatException ignored){}
            }
        };
        comboBox.valueProperty().addListener(changeListener1);


        //TextFields
        TextField textField = new TextField();
        textField.setPrefWidth(60);
        textField.setMaxWidth(60);
        GridPane.setConstraints(textField, 0, 3);

        //text
        Text mainText = new Text(20, 50, "Welcome to the linear regression calculator!");
        mainText.setFont(font("Calibri", BOLD, ITALIC, 18));
        GridPane.setConstraints(mainText, 0, 0);


        specialListener(textField);


        //Button
        Button button = new Button("Calculate");
        button.setOnAction(e -> {
            PeriodBox.display(parseInt(textField.getText()));
            primaryStage.close();
        });
        GridPane.setConstraints(button, 0, 15);


        //final setup of scene and stage
        gridPane.getChildren().addAll(label, textField, mainText, button);
        Scene scene = new Scene(gridPane, 1000, 700);
        primaryStage.setTitle("LR-Program");
        primaryStage.setScene(scene);
        primaryStage.show();


    }

    static void specialListener(TextField t1) {

        ChangeListener<String> changeListener = new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                TextField t2 = new TextField();
                t2.setPrefWidth(60);
                t2.setMaxWidth(60);
                GridPane.setConstraints(t2, xCord.get(), 3);
                xCord.getAndIncrement();
                gridPane.getChildren().add(t2);
                t1.textProperty().removeListener(this);
                specialListener(t2);
            }
        };
        t1.textProperty().addListener(changeListener);
    }
}
