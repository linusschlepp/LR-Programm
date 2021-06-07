import javafx.scene.control.TextField;

public class ConvertArrays {


    public static double[] convertArray(TextField[] tArray) {

        double[] dArray = new double[tArray.length];

        for (int i = 0; i < tArray.length; i++) {
            dArray[i] = Double.parseDouble((tArray[i].getText()));

        }

        return dArray;
    }

}
