package app;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;


import javafx.scene.paint.Color;
import javafx.scene.control.Button;

import static javafx.scene.text.Font.font;
import static javafx.scene.text.FontPosture.ITALIC;
import static javafx.scene.text.FontWeight.BOLD;

public class MessageBox {

    /**
     * Displays the MessageBox
     *
     * @param text text which is displayed
     * @param errorLevel shows which kind of error its is
     */
    public void display(Text text, ErrorLevel errorLevel) {

        Stage stage = new Stage();

        GridPane gridPane = new GridPane();

        text.setFont(font("Calibri", BOLD, ITALIC, 18));

        if (errorLevel.equals(ErrorLevel.ERROR)) {
            text.setFill(Color.RED);
            stage.setTitle("An error occurred");
        } else
            stage.setTitle(text.getText());

        // Scene and GridPane is created
        Scene scene = new Scene(gridPane, 300, 150);
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        GridPane.setConstraints(text,1,0);

        // Button is created
        Button okButton = new Button("Ok");
        GridPane.setConstraints(okButton, 1, 3);
        okButton.setOnAction(e -> stage.close());

        // Button and text are being added GridPane
        gridPane.getChildren().addAll(okButton, text);
        stage.setScene(scene);
        stage.show();

    }

}
