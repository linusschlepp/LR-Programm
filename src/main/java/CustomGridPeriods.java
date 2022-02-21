import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

public class CustomGridPeriods extends GridPane {

    private final TextField textField;

    public CustomGridPeriods(Label label, TextField textField){
        this.textField = textField;

        textField.setMaxWidth(60);
        textField.setPrefWidth(60);
        this.getChildren().addAll(label, textField);
        this.getColumnConstraints().add(new ColumnConstraints(20));
        setConstraints(label, 0,0);
        setConstraints(textField, 1, 0);
    }

    public TextField getTextField() {
        return textField;
    }
}
