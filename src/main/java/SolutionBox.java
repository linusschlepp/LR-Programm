import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import javafx.beans.value.ChangeListener;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javafx.scene.control.TextArea;

import java.util.ArrayList;
import java.util.List;

import static javafx.scene.text.Font.font;
import static javafx.scene.text.FontPosture.ITALIC;
import static javafx.scene.text.FontWeight.BOLD;


public class SolutionBox {


    static LineChart<Number, Number> lineChart;

    public static void display(String finalString, double[] periodArray, List<Double> b0List, List<Double> b1List, Multimap<Integer, Double> predictionsMap) {

        //Stage
        Stage stage = new Stage();


        //Layout
        GridPane gridPane = new GridPane();

        //mainText
        Text mainText = new Text(20, 50, "Data:");
        mainText.setFont(font("Calibri", BOLD, ITALIC, 18));
        GridPane.setConstraints(mainText, 1, 0);
        gridPane.getChildren().add(mainText);

        //nText
        Text nText = new Text(20, 50, "Select n:");
        nText.setFont(font("Calibri", BOLD, ITALIC, 18));
        GridPane.setConstraints(nText, 1, 2);
        gridPane.getChildren().add(nText);


        //TextArea
        TextArea textArea = new TextArea();
        textArea.setPrefWidth(700);
        textArea.setText(finalString);
        GridPane.setConstraints(textArea, 1, 1);
        gridPane.getChildren().add(textArea);


        //LineChart
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Period");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Pieces");
        lineChart = new LineChart<>(xAxis, yAxis);

        initializeSeries(periodArray);

      //  lineChart.setCreateSymbols(false);
        gridPane.getChildren().add(lineChart);

        GridPane.setConstraints(lineChart, 1, 8);

        ChangeListener<String> changeListener1 = (observable, oldValue, newValue) -> {
            XYChart.Series<Number, Number> seriesPredictions = new XYChart.Series<>();
            lineChart.getData().clear();
            initializeSeries(periodArray);
            Multimap<Integer, Double> tempMap = ArrayListMultimap.create(predictionsMap);
            tempMap.keySet().removeIf(i -> i != Integer.parseInt(newValue));
            for (int i = 0; i < new ArrayList<>(tempMap.values()).size(); i++)
                    seriesPredictions.getData().add(new XYChart.Data<>(Math.abs(new ArrayList<>(tempMap.values()).size()
                            -periodArray.length)+i+1, new ArrayList<>(tempMap.values()).get(i)));



            seriesPredictions.setName("Predictions for n= "+newValue);
            seriesPredictions.nodeProperty().get().setStyle("-fx-stroke: green");
            lineChart.getData().add(seriesPredictions);
        };

        //ComboBox
        ComboBox<String> comboBoxPredictions = new ComboBox<>();

        predictionsMap.keySet().forEach(i -> comboBoxPredictions.getItems().add(Integer.toString(i)));
        comboBoxPredictions.valueProperty().addListener(changeListener1);
        GridPane.setConstraints(comboBoxPredictions, 1, 3);
        gridPane.getChildren().add(comboBoxPredictions);


        //Button
        Button retButton = new Button("Enter new Data");
        GridPane.setConstraints(retButton, 1, 30);
        gridPane.getChildren().add(retButton);
        Button quitButton = new Button("Quit");
        GridPane.setConstraints(quitButton, 1, 31);
        gridPane.getChildren().add(quitButton);


        retButton.setOnAction(e -> {
            Main.launch();
            stage.close();
        });
        quitButton.setOnAction(e -> {
            stage.close();
            StartBox.closeStage();
        });


        //Scene
        Scene scene = new Scene(gridPane, 1200, 800);
        stage.setTitle("LR-Program");
        stage.setScene(scene);
        stage.show();

    }


    private static void initializeSeries(double[] periodArray){
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        for (int i = 0; i < periodArray.length; i++)
            series.getData().add(new XYChart.Data<>(i + 1, periodArray[i]));

        series.setName("Need");
        lineChart.getData().add(series);
    }

}
