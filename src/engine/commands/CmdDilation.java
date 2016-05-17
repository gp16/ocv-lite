package engine.commands;

import engine.AbstractCommand;
import engine.Engine;
import engine.Parameter;
import engine.Type;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class CmdDilation extends AbstractCommand {

    @Override
    protected Parameter[] getParamsOnce() {
        return new Parameter[] {
            new Parameter("source", Type.MAT_ID, 1, null,"Image name to get from memory", false, false),
            new Parameter("destination", Type.MAT_ID, 1, null,"Image name to save into memory", false, false),
            new Parameter("size", Type.INT, 1, 10,"Morph rect dilation size", false, false)
        };
    }

    @Override
    protected Object executeSafe() {
        String source = getArgImgId("source", 0);
        String destination = getArgImgId("destination", 0);
        int dilation_size = getArgInt("size", 0);
        
        Mat sourceMat = Engine.getInstance().getImage(source);
        Mat destinationMat = new Mat();
        
        Mat element = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new  Size(2*dilation_size + 1, 2*dilation_size+1));
        Imgproc.dilate(sourceMat, destinationMat, element);
        Engine.getInstance().allocImage(destination, destinationMat);
        
        return null;
    }

    @Override
    public String getName() {
        return "Dilate";
    }

    @Override
    public String getMan() {
     return "Dilation convolutes an image(A) with some kernel(B)"
            + ", which can have any shape or size, usually a square or circle.";
    }

}
