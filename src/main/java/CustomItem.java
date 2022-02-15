import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class CustomItem extends GridPane {

    public CustomItem(Label label, TextField textField){

        this.getChildren().addAll(label, textField);
        setConstraints(label, 0,0);
        setConstraints(textField, 1, 0);
    }


}
