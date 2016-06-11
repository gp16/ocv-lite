package engine.commands;

import engine.AbstractCommand;
import engine.Engine;
import engine.Parameter;
import engine.Type;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class CmdBilateralFilter extends AbstractCommand {

    @Override
    protected Parameter[] getParamsOnce() {
        return new Parameter[] {
            new Parameter("Source", Type.MAT_ID, 1, null, "Source 8-bit or "
                    + "floating-point, 1-channel or 3-channel image.", false, false),
            new Parameter("Destination", Type.MAT_ID, 1, null, "Destination "
                    + "image of the same size and type as src.", false, false),
            new Parameter("Dia", Type.INT, 5, 10, "Diameter of each pixel"
                    + " neighborhood that is used during filtering.", false, false),
            new Parameter("sigmaColor", Type.INT, 10, 120, "Filter sigma"
                    + " in the color space.", false, false),
            new Parameter("sigmaSpace", Type.INT, 10, 120, "Filter sigma"
                    + "in the coordinate space.", true, false),
            new Parameter("borderType", Type.INT, 1, 5, "a borderType", false, false)
        };
    }

    @Override
    protected Object executeSafe() {
        String source = getArgImgId("Source", 0);
        String dest = getArgImgId("Destination", 0);
        int dia = getArgInt("Dia", 0);
        double sColor = getArgInt("sigmaColor", 0);
        double sSpace = getArgInt("sigmaSpace", 0);
        int border = getArgInt("borderType", 0);
        
        Mat src = Engine.getInstance().getImage(source);
        Mat dst = new Mat();
        
        Imgproc.bilateralFilter(src, dst, dia, sColor, sSpace, border);
        Engine.getInstance().allocImage(dest, dst);
        
        
        return null;
    }

    @Override
    public String getName() {
        return "bilateralFilter";
    }

    @Override
    public String getMan() {
        return "Applies the bilateral filter to an image.";
    }
    
}
