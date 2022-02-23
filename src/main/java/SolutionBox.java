import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.TextArea;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import javafx.embed.swing.SwingFXUtils;

import static javafx.scene.text.Font.font;
import static javafx.scene.text.FontPosture.ITALIC;
import static javafx.scene.text.FontWeight.BOLD;


public class SolutionBox {


    private static LineChart<Number, Number> lineChart;
    private static final HashMap<CheckBox, XYChart.Series<Number, Number>> seriesHashMap = new HashMap<>();
    // private static boolean alreadyInitialized = false;



    /**
     * Displays layout of the SolutionBox, displays solution
     *
     * @param finalString finalString which is being entered in the TextArea
     * @param needArray contains the needs of each period
     * @param predictionsMap contains the predictions, for each n
     */
    public static void display(String finalString, double[] needArray, Multimap<Integer, Double> predictionsMap) {

        CustomGridCheckBox customGridCheckBox = new CustomGridCheckBox(predictionsMap);

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
        lineChart.setTitle("Visualization of actual need and predictions");
        lineChart.setPrefWidth(1000);
        lineChart.setPrefHeight(800);

        //Need series is being drawn
        initializeSeries(needArray);

        //The checkBoxes are being placed on the GridPane
        GridPane.setConstraints(customGridCheckBox.getPane(), 1, 3);
        GridPane.setConstraints(lineChart, 1, 4);


        //Buttons
//        Button showPredictions = new Button("Show all predictions");
//        GridPane.setConstraints(showPredictions, 1, 5);
        Button retButton = new Button("Enter new Data");
        GridPane.setConstraints(retButton, 1, 6);
        Button quitButton = new Button("Quit");
        GridPane.setConstraints(quitButton, 1, 7);
        Button printButton = new Button("Convert Chart to PDF");
        GridPane.setConstraints(printButton, 1, 5);


        //This code will maybe be used some time
//        showPredictions.setOnAction(e -> {
//            lineChart.getData().clear();
//            initializeSeries(needArray);
//
//            predictionsMap.keySet().forEach(i -> {
//                seriesAllPredictions = new XYChart.Series<>();
//                for (int j = 0; j < new ArrayList<>(predictionsMap.get(i)).size(); j++)
//                    seriesAllPredictions.getData().add(new XYChart.Data<>(Math.abs(new ArrayList<>(predictionsMap.get(i)).size()
//                            - needArray.length) + j + 1, new ArrayList<>(predictionsMap.get(i)).get(j)));
//
//                seriesAllPredictions.setName("Predictions for n= " + i);
//                lineChart.getData().add(seriesAllPredictions);
//            });
//            alreadyInitialized = true;
//
//        });

        //iterate through the HashMap of customGridCheckBox
        customGridCheckBox.getCheckBoxHashMap().keySet().forEach(checkBoxEntry -> {
            //for every item within the map of customGridCheckBox a Listener is being added
            customGridCheckBox.getCheckBoxHashMap().get(checkBoxEntry).selectedProperty().addListener((observableValue, oldValue, newValue) -> {
                // if the checkBox is selected the individual series is added to the chart
                if (newValue) {
//                    if (alreadyInitialized) {
//                        lineChart.getData().clear();
//                        initializeSeries(needArray);
//                        alreadyInitialized = false;
//
//                    }
                    // new Series is being created
                    XYChart.Series<Number, Number> seriesPredictions = new XYChart.Series<>();
                    // A temporary Map is being created to operate on the values of predictionsMap
                    Multimap<Integer, Double> tempMap = ArrayListMultimap.create(predictionsMap);
                    // all entries, which don't fit checkBoxEntry will be removed from tempMap
                    tempMap.keySet().removeIf(i -> !Objects.equals(i, checkBoxEntry));
                    for (int i = 0; i < new ArrayList<>(tempMap.values()).size(); i++)
                        seriesPredictions.getData().add(new XYChart.Data<>(Math.abs(new ArrayList<>(tempMap.values()).size()
                                - needArray.length) + i + 1, new ArrayList<>(tempMap.values()).get(i)));

                    seriesPredictions.setName("Predictions for n= " + checkBoxEntry);
                    // integer value and series are added to seriesHashMap
                    seriesHashMap.put(customGridCheckBox.getCheckBoxHashMap().get(checkBoxEntry), seriesPredictions);
                    // new Series is being added to the chart
                    lineChart.getData().add(seriesPredictions);
                    // if the checkBox is not selected the individual series is removed from the chart
                } else
                    lineChart.getData().remove(seriesHashMap.get(customGridCheckBox.getCheckBoxHashMap().get(checkBoxEntry)));
            });
        });


        //TODO: Fix Error in this button
        retButton.setOnAction(e -> {
          StartBox.display(new Stage());
            stage.close();
        });
        // if the user wants to quit, the stage is closed
        quitButton.setOnAction(e -> stage.close());
        printButton.setOnAction(e -> {

            //User selects the path for the Screenshot
            WritableImage nodeShot = lineChart.snapshot(new SnapshotParameters(), null);
            File tempFile = new File("chart.png");
            //selects path for pdf
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save pdf");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF files (*.pdf)","*.pdf"));


            try {
                ImageIO.write(SwingFXUtils.fromFXImage(nodeShot, null), "png", tempFile);
            } catch (IOException ignored) {

            }

            PDDocument doc = new PDDocument();
            PDPage page = new PDPage();
            PDImageXObject pdImage;
            PDPageContentStream content;
            try {
                //pdf file is being created from temporary file
                pdImage = PDImageXObject.createFromFile("chart.png", doc);
                content = new PDPageContentStream(doc, page);
                content.drawImage(pdImage, 50, 50, 550, 550);
                content.close();
                doc.addPage(page);
                doc.save(fileChooser.showSaveDialog(stage).getPath());
                doc.close();
                tempFile.delete();
            } catch (IOException ignored) {

            }


        });


        //Scene
        gridPane.getChildren().addAll(mainText, nText, textArea, lineChart, printButton, retButton, quitButton, customGridCheckBox.getPane());
        Scene scene = new Scene(gridPane, 1200, 800);
        scene.getStylesheets().add("styles/style.css");
        stage.setTitle("LR-Program");
        stage.setScene(scene);
        stage.show();

    }


    private static void initializeSeries(double[] needArray) {
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        for (int i = 0; i < needArray.length; i++)
            series.getData().add(new XYChart.Data<>(i + 1, needArray[i]));

        series.setName("Actual Need");
        lineChart.getData().add(series);
    }

}
