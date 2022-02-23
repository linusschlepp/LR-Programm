package app;

import com.google.common.collect.Multimap;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.GridPane;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Symbolizes the CheckBoxes in app.SolutionBox
 *
 */
public class CustomGridCheckBox extends GridPane {

   // contains the n in the integer and CheckBoxes in CheckBoxes
    private final HashMap<Integer, CheckBox> checkBoxHashMap = new HashMap<>();

    CustomGridCheckBox(Multimap<Integer, Double> predictionsMap) {

        AtomicInteger index = new AtomicInteger(1);
        predictionsMap.keySet().forEach(i -> {
            CheckBox checkBox = new CheckBox("n = " + i + " ");
            setConstraints(checkBox, index.get(), 3);
            index.getAndIncrement();
            checkBoxHashMap.put(i, checkBox);
            this.getChildren().add(checkBox);
        });
    }

    /**
     * Returns the Grid (Layout)
     *
     * @return object of GridPane
     */
    public GridPane getPane() {
        return this;
    }


    /**
     * checkBoxHashMap gets returned
     *
     * @return checkBoxHashMap
     */
    public HashMap<Integer, CheckBox> getCheckBoxHashMap() {
        return checkBoxHashMap;
    }

}
