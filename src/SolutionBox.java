import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import javafx.scene.control.TextArea;

public class SolutionBox {

    public static void display(String finalString) {
        //Stage
        Stage stage = new Stage();


        //Layout
        GridPane gridPane = new GridPane();


        //TextArea
        TextArea textArea = new TextArea();
        //  textArea.setPrefHeight(300);
        textArea.setPrefWidth(700);
        textArea.setText(finalString);
        gridPane.getChildren().add(textArea);

        //Button
        Button retButton = new Button("Enter new Data");
        gridPane.setConstraints(retButton, 1, 25);
        gridPane.getChildren().add(retButton);
        Button quitButton = new Button("Quit");
        gridPane.setConstraints(quitButton, 3, 25);
        gridPane.getChildren().add(quitButton);
        retButton.setOnAction(e -> {
            StartBox.display();
            stage.close();
        });
        quitButton.setOnAction(e -> {
            stage.close();
        });


        //Scene
        Scene scene = new Scene(gridPane, 800, 500);
        stage.setTitle("LR-Program");
        stage.setScene(scene);
        stage.show();

    }

}
