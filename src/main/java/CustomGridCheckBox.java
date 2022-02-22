import com.google.common.collect.Multimap;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.GridPane;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class CustomGridCheckBox extends GridPane {


    private final HashMap<Integer, CheckBox> checkBoxHashMap = new HashMap<>();

    CustomGridCheckBox(Multimap<Integer, Double> predictionsMap){

        AtomicInteger index = new AtomicInteger(1);
        predictionsMap.keySet().forEach(i -> {
            CheckBox checkBox = new CheckBox("n = "+i+" ");
            setConstraints(checkBox, index.get(), 3);
            index.getAndIncrement();
            checkBoxHashMap.put(i, checkBox);
            this.getChildren().add(checkBox);
        });
    }


    public GridPane getPane(){
        return this;
    }

    public HashMap<Integer, CheckBox> getCheckBoxHashMap(){
        return checkBoxHashMap;
    }

}
