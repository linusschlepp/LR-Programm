import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
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


    private static LineChart<Number, Number> lineChart;
    private static XYChart.Series<Number, Number> seriesPredictions;
    private static XYChart.Series<Number, Number> seriesAllPredictions;


    //TODO: Do some general refactoring of the code, make it prettier
    //TODO: Add comments
    public static void display(String finalString, double[] periodArray, List<Double> b0List, List<Double> b1List, Multimap<Integer, Double> predictionsMap) {

        //Stage
        Stage stage = new Stage();


        //Layout
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        //mainText
        Text mainText = new Text(20, 50, "Data:");
        mainText.setFont(font("Calibri", BOLD, ITALIC, 18));
        GridPane.setConstraints(mainText, 1, 0);


        //nText
        Text nText = new Text(20, 50, "Select n:");
        nText.setFont(font("Calibri", BOLD, ITALIC, 18));
        GridPane.setConstraints(nText, 1, 2);

        //TextArea
        TextArea textArea = new TextArea();
        textArea.setPrefHeight(500);
        textArea.setPrefWidth(700);
        textArea.setText(finalString);
        GridPane.setConstraints(textArea, 1, 1);



        //LineChart
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Period");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Pieces");
        lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setPrefWidth(1000);
        lineChart.setPrefHeight(800);

        initializeSeries(periodArray);


        GridPane.setConstraints(lineChart, 1, 4);

        ChangeListener<String> changeListener1 = (observable, oldValue, newValue) -> {

            if (seriesAllPredictions != null) {
                lineChart.getData().clear();
                initializeSeries(periodArray);
            }
            lineChart.getData().remove(seriesPredictions);
            seriesPredictions = new XYChart.Series<>();
            Multimap<Integer, Double> tempMap = ArrayListMultimap.create(predictionsMap);
            tempMap.keySet().removeIf(i -> i != Integer.parseInt(newValue));
            for (int i = 0; i < new ArrayList<>(tempMap.values()).size(); i++)
                seriesPredictions.getData().add(new XYChart.Data<>(Math.abs(new ArrayList<>(tempMap.values()).size()
                        - periodArray.length) + i + 1, new ArrayList<>(tempMap.values()).get(i)));


            seriesPredictions.setName("Predictions for n= " + newValue);
            lineChart.getData().add(seriesPredictions);

        };

        //ComboBox
        ComboBox<String> comboBoxPredictions = new ComboBox<>();
        predictionsMap.keySet().forEach(i -> comboBoxPredictions.getItems().add(Integer.toString(i)));
        comboBoxPredictions.valueProperty().addListener(changeListener1);
        GridPane.setConstraints(comboBoxPredictions, 1, 3);


        //Buttons
        Button showPredictions = new Button("Show all predictions");
        GridPane.setConstraints(showPredictions, 1, 5);
        Button retButton = new Button("Enter new Data");
        GridPane.setConstraints(retButton, 1, 6);
        Button quitButton = new Button("Quit");
        GridPane.setConstraints(quitButton, 1, 7);


        showPredictions.setOnAction(e -> {
            lineChart.getData().clear();
            initializeSeries(periodArray);

           predictionsMap.keySet().forEach(i -> {
                seriesAllPredictions = new XYChart.Series<>();
                for (int j = 0; j < new ArrayList<>(predictionsMap.get(i)).size(); j++)
                    seriesAllPredictions.getData().add(new XYChart.Data<>(Math.abs(new ArrayList<>(predictionsMap.get(i)).size()
                            - periodArray.length) + j + 1, new ArrayList<>(predictionsMap.get(i)).get(j)));

                seriesAllPredictions.setName("Predictions for n= " + i);
                lineChart.getData().add(seriesAllPredictions);
            });

        });


        //TODO: Fix Error in this button
        retButton.setOnAction(e -> {
            StartBox.display(new Stage());
            stage.close();
            StartBox.closeStage();
        });
        quitButton.setOnAction(e -> {
            stage.close();
            StartBox.closeStage();
        });


        //Scene
        gridPane.getChildren().addAll(mainText, nText, textArea, lineChart,comboBoxPredictions, showPredictions, retButton, quitButton);
        Scene scene = new Scene(gridPane, 1200, 800);
        scene.getStylesheets().add("styles/style.css");
        stage.setTitle("LR-Program");
        stage.setScene(scene);
        stage.show();

    }


    private static void initializeSeries(double[] periodArray) {
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        for (int i = 0; i < periodArray.length; i++)
            series.getData().add(new XYChart.Data<>(i + 1, periodArray[i]));

        series.setName("Need");
        lineChart.getData().add(series);
    }

}
