import com.google.common.collect.Multimap;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class CustomGridCheckBox extends GridPane {


    private final List<CheckBox> checkBoxList = new ArrayList<>();

    CustomGridCheckBox(Multimap<Integer, Double> predictionsMap){

        AtomicInteger index = new AtomicInteger(1);
        predictionsMap.keySet().forEach(i -> {
            //  comboBoxPredictions.getItems().add(Integer.toString(i));
            CheckBox checkBox = new CheckBox("n = "+i);
            setConstraints(checkBox, index.get(), 3);
            index.getAndIncrement();
            checkBoxList.add(checkBox);
            this.getChildren().add(checkBox);
        });
    }


    public GridPane getPane(){
        return this;
    }

    public List<CheckBox> getCheckBoxList(){
        return checkBoxList;
    }

}
