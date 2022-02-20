import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.scene.Scene;
import javafx.scene.chart.Axis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import javafx.scene.control.TextArea;

import java.util.ArrayList;
import java.util.List;


public class SolutionBox {

    public static void display(String finalString, double[] periodArray, List<Double> b0List, List<Double> b1List, Multimap<Integer, Double> predictionsMap) {

        //Stage
        Stage stage = new Stage();


        //Layout
        GridPane gridPane = new GridPane();


        //TextArea
        TextArea textArea = new TextArea();
        //  textArea.setPrefHeight(300);
        textArea.setPrefWidth(700);
        textArea.setText(finalString);
        GridPane.setConstraints(textArea, 1, 1);
        gridPane.getChildren().add(textArea);


        //LineChart
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Period");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Pieces");
        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        for (int i = 0; i < periodArray.length; i++)
            series.getData().add(new XYChart.Data<>(i + 1, periodArray[i]));

        lineChart.getData().add(series);
        lineChart.setCreateSymbols(false);
        gridPane.getChildren().add(lineChart);
        series.setName("Need");
        GridPane.setConstraints(lineChart, 1, 8);


        //TODO: This needs to be fixed and finished
        ChangeListener<String> changeListener1 = (observable, oldValue, newValue) -> {
            XYChart.Series<Number, Number> seriesPredictions = new XYChart.Series<>();
            lineChart.getData().remove(seriesPredictions);
            Multimap<Integer, Double> tempMap = ArrayListMultimap.create(predictionsMap);
            tempMap.keySet().removeIf(i -> i != Integer.parseInt(newValue));
            for (int i = 0; i < periodArray.length; i++) {
                try {
                    System.out.println(new ArrayList<>(tempMap.values()).get(i));
                    seriesPredictions.getData().add(new XYChart.Data<>(i + 1, new ArrayList<>(tempMap.values()).get(i)));
                } catch (IndexOutOfBoundsException ignored) {

                }

            }
            seriesPredictions.setName("Predictions for n= "+newValue);
            lineChart.getData().add(seriesPredictions);
        };

        //ComboBox
        ComboBox<String> comboBoxPredictions = new ComboBox<>();
        for (Integer i  : predictionsMap.keySet())
            comboBoxPredictions.getItems().add(Integer.toString(i));
        comboBoxPredictions.valueProperty().addListener(changeListener1);
        GridPane.setConstraints(comboBoxPredictions, 1, 2);
        gridPane.getChildren().add(comboBoxPredictions);


        //Button
        Button retButton = new Button("Enter new Data");
        GridPane.setConstraints(retButton, 1, 30);
        gridPane.getChildren().add(retButton);
        Button quitButton = new Button("Quit");
        GridPane.setConstraints(quitButton, 3, 30);
        gridPane.getChildren().add(quitButton);


        retButton.setOnAction(e -> {
            Main.launch();
            stage.close();
        });
        quitButton.setOnAction(e -> {
            stage.close();
        });


        //Scene
        Scene scene = new Scene(gridPane, 1200, 800);
        stage.setTitle("LR-Program");
        stage.setScene(scene);
        stage.show();

    }

}
