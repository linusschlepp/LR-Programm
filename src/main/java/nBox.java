import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.Arrays;

public class nBox {

    public static void display(int amN, TextField[] tArray ){
        TextField[] iArray = new TextField[amN];
        int col = 1;

        //Stage
        Stage stage = new Stage();

        //setup Layout
        GridPane gridPane = new GridPane();
        gridPane.setVgap(10);
        gridPane.setHgap(8);


        for(int i  = 1; i <= amN; i++)
        {
            Label label = new Label("n" + i+ " =");
            TextField textField = new TextField();
            textField.setPrefWidth(80);
            textField.setMaxWidth(80);
            iArray[i-1] = textField;
            GridPane.setConstraints(textField, col+1, i );
            GridPane.setConstraints(label, col , i);
            gridPane.getChildren().addAll(label, textField);

        }

        //Button
        Button conButton = new Button("Continue");
        GridPane.setConstraints(conButton, 0, amN+1);
        gridPane.getChildren().add(conButton);

        conButton.setOnAction(e -> {
            //new Calculations(ConvertArrays.convertArray(iArray), ConvertArrays.convertArray(tArray));
            new Calculations(Arrays.stream(iArray).mapToDouble(t -> Double.parseDouble(t.getText())).toArray(),
                    Arrays.stream(iArray).mapToDouble(t -> Double.parseDouble(t.getText())).toArray());
            stage.close();
        });


        //Scene
        Scene scene = new Scene(gridPane, 600, 600);
        stage.setScene(scene);
        stage.setTitle("LR-Program");
        stage.show();
    //    stage.close();

    }

}
