package engine.commands;

import engine.AbstractCommand;
import engine.Engine;
import engine.Parameter;
import engine.Type;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;


public class CmdScharr extends AbstractCommand {

    @Override
    protected Parameter[] getParamsOnce() {
        return new Parameter[]{
           new Parameter("Source", Type.MAT_ID, 1, null, "input image.", false, false),
            new Parameter("Destination",Type.MAT_ID, 1, null, "output image", false, false),
            new Parameter("ddepth",Type.INT, -1, Integer.MAX_VALUE, "the output"
                    + " image depth (-1 to use src.depth()).", false, false),
            new Parameter("dx", Type.INT, 0, Integer.MAX_VALUE, "order"
                    + " of the derivative x.", false,false),
            new Parameter("dy", Type.INT, 0, Integer.MAX_VALUE, "order "
                    + "of the derivative y.", false ,false)
        };
    }

    @Override
    protected Object executeSafe() {
        String Src = getArgImgId("Source", 0);
        String dest = getArgImgId("Destination", 0);
        int ddepth = getArgInt("ddepth",0);
        int dx = getArgInt("dx", 0);
        int dy = getArgInt("dy", 0);
        
        Mat src = Engine.getInstance().getImage(Src);
        Mat dst = new Mat();
        
        Imgproc.Scharr(src, dst, ddepth, dx, dy);
        Engine.getInstance().allocImage(dest, dst);
        return null;
    }

    @Override
    public String getName() {
        return "scharr";
    }

    @Override
    public String getMan() {
        return "Calculates the first x- or y- image "
                + "derivative using Scharr operator.";
    }
    
}
