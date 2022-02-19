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
import java.util.HashMap;
import java.util.List;

public class SolutionBox {

    public static void display(String finalString, double[] periodArray, List<Double> b0List, List<Double> b1List, HashMap<Double, Integer> predictionsMap) {

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
        yAxis.setLabel("Piece");
        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        for (int i = 0; i < periodArray.length; i++)
            series.getData().add(new XYChart.Data<>(i + 1, periodArray[i]));
        XYChart.Series<Number, Number> seriesPredictions = new XYChart.Series<>();
//        for (int i = 0; i < periodArray.length; i++) {
//            try {
//              //  series1.getData().add(new XYChart.Data<>(i + 1, b1List.get(i) * (i + 1) + b0List.get(i)));
//                seriesPredictions.getData().add(new XYChart.Data<>(i + 1, new ArrayList<>(predictionsMap.keySet()).get(i)));
//            } catch (IndexOutOfBoundsException e){
//                seriesPredictions.getData().add(new XYChart.Data<>(i + 1, 0));
//            }
//
//        }

        lineChart.getData().add(series);
        lineChart.setCreateSymbols(false);
        gridPane.getChildren().add(lineChart);
        series.setName("Need");
        GridPane.setConstraints(lineChart, 1, 8);


        //TODO: This needs to be fixed and finished
        ChangeListener<String> changeListener1 = (observable, oldValue, newValue) -> {
            lineChart.getData().remove(seriesPredictions);
            for (int i = 0; i < periodArray.length; i++) {
                try {
                    //  series1.getData().add(new XYChart.Data<>(i + 1, b1List.get(i) * (i + 1) + b0List.get(i)));
                    seriesPredictions.getData().add(new XYChart.Data<>(i + 1, new ArrayList<>(predictionsMap.keySet()).get(i)));
                } catch (IndexOutOfBoundsException e) {
                    seriesPredictions.getData().add(new XYChart.Data<>(i + 1, 0));
                }

            }
            lineChart.getData().add(seriesPredictions);
        };

        //ComboBox
        ComboBox<String> comboBoxPredictions = new ComboBox<>();
        for (Double d : predictionsMap.keySet())
            comboBoxPredictions.getItems().add(Integer.toString(predictionsMap.get(d)));
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
