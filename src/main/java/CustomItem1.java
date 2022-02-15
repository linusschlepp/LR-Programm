import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

import java.util.concurrent.atomic.AtomicInteger;

public class CustomItem1 extends GridPane {
    private TextField textField = new TextField();
    static AtomicInteger xCord = new AtomicInteger(1);


    CustomItem1() {
        this.getColumnConstraints().add(new ColumnConstraints(20));
        textField.setMaxWidth(60);
        textField.setPrefWidth(60);
        setConstraints(textField, xCord.get(), 0);
        this.getChildren().add(textField);
        specialListener(textField);
    }


    public GridPane getPane(){

        return this;
    }

    void specialListener(TextField t1) {

        ChangeListener<String> changeListener = new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                TextField t2 = new TextField();
                t2.setPrefWidth(60);
                t2.setMaxWidth(60);
                xCord.addAndGet(1);
                GridPane.setConstraints(t2, xCord.get(), 0);
                getPane().getChildren().add(t2);
                t1.textProperty().removeListener(this);
                specialListener(t2);
            }
        };
        t1.textProperty().addListener(changeListener);
    }




}
