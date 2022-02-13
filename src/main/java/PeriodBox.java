import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import static javafx.scene.text.Font.font;
import static javafx.scene.text.FontPosture.ITALIC;
import static javafx.scene.text.FontWeight.BOLD;

public class PeriodBox {

    public static void display(int amPeriods){

        Stage stage = new Stage();
        int col = 1;
        int row = 1;

        //Layout
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(8);


        //Text
        Text mainText = new Text(20 , 50, "Enter the needs for all "+amPeriods+" periods!");
        Font font = font("Calibri", BOLD, ITALIC, 12);
        mainText.setFont(font);
        GridPane.setConstraints(mainText, 0, 0);
        gridPane.getChildren().add(mainText);


        TextField[] tArray = new TextField[amPeriods];
        for(int i = 1; i <= amPeriods; i++){
            TextField textField = new TextField();
            textField.setPrefWidth(80);
            textField.setMaxWidth(80);
            tArray[i-1] = textField;
            Label label = new Label(i + ". period");
            GridPane.setConstraints(textField, col+1, row );
            GridPane.setConstraints(label, col , row );
            gridPane.getChildren().addAll(label, textField);
            row++;
            if(i >= 10 && i%10==0 ) {
                col += 5;
                row = 1;
            }
        }

        //Button & n-TextField, n-Label
        Label nLabel = new Label("How many n's do you want to enter?");
        GridPane.setConstraints(nLabel, 0, 12);
        gridPane.getChildren().add(nLabel);
        TextField nTextField = new TextField();
        nTextField.setPrefWidth(80);
        nTextField.setMaxWidth(80);
        gridPane.getChildren().add(nTextField);
        GridPane.setConstraints(nTextField, 0, 13);
        Button conButton = new Button("Continue");
        GridPane.setConstraints(conButton, 0, 14);
        gridPane.getChildren().add(conButton);


        conButton.setOnAction(e -> {

            nBox.display(Integer.parseInt(nTextField.getText()), tArray);
            stage.close();
        });


    //Scene and stage
        Scene scene = new Scene(gridPane, 1500, 600);
        stage.setScene(scene);
        stage.setTitle("LR-Program");
        stage.show();


    }

}
