package engine.commands;

import engine.AbstractCommand;
import engine.Engine;
import engine.Parameter;
import engine.Type;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;


public class CmdCornerHarris extends AbstractCommand{

    @Override
    protected Parameter[] getParamsOnce() {
        return new Parameter[] {
            new Parameter("Source", Type.MAT_ID, 1, null,
                    "Input single-channel 8-bit or floating-point image.", false, false),
            new Parameter("Destination", Type.MAT_ID, 1, null,
                    "Image to store the Harris detector responses.", false, false),
            new Parameter("BlockSize", Type.INT, 1, Integer.MAX_VALUE,
                    "Neighborhood size.", false, false),
            new Parameter("ksize", Type.INT, 1, Integer.MAX_VALUE, 
                    "Aperture parameter for the Sobel() operator.",false, false),
            new Parameter("k", Type.INT, 0, 10,
                    "Harris detector free parameter.", false, false)
        };
    }

    @Override
    protected Object executeSafe() {
       String Source = getArgImgId("Source", 0);
       String Destination = getArgImgId("Destination", 0);
       int blockSize = getArgInt("BlockSize", 0);
       int apertureSize = getArgInt("ksize", 0);
       double k = getArgInt("k",0);
       
       Mat src = Engine.getInstance().getImage(Source);
       Imgproc.cvtColor(src, src, Imgproc.COLOR_RGB2GRAY);
       Mat dst = new Mat();
       
       Imgproc.cornerHarris(src, dst, blockSize, apertureSize, k);
       Engine.getInstance().allocImage(Destination, dst);
       return null;
    }

    @Override
    public String getName() {
        return "cornerHarris";
    }

    @Override
    public String getMan() {
        return "Harris edge detector.";
    }
    
}
