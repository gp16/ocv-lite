package engine.commands;

import engine.AbstractCommand;
import engine.Engine;
import engine.Parameter;
import engine.Type;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class CmdErosion extends AbstractCommand {

    @Override
    protected Parameter[] getParamsOnce() {
        return new Parameter[] {
            new Parameter("source", Type.MAT_ID, 1, null,"Image name to get from memory", false, false),
            new Parameter("destination", Type.MAT_ID, 1, null,"Image name to save into memory", false, false),
            new Parameter("size", Type.INT, 1, 10,"Morph rect erosion size", false, false)
        };
    }

    @Override
    protected Object executeSafe() {
        String source = getArgImgId("source", 0);
        String destination = getArgImgId("destination", 0);
        int erosion_size = getArgInt("size", 0);
        
        Mat sourceMat = Engine.getInstance().getImage(source);
        Mat destinationMat = new Mat();
        
        Mat element = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new  Size(2*erosion_size + 1, 2*erosion_size+1));
        Imgproc.erode(sourceMat, destinationMat, element);
        Engine.getInstance().allocImage(destination, destinationMat);
        
        return null;
    }

    @Override
    public String getName() {
        return "Erosion";
    }

    @Override
    public String getMan() {
     return "Erosion computes a local minimum over the area of the kernel";
    }

}
