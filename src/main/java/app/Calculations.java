package app;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.apache.commons.lang3.ArrayUtils;
import java.util.ArrayList;
import java.util.Arrays;



public class Calculations {


    private final StringBuilder sb = new StringBuilder();
    private final Multimap<Integer, Double> predictionsMap = ArrayListMultimap.create();

    /**
     * Calculates the predictions, based on the needs, entered by the user
     *
     * @param nArray contains all the n's, which have been entered by the user
     * @param needArray contains all the needs, which have been entered by the user
     */
    Calculations(double[] nArray, double[] needArray) {

        sb.append("Entered Needs:").append("\n");
        printList(new ArrayList<>(Arrays.asList(ArrayUtils.toObject(needArray))));


        for (double v : nArray) allPredictions(needArray, (int) v);

        SolutionBox.display(sb.toString(), needArray, predictionsMap);

    }

    /**
     * Adds all parameters to list
     *
     * @param y needs
     * @param n n-parameter
     */
    public void allPredictions(double[] y, int n) {
        int periods = y.length;
        if (n < 1 || n > periods) {
            sb.append("Check n value...");
            sb.append("\n");
        }
        ArrayList<Double> predictions = new ArrayList<>();
        for (int i = 0; i < n; i++)
            predictions.add(0.00); // Fill up list with n zeros.
        for (int i = 0; i < periods - n + 1; i++)
            predictions.add(nextPrediction(y, i, n));
        sb.append("Predictions for n=").append(n);
        sb.append("\n");
        printList(predictions);
        errors(y, predictions, n);
    }

    /**
     * Calculates next prediction
     *
     * @param y needs
     * @param start start-value
     * @param n n-parameter
     * @return next prediction
     */
    public double nextPrediction(double[] y, int start, int n) {
        double midpoint = midpoint(y, start, n);
        double k = 1, b_1n, b_1n_numerator = 0, b_1n_denominator = 0, b_0n;
        for (int i = 0; i < n; i++) {
            // Calculating numerator, denominator and sum up
            b_1n_numerator += (k - ((n + 1.0) / 2.0)) * (y[start] - midpoint);
            b_1n_denominator += Math.pow((k - ((n + 1.0) / 2.0)), 2);
            k++;
            start++; // Increment k and start for next period
        }
        b_1n = b_1n_numerator / b_1n_denominator; // Division to get slope of linear function. Value of b_1n
        b_0n = midpoint - b_1n * ((n + 1.0) / 2.0); // Calculating value of b_0n
        predictionsMap.put(n, (b_0n + b_1n * (n + 1)));
        return (b_0n + b_1n * (n + 1)); // Returning prediction for nex period.
    }

    /**
     * Calculates midpoint
     *
     * @param y contains needs
     * @param start start value
     * @param n n-parameter
     * @return midpoint (average)
     */
    public double midpoint(double[] y, int start, int n) {
        double sum = 0;
        for (int i = 0; i < n; i++) {
            sum += y[start];
            start++;
        }
        return sum / n;
    }

    /**
     * Calculates errors (needs-predictions)
     *
     * @param y needs
     * @param predictions calculated predictions
     * @param n parameter-n
     */
    public void errors(double[] y, ArrayList<Double> predictions, int n) {
        ArrayList<Double> errors = new ArrayList<>();
        for (int i = 0; i < n; i++)
            errors.add(0.00);
        for (int i = n; i < y.length; i++)
            errors.add(y[i] - predictions.get(i));
        sb.append("Error for n=").append(n);
        sb.append("\n");
        printList(errors);
        minimizeErrors(errors, n);
    }

    /**
     * Calculates squared sum of errors
     *
     * @param errors calculated errors
     * @param n parameter-n
     */
    public void minimizeErrors(ArrayList<Double> errors, int n) {
        double squaredSum = 0;
        for (double error : errors) {
            squaredSum += Math.pow(error, 2);
        }
        sb.append("Squared sum of errors for n=").append(n);
        sb.append("\n");
        sb.append(Math.round(squaredSum * 100.0) / 100.0);
        sb.append("\n");
    }

    /**
     * Works as basic toString-function to print needs as well as predictions in a pretty way
     *
     * @param predictions predictions, which have been calculated
     */
    public void printList(ArrayList<Double> predictions) {
        sb.append("[ ");
        for (int i = 0; i < predictions.size(); i++) {
            double roundedDouble = Math.round(predictions.get(i) * 100.0) / 100.0;
            if (i == predictions.size() - 1) {
                sb.append(roundedDouble).append(" ]");
            } else {
                sb.append(roundedDouble).append(" | ");
            }
        }
        sb.append("\n");
    }


}

