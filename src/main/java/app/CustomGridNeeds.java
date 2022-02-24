package app;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import static javafx.scene.text.Font.font;
import static javafx.scene.text.FontPosture.ITALIC;
import static javafx.scene.text.FontWeight.BOLD;


/**
 * Symbolizes the needs on app.StartBox
 *
 */
public class CustomGridNeeds extends GridPane {

    AtomicInteger xCord = new AtomicInteger(1);
    AtomicInteger periodCounter = new AtomicInteger(1);
    private final List<TextField> textFieldList = new ArrayList<>();


    CustomGridNeeds() {
        this.getColumnConstraints().add(new ColumnConstraints(20));
        TextField textField = new TextField();
        textField.setMaxWidth(60);
        textField.setPrefWidth(60);
        Text text = new Text(20, 50, periodCounter.get() + ".");
        text.setFont(font("Calibri", BOLD, ITALIC, 14));
        setConstraints(text, xCord.get(), 0);
        setConstraints(textField, xCord.get(), 1);
        this.getChildren().addAll(textField, text);
        textFieldList.add(textField);
        specialListener(textField);
    }

    /**
     * Returns the layout
     *
     * @return GridPane
     */
    public GridPane getPane() {

        return this;
    }

    private void specialListener(TextField t1) {
        // Adds labels as well as TextFields to StartBox
        // Listener gets only added to latest Textfield
        ChangeListener<String> changeListener = new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                StartBox.gridPane.getChildren().remove(StartBox.errorText);
                TextField t2 = new TextField();
                t2.setPrefWidth(60);
                t2.setMaxWidth(60);
                xCord.addAndGet(1);
                periodCounter.addAndGet(1);
                Text text = new Text(20, 50, periodCounter.get() + ".");
                text.setFont(font("Calibri", BOLD, ITALIC, 14));
                setConstraints(text, xCord.get(), 0);
                setConstraints(t2, xCord.get(), 1);
                getPane().getChildren().addAll(t2, text);
                t1.textProperty().removeListener(this);
                textFieldList.add(t2);
                specialListener(t2);
            }
        };

        t1.textProperty().addListener(changeListener);
    }

    /**
     * Returns every TextField in form of a list
     *
     * @return list of all TextFields
     */
    public List<TextField> getTextFieldList() {
        return textFieldList;
    }
}
