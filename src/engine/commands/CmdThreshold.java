package engine.commands;

import engine.AbstractCommand;
import engine.Engine;
import engine.Parameter;
import engine.Type;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class CmdThreshold extends AbstractCommand {
    
    @Override
    protected Parameter[] getParamsOnce() {
        return new Parameter[]{
            new Parameter("Source", Type.MAT_ID, 1, null, "input array (single-channel, 8-bit or 32-bit floating point).", false, false),
            new Parameter("Destination", Type.MAT_ID, 1, null, "output array of the same size and type as src.", false, false),
            new Parameter("Thresh", Type.INT, 1, 255, "threshold value.", false, false),
            new Parameter("Max", Type.INT, 1, 255, "maximum value to use with the THRESH_BINARY and THRESH_BINARY_INV thresholding types.", false, false),
            new Parameter("Type", Type.INT, 0, 4, "Threshold type : 0: Binary\n" +
                    "     1: Binary Inverted\n" +
                    "     2: Threshold Truncated\n" +
                    "     3: Threshold to Zero\n" +
                    "     4: Threshold to Zero Inverted", false, false)
                
        };
        
    }
    
    @Override
    protected Object executeSafe() {
        String Source = getArgImgId("Source", 0);
        String Destination = getArgImgId("Destination", 0);
        double thresh = getArgInt("Thresh", 0);
        double max = getArgInt("Max", 0);
        int type = getArgInt("Type", 0);
        
        Mat Source_Mat = Engine.getInstance().getImage(Source);
        Mat Destination_Mat = new Mat(Source_Mat.rows(),Source_Mat.cols(),Source_Mat.type());
        
        Imgproc.threshold(Source_Mat, Destination_Mat, thresh, max, type);
        Engine.getInstance().allocImage(Destination, Destination_Mat);
        return null;
    }
    
    @Override
    public String getName() {
        return "threshold";
    }
    
    @Override
    public String getMan() {
        return "Applies a fixed-level threshold to each array element.";
    }
    
}
