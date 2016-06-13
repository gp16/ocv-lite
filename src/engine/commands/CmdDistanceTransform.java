package engine.commands;

import engine.AbstractCommand;
import engine.Engine;
import engine.Parameter;
import engine.Type;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class CmdDistanceTransform extends AbstractCommand {
    
    @Override
    protected Parameter[] getParamsOnce() {
        return new Parameter[]{
            new Parameter("Source", Type.MAT_ID, 1, null, "8-bit, single-channel"
                    + " (binary) source image.", false, false),
            new Parameter("Destination",Type.MAT_ID, 1, null, "Output image"
                    + " with calculated distances.", false, false),
            new Parameter("Distance Type", Type.INT, 0, 2, "Type of distance. "
                    + "It can be CV_DIST_L1 = 1, CV_DIST_L2 = 2 , or CV_DIST_C = 0",
                    false, false),
            new Parameter("Mask Size", Type.INT, 1, 5, "Size of the distance"
                    + " transform mask. It can be 3, 5,", false, false)
        };
    }

    @Override
    protected Object executeSafe() {
        String Src = getArgImgId("Source", 0);
        String dest = getArgImgId("Destination", 0);
        int distanceType = getArgInt("Distance Type", 0);
        int maskSize = getArgInt("Mask Size",0);
        
        Mat src = Engine.getInstance().getImage(Src);
        Mat dst = new Mat();
        
        Imgproc.distanceTransform(src, dst, distanceType, maskSize);
        Engine.getInstance().allocImage(dest, dst);
        return null;
    }

    @Override
    public String getName() {
        return "distanceTransform";
    }

    @Override
    public String getMan() {
        return "Calculates the distance to the closest zero "
                + "pixel for each pixel of the source image.\n";
    }
}
